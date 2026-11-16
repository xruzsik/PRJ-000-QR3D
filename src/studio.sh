#!/usr/bin/env bash
# Launch the Android Studio from the local tools/ folder (see README.md) with all
# of its state kept inside this folder.
set -euo pipefail

source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/env.sh"

if [ ! -x "$QR3D_SRC/tools/android-studio/bin/studio.sh" ]; then
  echo "No local Android Studio in $QR3D_SRC/tools; open this folder in your own Studio instead." >&2
  exit 1
fi

CFG="$QR3D_SRC/tools/studio-config"

# idea.properties does not expand env vars, so regenerate it with absolute
# paths on every launch; this keeps the folder relocatable.
cat > "$STUDIO_PROPERTIES" <<EOF
idea.config.path=$CFG/config
idea.system.path=$CFG/system
idea.plugins.path=$CFG/plugins
idea.log.path=$CFG/log
EOF

# User-level JVM options, merged on top of the bundled bin/studio64.vmoptions
# (Studio also saves memory settings here): keep Java preferences out of ~/.java.
export STUDIO_VM_OPTIONS="$CFG/studio64.vmoptions"
touch "$STUDIO_VM_OPTIONS"
# Refresh the prefs path (folder may have moved) without dropping other lines.
sed -i '/^-Djava\.util\.prefs\.userRoot=/d' "$STUDIO_VM_OPTIONS"
echo "-Djava.util.prefs.userRoot=$CFG/java-prefs" >> "$STUDIO_VM_OPTIONS"

# Catch tools that ignore the settings above and fall back to XDG dirs
# (e.g. the Kotlin compiler daemon in ~/.local/share/kotlin).
export XDG_DATA_HOME="$CFG/xdg/data"
export XDG_CACHE_HOME="$CFG/xdg/cache"
export XDG_STATE_HOME="$CFG/xdg/state"
mkdir -p "$XDG_DATA_HOME" "$XDG_CACHE_HOME" "$XDG_STATE_HOME"

exec "$QR3D_SRC/tools/android-studio/bin/studio.sh" "$@"
