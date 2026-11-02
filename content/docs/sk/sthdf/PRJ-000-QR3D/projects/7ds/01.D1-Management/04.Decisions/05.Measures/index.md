---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.546014+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "dc2431b7-c5db-4ee0-a08a-127fd0c37953"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "Measures"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Metriky úspechu projektu QR3D a ich cieľové hodnoty."


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

# Measures

Cieľové hodnoty sú počiatočný odhad – spresníme ich po experimente s kapacitou QR (M4) a výsledky zapíšeme do [D6](../../../06.D6-Verification-Testing/index.md).

| # | Metrika | Cieľová hodnota | Ako meriame | Výsledok |
|---|---------|-----------------|-------------|----------|
| K1 | Čitateľnosť QR kódu | QR s rozmerom 6 × 6 cm sa načíta zo vzdialenosti aspoň 30 cm | test s 2 telefónmi, bežné osvetlenie | [doplniť] |
| K2 | Čas do zobrazenia modelu (offline) | do 3 s od naskenovania | stopky / meranie v prehliadači | [doplniť] |
| K3 | Detail offline modelu | aspoň 200 trojuholníkov v jednom QR kóde | výstup generátora | [doplniť] |
| K4 | Podpora prehliadačov | funguje v Chrome (Android) aj Safari (iOS) | manuálny test | [doplniť] |
| K5 | Úspešnosť používateľov | aspoň 4 z 5 testerov zvládnu scenár bez pomoci | test s používateľmi | [doplniť] |
| K6 | Spokojnosť používateľov | priemer aspoň 4 z 5 na otázku „Pomohlo ti to predstaviť si stavbu?“ | krátky dotazník po teste | [doplniť] |
---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../../index.md)
