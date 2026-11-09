---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.543530+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "d91c726a-d196-4c2d-9c96-6d9de4b5bc17"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "Risks"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "Riziká projektu QR3D – pravdepodobnosť, dopad a opatrenia."


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

# Risks

| # | Riziko | Pravdepodobnosť | Dopad | Opatrenie |
|---|--------|-----------------|-------|-----------|
| R1 | AR vonku funguje nespoľahlivo (ostré slnko, tieň, málo textúr, pohyb) | stredná | vysoký | experiment hneď na začiatku (M5); postavičku ukotviť k zemi (detekcia roviny), nie len ku kódu |
| R2 | QR kód sa v teréne zle číta (vzdialenosť, uhol, zašpinený) | stredná | vysoký | dostatočná veľkosť kódu, vyššia úroveň korekcie chýb, test T1 |
| R3 | QR kódy poškodí počasie alebo vandal | vysoká | stredný | laminácia / odolné nálepky; v MVP testovacia trasa len na čas testu |
| R4 | Telefón testera nepodporuje ARCore | stredná | stredný | overiť zoznam podporovaných zariadení vopred; náhradný režim bez AR (postavička na obrazovke) |
| R5 | Nemáme vhodné 3D postavičky | stredná | stredný | voľne licencované modely (CC0 / CC-BY) alebo jednoduché vlastné low-poly v Blenderi |
| R6 | Autorské práva k použitým modelom | nízka | stredný | len modely s licenciou CC0 / CC-BY, uvádzať autora |
| R7 | Podvádzanie – „zbieranie“ z fotiek QR kódov na internete | vysoká | nízky | GPS overenie zastávky (bonus G9); pre MVP akceptovateľné |
| R8 | Umiestnenie QR kódov bez povolenia (chránené územie, súkromný pozemok) | stredná | vysoký | pre test krátka trasa s povolením alebo dočasné umiestnenie; trvalé umiestnenie len cez správcu územia (princíp P5) |
| R9 | Nepozornosť pri chôdzi s mobilom | nízka | vysoký | skenuje sa len pri zastávke, upozornenie v aplikácii (princíp P6) |
| R10 | Časová vyťaženosť tímu počas semestra | stredná | stredný | malé priebežné míľniky, týždenný záznam práce, prioritizácia MVP |

---
**Navigation:** [⬆️ Up](../index.md) · [🏠 Home](../../../index.md)
