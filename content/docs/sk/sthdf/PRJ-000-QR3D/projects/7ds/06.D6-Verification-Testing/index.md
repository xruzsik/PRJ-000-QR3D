---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.493620+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "1a21c4d2-2add-4a3c-a148-8f647cbabe5d"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "D6 – Verification & Testing"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Plán overenia a testovania QR3D – technické testy, test na trase s používateľmi a výsledky."


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
modified: "2026-11-16 18:00"

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

# D6 – Verification & Testing

## 🧪 Technické testy

| # | Test | Postup | Očakávaný výsledok | Výsledok |
|---|------|--------|--------------------|----------|
| T1 | Čitateľnosť QR | QR 5 / 10 / 15 cm, vzdialenosť 0,5 / 1 / 2 m, rôzne uhly, vnútri aj vonku | 10 cm kód sa načíta z 1 m (metrika K1) | [doplniť] |
| T2 | Offline režim | zapnúť režim lietadlo, prejsť celú hru | všetko funguje bez internetu | [doplniť] |
| T3 | Čas zobrazenia | merať od namierenia na kód po zjavenie postavičky | do 3 s (K2) | [doplniť] |
| T4 | Stabilita postavičky | obísť postavičku dookola, pohnúť mobilom | postavička zostane na mieste (K3) | [doplniť] |
| T5 | Zariadenia | aspoň 2 rôzne telefóny s Androidom | funguje na oboch (K4) | [doplniť] |
| T6 | Zbierka | získať 2 postavičky, zatvoriť a znova otvoriť aplikáciu | postavičky zostanú v zbierke | [doplniť] |
| T7 | Cudzí / poškodený QR | naskenovať iný QR kód alebo čiastočne zakrytý kód | aplikácia oznámi *„Neznámy kód: …“*, nespadne | [doplniť] |
| T8 | Svetelné podmienky | test na priamom slnku, v tieni, pri zamračenom počasí | QR sa načíta a postavička sa zobrazí | [doplniť] |
| T9 | Úvodná obrazovka | spustiť aplikáciu, nájsť 1 postavičku, vrátiť sa tlačidlom Späť | zastávky v poradí, nájdená s menom a ✓, ostatné „???“; tlačidlo *Pokračovať*; kamera beží až po ťuknutí (US-08, US-09) | [doplniť] |
| T10 | Nápoveda | po *Začať* mieriť 5 s mimo kódu; potom zakryť objektív | zobrazí sa tip „priblíž sa ku kódu…“; so zakrytým objektívom po 2 s „je tu príliš tma…“; po nájdení kódu zmizne (US-07) | [doplniť] |
| T11 | 3D tlačená QR doska | skontrolovať rozmer (QR 100 mm) a rovinnosť; naskenovať z 0,5 / 1 / 2 m, pod uhlom, na slnku aj v tieni; porovnať s papierom | kód sa načíta ako papierový (K1), postavička stojí na zemi pod kódom na stĺpiku | [doplniť] |
| T12 | Prehriatie a batéria | 30 min hry vonku, raz aj pri nabíjaní; sledovať pokles batérie | pri prehriatí upozornenie „Telefón je horúci…“, aplikácia nespadne (NFR-07) | [doplniť] |

## 🤖 Automatické testy (unit testy)

V projekte je **57 unit testov**, ktoré bežia na PC bez telefónu (`./gradlew :app:testDebugUnitTest` v priečinku `src/`):

| Testy | Počet | Čo overujú |
|-------|-------|------------|
| `QrPoseSolverTest` | 2 | výpočet polohy kódu zo 4 rohov |
| `QrOrientationTest` | 7 | určenie natočenia podľa rohových štvorcov QR |
| `FramesAndFilterTest` | 9 | prevody súradníc, filter zhodných záchytov |
| `PoseSmootherTest` | 3 | plynulý pohyb modelu |
| `CompanionTest` | 8 | sprevádzanie hráča (chôdza, cval, túlanie, návrat po skoku súradníc) |
| `OnGroundTest` | 2 | postavička na zemi pod kódom na stĺpiku; kód pri zemi ostane |
| `ScanPacerTest` | 3 | ako často čítať QR (pri kóde, cestou, pri prehriatí) |
| `CatalogTest` | 11 | skutočný `route.json`: modely a animácie existujú v GLB, unikátne id, rozumné veľkosti, aliasy; chybné súbory hlásia riadok, postavičku a pole |
| `JsonTest` | 3 | čítačka JSON (`Json.kt`) |
| `SearchHintTest` | 4 | kedy a aký tip sa ukáže (po 5 s bez kódu, po 2 s bez sledovania s príčinou; US-07) |
| `TrailProgressTest` | 5 | zbierka a odznak |

*Výsledok posledného behu: [doplniť po spustení].*

## 👥 Test na trase s používateľmi

- **Kde:** krátka testovacia trasa s 3–5 dočasne umiestnenými QR kódmi (s povolením), prípadne areál školy.
- **Kto:** 3–5 ľudí, ideálne aj dieťa a niekto, kto AR nikdy nepoužíval.
- **Scenár:** „Prejdi trasu a nazbieraj všetky postavičky.“ Nič nevysvetľujeme vopred.
- **Sledujeme:** či kódy nájdu a naskenujú bez pomoci, kde zaváhajú, či dôjdu do cieľa, ako dlho to trvá.
- **Po teste 3 otázky:** Bavilo ťa to? (1–5) · Šiel by si na ďalšiu trasu s touto hrou? (1–5) · Čo bolo mätúce alebo čo by si pridal?

## 📊 Výsledky

*Doplníme po testovaní (míľnik M8) – súhrn, porovnanie s metrikami v [Measures](../01.D1-Management/04.Decisions/05.Measures/index.md) a navrhnuté úpravy.*

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../index.md)
