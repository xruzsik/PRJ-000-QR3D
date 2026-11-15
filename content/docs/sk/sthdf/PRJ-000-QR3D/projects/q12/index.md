---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.463624+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "905dcfd1-9077-4058-91eb-8d4cd3aa57dc"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "q12"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Q12 – dvanásť kvadrantov (4 fázy × procesy, systémy, technológie) pre hru Postavičky na trase."


# 👥 AUTHORSHIP ------------------------------------------------------

# Hlavný autor – z globálneho configu
author: "Roman Kazicka"

# Zoznam autorov – generuje skript
authors:
  - "Dániel Ruzsik, Lukáš Šebök"


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
modified: "2026-11-15 23:00"

# Stav dokumentu – default "backlog"
status: "draft"

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

> 🚧 **Koncept** – pripravený z 7Ds dokumentácie, tím ho ešte prejde a upraví.


# Q12 – Twelve quadrants for starting our business

Q12 rozkladá „firmu“ okolo produktu na **4 fázy životného cyklu** (marketing, vývoj a dizajn, predaj, údržba) a v každej na **3 vrstvy**: *procesy* (čo robíme), *systémy* (v čom to vedieme) a *technológie* (čím). Spolu 12 kvadrantov.

| Fáza | Procesy | Systémy | Technológie |
|------|---------|---------|-------------|
| **Marketing** | [osloviť obce a oddiely, pilotná trasa ako referencia](./01.ProductServiceLifeCycle/01.Marketing/01.Processes/index.md) | [web projektu, tabuľa na štarte trasy, Google Play](./01.ProductServiceLifeCycle/01.Marketing/02.Systems/index.md) | [GitHub Pages, Canva / Figma, generátor QR](./01.ProductServiceLifeCycle/01.Marketing/03.Technologies/index.md) |
| **Vývoj a dizajn** | [7Ds + SDLC, 2-týždňové iterácie, experiment M5](./01.ProductServiceLifeCycle/02.DevelopmentDesign/01.Processes/index.md) | [Android aplikácia (AR, QR, trasa, zbierka)](./01.ProductServiceLifeCycle/02.DevelopmentDesign/02.Systems/index.md) | [Kotlin, ARCore, ML Kit, SceneView, Blender](./01.ProductServiceLifeCycle/02.DevelopmentDesign/03.Technologies/index.md) |
| **Predaj** | [ponuka → príprava trasy → spustenie](./01.ProductServiceLifeCycle/03.Sales/01.Processes/index.md) | [zoznam kontaktov, cenová ponuka, objednávka](./01.ProductServiceLifeCycle/03.Sales/02.Systems/index.md) | [tabuľka, e-mail, Google Play (zadarmo)](./01.ProductServiceLifeCycle/03.Sales/03.Technologies/index.md) |
| **Údržba** | [kontrola kódov, výmena, aktualizácie](./01.ProductServiceLifeCycle/04.Maintenance/01.Processes/index.md) | [GitHub Issues, verzie obsahu trasy](./01.ProductServiceLifeCycle/04.Maintenance/02.Systems/index.md) | [Play Console, laminácia, odolné nálepky](./01.ProductServiceLifeCycle/04.Maintenance/03.Technologies/index.md) |

**Obchodný model (hypotéza):** aplikácia je pre turistov zadarmo, platí prevádzkovateľ trasy – jednorazovo za prípravu trasy a ročne za údržbu obsahu. Overíme rozhovorom aspoň s jednou obcou alebo OOCR.

- [01. Product/Service Life Cycle](./01.ProductServiceLifeCycle/index.md)

## Navigácia
- [↩️ Späť](../index.md)
