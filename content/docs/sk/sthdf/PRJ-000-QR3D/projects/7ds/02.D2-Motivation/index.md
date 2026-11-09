---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.483330+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "c53acbef-c7e5-449b-8b61-eeac83c0878f"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "D2 – Motivation"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Prečo projekt QR3D vzniká – problém, vízia, persony a hodnota pre stakeholderov."


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

# D2 – Motivation

## 🌄 Vízia

> **Každá turistická trasa môže byť hrou, na ktorej konci sa oplatí byť.**

## ❓ Problém

Ľudia, najmä deti a mladí, sa hýbu čoraz menej a turistika im často pripadá nudná – „ideme hore, pozrieme sa a ideme dole“. Zaujímavé miesta na trase (vyhliadka, prameň, skalné útvary, lesostep) majú síce informačné tabule, no tie si takmer nikto nečíta. Chýba **dôvod ísť von**, **dôvod dôjsť až do cieľa** a **dôvod zastaviť sa** a dozvedieť sa niečo o mieste.

## 💡 Naše riešenie v skratke

Na zaujímavých miestach trasy sú QR kódy. Po naskenovaní v aplikácii sa vedľa kódu v rozšírenej realite objaví 3D postavička viazaná na to miesto – napríklad zviera, ktoré tu žije, alebo postava z miestnej povesti – s krátkym príbehom. Postavička sa uloží do zbierky. Kto nazbiera všetky, získa v cieli odznak.

Spájame tri overené princípy: **hľadanie** (ako geocaching), **zbieranie** (ako turistické známky) a **rozšírenú realitu**.

## 👥 Persony

| Persona | Kto to je | Čo potrebuje | Čo mu/jej prekáža dnes |
|---------|-----------|--------------|------------------------|
| **Ema (10)** | dieťa na rodinnom výlete | hru, niečo, čo sa dá „chytiť“ a ukázať kamarátom | „Kedy už tam budeme?“ – trasa ju nebaví |
| **Rodičia Emy** | rodina, ktorá chce tráviť víkendy vonku | dôvod, prečo deti pôjdu na výlet rady | presviedčanie detí, aby išli von |
| **Marek (24)** | „zberateľ“, hrá hry v mobile | výzvu, ktorú môže dokončiť, a niečo nové | bežné trasy už pozná, nič nové ho neláka |
| **Vedúci oddielu** | vedie skupinu mládeže (skauti, turistický krúžok, škola) | program na výlet, ktorý deti zabaví aj naučí | musí všetko pripravovať sám |
| **Správca trasy** | obec, turistický klub, regionálna organizácia cestovného ruchu | lacno oživiť trasu a pritiahnuť návštevníkov | tabule sú drahé a nikto ich nečíta |

*Persony sú vymyslené – slúžia na tvorbu user stories a testovacích scenárov.*

## 🔗 Podrobnejšie

- [Stakeholder Analysis](./01.StakeholderAnalysis/index.md)

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../index.md)
