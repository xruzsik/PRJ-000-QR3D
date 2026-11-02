---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.516355+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "edb7f8d9-9d28-49f2-862d-f8de58285839"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "04.Decisions"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Záznam kľúčových rozhodnutí projektu QR3D s dôvodmi a odkazy na princípy, pravidlá, SWOT, riziká a metriky."


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

# Decisions

## 🗂️ Podsekcie

- [Principles](./01.Principles/index.md) – zásady, ktorými sa riadime
- [Rules](./02.Rules/index.md) – pravidlá spolupráce v tíme
- [SWOT](./03.SWOT/index.md) – silné a slabé stránky, príležitosti, hrozby
- [Risks](./04.Risks/index.md) – riziká a opatrenia
- [Measures](./05.Measures/index.md) – ako meriame úspech

## 📝 Záznam rozhodnutí

| # | Rozhodnutie | Prečo | Alternatíva, ktorú sme zamietli | Dátum | Stav |
|---|-------------|-------|----------------------------------|-------|------|
| R-01 | Pracujeme s príbehom A – **„Okno do minulosti“** (zrúcanina) | najsilnejší efekt, zrozumiteľný problém, reálny zákazník (múzeum, obec) | náučný chodník, 3D tlačený predmet (zostávajú ako ďalšie využitia) | 02.11.2026 | ✅ |
| R-02 | Webová stránka namiesto natívnej aplikácie | používateľ nemusí nič inštalovať | aplikácia pre Android/iOS | 02.11.2026 | ✅ |
| R-03 | Dáta modelu ukladáme do URL za znak `#` (fragment) | fragment sa neposiela na server → funguje so statickým webom a offline | samostatný formát QR, ktorý by vyžadoval vlastnú aplikáciu | 02.11.2026 | 🔄 overí M4 |
| R-04 | Hosting na GitHub Pages | zadarmo, bez servera, rovnaký repozitár ako dokumentácia | vlastný server, komerčné AR platformy | 26.10.2026 | ✅ |
| R-05 | Kód aplikácie v priečinku `app/` v tom istom repozitári | jeden odkaz pre vyučujúceho, dokumentácia a kód spolu | samostatný repozitár | 02.11.2026 | ✅ |
| R-06 | Zobrazenie cez three.js, AR voliteľne cez `model-viewer` | three.js dovolí vlastný dekódovaný model a časovú os | iba `model-viewer` (vyžaduje hotový GLB súbor) | [dátum] | ⏳ návrh |

Legenda: ✅ prijaté · 🔄 prijaté, čaká na overenie · ⏳ návrh

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
