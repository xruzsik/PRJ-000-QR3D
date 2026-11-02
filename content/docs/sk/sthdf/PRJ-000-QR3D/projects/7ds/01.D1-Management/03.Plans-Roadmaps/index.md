---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.510834+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "edeb671b-495e-4343-b1ac-00b25cc79893"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "Plans & Roadmaps"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Roadmapa projektu QR3D – štyri fázy, ich obsah a výstupy v repozitári."


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

# Plans & Roadmaps

## 🗺️ Roadmapa

| Fáza | Obsah | Výstupy v repozitári | Míľniky |
|------|-------|----------------------|---------|
| **1. Príprava** | tím, príbeh, ciele, stakeholderi, repozitár | About Me, Project Summary, 7Ds D1–D2 | M1, M2 |
| **2. Pitch a analýza** | problém, konkurencia, biznis pohľad, experiment s QR | Pitch, Q12, 7Ds D3, SDLC 01–02 | M3, M4 |
| **3. Vývoj** | dátový formát, viewer, generátor, HD režim, maketa | 7Ds D4–D5, SDLC 03–04, KNIFE článok | M5, M6 |
| **4. Overenie a uzavretie** | testy s používateľmi, demo, zhodnotenie | 7Ds D6–D7, SDLC 05–06, Outcomes, Final, Reflexia | M7, M8 |

Podrobné termíny sú v [Plans & Milestones](../02.Plans-Milestones/index.md).

## 🔀 Pravidlá práce s repozitárom

- Vývoj prebieha vo vetve `dev`, väčšie úlohy vo vetvách `feature/nazov-ulohy`.
- Do `main` sa zlučuje cez Pull Request, keď je časť hotová – z `main` sa publikuje web.
- Kód aplikácie je v priečinku `app/` v koreni repozitára.

**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
