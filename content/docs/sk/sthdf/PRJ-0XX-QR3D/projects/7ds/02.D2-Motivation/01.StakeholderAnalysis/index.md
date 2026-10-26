---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.548521+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "0f9645c7-96f8-4d27-9c59-3173889623a9"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "01.StakeholderAnalysis"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "{{DESCRIPTION}}"


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

# Stakeholder Analysis

| Stakeholder | Čo potrebuje / očakáva | Prínos riešenia | Vplyv na projekt |
|-------------|------------------------|-----------------|------------------|
| Návštevník múzea | pochopiť exponát rýchlo a zaujímavo | 3D pohľad na pôvodnú podobu stavby bez inštalácie aplikácie | nízky (používateľ) |
| Deti a študenti | interaktívny, hravý zážitok | model sa dá otáčať a približovať | nízky |
| Kurátor / múzeum | lacné a jednoduché doplnenie expozície | stačí vytlačiť QR kód, statický web bez servera | vysoký (zákazník) |
| Učiteľ / sprievodca (napr. náučný chodník) | názorná pomôcka pri výklade | QR kód na tabuli alebo v pracovnom liste | stredný |
| Tím projektu | naučiť sa 3D web, kódovanie dát, prácu podľa KNIFE | praktická skúsenosť a portfólio | vysoký |
| Vyučujúci | splnené deliverables a kvalitná dokumentácia | projekt zdokumentovaný podľa 7Ds/SDLC/Q12 | vysoký (hodnotí) |

## 💡 Prečo projekt dáva zmysel

Fyzický exponát ukazuje len to, čo sa zachovalo. Digitálne doplnenie cez QR kód ukáže aj to, čo chýba – lacno, bez aplikácie a pre každého, kto má mobil.

**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
