---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.563603+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "c7e9c5f8-5c99-47e2-bead-1749f80b1256"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "HowTo"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Krátke návody, ktoré sa zišli pri práci na projekte QR3D."


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

# HowTo

## Spustiť dokumentáciu lokálne (Windows, Git Bash)

```bash
cd publishing/docusaurus && npm ci && cd ../..        # raz: inštalácia závislostí
rm -rf publishing/docusaurus/docs && mkdir -p publishing/docusaurus/docs && cp -r content/docs/. publishing/docusaurus/docs/
MSYS_NO_PATHCONV=1 make dev                           # web na http://localhost:3000
```

Po každej úprave v `content/docs/` treba znova skopírovať obsah (2. riadok, u nás alias `docsync`).

## Vytvoriť novú stránku

1. Vytvoriť priečinok a v ňom `index.md` (najľahšie skopírovať existujúci, aby sa zachoval frontmatter).
2. Vygenerovať nové `guid`: `python3 -c "import uuid; print(uuid.uuid4())"`.
3. Upraviť `title` (zobrazí sa v menu) a obsah.
4. Pridať odkaz na stránku z nadradeného `index.md`.

## Vytvoriť KNIFE článok

```bash
make knifes-new ID=K000XXX name="nazov-clanku" title="Názov článku" LOCALE=sk
```

## Publikovať na GitHub Pages

Zlúčiť zmeny z `dev` do `main` cez Pull Request – publikovanie prebehne automaticky (GitHub Actions). Stav je vidieť v záložke **Actions**.

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
