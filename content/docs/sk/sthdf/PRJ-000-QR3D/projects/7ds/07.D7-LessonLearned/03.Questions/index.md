---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.558068+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "2f4a1d20-92ca-49af-a4f0-0a255c041271"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "Questions"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Otvorené otázky projektu QR3D – pre vyučujúceho aj technické."


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

# Questions

## 🎓 Pre vyučujúceho

| # | Otázka | Odpoveď |
|---|--------|---------|
| Q1 | Aké je naše číslo projektu (`PRJ-0XX`)? Zatiaľ používame `PRJ-000`. | [doplniť] |
| Q2 | Majú byť individuálne výstupy (About Me, Reflexia, KNIFE) v tímovom repozitári, alebo v osobných repozitároch `ST-0XX`? | [doplniť] |
| Q3 | Má mať každý člen tímu vlastný KNIFE článok? | [doplniť] |

## ⚙️ Technické

| # | Otázka | Ako ju zodpovieme |
|---|--------|-------------------|
| Q4 | Koľko trojuholníkov sa reálne zmestí do čitateľného QR kódu? | experiment M4 |
| Q5 | Ktorú verziu QR a úroveň korekcie chýb zvoliť? | experiment M4, test T1 |
| Q6 | Bude viewer fungovať offline aj pri prvom otvorení (bez cache)? | test T2; prípadne service worker |
| Q7 | Kde získať vhodný 3D model zrúcaniny s voľnou licenciou? | prieskum, prípadne vlastný model |

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
