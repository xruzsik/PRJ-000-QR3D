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

# D4 – Design

*Stav: návrh – spresníme po experimente s umiestnením v AR (M5). Diagram architektúry v draw.io treba prekresliť podľa tohto návrhu.*

## 🏗️ Architektúra

```text
 TERÉN                         ANDROID APLIKÁCIA (Kotlin + ARCore)
 ┌──────────────┐   kamera    ┌──────────────────────────────────────────────┐
 │  QR kód na   │ ──────────▶ │ 1. AR kamera (ARCore)                         │
 │  zastávke    │             │ 2. čítačka QR (ML Kit / ZXing) → ID zastávky  │
 │  „zastavka-3“│             │ 3. výpočet polohy kódu → kotva (Anchor)       │
 └──────────────┘             │ 4. postavička vedľa kódu (offset, na zem)     │
                              │ 5. uloženie do zbierky → odznak v cieli       │
                              └──────────────┬───────────────────────────────┘
                                             │ všetko lokálne v telefóne
                              ┌──────────────▼───────────────┐
                              │ obsah trasy (JSON) + modely  │
                              │ postavičiek (GLB) v aplikácii│
                              │ zbierka (lokálna databáza)   │
                              └──────────────────────────────┘
```

Aplikácia nepotrebuje server ani internet – trasa, postavičky aj zbierka sú v telefóne.

## 🧩 Komponenty

| Komponent | Úloha | Technológia (návrh) |
|-----------|-------|---------------------|
| **AR obrazovka** | zobrazuje kameru, hľadá QR kód, vykresľuje postavičku | ARCore + vykresľovanie 3D (napr. SceneView / Filament) |
| **Čítačka QR** | z obrazu kamery prečíta kód a nájde jeho rohy | ML Kit Barcode Scanning alebo ZXing |
| **Ukotvenie** | z polohy kódu vypočíta, kam postaviť postavičku | ARCore Anchor, hit test na rovinu |
| **Obsah trasy** | zoznam zastávok, postavičiek a textov | JSON súbor v aplikácii (assets) |
| **Zbierka** | ktoré postavičky už hráč má, kedy ich získal | lokálna databáza (Room) alebo DataStore |
| **Postavičky** | 3D modely | GLB súbory v aplikácii |

## 🔳 Obsah QR kódu

QR kód nesie **len identifikátor zastávky**, napr.:

```text
https://xruzsik.github.io/PRJ-000-QR3D/t/zobor/3
```

- Aplikácia z neho prečíta trasu (`zobor`) a zastávku (`3`) a postavičku nájde vo vlastných dátach.
- Ak niekto naskenuje kód bežným fotoaparátom bez aplikácie, otvorí sa webová stránka s informáciou o hre a odkazom na stiahnutie *(bonus)*.
- Krátky obsah = menší a ľahšie čitateľný QR kód aj z väčšej vzdialenosti.

## 📍 Umiestnenie postavičky vo vzťahu ku QR kódu

1. Čítačka nájde QR kód a jeho 4 rohy v obraze kamery.
2. Cez ARCore (hit test) zistíme, kde sú tieto body v priestore → poloha a natočenie kódu.
3. Vytvoríme kotvu (Anchor) v mieste kódu.
4. Postavičku umiestnime s **posunom (offset)** voči kotve – napr. 1 m vpravo a 0,5 m pred kód – a „položíme“ ju na najbližšiu rovinu (zem).
5. Posun a mierka sú uložené pri každej zastávke v dátach trasy – každá postavička môže stáť inak (pri strome, na skale…).

## 🗃️ Dátový model trasy (návrh)

| Objekt | Polia |
|--------|-------|
| **Trasa** | id, názov, popis, zoznam zastávok, odznak za dokončenie |
| **Zastávka** | id, poradie, názov miesta, GPS súradnice *(pre bonus G9)*, postavička |
| **Postavička** | id, meno, 3D model (GLB), posun voči kódu, mierka, krátky text o mieste |
| **Zbierka** (v telefóne) | id postavičky, dátum a čas získania |

## 📱 Obrazovky

1. **Úvod / mapa trasy** – zoznam zastávok, koľko postavičiek už mám (napr. 2 / 5).
2. **Skenovanie (AR)** – kamera, nápoveda „Namier na QR kód“; po nájdení sa zjaví postavička.
3. **Detail postavičky** – meno, krátky príbeh miesta, tlačidlo „Pridať do zbierky“.
4. **Zbierka** – získané postavičky farebne, chýbajúce ako tiene.
5. **Cieľ** – gratulácia a odznak po nazbieraní všetkých postavičiek.

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../index.md)
