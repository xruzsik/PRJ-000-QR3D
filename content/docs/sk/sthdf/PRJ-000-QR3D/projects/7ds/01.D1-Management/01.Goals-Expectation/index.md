---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.502769+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "adb67b65-16bb-420c-bd2c-1a223e0191d4"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "Goals & Expectations"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Hlavný cieľ, čiastkové ciele s kritériami splnenia, očakávania a rozsah projektu QR3D."


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

# Goals & Expectations

## 🎯 Hlavný cieľ

Vytvoriť funkčný prototyp **„Okno do minulosti“**: návštevník pri zrúcanine alebo jej makete naskenuje QR kód a v mobilnom prehliadači – bez inštalácie aplikácie – uvidí 3D model stavby v jej pôvodnej podobe. Zjednodušený model je uložený priamo v QR kóde, takže funguje aj bez signálu.

## 📌 Čiastkové ciele

| # | Cieľ | Ako overíme splnenie |
|---|------|----------------------|
| G1 | Zakódovať zjednodušený (low-poly) 3D model priamo do QR kódu | model sa zobrazí v režime lietadlo (bez internetu) |
| G2 | Web viewer bez inštalácie aplikácie | funguje v Chrome (Android) aj Safari (iOS) |
| G3 | Hybridný režim – detailný (HD) model z webu | pri pripojení sa low-poly verzia nahradí detailnou |
| G4 | Generátor QR kódov | z 3D súboru (OBJ/GLB) vznikne QR kód jedným príkazom |
| G5 | Fyzická ukážka | maketa zrúcaniny s QR kódom pripravená na finálnu prezentáciu |
| G6 | Dokumentácia podľa KNIFE (7Ds, SDLC, Q12) a 1 KNIFE článok | všetky deliverables vyplnené a publikované na GitHub Pages |
| G7 *(bonus)* | Časová os – prepínanie období stavby | posuvník ukáže aspoň 2 obdobia |
| G8 *(bonus)* | AR zobrazenie | model sa dá umiestniť do priestoru cez kameru mobilu |

## 🤝 Očakávania

- **Od tímu:** pravidelná práca (cca 4–6 h týždenne na člena), práca vo vetve `dev`, záznam do [Work Activities Evidence](../05.WorkActivitiesEvidence/index.md) aspoň raz týždenne.
- **Od vyučujúceho:** spätná väzba k Project Summary a k pitchu.
- **Od používateľov (testerov):** 3–5 ľudí, ktorí vyskúšajú scenár „naskenuj → prezri si model“ a povedia, čo im prekážalo.

## 🚫 Mimo rozsahu (out of scope)

- natívna mobilná aplikácia (Android/iOS),
- vlastné 3D skenovanie reálnych zrúcanín,
- odborne presná historická rekonštrukcia – náš model je **ilustračný**,
- správa obsahu pre múzeá (CMS, používateľské účty).

**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
