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
description: "SWOT analýza projektu QR3D - silné a slabé stránky, príležitosti a hrozby."


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

# SWOT

| | **Pozitívne** | **Negatívne** |
|---|---|---|
| **Interné** | **Silné stránky (S)**<br/>• žiadna aplikácia – stačí mobil a prehliadač<br/>• funguje aj offline (model je priamo v QR)<br/>• takmer nulové náklady na prevádzku (statický web)<br/>• otvorené riešenie bez predplatného<br/>• jednoduché nasadenie – vytlačiť QR kód | **Slabé stránky (W)**<br/>• kapacita QR kódu obmedzuje detail offline modelu<br/>• tím má len dvoch členov a obmedzený čas<br/>• nemáme skúsenosti s 3D modelovaním historických stavieb<br/>• bez AR je zážitok menej pôsobivý ako pri komerčných riešeniach |
| **Externé** | **Príležitosti (O)**<br/>• digitalizácia kultúrneho dedičstva a cestovného ruchu<br/>• veľa malých múzeí, obcí a náučných chodníkov bez rozpočtu na aplikácie<br/>• využitie v školách (výlety, vyučovanie dejepisu)<br/>• ďalšie využitia: náučné chodníky, 3D tlačené predmety | **Hrozby (T)**<br/>• komerčné AR QR platformy (napr. AR Code) s hotovými nástrojmi<br/>• rozdiely v podpore prehliadačov a AR na mobiloch<br/>• autorské práva k 3D modelom<br/>• kritika odborníkov, ak by rekonštrukcia pôsobila ako historicky presná |

## 💡 Čo z toho vyplýva

- **S + O:** zameriame sa na malé inštitúcie, pre ktoré sú komerčné riešenia drahé.
- **W → opatrenie:** hybridný režim (offline low-poly + HD z webu) zmierňuje obmedzenú kapacitu QR.
- **T → opatrenie:** model označujeme ako ilustračný (princíp P6) a používame len modely s vhodnou licenciou.
---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../../index.md)
