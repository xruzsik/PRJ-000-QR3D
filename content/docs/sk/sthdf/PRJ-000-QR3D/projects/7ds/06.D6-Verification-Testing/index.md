---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.493620+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "1a21c4d2-2add-4a3c-a148-8f647cbabe5d"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "D6 - Verification & Testing"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Plán overenia a testovania QR3D - technické testy, test s používateľmi a výsledky"


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

# D6 – Verification & Testing

## 🧪 Technické testy

| # | Test | Postup | Očakávaný výsledok | Výsledok |
|---|------|--------|--------------------|----------|
| T1 | Čitateľnosť QR | QR 4 / 6 / 8 cm, vzdialenosť 20 / 30 / 50 cm, 2 telefóny, denné a umelé svetlo | 6 cm kód sa načíta z 30 cm (metrika K1) | [doplniť] |
| T2 | Offline režim | zapnúť režim lietadlo, naskenovať QR | zobrazí sa low-poly model | [doplniť] |
| T3 | Čas zobrazenia | merať od naskenovania po vykreslenie | do 3 s (K2) | [doplniť] |
| T4 | Prehliadače | Chrome (Android), Safari (iOS) | model sa zobrazí v oboch (K4) | [doplniť] |
| T5 | HD režim | naskenovať s pripojením | detailný model nahradí low-poly | [doplniť] |
| T6 | Poškodené dáta | upraviť znaky v URL | viewer zobrazí zrozumiteľnú chybu, nespadne | [doplniť] |

## 👥 Test s používateľmi

- **Kto:** 3–5 ľudí rôzneho veku (ideálne aj dieťa a starší človek).
- **Scenár:** „Si pri zrúcanine. Naskenuj QR kód a zisti, ako stavba vyzerala.“ Nič nevysvetľujeme vopred.
- **Sledujeme:** či to zvládnu bez pomoci, kde zaváhajú, ako dlho to trvá.
- **Po teste 3 otázky:** Pomohlo ti to predstaviť si stavbu? (1–5) · Čo bolo mätúce? · Čo by si pridal?

## 📊 Výsledky

*Doplníme po testovaní (míľnik M7) – súhrn, porovnanie s metrikami v [Measures](../01.D1-Management/04.Decisions/05.Measures/index.md) a navrhnuté úpravy.*

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../index.md)
