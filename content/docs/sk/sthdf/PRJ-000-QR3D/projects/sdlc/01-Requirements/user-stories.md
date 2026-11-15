---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.630286+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "f23b28f9-55f9-4b07-be05-713d1a3fe50e"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "user stories"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "User stories pre hru Postavičky na trase s kritériami prijatia a prioritou."


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


# 🧑‍🤝‍🧑 User Stories

## 👤 Používatelia (persony)

| Persona | Kto to je | Čo chce |
|---------|-----------|---------|
| **Hráč** | dieťa 8–14 rokov alebo mladý turista | nájsť a nazbierať všetky postavičky |
| **Rodič** | dospelý, ktorý vezme deti na výlet | aby deti išli rady a výlet sa nezmenil na boj |
| **Vedúci oddielu** | skautský vedúci, učiteľ, vedúci krúžku | hotový program pre skupinu |
| **Prevádzkovateľ trasy** | obec, OOCR, turistický klub | oživiť trasu lacno a bez nového mobiliáru |
| **Tím (správca obsahu)** | my | pridať zastávku alebo postavičku bez prepisovania aplikácie |

## 📋 Príbehy

Priorita podľa MoSCoW: **M** = must (MVP), **S** = should, **C** = could, **W** = won't (nie v MVP).

| ID | Ako… | chcem… | aby… | Kritériá prijatia | Prior. | Míľnik |
|----|------|--------|------|-------------------|--------|--------|
| US-01 | hráč | namieriť telefón na QR kód a uvidieť vedľa neho postavičku | som mal pocit, že som ju našiel | postavička sa zobrazí do 3 s; stojí na zemi vedľa kódu; pri obchádzaní sa neposunie o viac ako cca 10 cm | M | M5, M6 |
| US-02 | hráč | si nájdenú postavičku uložiť do zbierky | ju mal aj po výlete | po uložení je v zbierke aj po reštarte aplikácie; opätovné naskenovanie nepridá duplikát | M | M6 |
| US-03 | hráč | vidieť, koľko postavičiek mi ešte chýba (napr. 3 / 5) | vedel, či mám ísť ďalej | počítadlo na hlavnej obrazovke aj v zbierke | M | M6 |
| US-04 | hráč | v cieli získať odznak, keď mám všetky postavičky | mal odmenu za celú trasu | odznak sa odomkne len pri kompletnej zbierke danej trasy | M | M7 |
| US-05 | rodič | aby hra fungovala bez signálu | sme sa nezasekli v lese | celá trasa prejde v režime lietadlo | M | M7 |
| US-06 | dieťa | si o postavičke prečítať krátky príbeh | vedel, prečo býva práve tu | 2–4 vety na postavičku, zrozumiteľné pre 8-ročného | S | M6 |
| US-07 | hráč | nápovedu, keď AR nevie nájsť zem alebo kód | nevzdal som to | po 5 s bez nájdenia sa zobrazí rada („pohni pomaly telefónom“, „priblíž sa ku kódu“) | S | M5 |
| US-08 | rodič | pri štarte vidieť zoznam zastávok a dĺžku trasy | vedel, či to deti zvládnu | obrazovka trasy so zastávkami, dĺžkou a odhadovaným časom | S | M7 |
| US-09 | hráč | upozornenie, aby som pri chôdzi nepozeral do mobilu | sa mi nič nestalo | upozornenie pri štarte trasy; AR kamera sa zapína až pri zastávke | S | M7 |
| US-10 | vedúci oddielu | aby hru hralo viac detí naraz, každé na svojom telefóne | celá skupina bola zapojená | každý telefón má vlastnú zbierku; funguje aspoň na 2 rôznych telefónoch | S | M8 |
| US-11 | správca obsahu | pridať zastávku úpravou dátového súboru trasy | netreba meniť kód aplikácie | nová zastávka v JSON + GLB model sa po novom builde zobrazí bez zmeny kódu | S | M6 |
| US-12 | prevádzkovateľ trasy | dostať QR kódy pripravené na tlač aj s krátkym návodom | ich vieme sami vytlačiť a umiestniť | PDF s kódmi (10 × 10 cm), číslom zastávky a návodom „ako hrať“ | C | M7 |
| US-13 | turista bez aplikácie | po naskenovaní kódu bežnou kamerou vidieť stránku s odkazom na hru | vedel, čo to je a kde ju získam | QR obsahuje URL; stránka vysvetlí hru a odkáže na aplikáciu | C | rozšírenie |
| US-14 | hráč | aby postavičku nešlo „nazbierať“ z fotky kódu doma | hra bola férová | zastávka sa uzná len v okolí jej GPS polohy | C | rozšírenie |
| US-15 | prevádzkovateľ trasy | vidieť, koľko ľudí trasu prešlo | vedel, či sa investícia oplatila | – (vyžaduje server a súhlas používateľov) | W | – |

## 🔗 Súvislosti
- Nefunkčné požiadavky: [non-functional](./non-functional.md)
- Kritériá úspechu: [Measures](../../7ds/01.D1-Management/04.Decisions/05.Measures/index.md)
- Míľniky: [Plans & Milestones](../../7ds/01.D1-Management/02.Plans-Milestones/index.md)

---
**Navigation:** [⬆️ Up](./index.md) · [🏠 Home](../index.md)
