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
title: "D3 - Analysis"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Analýza projektu QR3D – existujúce riešenia, výskum, technické obmedzenia QR kódu a plánovaný experiment."


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

| Riešenie | Ako funguje | Obmedzenie pre náš prípad |
|----------|-------------|---------------------------|
| **AR Code** | WebAR bez aplikácie; 3D model v cloude, QR je odkaz | potrebuje internet, platené predplatné |
| **3DQR** | QR spustí AR scénu | potrebuje vlastnú aplikáciu, zameranie na firmy |
| **ARLOOPA, QR TIGER (AR QR)** | generátory AR QR kódov s hostingom | potrebujú internet, obsah je u poskytovateľa |
| **8th Wall** | bývalá veľká WebAR platforma | hostované služby v roku 2026 skončili – ukážka rizika závislosti od platformy |
| **Google `model-viewer`** | open source komponent na zobrazenie 3D modelu a AR na webe | potrebuje hotový súbor modelu (GLB), nemá offline režim z QR |

## 📚 Výskum

**Ertugrul et al. (2020): *Embedding 3D models in offline physical environments*** (Computer Animation and Virtual Worlds). Autori ukladajú voxelizovaný 3D model ako *octree* do QR kódu a väčšie modely komprimujú autoenkóderom. Motivácia je rovnaká ako naša – offline prostredie. Na zobrazenie však používajú vlastnú aplikáciu.

## 🧭 Čím sme iní

| | Komerčné AR QR | Výskum (2020) | **QR3D** |
|---|---|---|---|
| Bez aplikácie | väčšinou áno | nie | **áno** |
| Offline | nie | áno | **áno** |
| Bez servera / predplatného | nie | áno | **áno** |
| Hybrid offline + HD | nie | nie | **áno** |
| Časová os stavby | nie | nie | **áno (bonus)** |

## ⚙️ Technické obmedzenia QR kódu

- Najväčší QR kód (verzia 40, najnižšia korekcia chýb L) pojme **2 953 bajtov** v binárnom režime.
- Časť zaberie adresa viewera (cca 50 znakov) a kódovanie do textu (base64url pridá cca 33 %). Pre samotné dáta modelu tak zostáva **približne 2 KB**.
- Čím viac dát, tým hustejší kód a tým horšie sa skenuje. Prakticky budeme hľadať kompromis okolo verzie 20–30.
- **Odhad:** po kvantizácii súradníc a kompresii by sa malo zmestiť **niekoľko sto trojuholníkov** – na rozpoznateľný tvar stavby to stačí.

## 🧪 Plánovaný experiment (míľnik M4)

1. Vezmeme 3 modely rôznej zložitosti a zjednodušíme ich na 100 / 200 / 400 / 800 trojuholníkov.
2. Pre každý zmeriame veľkosť dát po kvantizácii (8 / 10 / 12 bitov na súradnicu) a po kompresii.
3. Vygenerujeme QR kódy a otestujeme čitateľnosť (veľkosť kódu × vzdialenosť × 2 telefóny).
4. Výsledok: tabuľka „koľko detailu sa oplatí dať do QR“ → podklad pre návrh (D4) aj pre KNIFE článok.

## 🔗 Podrobnejšie

- [Resources](./01.Resources/index.md) – ľudia, čas, náklady, nástroje

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../index.md)
