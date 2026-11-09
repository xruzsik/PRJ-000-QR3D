---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.541531+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "2d7eb32d-09fd-4810-a79d-5ec7b0295753"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "SWOT"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "SWOT analýza projektu QR3D – silné a slabé stránky, príležitosti a hrozby."


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

# SWOT

| | **Pozitívne** | **Negatívne** |
|---|---|---|
| **Interné** | **Silné stránky (S)**<br/>• spojenie hľadania, zbierania a AR – zážitok, nie len informácia<br/>• postavičky viazané na konkrétne miesto a jeho príbeh<br/>• funguje offline<br/>• lacné nasadenie – stačí vytlačiť QR kódy<br/>• máme funkčný prototyp (QR + objekt v AR) | **Slabé stránky (W)**<br/>• treba si stiahnuť aplikáciu<br/>• len Android (ARCore), nie všetky telefóny podporujú AR<br/>• tím má len dvoch členov a obmedzený čas<br/>• potrebujeme 3D postavičky – nemáme skúseného 3D grafika |
| **Externé** | **Príležitosti (O)**<br/>• obce a regionálne organizácie cestovného ruchu už dnes financujú QR kódy na náučných chodníkoch<br/>• princíp zbierania na trasách funguje (turistické známky, geocaching)<br/>• rodiny s deťmi, školy a oddiely mládeže hľadajú dôvod ísť von<br/>• trasa sa dá rozšíriť o povesti, prírodu a históriu regiónu | **Hrozby (T)**<br/>• existujúce hry (Munzee, geocaching, Pokémon GO)<br/>• povolenia na umiestnenie QR kódov (chránené územia)<br/>• poškodenie QR kódov počasím a vandalizmom<br/>• podvádzanie – zdieľané fotky QR kódov<br/>• bezpečnosť – nepozornosť pri pozeraní do mobilu |

## 💡 Čo z toho vyplýva

- **S + O:** ponúkneme obciam a regionálnym organizáciám lacný spôsob, ako oživiť existujúce trasy – nie konkurenciu globálnym hrám, ale lokálnu hru o ich regióne.
- **W → opatrenie:** postavičky v MVP jednoduché (low-poly), voľne licencované modely; overiť podporu AR na telefónoch testerov včas.
- **T → opatrenie:** GPS overenie zastávky (bonus), odolné QR (laminácia), umiestnenie len s povolením (princíp P5).

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../../index.md)
