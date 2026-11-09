---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.551530+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "0a2efa3b-d5a1-4fd5-834d-09fa613e7afd"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "Resources"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Zdroje projektu QR3D – ľudia, čas, náklady, nástroje a zdroje 3D postavičiek."


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

# Resources

## 👥 Ľudia

| Člen | Rola | Zameranie |
|------|------|-----------|
| Dániel Ruzsik (ST043) | Project Lead, Developer | koordinácia, dokumentácia, Android aplikácia |
| Lukáš Šebök (ST045) | 3D / Designer | 3D postavičky, UI aplikácie, príprava trasy a QR kódov |

## ⏱️ Čas

- cca 4–6 hodín týždenne na člena počas semestra,
- najviac práce vo fáze vývoja (míľniky M6–M7).

## 💶 Náklady

| Položka | Odhad |
|---------|-------|
| Vývojové nástroje (Android Studio, Kotlin, ARCore, Blender) | 0 € (zadarmo / open source) |
| Hosting dokumentácie a APK (GitHub) | 0 € |
| Tlač a laminácia QR kódov pre testovaciu trasu | do 15 € |
| Testovacie zariadenia | vlastné telefóny s Androidom a podporou ARCore |
| Zverejnenie v Google Play *(mimo MVP)* | jednorazový poplatok za vývojársky účet |

## 🧰 Nástroje

- **Vývoj:** Android Studio, Kotlin, ARCore, knižnica na čítanie QR kódov (ML Kit / ZXing), Git
- **3D:** Blender (úprava a zjednodušenie postavičiek, export do GLB)
- **Dokumentácia:** KNIFE šablóna, Docusaurus, GitHub Pages, draw.io

## 🧸 Zdroje 3D postavičiek

- voľne licencované modely (CC0 / CC-BY) z verejných 3D knižníc,
- vlastné jednoduché low-poly postavičky v Blenderi,
- pri každej postavičke uvádzame autora a licenciu (riziko R6).

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
