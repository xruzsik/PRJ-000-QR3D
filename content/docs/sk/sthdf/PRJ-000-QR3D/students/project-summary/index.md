---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.690816+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "ba19f648-32f0-4301-ad27-d13c4391e595"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "project summary"

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

# 📝 Project Summary

## 🔖 Identifikácia

- **Projekt:** PRJ-000-QR3D
- **Repozitár:** https://github.com/xruzsik/PRJ-000-QR3D
- **Predmet:** Systémové myslenie v IT a digitálna fabrikácia (STHDF), FIIT STU, 2026/2027

## 1️⃣ Názov projektu

- **Názov projektu:** `PRJ_000_QR3D_<ST_042>_<ST_045>`
- **Pracovný názov:** *QR3D – 3D model ukrytý v QR kóde*
- **Stručný popis:** Navrhujeme systém, ktorý do QR kódu zakóduje údaje o 3D modeli (napr. budova, zviera, artefakt). Po naskenovaní mobilom sa model zobrazí priamo v prehliadači – bez inštalácie aplikácie. Typické využitie je v múzeu: pri makete zrúcaniny návštevník naskenuje kód a uvidí, ako stavba vyzerala v pôvodnej podobe.

## 2️⃣ Členovia tímu

| ST ID | Meno | Rola v tíme | Kompetencie |
|-------|------|-------------|-------------|
| ST043 | Dániel Ruzsik | Project Lead | koordinácia, dokumentácia (7Ds, SDLC) |
| ST043 | Dániel Ruzsik | Developer | web viewer (three.js), QR generátor |
| ST045 | Lukáš Šebök | 3D / Designer | príprava a optimalizácia 3D modelov, UI |

## 3️⃣ Motivácia tímu

- **Prečo tento projekt:** spája softvér s fyzickým svetom (tlač QR kódu, maketa, 3D model) – presne v duchu digitálnej fabrikácie.
- **Aký problém rieši:** fyzické exponáty (makety, zrúcaniny, preparáty) ukazujú len to, čo sa zachovalo. Návštevník si chýbajúce časti musí predstaviť z textu na tabuľke.
- **Prečo je to dôležité:** interaktívny 3D pohľad zvyšuje záujem a pochopenie, najmä u detí a mladých návštevníkov. Riešenie v prehliadači znižuje bariéru – netreba sťahovať aplikáciu.
- **Osobný význam:** _(doplňte: čo vás na téme baví, čo sa chcete naučiť)_

## 4️⃣ Cieľ a pridaná hodnota projektu

- **Finálny cieľ:** funkčný prototyp „QR kód → 3D model v prehliadači“ a fyzická ukážka (maketa s nalepeným QR kódom).
- **Pridaná hodnota:**
  - žiadna inštalácia – stačí fotoaparát mobilu a prehliadač,
  - hybridný režim: základný (low-poly) model je uložený priamo v QR kóde a funguje aj offline, detailný model sa dotiahne z webu,
  - nízke náklady pre prevádzkovateľa (statický web, napr. GitHub Pages).
- **Ako spoznáme úspech (merateľné kritériá):**
  - [ ] QR kód sa dá naskenovať bežným mobilom zo vzdialenosti aspoň x cm pri veľkosti Y cm,
  - [ ] model sa zobrazí do Z s od naskenovania,
  - [ ] offline režim zobrazí aspoň zjednodušený model,
  - [ ] min. N testovacích používateľov úspešne prejde scenár „naskenuj → prezri si model“.

## 5️⃣ Popis riešenia

- **Komponenty:**
  1. **Generátor** – zo 3D modelu (OBJ/GLB) vytvorí zjednodušenú verziu, skomprimuje ju a vygeneruje QR kód (URL + dáta v časti za `#`).
  2. **Web viewer** – statická stránka, ktorá dekóduje dáta z URL a zobrazí model (three.js / `<model-viewer>`), voliteľne načíta HD verziu.
  3. **Fyzická ukážka** – vytlačená alebo kartónová maketa zrúcaniny s QR kódom.
- **Technológie (predbežne):** JavaScript, three.js alebo `<model-viewer>`, Python (generátor), knižnica na QR, GitHub Pages.
- **Rozšírenia (ak zostane čas):** AR – model sa zobrazí priamo nad maketou v kamere; časová os (rôzne obdobia stavby); viac jazykov.

## 6️⃣ Projektový plán

- **Odhadovaný čas na prácu:** _x hodín / týždeň_ na člena
- **Predpokladaný stav v čase prezentácie:** _funkčný prototyp (MVP) + fyzická ukážka_
- **Hlavné míľniky:**
  - [ ] Príprava – zadanie, Project Summary, 7Ds D1–D2, výber technológií
  - [ ] Pitch – problém, riešenie, hodnota (Q12), user stories
  - [ ] Implementácia – generátor, viewer, prvý QR kód s modelom
  - [ ] Testovanie – čitateľnosť QR, rýchlosť, test s používateľmi
  - [ ] Finálna prezentácia – demo, výsledky, reflexia
