---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.491470+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "61360c03-5411-4509-9932-fa3db726ecac"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "D5 – Implementation"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Implementácia Android aplikácie Postavičky na trase – technológie, štruktúra kódu, stav a rozhodnutia."


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

# D5 – Implementation

*Stav k 16.11.2026 (commit `277b3ca`): hracia slučka funguje (úvodná obrazovka → skenovanie → postavička → zbierka → odznak), otestovaná na Samsung Galaxy A53 pri stole. Test vonku a na trase ešte nebol.*

Zdrojový kód: [`src/` v repozitári](https://github.com/xruzsik/PRJ-000-QR3D/tree/main/src) · návod na build a spustenie: [`src/README.md`](https://github.com/xruzsik/PRJ-000-QR3D/tree/main/src/README.md)

## 📱 O aplikácii

| | |
|---|---|
| **Názov / balík** | Postavičky na trase · `com.smvit.ar` · verzia 0.1 |
| **Platforma** | Android 9+ (minSdk 28, targetSdk 37), len telefóny s podporou ARCore (AR je povinné) |
| **Oprávnenia** | kamera; rýchle snímanie pohybových senzorov (bez neho A53 posielal dáta len 6× za sekundu a sledovanie sa neustálilo) |
| **Testované na** | Samsung Galaxy A53 |
| **Postavičky** | Líška, Vlk, Srna, Jeleň (+ testovacia kocka) – animované modely Quaternius *Ultimate Animated Animals*, licencia CC0 |
| **Texty** | slovenské; príbehy postavičiek sú koncept na kontrolu tímom |

## 🎮 Čo aplikácia robí

1. **Úvodná obrazovka trasy:** názov trasy, krátke „ako hrať“, zoznam zastávok v poradí (meno postavičky až po jej nájdení, s ✓), počítadlo *Zbierka 1/4*, upozornenie ⚠️ *„Bezpečne na trase“* a tlačidlo **Začať** (po prvom náleze **Pokračovať**). Kamera sa zapne až po ťuknutí; systémové tlačidlo Späť vráti hráča na úvodnú obrazovku. Dĺžka a čas trasy sa zobrazia, len ak sú vyplnené v `route.json`.
2. Po štarte čaká, kým ARCore začne sledovať priestor (*„Pomaly pohybuj telefónom…“*), a ešte 1,5 s, kým sa poloha ustáli.
3. Hráč namieri kameru na QR kód. Kód nesie len `smvit-ar:<id>` (napr. `smvit-ar:fox`), všetko ostatné je v súbore trasy `route.json` v aplikácii.
4. Ak sa **5 s** nenájde žiadny známy kód, zobrazí sa tip: kým sledovanie nebeží *„pomaly pohni telefónom do strán…“*, inak *„priblíž sa ku kódu, aby bol celý v zábere…“*.
5. Postavička sa ukáže až po **3 zhodných záchytoch** kódu (*„Nájdené: Líška – podrž telefón (2/3)“*), takže jedna chybná detekcia ju nepostaví zle.
6. Vedľa kódu sa objaví animovaná postavička a karta s jej menom a príbehom.
7. Postavička sa uloží do **zbierky** (*„Nové! Pridané do zbierky (2/4)“*); zbierka ostane v telefóne aj po reštarte.
8. Keď hráč od kódu odíde (viac ako 1,5 m) alebo ho 2 s nevidí, postavička kód **opustí a sprevádza hráča** – ide za ním krokom, pri väčšej vzdialenosti cvalom, a keď hráč stojí, túla sa pred ním. Pritom narastie z „kódovej“ veľkosti na väčšiu.
9. Po nájdení všetkých postavičiek trasy sa zobrazí 🏅 **„Odznak získaný!“**
10. Obrazovka **Zbierka**: nájdené postavičky s príbehom, chýbajúce ako *„??? – ešte si ju nenašiel“*; vymazanie zbierky (dvoma ťuknutiami, aby sa nevymazala omylom).

## 📁 Štruktúra kódu

```text
src/
├── app/src/main/java/com/smvit/ar/
│   ├── MainActivity.kt        # vstup, obmedzenie na 60 Hz, úvodná obrazovka ↔ AR obrazovka
│   ├── ArScreen.kt            # AR obrazovka: skenovanie, sledovanie, umiestnenie, odpojenie postavičiek
│   ├── Catalog.kt             # načíta a skontroluje route.json (postavičky, zastávky, aliasy)
│   ├── Json.kt                # malá čítačka JSON (bez knižnice, aby šla testovať na PC)
│   ├── SearchHint.kt          # kedy ukázať tip (5 s bez nájdeného kódu)
│   ├── TrailProgress.kt       # postup na trase a odznak
│   ├── CollectionStore.kt     # uloženie zbierky v telefóne
│   ├── TrailUi.kt             # úvodná obrazovka, karta príbehu, zbierka, odznak, všetky texty (SK)
│   ├── qr/                    # výpočet polohy QR kódu v priestore
│   │   ├── QrScanner.kt       #   ML Kit na obraze z ARCore → poloha kódu vo svete
│   │   ├── QrPoseSolver.kt    #   4 rohy + tlačená veľkosť → poloha a natočenie
│   │   ├── QrOrientation.kt   #   ktorý roh je ktorý (podľa rohových štvorcov QR)
│   │   ├── QrPoseFilter.kt    #   prijme polohu až po niekoľkých zhodných záchytoch
│   │   ├── PoseSmoother.kt    #   plynulý pohyb modelu medzi meraniami
│   │   ├── Frames.kt          #   prevody súradníc (kamera, kód, „zvislý“ rámec)
│   │   └── Geometry.kt        #   vektory, matice, kvaternióny
│   └── companion/
│       ├── Companion.kt       # správanie postavičky, ktorá sprevádza hráča
│       └── Floor.kt           # odhad výšky podlahy
├── app/src/main/assets/route.json # obsah trasy: postavičky, modely, veľkosti, posuny, animácie, príbehy, zastávky
├── app/src/main/assets/models/ # GLB modely (fox, wolf, deer, stag, placeholder_cube)
├── app/src/test/              # 48 unit testov
└── content/
    ├── markers/               # QR kódy na tlač (PDF, s pravítkom 10 cm)
    ├── qrsheet/               # PC nástroj, ktorý generuje PDF s kódmi
    └── tools/                 # glTF → GLB, testovacia kocka
```

## 🛠️ Technológie

| Oblasť | Technológia | Prečo |
|--------|-------------|-------|
| Jazyk, UI | Kotlin, Jetpack Compose (Material 3) | moderný štandard pre Android |
| AR | Google ARCore 1.57 | sledovanie polohy telefónu v priestore, kotvy, body prostredia |
| 3D | SceneView 4.62 (Filament) | vykreslenie a animácie GLB modelov v AR, priamo pre Compose |
| Čítanie QR | Google ML Kit Barcode Scanning | spoľahlivo vráti obsah aj 4 rohy kódu |
| Poloha kódu | vlastný výpočet (`qr/`) | pozri rozhodnutia nižšie |
| Obsah trasy | `assets/route.json` + vlastná čítačka JSON (`Json.kt`) | obsah sa mení bez zásahu do kódu; chyby v súbore nahlási aplikácia aj testy (s menom postavičky a poľa); testy overujú modely a názvy animácií |
| Zbierka | SharedPreferences | stačí množina ID; funguje offline, prežije reštart |
| 3D modely | GLB (Quaternius, CC0), Python skript na konverziu glTF → GLB | štandardný formát pre mobilné 3D |
| Build | Gradle 9 (AGP 9.4, Kotlin 2.4), JDK 21 | |

## ✅ Stav implementácie

| Časť | Stav | Poznámka |
|------|------|----------|
| AR kamera (ARCore) | ✅ | roviny a hĺbka vypnuté (výkon na A53) |
| Nájdenie QR kódu a jeho polohy v priestore | ✅ | vlastný výpočet zo 4 rohov a tlačenej veľkosti |
| Správne natočenie kódu (aj bokom / hore nohami) | ✅ | podľa rohových štvorcov QR |
| Postavička vedľa kódu (posun) | ✅ | zatiaľ zmenšené na stôl (líška 35 cm, 20 cm vedľa kódu) |
| Postavička stojí zvislo aj pri kóde na stĺpiku | ✅ | „zvislý“ rámec (`Frames.upright`) |
| 3D postavičky s animáciou | ✅ | 4 zvieratá: státie, chôdza, cval |
| Rôzne postavičky podľa obsahu QR | ✅ | `smvit-ar:<id>` → záznam v `route.json` |
| Obsah trasy v súbore `route.json` | ✅ | postavičky, zastávky, aliasy; kontrola chýb pri štarte aj v testoch |
| Príbeh postavičky | ✅ | karta s textom (koncept) |
| Zbierka (uloženie) | ✅ | SharedPreferences |
| Odznak za celú trasu | ✅ | |
| Postavička sprevádza hráča | ✅ | navyše oproti pôvodnému návrhu |
| Postavičky v životnej veľkosti na zemi pri stĺpiku | ⏳ | úprava `offset`, `modelSizeMeters`, `roamSizeMeters` v `route.json` pre trasu |
| Úvodná obrazovka trasy (zoznam zastávok, dĺžka) | 🔄 | US-08: obrazovka hotová; dĺžka a čas trasy chýbajú v `route.json` (`lengthKm`, `minutes`) |
| Upozornenie na bezpečnosť pri chôdzi | ✅ | US-09: na úvodnej obrazovke; kamera sa zapne až po *Začať* |
| Nápoveda po 5 s bez nájdeného kódu | ✅ | US-07: dva tipy podľa toho, či už beží sledovanie |
| Test vonku a na trase | ⏳ | míľniky M5, M8 |
| GPS overenie | ⏳ | rozšírenie |

**Dočasné:** kódy z `qr-test-sheet.pdf` (`test_cube`, `test_cube_2`) zatiaľ zastupujú líšku a srnu (`aliases` v `route.json`), kým sa nevytlačí `animals.pdf`. Testovacia kocka (`axis_cube`) je v `route.json`, ale nie je zastávkou, takže sa nepočíta do zbierky.

Legenda: ✅ hotovo · 🔄 rozpracované · ⏳ čaká

## 🧠 Dôležité implementačné rozhodnutia

| # | Rozhodnutie | Prečo | Alternatíva |
|---|-------------|-------|-------------|
| I-1 | **Polohu kódu počítame sami** zo 4 rohov a známej tlačenej veľkosti (homografia + spresnenie Levenberg–Marquardt) | nepotrebuje roviny ani hĺbku, funguje aj pre kód na stĺpiku či strome | hit test na rovinu ARCore; ARCore Augmented Images |
| I-2 | **Vypnutá detekcia rovín aj hĺbky** | na A53 sledovanie niekoľkokrát za sekundu zastavili; QR ich nepotrebuje | zapnuté roviny a hit test |
| I-3 | **Kým je kód v zábere, model sleduje priamo meranie kódu**; mimo záberu ho drží kotva ARCore | ARCore na A53 „uteká“ až o ~20 cm, meranie kódu je presné voči kamere | len kotva ARCore |
| I-4 | Postavička sa ukáže až po **3 zhodných záchytoch**; meranie s chybou nad 3 px sa ignoruje | jedna zlá detekcia nesmie postaviť model vedľa | zobraziť hneď |
| I-5 | Natočenie kódu podľa **rohových štvorcov QR**; pri sledovaní podľa predchádzajúceho natočenia | ML Kit číta rohy od rohu obrazu, nie kódu; rozmazaný obraz pri pohybe nechal viac ako polovicu detekcií nerozhodnutých | spoľahnúť sa na poradie z ML Kit |
| I-6 | **Výkon:** QR najviac 8× za sekundu, displej 60 Hz, lacnejší odhad svetla | telefón sa prehrieval, sledovanie zaostávalo | každý snímok, 120 Hz |
| I-7 | **Podlaha** z bodov ARCore 0,7–2,2 m pod telefónom, inak predpoklad 1,3 m pod telefónom | bez detekcie rovín | detekcia rovín |
| I-8 | **Obsah trasy v súbore `app/src/main/assets/route.json`**, QR nesie len `smvit-ar:<id>` *(pôvodne v kóde `Catalog.kt`, presunuté 16.11.)* | obsah (postavičky, príbehy, posuny, poradie zastávok) sa upraví bez zmeny kódu; pri zmene modelu či umiestnenia netreba nové tlačiť; chybný súbor zastaví aplikáciu pri štarte so správou, ktorá pomenuje postavičku a pole; testy čítajú skutočný súbor a overia modely aj animácie. Súbor je súčasťou APK – zmena = nový build | obsah v kóde (`Catalog.kt`); sťahovanie zo servera; URL v QR |
| I-9 | Postavička po odchode hráča **sprevádza hráča** | silnejší zážitok, postavička „ide s tebou“ | zostane pri kóde |
| I-10 | **Úvodná obrazovka, kamera až po *Začať*** | hráč nechodí so zapnutou kamerou (US-09), šetrí batériu; mená zastávok sa odkryjú až po nájdení | AR hneď po spustení |
| I-11 | **Vlastná čítačka JSON** (`Json.kt`) namiesto `org.json` | `route.json` sa dá načítať a skontrolovať v unit testoch na PC, bez ďalšej knižnice | `org.json` (len na Androide), Gson/Moshi |

Väčšie rozhodnutia patria aj do [záznamu rozhodnutí](../01.D1-Management/04.Decisions/index.md).

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../index.md)
