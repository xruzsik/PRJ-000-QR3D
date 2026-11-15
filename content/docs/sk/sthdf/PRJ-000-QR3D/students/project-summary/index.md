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
description: "Zhrnutie projektu Postavičky na trase – turistická hra s AR a QR kódmi."


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
modified: "2026-11-15 12:00"

# Stav dokumentu – default "backlog"
status: "inprogress"

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

> ℹ️ **Zmena zamerania:** pôvodný návrh „Okno do minulosti“ (3D model zrúcaniny zakódovaný v QR kóde, web viewer) sme nahradili hrou **„Postavičky na trase“**. Technický základ – nájdenie QR kódu v priestore a zobrazenie 3D objektu v AR – zostáva, mení sa využitie. Zmena čaká na schválenie vyučujúcim.

## 🔖 Identifikácia

- **Projekt:** PRJ-000-QR3D
- **Repozitár:** https://github.com/xruzsik/PRJ-000-QR3D
- **Predmet:** Systémové myslenie v IT a digitálna fabrikácia (STHDF), FIIT STU, 2026/2027

## 1️⃣ Názov projektu

- **Názov projektu:** `PRJ_000_QR3D_ST043_ST045`
- **Pracovný názov:** *Postavičky na trase – turistická hra s AR a QR kódmi*
- **Stručný popis:** Android aplikácia, ktorá premení turistickú trasu na hru. Na zaujímavých miestach trasy sú QR kódy. Po naskenovaní sa vedľa kódu v rozšírenej realite (AR) objaví 3D postavička viazaná na dané miesto – napríklad zviera, ktoré tu žije, alebo postava z miestnej povesti – s krátkym príbehom. Postavička sa uloží do zbierky a kto nazbiera všetky, získa v cieli odznak.

## 2️⃣ Členovia tímu

| ST ID | Meno | Rola v tíme | Kompetencie |
|-------|------|-------------|-------------|
| ST043 | Dániel Ruzsik | Project Lead, Developer | koordinácia, dokumentácia (7Ds, SDLC, Q12), Android aplikácia (Kotlin, ARCore) |
| ST045 | Lukáš Šebök | 3D / Designer | 3D postavičky (Blender, GLB), UI aplikácie, príprava QR kódov na tlač |

## 3️⃣ Motivácia tímu

- **Prečo tento projekt:** spája softvér s fyzickým svetom – vytlačený QR kód v teréne, 3D postavička a reálny pohyb v prírode – presne v duchu digitálnej fabrikácie.
- **Aký problém rieši:** ľudia, najmä deti a mladí, sa hýbu čoraz menej a turistika im pripadá nudná. Zaujímavé miesta na trase majú informačné tabule, ktoré si takmer nikto nečíta. Chýba dôvod ísť von, dôvod dôjsť až do cieľa a dôvod zastaviť sa a dozvedieť sa niečo o mieste.
- **Prečo je to dôležité:** hra s hľadaním a zbieraním motivuje deti aj dospelých a nenápadne im odovzdá informácie o prírode a mieste. Pre obce a turistické regióny je to lacný spôsob, ako oživiť existujúce trasy.
- **Osobný význam:** _(doplňte: čo vás na téme baví, čo sa chcete naučiť)_

## 4️⃣ Cieľ a pridaná hodnota projektu

- **Finálny cieľ:** funkčná Android aplikácia (MVP) a testovacia trasa s 3–5 QR kódmi, otestovaná s používateľmi.
- **Pridaná hodnota:**
  - spája tri overené princípy: **hľadanie** (geocaching), **zbieranie** (turistické známky) a **rozšírenú realitu**,
  - každá postavička patrí ku konkrétnemu miestu a nesie jeho príbeh – nie je to globálna hra bez väzby na trasu,
  - funguje **offline** – trasa, postavičky aj zbierka sú uložené v telefóne (v horách býva slabý signál),
  - nízke náklady pre prevádzkovateľa trasy – stačí vytlačiť QR kódy, netreba server ani nové tabule.
- **Ako spoznáme úspech (merateľné kritériá):**
  - [ ] QR kód veľkosti 10 cm aplikácia nájde vonku zo vzdialenosti aspoň 1,5 m *(spresníme experimentom M5)*,
  - [ ] postavička stojí vedľa kódu na zemi a pri obchádzaní sa neposunie o viac ako cca 10 cm,
  - [ ] postavička sa zobrazí do 3 s od namierenia na kód,
  - [ ] celá hra funguje v režime lietadlo,
  - [ ] aspoň 3 z 5 testovacích používateľov prejdú celú trasu a získajú odznak bez pomoci tímu.

## 5️⃣ Popis riešenia

- **Komponenty:**
  1. **AR obrazovka** – kamera cez ARCore, nájdenie QR kódu v obraze a výpočet jeho polohy v priestore.
  2. **Umiestnenie postavičky** – kotva (Anchor) v mieste kódu, postavička s posunom (napr. 1 m vedľa) položená na zem.
  3. **Obsah trasy** – zastávky, postavičky a texty v JSON súbore priamo v aplikácii; QR kód nesie len identifikátor zastávky.
  4. **Zbierka a odznak** – lokálne uloženie získaných postavičiek, odznak po dokončení trasy.
  5. **Fyzická časť** – vytlačené a zalaminované QR kódy na testovacej trase.
- **Technológie:** Kotlin, Google ARCore, ML Kit Barcode Scanning alebo ZXing (čítanie QR), SceneView / Filament (3D), Room alebo DataStore (zbierka), Blender a formát GLB (postavičky).
- **Mimo rozsahu MVP:** iOS, používateľské účty a online rebríček, editor trás pre správcov, trvalé rozmiestnenie QR kódov v teréne.
- **Rozšírenia (ak zostane čas):** overenie polohy cez GPS (proti „zbieraniu“ z fotiek kódov), body a viac trás, webová stránka pre tých, čo naskenujú kód bez aplikácie.

## 6️⃣ Projektový plán

- **Odhadovaný čas na prácu:** 4–6 hodín týždenne na člena
- **Predpokladaný stav v čase prezentácie:** funkčná aplikácia (MVP) s 3–5 postavičkami a testovacia trasa
- **Hlavné míľniky:**
  - [x] Repozitár, dokumentácia a základ AR aplikácie (kocka na QR kóde)
  - [ ] Zadanie – Project Summary (nové zameranie) schválené, About Me, 7Ds D1–D2
  - [ ] Pitch – problém, riešenie, hodnota (Q12), user stories
  - [ ] Experiment – spoľahlivé umiestnenie objektu vedľa QR kódu, aj vonku
  - [ ] Postavičky a zbierka – rôzne postavičky pre rôzne kódy, uloženie zbierky
  - [ ] Trasa a cieľ – 3–5 zastávok, odznak, offline režim
  - [ ] Testovanie na trase s 3–5 používateľmi
  - [ ] Finálna prezentácia – demo, výsledky, KNIFE článok, reflexia

Podrobnosti: [Goals & Expectations](../../projects/7ds/01.D1-Management/01.Goals-Expectation/index.md) · [Plans & Milestones](../../projects/7ds/01.D1-Management/02.Plans-Milestones/index.md) · [Riziká](../../projects/7ds/01.D1-Management/04.Decisions/04.Risks/index.md)

## Navigácia
- [↩️ Späť](../index.md)
