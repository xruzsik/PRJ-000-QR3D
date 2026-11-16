#!/usr/bin/env python3
"""Pack a .gltf whose buffers are embedded data URIs into a binary .glb.

Quaternius ships glTF with base64 buffers; GLB stores the same bytes as a binary
chunk (25% smaller, no decoding on the phone). Geometry, materials and
animations are untouched. External buffer or image files are not supported.

Usage: python3 gltf_to_glb.py IN.gltf OUT.glb
"""
import base64
import json
import struct
import sys


def pad(data, fill):
    return data + fill * (-len(data) % 4)


def main(src, dst):
    with open(src) as f:
        gltf = json.load(f)

    for image in gltf.get("images", []):
        if "uri" in image and not image["uri"].startswith("data:"):
            sys.exit(f"{src}: external image {image['uri']!r} is not supported")

    # Concatenate all buffers into the single GLB binary chunk, rebasing views.
    binary = b""
    starts = []
    for buffer in gltf.get("buffers", []):
        uri = buffer.get("uri", "")
        if not uri.startswith("data:"):
            sys.exit(f"{src}: external buffer {uri!r} is not supported")
        data = base64.b64decode(uri.split(",", 1)[1])
        if len(data) != buffer["byteLength"]:
            sys.exit(f"{src}: buffer is {len(data)} bytes, header says {buffer['byteLength']}")
        starts.append(len(binary))
        binary += pad(data, b"\0")
    for view in gltf.get("bufferViews", []):
        view["byteOffset"] = view.get("byteOffset", 0) + starts[view["buffer"]]
        view["buffer"] = 0
    gltf["buffers"] = [{"byteLength": len(binary)}]

    json_chunk = pad(json.dumps(gltf, separators=(",", ":")).encode(), b" ")
    total = 12 + 8 + len(json_chunk) + 8 + len(binary)
    with open(dst, "wb") as f:
        f.write(struct.pack("<4sII", b"glTF", 2, total))
        f.write(struct.pack("<I4s", len(json_chunk), b"JSON") + json_chunk)
        f.write(struct.pack("<I4s", len(binary), b"BIN\0") + binary)


if __name__ == "__main__":
    if len(sys.argv) != 3:
        sys.exit(__doc__)
    main(sys.argv[1], sys.argv[2])
