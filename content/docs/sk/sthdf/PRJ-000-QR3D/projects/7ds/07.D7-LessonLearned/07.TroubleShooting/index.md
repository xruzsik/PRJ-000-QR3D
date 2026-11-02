---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.565621+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "449c109f-e0ea-4d7f-afb1-ba70357f5d1a"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "TroubleShooting"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Problémy, na ktoré sme narazili pri projekte QR3D, a ich riešenia."


# 👥 AUTHORSHIP ------------------------------------------------------

# Hlavný autor – z globálneho configu
author: "Roman Kazicka"

# Zoznam autorov – generuje skript
authors:
  - "Dániel Ruzsik"
  - "Lukáš Šebök"


# 🗂 CLASSIFICATION ---------------------------------------------------

# Nadradená kategória – môže doplniť používateľ
category: ""

# Typ dokumentu (guide, case, tutorial...) – používateľ (voliteľné)
type: ""

# Priorita (low/medium/high) – voliteľné
priority: ""

# Tagy – odporúča sa 2–6 tagov.
# Typy tagov:
#   - rámce: knife, 7ds, sdlc, q12
#   - účel: tutorial, guide, pattern, case-study
#   - téma: git, backup, ai, communication
#   - úroveň: beginner, intermediate, advanced
tags: []


# 🌍 LOCALIZATION -----------------------------------------------------

# Jazyk dokumentu – doplní skript podľa štruktúry
locale: "sk"


# 🕒 LIFECYCLE --------------------------------------------------------

# Dátum vytvorenia – generuje skript
created: "2026-10-26 09:24"

# Dátum poslednej úpravy – dopĺňa človek
modified: "2026-10-26 09:24"

# Stav dokumentu – default "backlog"
status: "backlog"

# Viditeľnosť – default "public"
privacy: "public"


# ⚖ INTELLECTUAL PROPERTY -------------------------------------------

# Držiteľ práv k obsahu – dopĺňa skript
rights_holder_content: "Roman Kazicka"

# Systémový vlastník práv
rights_holder_system: "CAA / KNIFE / LetItGrow"

# Licencia
license: "CC-BY-NC-SA-4.0"

# Disclaimer
disclaimer: "Use at your own risk. Methods provided as-is; participation is voluntary and context-aware."

# Copyright
copyright: "© 2025 Roman Kazicka"


# 🔗 ORIGIN / PROVENANCE ---------------------------------------------

# Repozitár pôvodu
origin_repo: ""

# URL pôvodného repozitára
origin_repo_url: ""

# Commit pôvodu
origin_commit: ""

# Branch pôvodu
origin_branch: ""

# Systém pôvodu (CAA/KNIFE/STHDF…)
origin_system: "CAA"

# Pôvodný autor
origin_author: "Roman Kazicka"

# Importovaný zdroj
origin_imported_from: ""

# Dátum importu
origin_import_date: ""


# 🧱 RESERVED ---------------------------------------------------------

fm_reserved1: ""
fm_reserved2: ""
---

<!-- SDLC_INSTANCE_ID: PRJ-000-QR3D -->

# TroubleShooting

| Problém | Príčina | Riešenie |
|---------|---------|----------|
| `make: command not found` v Git Bash | Git Bash neobsahuje `make` | `winget install ezwinports.make`, reštart Git Bash |
| `Python was not found` | Windows má namiesto Pythonu len odkaz do Microsoft Store | nainštalovať Python z Microsoft Store (vytvorí aj príkaz `python3`) |
| `rm -rf` nefunguje v cmd | je to príkaz pre bash | v cmd `rmdir /s /q .git`, v PowerShelli `Remove-Item -Recurse -Force .git`, alebo použiť Git Bash |
| `Sidebar category 7Ds (SK) has neither any subitem nor a link` | `sidebars.ts` hľadá `sk/7ds`, priečinok je `7Ds` | opraviť `dirName` na `sk/7Ds` |
| `MDX compilation failed … Unexpected character` | v texte je `<niečo>` mimo spätných apostrofov | nahradiť `[niečo]` alebo dať do `` `…` `` |
| Chyba pretrváva aj po oprave | Docusaurus číta starú kópiu / cache | `npx docusaurus clear`, znova skopírovať obsah, reštart `make dev` |
| Všetky stránky „Page Not Found“, server beží na `localhost:3000/C:/Program Files/Git/` | Git Bash preložil `BASE_URL=/` na Windows cestu | `MSYS_NO_PATHCONV=1 make dev` (natrvalo cez `~/.bashrc`) |
| Prepínač jazyka (EN) v menu ukáže 404 pri `make dev` | dev server spúšťa iba jeden jazyk | ignorovať, alebo `make dev DS_LOCALE=en`; na GitHub Pages funguje |


---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
