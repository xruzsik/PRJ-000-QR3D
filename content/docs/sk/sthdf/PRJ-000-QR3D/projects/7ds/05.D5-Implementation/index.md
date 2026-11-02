---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.491470+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "61360c03-5411-4509-9932-fa3db726ecac"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "D5 - Implementation"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Realizácia QR3D – štruktúra kódu, technológie a stav implementácie."


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

# D5 – Implementation

*Stav: pripravuje sa – sekciu dopĺňame priebežne počas vývoja.*

## 📁 Štruktúra kódu

```text
app/
├── generator/      # Python: 3D model → zjednodušenie → bajty → QR kód
├── viewer/         # statická webová stránka (HTML + JS, three.js)
├── models/         # HD modely (GLB) a zdrojové modely
└── experiments/    # experiment s kapacitou QR (M4)
```

## 🛠️ Technológie

| Oblasť | Technológia | Prečo |
|--------|-------------|-------|
| Generátor | Python, knižnica na tvorbu QR kódov | jednoduché spracovanie dát a súborov |
| Viewer | JavaScript, three.js | zobrazí aj model, ktorý si sami dekódujeme |
| Kompresia | deflate (v prehliadači cez `DecompressionStream`) | bez ďalších knižníc |
| Hosting | GitHub Pages | zadarmo, bez servera |
| 3D modely | Blender | zjednodušenie a export modelov |

## ✅ Stav implementácie

| Časť | Stav | Poznámka |
|------|------|----------|
| Experiment s kapacitou QR | ⏳ | míľnik M4 |
| Dekódovanie a zobrazenie modelu z URL | ⏳ | míľnik M5 |
| Generátor QR kódov | ⏳ | míľnik M6 |
| HD model z webu | ⏳ | míľnik M6 |
| Časová os | ⏳ | bonus |
| AR | ⏳ | bonus |

## 🧠 Dôležité implementačné rozhodnutia

Zapisujeme sem priebežne (čo, prečo, aká bola alternatíva). Väčšie rozhodnutia patria aj do [záznamu rozhodnutí](../01.D1-Management/04.Decisions/index.md).

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../index.md)
