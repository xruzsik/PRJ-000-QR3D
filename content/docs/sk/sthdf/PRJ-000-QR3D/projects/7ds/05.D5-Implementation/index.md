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
title: "D5 – Implementation"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Realizácia QR3D – štruktúra Android projektu, technológie a stav implementácie."


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

*Stav: rozpracované – sekciu dopĺňame priebežne počas vývoja.*

## 📁 Štruktúra kódu (návrh)

```text
app/                          # Android projekt (Android Studio, Gradle)
└── src/main/
    ├── java/.../ar/          # AR obrazovka, ukotvenie, vykresľovanie
    ├── java/.../qr/          # čítanie QR kódov z obrazu kamery
    ├── java/.../trail/       # načítanie trasy a zastávok (JSON)
    ├── java/.../collection/  # zbierka, odznaky (lokálna databáza)
    ├── java/.../ui/          # obrazovky
    └── assets/
        ├── trails/           # trasy (JSON)
        └── models/           # 3D postavičky (GLB)
qr-codes/                     # QR kódy testovacej trasy na tlač (PDF)
```

## 🛠️ Technológie

| Oblasť | Technológia | Prečo |
|--------|-------------|-------|
| Jazyk | Kotlin | odporúčaný jazyk pre Android |
| AR | Google ARCore | sledovanie priestoru, roviny, kotvy |
| Čítanie QR | [doplniť: ML Kit / ZXing / iné] | [doplniť] |
| Vykresľovanie 3D | [doplniť: SceneView / Filament / OpenGL] | [doplniť] |
| Dáta trasy | JSON v `assets` | jednoduché, offline |
| Zbierka | [doplniť: Room / DataStore] | uloženie aj po reštarte |
| 3D modely | Blender, formát GLB | štandardný formát pre mobilné 3D |

## ✅ Stav implementácie

| Časť | Stav | Poznámka |
|------|------|----------|
| AR kamera (ARCore) | ✅ | |
| Nájdenie QR kódu v priestore | ✅ | |
| Objekt (kocka) na QR kóde | ✅ | prvý prototyp |
| Objekt vedľa QR kódu (offset, na zemi) | ⏳ | míľnik M5 |
| 3D postavička namiesto kocky | ⏳ | míľnik M6 |
| Rôzne postavičky podľa obsahu QR | ⏳ | míľnik M6 |
| Zbierka (uloženie) | ⏳ | míľnik M6 |
| Trasa, cieľ a odznak | ⏳ | míľnik M7 |
| GPS overenie | ⏳ | bonus |

## 🧠 Dôležité implementačné rozhodnutia

Zapisujeme sem priebežne (čo, prečo, aká bola alternatíva). Väčšie rozhodnutia patria aj do [záznamu rozhodnutí](../01.D1-Management/04.Decisions/index.md).

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../index.md)
