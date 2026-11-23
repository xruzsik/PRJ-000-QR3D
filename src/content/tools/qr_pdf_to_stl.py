#!/usr/bin/env python3
"""Turn the QR marker sheets made by `qrsheet` (content/markers/*.pdf) into
3D-printable STL plates, one per code.

The dark modules are read straight from the PDF (they are vector rectangles),
so the printed plate carries exactly the same code as the paper sheet.

Each code gives three files in OUT_DIR:

  qr-<id>.stl          whole plate in one piece: white base + raised modules.
                       For a single-nozzle printer: pause / change filament
                       (white -> black) at the height of the base.
  qr-<id>-base.stl     base only        } the same plate split in two parts,
  qr-<id>-modules.stl  dark modules only } for multi-colour printers (load both
                                         as one object with two parts).
  qr-<id>-preview.png  top view, to scan off the screen before printing.

Sizes are in millimetres. The QR symbol keeps its printed edge (100 mm on
animals.pdf), which must stay as `qrSizeMeters` in route.json: the app works
out distances from it.

Usage:
  python qr_pdf_to_stl.py SHEET.pdf OUT_DIR [--base 2.0] [--relief 0.8] [--quiet 4]
"""
import argparse
import pathlib
import re
import struct
import sys
import zlib

PT_TO_MM = 25.4 / 72


def read_pages(pdf: bytes):
    """Yields (payload, module_count, symbol_mm, dark_rects_mm) per page."""
    for m in re.finditer(rb"stream\n(.*?)\nendstream", pdf, re.S):
        content = m.group(1).decode("latin-1")
        payload = re.search(r"\(ID: (.*?)\) Tj", content)
        size = re.search(r"\(Black square edge = ([\d.]+) mm, (\d+)x\d+ modules\) Tj", content)
        if not payload or not size:
            continue
        # Dark modules: the rectangles before the first fill.
        fill = content.index("\nf\n")
        rects = [tuple(float(v) * PT_TO_MM for v in r.groups())
                 for r in re.finditer(r"([\d.-]+) ([\d.-]+) ([\d.-]+) ([\d.-]+) re", content[:fill])]
        yield payload.group(1), int(size.group(2)), float(size.group(1)), rects


def module_grid(n: int, symbol_mm: float, rects):
    """Rectangles (PDF coordinates, y up) -> n x n booleans, row 0 at the top."""
    module = symbol_mm / n
    left = min(r[0] for r in rects)
    top = max(r[1] + r[3] for r in rects)
    grid = [[False] * n for _ in range(n)]
    for x, y, w, h in rects:
        col = round((x - left) / module)
        row = round((top - (y + h)) / module)
        for c in range(col, col + round(w / module)):
            for r in range(row, row + round(h / module)):
                grid[r][c] = True
    # Sanity checks: the three finder patterns (7x7 dark rings) must be there.
    for r0, c0 in ((0, 0), (0, n - 7), (n - 7, 0)):
        ring = [grid[r0 + i][c0 + j] for i in range(7) for j in range(7) if i in (0, 6) or j in (0, 6)]
        if not all(ring):
            raise ValueError("finder pattern not found: the PDF does not look like a qrsheet page")
    return grid


def prism_mesh(cells, z_of):
    """Closed surface of a set of grid cells, each an upright box.

    cells: {(col, row): None}, row growing towards -y (row 0 at the top).
    z_of(cell) -> (z0, z1). Neighbouring cells share their bottom z0; where a
    neighbour is lower, only the step between the two tops gets a wall.
    Returns triangles as ((x, y, z) * 3) with counter-clockwise outer faces.
    """
    tris = []

    def quad(a, b, c, d):  # counter-clockwise seen from outside
        tris.append((a, b, c))
        tris.append((a, c, d))

    for (c, r) in cells:
        z0, z1 = z_of((c, r))
        x0, x1 = c, c + 1
        y0, y1 = -(r + 1), -r
        quad((x0, y0, z1), (x1, y0, z1), (x1, y1, z1), (x0, y1, z1))  # top
        quad((x0, y0, z0), (x0, y1, z0), (x1, y1, z0), (x1, y0, z0))  # bottom
        # Walls: (neighbour, edge from p to q seen from outside, left to right)
        for nb, p, q in (
            ((c, r + 1), (x0, y0), (x1, y0)),  # south (-y)
            ((c + 1, r), (x1, y0), (x1, y1)),  # east (+x)
            ((c, r - 1), (x1, y1), (x0, y1)),  # north (+y)
            ((c - 1, r), (x0, y1), (x0, y0)),  # west (-x)
        ):
            lo = z_of(nb)[1] if nb in cells else z0
            if lo < z1:
                quad((p[0], p[1], lo), (q[0], q[1], lo), (q[0], q[1], z1), (p[0], p[1], z1))
    return tris


def write_stl(path: pathlib.Path, tris, scale: float, name: str):
    def normal(a, b, c):
        u = [b[i] - a[i] for i in range(3)]
        v = [c[i] - a[i] for i in range(3)]
        n = (u[1] * v[2] - u[2] * v[1], u[2] * v[0] - u[0] * v[2], u[0] * v[1] - u[1] * v[0])
        length = sum(x * x for x in n) ** 0.5 or 1.0
        return tuple(x / length for x in n)

    with open(path, "wb") as f:
        f.write(name.encode("ascii", "replace")[:80].ljust(80, b" "))
        f.write(struct.pack("<I", len(tris)))
        for t in tris:
            pts = [(p[0] * scale, p[1] * scale, p[2]) for p in t]
            f.write(struct.pack("<3f", *normal(*pts)))
            for p in pts:
                f.write(struct.pack("<3f", *p))
            f.write(b"\0\0")


def write_preview_png(path: pathlib.Path, dark, size: int, px: int = 12):
    """Top view of the plate, black on white, for scanning off the screen."""
    width = size * px
    rows = b"".join(
        b"\0" + bytes(0 if (x // px, y // px) in dark else 255 for x in range(width))
        for y in range(width)
    )

    def chunk(kind, data):
        return struct.pack(">I", len(data)) + kind + data + struct.pack(">I", zlib.crc32(kind + data) & 0xFFFFFFFF)

    path.write_bytes(
        b"\x89PNG\r\n\x1a\n"
        + chunk(b"IHDR", struct.pack(">IIBBBBB", width, width, 8, 0, 0, 0, 0))
        + chunk(b"IDAT", zlib.compress(rows))
        + chunk(b"IEND", b"")
    )


def check_closed(tris):
    """Every edge must be used once in each direction (watertight, consistently oriented)."""
    edges = {}
    for a, b, c in tris:
        for e in ((a, b), (b, c), (c, a)):
            edges[e] = edges.get(e, 0) + 1
    bad = [e for e, k in edges.items() if edges.get((e[1], e[0]), 0) != k]
    return not bad


def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("pdf", type=pathlib.Path)
    ap.add_argument("out_dir", type=pathlib.Path)
    ap.add_argument("--base", type=float, default=2.0, help="white base thickness, mm (default 2.0)")
    ap.add_argument("--relief", type=float, default=0.8, help="height of the dark modules above the base, mm (default 0.8)")
    ap.add_argument("--quiet", type=int, default=4, help="white margin around the code, in modules (default 4, the QR minimum)")
    args = ap.parse_args()

    args.out_dir.mkdir(parents=True, exist_ok=True)
    pages = list(read_pages(args.pdf.read_bytes()))
    if not pages:
        sys.exit(f"{args.pdf}: no qrsheet pages found")

    for payload, n, symbol_mm, rects in pages:
        grid = module_grid(n, symbol_mm, rects)
        code_id = payload.split(":", 1)[-1]
        q = args.quiet
        size = n + 2 * q
        module = symbol_mm / n
        dark = {(c + q, r + q): None for r in range(n) for c in range(n) if grid[r][c]}
        plate = {(c, r): None for r in range(size) for c in range(size)}
        top = args.base + args.relief

        parts = {
            f"qr-{code_id}.stl": prism_mesh(plate, lambda cell: (0.0, top if cell in dark else args.base)),
            f"qr-{code_id}-base.stl": prism_mesh(plate, lambda cell: (0.0, args.base)),
            f"qr-{code_id}-modules.stl": prism_mesh(dark, lambda cell: (args.base, top)),
        }
        for name, tris in parts.items():
            if not check_closed(tris):
                sys.exit(f"{name}: mesh is not closed (bug)")
            write_stl(args.out_dir / name, tris, module, f"{payload} {name}")
        write_preview_png(args.out_dir / f"qr-{code_id}-preview.png", dark, size)
        print(f"{payload}: {n}x{n} modules of {module:.3f} mm, symbol {symbol_mm:g} mm, "
              f"plate {size * module:.1f} x {size * module:.1f} x {top:g} mm -> qr-{code_id}*.stl")


if __name__ == "__main__":
    main()
