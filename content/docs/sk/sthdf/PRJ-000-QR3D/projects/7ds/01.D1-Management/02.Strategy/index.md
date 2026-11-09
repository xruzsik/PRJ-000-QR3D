---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.507831+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "baa3ae2c-4f26-4efb-80a2-b6978e143252"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "Strategy"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Strategický prístup projektu QR3D – najprv overiť AR v teréne, MVP pred rozšíreniami, offline-first."


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

# Strategy

## 🧭 Ako k projektu pristupujeme

1. **Najprv overiť najväčšie riziko.** Kľúčová otázka je, či AR vonku na trase funguje spoľahlivo – svetlo, pohyb, QR kód na strome či stĺpiku. Preto čo najskôr skúšame umiestnenie postavičky vedľa QR kódu aj vonku (míľnik M5).
2. **Stavať na tom, čo funguje.** Máme aplikáciu, ktorá nájde QR kód a položí naň kocku. Každý ďalší krok (postavička, zbierka, cieľ) ju len rozširuje.
3. **MVP pred rozšíreniami.** Najprv jedna trasa, pár postavičiek, zbierka a odznak. GPS overenie, body a viac trás prídu, až keď základ funguje.
4. **Offline-first.** Na turistických trasách býva slabý signál. Postavičky, texty aj zbierka sú uložené priamo v aplikácii – internet nie je potrebný.
5. **Hra musí mať zmysel.** Postavička nie je len odmena, ale nesie aj krátku informáciu o mieste – výlet je zábavný aj obohacujúci.
6. **Jedna konkrétna trasa.** Všetko demonštrujeme na jednej trase s 3–5 zastávkami. Ďalšie trasy sú len otázka obsahu, nie novej technológie.
7. **Otvorené riešenie.** Kód aj dokumentácia sú verejné (MIT / CC-BY).

## 🔗 Súvisiace

- [Knife Management](./Knife_Management/index.md) – plán KNIFE článkov
- [Docusaurus Guide](./Docusaurus-Guide/index.md) – ako písať dokumentáciu

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
