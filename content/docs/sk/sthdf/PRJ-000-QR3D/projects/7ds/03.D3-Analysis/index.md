---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.486476+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "bd220280-e3de-494b-930b-88ecd411b436"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "D3 – Analysis"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Analýza projektu QR3D – existujúce riešenia, technické možnosti AR s QR kódom a plánovaný experiment."


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

# D3 – Analysis

## 🔍 Existujúce riešenia

| Riešenie | Ako funguje | V čom sme iní |
|----------|-------------|---------------|
| **Munzee** | hra na hľadanie pokladu: QR kódy rozmiestnené po svete, za naskenovanie body; poloha sa overuje cez GPS | nemá AR postavičky ani príbeh konkrétneho miesta |
| **Geocaching** | hľadanie skrýš podľa GPS súradníc | bez AR, bez QR; hľadá sa fyzická schránka |
| **Pokémon GO** | zbieranie postavičiek v AR podľa polohy | globálna hra bez väzby na konkrétnu trasu a jej príbeh |
| **QR kódy na náučných chodníkoch** (napr. Predná Hora – zvuky zvierat, Bojnice – turistická aplikácia) | QR otvorí webovú stránku, zvuk alebo aplikáciu | informácia bez hry a bez zbierania |
| **Turistické známky** | drevené odznaky, ktoré si turista kúpi a zbiera na miestach | fyzické, nie digitálne; neprinášajú informáciu o mieste |

**Záver:** princípy „hľadaj“, „zbieraj“ a „AR“ sú overené, ale v spojení s lokálnou trasou a príbehom jej miest sme podobnú hru na Slovensku nenašli. Regióny už dnes financujú QR kódy na náučných chodníkoch – existuje teda zákazník.

## ⚙️ Technické možnosti (Android, ARCore)

- **Nájdenie QR kódu:** obraz z kamery ARCore sa pošle do knižnice na čítanie kódov (napr. ML Kit Barcode Scanning alebo ZXing). Tá vráti obsah kódu a jeho rohy v obraze.
- **Poloha kódu v priestore:** z rohov kódu v obraze a z údajov ARCore (hit test na rovinu, prípadne hĺbka) vypočítame polohu a natočenie kódu a vytvoríme **kotvu (Anchor)**.
- **Umiestnenie postavičky:** postavičku postavíme vo vzťahu ku kotve – napr. 1 m vpravo od kódu a na najbližšiu rovinu (zem). Takto stojí vedľa stĺpika či stromu, nie „vo vzduchu“.
- **Alternatíva:** ARCore *Augmented Images* – sleduje vopred známe obrázky. QR kódy však majú málo charakteristických bodov, preto ho len vyskúšame ako porovnanie.
- **Obsah QR kódu:** len identifikátor zastávky (napr. odkaz `…/trasa/zastavka-3`) – kód je malý a ľahko čitateľný aj z diaľky.

## 🧪 Plánovaný experiment (míľnik M5)

1. Umiestnime QR kód vnútri a vonku (stena, stĺpik, strom), v rôznych veľkostiach (5 / 10 / 15 cm).
2. Zmeriame, z akej vzdialenosti a uhla ho aplikácia nájde.
3. Postavíme objekt 1 m vedľa kódu a sledujeme, či pri obchádzaní zostane na mieste.
4. Porovnáme dva prístupy: kotva odvodená z QR kódu vs. ARCore Augmented Images.
5. Výsledok: odporúčaná veľkosť kódu a spôsob ukotvenia → podklad pre návrh (D4) aj pre KNIFE článok.

## 🔗 Podrobnejšie

- [Resources](./01.Resources/index.md) – ľudia, čas, náklady, nástroje

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../index.md)
