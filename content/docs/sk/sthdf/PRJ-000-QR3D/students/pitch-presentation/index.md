---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.687814+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "f0eb3899-4dad-43ad-8c2a-e9cd109d692b"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "pitch presentation"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Pitch projektu Postavičky na trase – obsah slajdov a časovanie."


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
modified: "2026-11-16 18:00"

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

# 🎤 Pitch Presentation

> 🚧 **Koncept obsahu.** Slajdy a ukážku (demo) treba ešte pripraviť; časy sú orientačné pre 6 minút.

**Postavičky na trase** – *Každá turistická trasa môže byť hrou, na ktorej konci sa oplatí byť.*

## 1. Problém *(cca 1 min)*
- Deti a mladí sa hýbu čoraz menej; turistika im pripadá nudná – „ideme hore, pozrieme sa a ideme dole“.
- Rodičia a vedúci oddielov musia deti na výlet presviedčať.
- Zaujímavé miesta na trase majú informačné tabule, ktoré si takmer nikto nečíta.
- **Chýba dôvod ísť von, dôvod dôjsť až do cieľa a dôvod zastaviť sa.**

## 2. Riešenie *(cca 1,5 min + demo)*
- Na zaujímavých miestach trasy sú QR kódy.
- Naskenuješ kód v aplikácii → vedľa kódu sa v rozšírenej realite objaví **3D postavička** viazaná na to miesto (zviera, ktoré tu žije, postava z miestnej povesti) s krátkym príbehom.
- Postavička sa uloží do **zbierky**; kto nazbiera všetky, získa v cieli **odznak**.
- Funguje **offline** – v horách netreba signál.
- **Demo:** telefón namierený na vytlačený QR kód → objekt sa zjaví vedľa neho.

## 3. Hodnota *(cca 1 min)*
- **Rodiny a deti:** výlet je hra, deti chcú dôjsť do cieľa.
- **Školy a oddiely mládeže:** hotový program na výlet, ktorý zabaví aj naučí.
- **Obce a turistické regióny:** lacné oživenie existujúcich trás – stačí vytlačiť QR kódy, netreba nové tabule ani server.
- **V čom sme iní:** spájame overené princípy – hľadanie (geocaching), zbieranie (turistické známky) a AR (Pokémon GO) – ale viazané na **konkrétnu trasu a príbeh jej miest**. Podobnú hru na Slovensku sme nenašli.

## 4. Biznis model *(cca 0,5 min, hypotéza)*
- Aplikácia je pre turistov **zadarmo**.
- Platí **prevádzkovateľ trasy** (obec, regionálna organizácia cestovného ruchu, turistický klub) – jednorazovo za prípravu trasy (postavičky, texty, QR kódy na tlač) a malý ročný poplatok za údržbu obsahu.
- Regióny už dnes financujú QR kódy na náučných chodníkoch – zákazník existuje.

## 5. Tím *(cca 0,5 min)*
- **Dániel Ruzsik (ST043)** – Project Lead, Android vývoj (Kotlin, ARCore), dokumentácia. Skaut a turista – cieľovú skupinu pozná z praxe.
- **Lukáš Šebök (ST045)** – 3D postavičky (Blender), UI, QR kódy na tlač.
- **Čo už máme:** funkčnú aplikáciu – 4 animované zvieratá pri QR kódoch, zbierku, odznak; zviera po nájdení ide s hráčom.

## 6. Call to action *(cca 0,5 min)*
- **Spätná väzba:** bavila by vás takáto hra? Ktoré miesto by ste chceli vidieť ako prvú trasu?
- **Testeri:** hľadáme 3–5 ľudí (ideálne rodina s deťmi alebo oddiel), ktorí prejdú testovaciu trasu.
- **Kontakt na trasu:** poznáte obec, klub alebo správcu chodníka, ktorý by trasu povolil?

## 🖼️ Návrh slajdov
| # | Slajd | Obsah |
|---|-------|-------|
| 1 | Titul | názov, vízia v jednej vete, tím |
| 2 | Problém | fotka nudiaceho sa dieťaťa na trase / nečítaná tabuľa, 3 krátke body |
| 3 | Riešenie | schéma: QR kód → AR postavička → zbierka → odznak |
| 4 | Demo | živá ukážka alebo krátke video z telefónu |
| 5 | Hodnota a konkurencia | tabuľka „my vs. geocaching / Pokémon GO / QR na náučných chodníkoch“ |
| 6 | Biznis model a tím | kto platí, kto sme |
| 7 | Call to action | čo od vás potrebujeme |

## 🛠️ Štruktúra pitchu
1. **Problém** – aký problém riešime?
2. **Riešenie** – čo sme vyvinuli?
3. **Trh / Kontext** – pre koho to je, aký má dopad?
4. **Tím** – kto sme, aké máme roly?
5. **Výzva** – čo potrebujeme (čas, ľudí, investíciu)?

## ⏱️ Časovanie
- Max 5–7 minút na prezentáciu
- 2–3 minúty Q&A

## 📊 Podklady
- 3–5 slajdov (stručné, vizuálne) – návrh vyššie má 7, slajdy 5 a 6 sa dajú spojiť
- 1 demo / ukážka výsledku

## 🧭 Tipy
- Menej textu, viac vizualizácií
- Jedna hlavná myšlienka na slide
- Demo si vopred vyskúšaj na mieste prezentácie (svetlo, vytlačený kód) a maj záložné video

## Navigácia
- [↩️ Späť](../index.md)
