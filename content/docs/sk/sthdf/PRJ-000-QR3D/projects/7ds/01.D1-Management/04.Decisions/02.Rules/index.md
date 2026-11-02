---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.539049+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "46482264-f85e-4174-8d60-deeb18ced9cb"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "Rules"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Pravidlá spolupráce v tíme QR3D - git, komunikácia, dokumentácia."


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

# Rules

# Rules

## 🔀 Git a repozitár

- Pracujeme vo vetve `dev`; väčšie úlohy vo vetvách `feature/nazov-ulohy`.
- Do `main` ide zmena len cez **Pull Request**, ktorý si pozrie druhý člen tímu.
- Commit správy píšeme stručne a výstižne (čo a prečo), napr. `Add QR capacity experiment results`.
- Pred pushom do `main` overíme, že prejde `make build` (inak spadne publikovanie webu).

## 📝 Dokumentácia

- Obsah píšeme do `content/docs/`, nikdy nie do `publishing/docusaurus/docs/` (to je len kópia).
- V texte nepoužívame znaky `<` a `{` mimo spätných apostrofov (MDX ich berie ako kód).
- Každú významnú prácu zapíšeme do [Work Activities Evidence](../../05.WorkActivitiesEvidence/index.md).

## 💬 Komunikácia

- Dohodnutý kanál: [Discord / Teams / WhatsApp].
- Krátke stretnutie tímu raz týždenne ([deň, čas]) – čo sme urobili, čo ďalej, čo nás blokuje.
- Ak niekto nestíha úlohu, ozve sa včas, nie v deň termínu.

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../../index.md)
