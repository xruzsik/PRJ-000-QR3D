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
title: "Decisions"

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
| R-01 | ~~Príbeh „Okno do minulosti“ – vzdelávací doplnok pre múzeá~~ | – | – | 02.11.2026 | ❌ nahradené R-07 |
| R-02 | ~~Webová stránka namiesto natívnej aplikácie~~ | – | – | 02.11.2026 | ❌ nahradené R-08 |
| R-03 | ~~Dáta 3D modelu priamo v QR kóde (v URL za `#`)~~ | – | – | 02.11.2026 | ❌ nahradené R-09 |
| R-04 | Dokumentácia na GitHub Pages | zadarmo, bez servera, rovnaký repozitár ako kód | vlastný server | 26.10.2026 | ✅ |
| R-05 | Kód aplikácie v priečinku `app/` v tom istom repozitári | jeden odkaz pre vyučujúceho, dokumentácia a kód spolu | samostatný repozitár | 02.11.2026 | ✅ |
| R-06 | ~~Zobrazenie cez three.js / `model-viewer`~~ | – | – | 02.11.2026 | ❌ nahradené R-08 |
| **R-07** | **Zmena zamerania: turistická hra so zbieraním AR postavičiek namiesto vzdelávacieho nástroja pre múzeá** | funkčný prototyp (kocka na QR) ukázal, že AR v priestore je silnejší zážitok ako samotný model; motivácia k pohybu je širší problém; obce a regióny už dnes platia za QR kódy na trasách | pokračovať s múzeom („Okno do minulosti“); spojiť obe myšlienky (odložené) | 02.11.2026 | ✅ |
| **R-08** | **Natívna Android aplikácia v Kotline s ARCore** | AR so sledovaním priestoru, zbierka uložená v telefóne, plná kontrola nad umiestnením postavičky; tím už má funkčný prototyp | webová aplikácia (WebXR – slabšia podpora), Unity (väčšia aplikácia, nová technológia pre tím) | 02.11.2026 | ✅ |
| **R-09** | **QR kód nesie len identifikátor zastávky, postavičky sú uložené v aplikácii** | kód je malý a ľahko čitateľný; postavičky môžu byť detailné; funguje offline | 3D model priamo v QR (kapacita max. cca 3 KB) | 02.11.2026 | ✅ |
| R-10 | Overenie polohy cez GPS ako bonus | bráni „zbieraniu z gauča“, ale nie je nutné pre MVP | povinné GPS od začiatku | [dátum] | ⏳ návrh |

Legenda: ✅ prijaté · ⏳ návrh · ❌ zrušené (ponechané kvôli histórii)

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
