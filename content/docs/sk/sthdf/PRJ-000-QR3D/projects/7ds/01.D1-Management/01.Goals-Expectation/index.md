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

Vytvoriť Android aplikáciu **„Postavičky na trase“**, ktorá premení turistickú trasu na hru: na zaujímavých miestach trasy sú QR kódy, po ich naskenovaní sa v rozšírenej realite (AR) vedľa kódu objaví 3D postavička viazaná na dané miesto a uloží sa do zbierky. Kto nazbiera všetky postavičky, získa v cieli odznak. Cieľom je **motivovať ľudí ísť von a dôjsť až do cieľa**.

## 📌 Čiastkové ciele

| # | Cieľ | Ako overíme splnenie |
|---|------|----------------------|
| G1 | Nájsť QR kód v obraze kamery a určiť jeho polohu v priestore | aplikácia položí objekt na QR kód *(✅ hotové – kocka na QR)* |
| G2 | Umiestniť postavičku **vo vzťahu ku QR kódu** (napr. 1 m vedľa, na zemi) | postavička stojí na správnom mieste a „neuteká“ pri pohybe mobilu |
| G3 | Rôzne postavičky pre rôzne zastávky | každý QR kód trasy zobrazí inú postavičku s krátkym textom o mieste |
| G4 | Zbierka | naskenované postavičky sa uložia a ostanú aj po reštarte aplikácie |
| G5 | Cieľ a odznak | po nazbieraní všetkých postavičiek trasy aplikácia udelí odznak |
| G6 | Offline režim | celá hra funguje bez internetu (v horách býva slabý signál) |
| G7 | Testovacia trasa | 3–5 QR kódov na krátkej trase, otestované s používateľmi |
| G8 | Dokumentácia podľa KNIFE (7Ds, SDLC, Q12) a 1 KNIFE článok | všetky deliverables vyplnené a publikované na GitHub Pages |
| G9 *(bonus)* | Overenie polohy cez GPS | postavičku získa len ten, kto je naozaj pri zastávke |
| G10 *(bonus)* | Body a viac trás | body za trasu, výber z viacerých trás |

## 🤝 Očakávania

- **Od tímu:** pravidelná práca (cca 4–6 h týždenne na člena), práca vo vetve `dev`, záznam do [Work Activities Evidence](../05.WorkActivitiesEvidence/index.md) aspoň raz týždenne.
- **Od vyučujúceho:** spätná väzba k Project Summary a k pitchu, schválenie zmeny zamerania projektu.
- **Od používateľov (testerov):** 3–5 ľudí, ktorí prejdú testovaciu trasu a povedia, čo ich bavilo a čo prekážalo.

## 🚫 Mimo rozsahu (out of scope)

- verzia pre iOS,
- online rebríček, používateľské účty a multiplayer,
- editor trás pre správcov (obce, kluby) – v MVP trasu pripravujeme ručne,
- trvalé rozmiestnenie QR kódov v teréne – na to treba povolenie správcu územia.

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../index.md)
