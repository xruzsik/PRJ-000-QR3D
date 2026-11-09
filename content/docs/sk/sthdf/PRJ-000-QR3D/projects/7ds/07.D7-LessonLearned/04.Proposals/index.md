---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.559594+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "a4126a58-de5b-488e-8d91-e03da0ea16c1"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "Proposals"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Návrhy na zlepšenie projektu QR3D a šablóny predmetu."


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

# Proposals

## 🚀 Pre projekt

- **GPS overenie zastávky** – postavičku získa len ten, kto je naozaj na mieste.
- **Body a rebríček** – súťaž medzi rodinami, triedami alebo oddielmi.
- **Viac trás** – výber trasy v aplikácii, trasy podľa regiónov.
- **Editor trás pre správcov** – obec alebo klub si sám pripraví zastávky a vytlačí QR kódy.
- **Zvuky a animácie postavičiek** – postavička sa pohne alebo prehovorí.
- **Viac jazykov** – SK / EN / HU podľa jazyka telefónu.
- **Verzia pre iOS** – po overení na Androide.

## 🧰 Pre šablónu predmetu (spätná väzba vyučujúcemu)

- `sidebars.ts` odkazuje na `sk/7ds`, priečinok sa však volá `7Ds` → na Linuxe (GitHub) build spadne.
- Generátor (`make S31-sthdf-new`) vytvára v `index.md` odkazy na neexistujúce súbory `students/deliverables/0X_….md`.
- Nadpis v `01.D1-Management/index.md` je „D2 – Motivation“ namiesto „D1 – Management“.
- Workflow `pages.yml` (MkDocs) sa spúšťa pri každom pushi do `dev` a končí chybou.
- `make dev` v Git Bash na Windows nastaví `BASE_URL` na `C:/Program Files/Git/` (pomôže `MSYS_NO_PATHCONV=1`).

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
