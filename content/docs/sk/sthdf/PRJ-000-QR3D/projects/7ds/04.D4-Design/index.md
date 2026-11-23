---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.489481+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "0b824284-f523-45b8-a564-e8fb7cff5587"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "D4 – Design"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Návrh riešenia QR3D – architektúra Android aplikácie, obsah QR kódu, umiestnenie postavičky, dátový model a obrazovky."


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

# D4 – Design

*Stav: návrh aktualizovaný podľa implementácie (16.11.2026). Ako je to naozaj spravené, opisuje [D5 – Implementation](../05.D5-Implementation/index.md). Diagram architektúry v draw.io treba prekresliť podľa tohto návrhu.*

## 🏗️ Architektúra

```text
 TERÉN                         ANDROID APLIKÁCIA (Kotlin + ARCore)
 ┌──────────────┐   kamera    ┌───────────────────────────────────────────────┐
 │              │             │ 0. úvodná obrazovka → Začať (kamera až potom)  │
 │  QR kód na   │ ──────────▶ │ 1. AR kamera (ARCore, bez rovín a hĺbky)       │
 │  zastávke    │             │ 2. ML Kit → obsah kódu + 4 rohy                │
 │„smvit-ar:fox“│             │ 3. vlastný výpočet polohy kódu (rohy+veľkosť)  │
 └──────────────┘             │ 4. postavička vedľa kódu (posun), potom ide    │
                              │    s hráčom                                    │
                              │ 5. uloženie do zbierky → odznak                │
                              └──────────────┬────────────────────────────────┘
                                             │ všetko lokálne v telefóne
                              ┌──────────────▼───────────────┐
                              │ trasa (assets/route.json) +  │
                              │ modely postavičiek (GLB)     │
                              │ zbierka (SharedPreferences)  │
                              └──────────────────────────────┘
```

Aplikácia nepotrebuje server ani internet – trasa, postavičky aj zbierka sú v telefóne.

## 🧩 Komponenty

| Komponent | Úloha | Technológia |
|-----------|-------|---------------------|
| **AR obrazovka** | zobrazuje kameru, hľadá QR kód, vykresľuje postavičku | ARCore + SceneView (Filament), Jetpack Compose |
| **Čítačka QR** | z obrazu kamery prečíta kód a nájde jeho rohy | ML Kit Barcode Scanning |
| **Poloha kódu** | zo 4 rohov a tlačenej veľkosti vypočíta polohu a natočenie kódu | vlastný výpočet (`qr/`); kotva ARCore, keď je kód mimo záberu |
| **Sprevádzanie** | postavička po odchode hráča ide s ním, stojí na odhadnutej podlahe | vlastná logika (`companion/`) |
| **Obsah trasy** | postavičky, modely, veľkosti, posuny, animácie, príbehy, poradie zastávok | súbor `assets/route.json` v aplikácii, načíta a skontroluje ho `Catalog.kt` |
| **Úvodná obrazovka** | trasa, zastávky, postup, bezpečnostné upozornenie, štart kamery | Jetpack Compose (`StartScreen`) |
| **Nápoveda** | tip, keď sa 5 s nenájde žiadny kód, alebo keď ARCore 2 s nesleduje (s príčinou: tma, rýchly pohyb, málo detailov) | `SearchHint.kt` |
| **Šetrenie výkonu** | QR 8×/s pri kóde, inak 2×/s; pri prehriatí polovica a upozornenie | `ScanPacer.kt`, Android `PowerManager` |
| **Zbierka** | ktoré postavičky už hráč má | SharedPreferences |
| **Postavičky** | 3D modely | GLB súbory v aplikácii |

## 🔳 Obsah QR kódu

QR kód nesie **len identifikátor postavičky**:

```text
smvit-ar:fox
```

- Aplikácia podľa neho nájde postavičku v súbore trasy (`route.json`) – model, veľkosť, umiestnenie, príbeh. Zmena modelu či umiestnenia teda nevyžaduje nové tlačenie kódov.
- Krátky obsah = menší a ľahšie čitateľný QR kód aj z väčšej vzdialenosti.
- Kód má presne **10 × 10 cm** (bez bieleho okraja), lebo z jeho veľkosti aplikácia počíta vzdialenosť.
- Kód má **25 × 25 modulov** (verzia 2, modul 4 mm) a opravu chýb **Q** (prečíta sa aj pri ~25 % poškodenia). Najmenší kód 21 × 21 nemá zarovnávací štvorček, takže štvrtý roh sa pri naklonení určil nepresne.
- Na trase kódy **tlačíme na 3D tlačiarni v laboratóriu**: doska 132 × 132 × 2,8 mm: biely podklad 2 mm a čierne moduly vyvýšené o 0,8 mm (STL v `src/content/markers/3d/`). Na testy pri stole stačí PDF s kontrolným pravítkom.
- *Rozšírenie:* kód s URL, ktorý bežný fotoaparát otvorí ako webovú stránku o hre (US-13).


## 📍 Umiestnenie postavičky vo vzťahu ku QR kódu

1. ML Kit nájde QR kód a jeho 4 rohy v obraze kamery.
2. Zo 4 rohov a známej tlačenej veľkosti (10 cm) vypočítame polohu a natočenie kódu voči kamere a cez polohu kamery z ARCore aj vo svete. Ktorý roh je ktorý, určíme podľa rohových štvorcov QR kódu.
3. Postavičku umiestnime v **„zvislom“ rámci kódu** (+Y hore, +Z k čitateľovi, +X vpravo od kódu) – stojí rovno, aj keď kód visí na stĺpiku alebo leží na zemi.
4. Postavička má **posun (offset)** voči kódu, v `route.json` napr. `"offset": { "x": 0.8, "y": -1.2 }` = na zemi 0,8 m vpravo od kódu, ktorý visí 1,2 m vysoko (`z` k čitateľovi, `yawDegrees` otočenie). Pri `"standOnGround": true` (zapnuté pre trasu) stojí postavička na zemi pod kódom sama a výšku kódu netreba zadávať; kód nižšie ako 30 cm sa berie ako ležiaci na zemi.
5. Kým je kód v zábere, postavička sleduje priamo meranie kódu; keď zmizne zo záberu, drží ju kotva ARCore.
6. Keď hráč odíde, postavička kód opustí a sprevádza ho po podlahe. Ak sa ocitne ďalej ako 6 m (skok súradníc ARCore), objaví sa 2 m od hráča mimo záberu a pribehne.
7. Posun, mierka a animácie sú pri každej postavičke v `route.json` – každá môže stáť inak (pri strome, na skale…).

## 🗃️ Dátový model trasy (návrh)

| Objekt | Polia |
|--------|-------|
| **route.json** (súbor trasy) | `name`, `lengthKm`, `minutes` (voliteľné, pre úvodnú obrazovku), `standOnGround` (postavičky na zemi pod kódom), `stops`, `aliases`, `characters` |
| **CatalogEntry** (postavička, položka v `characters`) | id, meno, model (GLB), tlačená veľkosť kódu, veľkosť modelu, posun voči kódu, animácia, príbeh, veľkosť pri sprevádzaní, animácie chôdze a cvalu |
| **stops** → `Catalog.trail` (trasa) | zoznam id postavičiek v poradí na trase; všetky = odznak |
| **aliases** | ďalšie vytlačené kódy, ktoré ukážu existujúcu postavičku (napr. `test_cube` → líška) |
| **TrailProgress** | trasa + množina nájdených id; počet, celkovo, dokončené |
| **Zbierka** (v telefóne) | množina id nájdených postavičiek |
| *Zastávka s GPS* | *rozšírenie (bonus G9), zatiaľ nie je* |

## 📱 Obrazovky

1. **Úvod trasy** ✅ – názov trasy, „ako hrať“, zastávky v poradí (meno až po nájdení, ✓), *Zbierka 1/4*, upozornenie *„Bezpečne na trase“*, tlačidlo *Začať* / *Pokračovať* (US-08, US-09). Dĺžka a čas trasy sa ukážu po doplnení do `route.json`.
2. **Skenovanie (AR)** ✅ – kamera, stavový riadok (*„Namier kameru na QR kód“*), po 5 s bez kódu alebo 2 s bez sledovania tip (US-07), pri prehriatí upozornenie, tlačidlá *Zbierka 2/4* a *Znova skenovať*; po nájdení sa zjaví postavička. Späť → úvod trasy.
3. **Karta postavičky** ✅ – meno, *„Nové! Pridané do zbierky (2/4)“* alebo *„Už v zbierke“*, príbeh. Postavička sa do zbierky pridá automaticky.
4. **Zbierka** ✅ – nájdené postavičky s príbehom, chýbajúce ako *„???“*, vymazanie zbierky.
5. **Odznak** ✅ – dialóg *„Odznak získaný!“* po nájdení všetkých postavičiek.

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../index.md)
