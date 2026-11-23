#!/usr/bin/env bash
# QR3D – aktualizuje obsah a spustí lokálnu stránku (Docusaurus) v prehliadači.
# Spustenie: dvojklik (Git Bash) alebo v Git Bash: ~/OneDrive/Desktop/QR3D-web.sh

REPO="/c/2026-2027/SMVIT/PRJ-000-QR3D"
PORT=3000
URL="http://localhost:$PORT/"
OPEN_URL="${URL}sk/sthdf/PRJ-000-QR3D/projects/7ds/"

# Git Bash inak prepisuje "/" a "/MIR" na Windows cesty
export MSYS_NO_PATHCONV=1

pause_exit() { echo; read -rp "Stlač Enter pre zatvorenie..."; exit "${1:-0}"; }

cd "$REPO" || { echo "❌ Nenájdený repozitár: $REPO"; pause_exit 1; }

# Server už beží? Stačí zosynchronizovať obsah (hot reload) a otvoriť prehliadač.
ALREADY_RUNNING=0
curl -s -o /dev/null "$URL" && ALREADY_RUNNING=1

# KNIFE prehľady (make knifes-overview) sa tu zámerne negenerujú –
# pri každom behu by prepísali GUID a autorov v sledovaných súboroch.

echo "🔄 1/2 Kopírujem content/docs → publishing/docusaurus/docs..."
# Náhrada za rsync --delete --exclude 'assets/' (rsync na Windows nie je)
robocopy "content\\docs" "publishing\\docusaurus\\docs" /MIR /XD assets /NFL /NDL /NJH /NJS /NP >/dev/null
RC=$?
if [ "$RC" -ge 8 ]; then
  echo "❌ Kopírovanie zlyhalo (robocopy kód $RC)."
  pause_exit 1
fi
echo "✅ Obsah aktualizovaný."

if [ "$ALREADY_RUNNING" = "1" ]; then
  echo "🌐 2/2 Server už beží – otváram $OPEN_URL"
  start "" "$OPEN_URL"
  pause_exit 0
fi

echo "🚀 2/2 Spúšťam server (prvé spustenie trvá chvíľu, zastavíš Ctrl+C)..."
(
  # Po naštartovaní servera otvor prehliadač
  for _ in $(seq 1 180); do
    sleep 2
    if curl -s -o /dev/null "$URL"; then start "" "$OPEN_URL"; break; fi
  done
) &

cd publishing/docusaurus || pause_exit 1
SITE_URL="http://localhost:$PORT" BASE_URL="/" npm start -- --port "$PORT" --no-open
pause_exit $?
