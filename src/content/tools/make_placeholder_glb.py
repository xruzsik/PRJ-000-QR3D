#!/usr/bin/env python3
"""Write a placeholder GLB: a 1 m cube resting on its origin (y = 0 at the
bottom), each face coloured by its axis so orientation is easy to read in AR:
+X red, +Y green, +Z blue, and darker shades for the negative faces.

Usage: python3 make_placeholder_glb.py OUT.glb
"""
import json
import struct
import sys

FACES = [
    # normal,     (u axis,     v axis),    colour
    ((1, 0, 0),  ((0, 0, -1), (0, 1, 0)), (0.90, 0.15, 0.15)),
    ((-1, 0, 0), ((0, 0, 1),  (0, 1, 0)), (0.45, 0.08, 0.08)),
    ((0, 1, 0),  ((1, 0, 0),  (0, 0, -1)), (0.15, 0.85, 0.20)),
    ((0, -1, 0), ((1, 0, 0),  (0, 0, 1)), (0.08, 0.40, 0.10)),
    ((0, 0, 1),  ((1, 0, 0),  (0, 1, 0)), (0.15, 0.35, 0.95)),
    ((0, 0, -1), ((-1, 0, 0), (0, 1, 0)), (0.08, 0.18, 0.45)),
]


def build_mesh():
    positions, normals, colors, indices = [], [], [], []
    for normal, (u, v), color in FACES:
        base = len(positions)
        for su, sv in ((-1, -1), (1, -1), (1, 1), (-1, 1)):
            p = [0.5 * (n + su * a + sv * b) for n, a, b in zip(normal, u, v)]
            p[1] += 0.5  # move the bottom face to y = 0
            positions.append(p)
            normals.append(normal)
            colors.append((*color, 1.0))
        indices += [base, base + 1, base + 2, base, base + 2, base + 3]
    return positions, normals, colors, indices


def pad(data, fill):
    return data + fill * (-len(data) % 4)


def main(out_path):
    positions, normals, colors, indices = build_mesh()
    count = len(positions)

    blobs = [
        b"".join(struct.pack("<3f", *p) for p in positions),
        b"".join(struct.pack("<3f", *n) for n in normals),
        b"".join(struct.pack("<4f", *c) for c in colors),
        b"".join(struct.pack("<H", i) for i in indices),
    ]
    views, offset = [], 0
    for i, blob in enumerate(blobs):
        target = 34963 if i == 3 else 34962  # ELEMENT_ARRAY_BUFFER / ARRAY_BUFFER
        views.append({"buffer": 0, "byteOffset": offset, "byteLength": len(blob), "target": target})
        offset += len(pad(blob, b"\0"))
    binary = b"".join(pad(blob, b"\0") for blob in blobs)

    gltf = {
        "asset": {"version": "2.0", "generator": "SMVIT-AR make_placeholder_glb.py"},
        "scene": 0,
        "scenes": [{"nodes": [0]}],
        "nodes": [{"mesh": 0, "name": "PlaceholderCube"}],
        "meshes": [{
            "name": "PlaceholderCube",
            "primitives": [{
                "attributes": {"POSITION": 0, "NORMAL": 1, "COLOR_0": 2},
                "indices": 3,
                "material": 0,
            }],
        }],
        "materials": [{
            "name": "VertexColour",
            "pbrMetallicRoughness": {"baseColorFactor": [1, 1, 1, 1], "metallicFactor": 0.0, "roughnessFactor": 0.8},
        }],
        "accessors": [
            {"bufferView": 0, "componentType": 5126, "count": count, "type": "VEC3",
             "min": [-0.5, 0.0, -0.5], "max": [0.5, 1.0, 0.5]},
            {"bufferView": 1, "componentType": 5126, "count": count, "type": "VEC3"},
            {"bufferView": 2, "componentType": 5126, "count": count, "type": "VEC4"},
            {"bufferView": 3, "componentType": 5123, "count": len(indices), "type": "SCALAR"},
        ],
        "bufferViews": views,
        "buffers": [{"byteLength": len(binary)}],
    }

    json_chunk = pad(json.dumps(gltf, separators=(",", ":")).encode(), b" ")
    total = 12 + 8 + len(json_chunk) + 8 + len(binary)
    with open(out_path, "wb") as f:
        f.write(struct.pack("<4sII", b"glTF", 2, total))
        f.write(struct.pack("<I4s", len(json_chunk), b"JSON") + json_chunk)
        f.write(struct.pack("<I4s", len(binary), b"BIN\0") + binary)


if __name__ == "__main__":
    if len(sys.argv) != 2:
        sys.exit(__doc__)
    main(sys.argv[1])
