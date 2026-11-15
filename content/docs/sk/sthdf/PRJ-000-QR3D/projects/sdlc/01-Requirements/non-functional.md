---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.629286+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "e73ddb86-e7d2-4bb2-8dc0-898d41469d65"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "non functional"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Nefunkčné požiadavky hry Postavičky na trase – výkon, presnosť AR, offline, súkromie, bezpečnosť."


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
modified: "2026-11-15 23:00"

# Stav dokumentu – default "backlog"
status: "draft"

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

> 🚧 **Koncept** – pripravený z 7Ds dokumentácie, tím ho ešte prejde a upraví.


# 🔧 Non-Functional Requirements (NFR)

| ID | Oblasť | Požiadavka | Ako overíme |
|----|--------|-----------|-------------|
| NFR-01 | Výkon | postavička sa zobrazí do **3 s** od namierenia kamery na kód | stopky pri teste T-série v D6 |
| NFR-02 | Čitateľnosť QR | kód 10 × 10 cm sa načíta zo vzdialenosti aspoň **1 m** (spresní experiment M5) | meranie pri experimente M5 |
| NFR-03 | Presnosť AR | pri obchádzaní sa postavička neposunie o viac ako cca **10 cm** | značka na zemi + video z obchádzania |
| NFR-04 | Offline | celá hra (trasa, modely, zbierka, odznak) funguje **bez internetu** | test v režime lietadlo |
| NFR-05 | Kompatibilita | Android telefóny s podporou ARCore; overené aspoň na **2 rôznych** telefónoch | test na telefónoch tímu a testerov |
| NFR-06 | Použiteľnosť | hra je zrozumiteľná pre deti od 8 rokov; aspoň **4 z 5** testerov dokončí trasu bez pomoci | test na trase (M8) |
| NFR-07 | Batéria | QR sa číta len z každého 5.–10. snímku a po nájdení sa skenovanie vypne; AR kamera beží len pri zastávke | zmerať pokles batérie počas testovacej trasy |
| NFR-08 | Súkromie | žiadne účty ani osobné údaje; obraz z kamery sa spracúva len v telefóne a nikam sa neodosiela | kontrola oprávnení a sieťovej komunikácie aplikácie |
| NFR-09 | Bezpečnosť hráča | zastávky len na značenom chodníku; upozornenie na pozornosť pri chôdzi | kontrola trasy pred testom, review textov |
| NFR-10 | Odolnosť v teréne | QR kódy zalaminované, čitateľné aj po daždi | kontrola kódov pred a po teste |
| NFR-11 | Udržiavateľnosť | obsah trasy (zastávky, texty, posuny) v JSON oddelene od kódu; licencie 3D modelov evidované | review pri pridaní zastávky (US-11) |

---
**Navigation:** [⬆️ Up](./index.md) · [🏠 Home](../index.md)
