---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.517863+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "e7144c40-6bde-426c-aca5-13c671e2af16"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "Work Activities Evidence"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Denník práce tímu QR3D – čo, kto a kedy urobil."


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

# Work Activities Evidence

Denník práce tímu. Nový záznam pridávame **hore**. Podrobnosti o zmenách v kóde sú v histórii commitov na GitHube.

| Dátum | Kto | Čo sme urobili | Výstup / odkaz |
|-------|-----|----------------|----------------|
| 02.11.2026 | tím | **zmena zamerania projektu** na turistickú hru so zbieraním AR postavičiek; prepis 7Ds | [Decisions – R-07](../04.Decisions/index.md) |
| 02.11.2026 | tím | Android aplikácia (Kotlin, ARCore): nájde QR kód a položí naň kocku | [D5 – Implementation](../../05.D5-Implementation/index.md) |
| 02.11.2026 | tím | prieskum existujúcich riešení (AR QR platformy, Munzee, QR na náučných chodníkoch) | [D3 – Analysis](../../03.D3-Analysis/index.md) |
| 02.11.2026 | Dániel Ruzsik | 7Ds texty (pôvodná verzia), diagram architektúry | commit `d9d5dbf` |
| 26.10.2026 | Dániel Ruzsik | stránky About Me a Reflexia pre každého člena tímu | `students/about-me`, `students/reflexia` |
| 26.10.2026 | Dániel Ruzsik | oprava chýb šablóny (veľké/malé písmená v cestách, nefunkčné odkazy, MDX), workflow pre GitHub Pages | commity `0f7ac16`, `673d440` |
| 26.10.2026 | Dániel Ruzsik | sfunkčnenie Docusaurusu lokálne aj na GitHub Pages | commit `7523c28` |
| 26.10.2026 | Dániel Ruzsik | prvé texty: Project Summary, ciele, plán, riziká, stakeholderi | commit `b9baff5` |
| 26.10.2026 | Dániel Ruzsik | čistý klon šablóny predmetu, vygenerovanie štruktúry projektu (`make S31-sthdf-new`) | commity `a677984`, `6ae8f21` |

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
