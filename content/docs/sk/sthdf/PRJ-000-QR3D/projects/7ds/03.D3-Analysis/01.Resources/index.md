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
description: "Zdroje projektu QR3D – ľudia, čas, náklady, nástroje a zdroje 3D modelov."


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
| Dániel Ruzsik (ST043) | Project Lead, Developer | koordinácia, dokumentácia, web viewer, generátor |
| Lukáš Šebök (ST045) | 3D / Designer | príprava a zjednodušenie 3D modelov, UI, maketa |

## ⏱️ Čas

- cca 4–6 hodín týždenne na člena počas semestra,
- najviac práce vo fáze vývoja (míľniky M5–M6).

## 💶 Náklady

| Položka | Odhad |
|---------|-------|
| Hosting (GitHub Pages) | 0 € |
| Softvér (Blender, three.js, Python, VS Code) | 0 € (open source) |
| Tlač QR kódov a materiál na maketu (kartón, prípadne 3D tlač) | do 20 € |
| Testovacie zariadenia | vlastné telefóny (Android + iOS) |

## 🧰 Nástroje

- **Vývoj:** VS Code, Git, Node.js, Python
- **3D:** Blender (zjednodušenie modelu, export do OBJ/GLB)
- **Web:** three.js, prípadne Google `model-viewer`
- **Dokumentácia:** KNIFE šablóna, Docusaurus, GitHub Pages

## 🏛️ Zdroje 3D modelov

- voľne licencované modely (CC0 / CC-BY) z verejných 3D knižníc,
- vlastný zjednodušený model v Blenderi podľa pôdorysu a dobových vyobrazení,
- pri každom modeli uvádzame autora a licenciu (riziko R6).

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
