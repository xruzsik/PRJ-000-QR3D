---
# 🧩 Versioning – systém dopĺňa automaticky
fm_version: "1.0.1"

# Dátum buildu – generuje skript
fm_build: "2026-10-26T08:24:38.685297+00:00"

# Poznámka k verzii – voliteľné
fm_version_comment: ""


# 🆔 IDENTITY --------------------------------------------------------

# ID generuje CLI / skript

# Unikátne UUID – generuje skript
guid: "a127b43f-6c7c-4a59-8609-65c7cf2f789d"


# 🧭 CONTEXT ---------------------------------------------------------

# DAO / doména (knife, sdlc, q12, 7ds...) dopĺňa skript
dao: "sthdf"

# Názov zápisu – dopĺňa používateľ
title: "knowledge contribution"

# Krátky popis – dopĺňa používateľ (voliteľné)
description: "KNIFE: Ako umiestniť AR objekt vo vzťahu ku QR kódu (Android, ARCore, Kotlin)."


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

# 📚 Knowledge Contribution

Toto deliverable je v systéme reprezentované ako **KNIFE** — samostatný
príspevok so štandardnou KNIFE štruktúrou (účel, princíp, aplikácia,
detailný článok, tipy, hodnota).

> 🚧 **Koncept.** Postup vychádza z návrhu a z prvého prototypu (kocka na QR kóde). Čísla a odporúčania doplníme po experimente s umiestnením v AR (míľnik M5).

## 🔖 Názov a stručný popis
- **Téma:** Ako umiestniť AR objekt vo vzťahu ku QR kódu (Android, ARCore, Kotlin)
- **Prečo je dôležitá:** položiť 3D objekt priamo *na* QR kód je jednoduché. V praxi ho ale chceme postaviť *vedľa* kódu – na zem, pri strom či stĺpik – a tak, aby pri pohybe telefónu „neutekal“. Návodov na to je málo a kombinujú sa v nich tri veci: čítanie QR kódu, prevod súradníc z obrazu do priestoru a ukotvenie v ARCore.

## 🎯 Čo rieši (účel, cieľ)
- Z obrazu kamery nájsť QR kód a určiť, **kde v priestore** sa nachádza.
- Vytvoriť v tom mieste **kotvu (Anchor)**, ktorú ARCore drží na mieste aj pri pohybe.
- Postaviť 3D objekt s **posunom (offset)** voči kódu a položiť ho na najbližšiu rovinu (zem).

## 🧩 Ako to rieši (princíp)
1. **Dve oddelené úlohy:** knižnica na čítanie kódov (ML Kit alebo ZXing) vie, *čo* je v kóde a *kde je v obraze*. ARCore vie, *kde je ktorý bod obrazu v priestore*. Spojíme ich.
2. **Obraz → obrazovka → priestor:** rohy kódu prídu v pixeloch obrazu z kamery. Prepočítame ich na súradnice obrazovky a cez *hit test* zistíme, ktorý bod v 3D priestore im zodpovedá.
3. **Kotva namiesto súradníc:** priestor, ktorý ARCore buduje, sa počas behu spresňuje. Objekt preto nepripíname na pevné súradnice, ale na kotvu, ktorú ARCore priebežne opravuje.
4. **Posun relatívne ku kódu:** posun (napr. 1 m vpravo) počítame v súradniciach kotvy – takže „vpravo“ znamená vpravo od kódu, nie od telefónu.

## 🧪 Ako to použiť (aplikácia)
V projekte *Postavičky na trase* nesie každý QR kód len identifikátor zastávky. Aplikácia podľa neho nájde postavičku a jej posun v dátach trasy a postaví ju vedľa kódu – napríklad líšku 1 m vpravo od stĺpika s kódom.

## ⚡ Rýchly návod (Top)
1. Zapni ARCore session s detekciou horizontálnych rovín.
2. Každý snímok (alebo každý n-tý) pošli do čítačky QR kódov.
3. Keď čítačka nájde kód, prepočítaj jeho stred z pixelov obrazu na súradnice obrazovky (`Frame.transformCoordinates2d`).
4. Sprav `frame.hitTest(x, y)` a z prvého zásahu vytvor kotvu (`hitResult.createAnchor()`).
5. Posun voči kódu vyjadri ako `Pose.makeTranslation(...)` a zlož ho s pózou kotvy (`anchor.pose.compose(...)`).
6. Z vypočítaného bodu sprav ešte jeden hit test smerom k zemi a postavičku ukotvi na nájdenú rovinu.
7. Kód viac neskenuj, kým je postavička na scéne – šetrí to výkon a objekt neskáče.

## 📜 Detailný článok

### 1. Nájdenie QR kódu v obraze
ARCore dá ku každému snímku obraz z kamery (`frame.acquireCameraImage()`). Ten pošleme do ML Kit Barcode Scanning. Výsledok obsahuje text kódu a jeho štyri rohy (`cornerPoints`) **v pixeloch obrazu kamery** – nie obrazovky.

### 2. Z pixelov obrazu na obrazovku
Obraz z kamery má iné rozlíšenie a orientáciu ako obrazovka. ARCore na prepočet ponúka `frame.transformCoordinates2d` (z `Coordinates2d.IMAGE_PIXELS` do `Coordinates2d.VIEW`). Bez tohto kroku hit test mieri vedľa a objekt sa zjaví na zlom mieste.

### 3. Z obrazovky do priestoru – kotva
Na prepočítaný stred kódu spravíme hit test. Ak zasiahne rovinu alebo bod, ktorý ARCore pozná, vytvoríme z neho kotvu.

```kotlin
// Náčrt – overiť a doplniť podľa skutočného kódu aplikácie
val center = qrCenterInViewCoords(frame, barcode)   // krok 2
val hit = frame.hitTest(center.x, center.y).firstOrNull() ?: return
val qrAnchor = hit.createAnchor()
```

Ak chceme poznať aj **natočenie** kódu (kam smeruje „vpravo“), spravíme hit test pre dva rohy (napr. ľavý a pravý horný) a z ich rozdielu určíme smer osi X kódu.

### 4. Posun vedľa kódu a položenie na zem
Posun zložíme s pózou kotvy – výsledný bod je teda „1 m vpravo od kódu“ v jeho vlastnom smere. Z tohto bodu spravíme ešte jeden hit test na najbližšiu horizontálnu rovinu, aby postavička stála na zemi, a nie vo výške kódu.

```kotlin
val offset = Pose.makeTranslation(stop.offsetX, 0f, stop.offsetZ)  // napr. 1.0f, 0f, 0.5f
val targetPose = qrAnchor.pose.compose(offset)
val figureAnchor = anchorOnGroundBelow(frame, targetPose) ?: session.createAnchor(targetPose)
```

### 5. Prečo nie ARCore Augmented Images?
ARCore vie sledovať aj vopred známe obrázky (*Augmented Images*). QR kód však má málo charakteristických bodov a pre každú zastávku by sme museli vopred registrovať iný obrázok. Preto ho používame len ako porovnanie v experimente.

### 6. Výsledky experimentu
_(doplniť po M5: z akej vzdialenosti a uhla sa kód nájde pri veľkosti 5 / 10 / 15 cm, o koľko sa objekt posunie pri obchádzaní, vnútri vs. vonku)_

## 💡 Tipy a poznámky
- **Neskenuj každý snímok.** Čítanie QR je náročné – stačí každý 5.–10. snímok, a po nájdení skenovanie vypni.
- **Kotvy stoja výkon.** Keď postavičku odstraňuješ, zavolaj na kotvu `detach()`.
- **Vonku pomáha textúra.** ARCore potrebuje body, ktoré vie sledovať – holý betón alebo trávnik na priamom slnku sledovanie zhoršujú.
- **Kód nech je malý.** Krátky obsah (len ID zastávky) = menej modulov = kód sa dá prečítať z väčšej vzdialenosti.
- **Rovina sa musí najprv nájsť.** Kým ARCore nedetekuje zem, hit test na rovinu nič nevráti – používateľovi zobraz nápovedu „pohni pomaly telefónom“.

## ✅ Hodnota / Zhrnutie
Spojením čítačky QR kódov a ARCore dostaneme z bežného vytlačeného kódu presný bod v priestore. Kotva a posun voči nej umožnia postaviť objekt kdekoľvek okolo kódu tak, aby zostal na mieste. Postup je použiteľný všade, kde chceme AR obsah viazať na fyzické miesto – náučné chodníky, múzeá, hry v teréne.

## 🗂️ Taxonómia KNIFE
- **Kategória:** IT
- **Typ:** návod
- **Tagy:** knife, tutorial, android, arcore, qr, ar

## 🌍 Referencie
- ARCore – dokumentácia pre Android: https://developers.google.com/ar/develop/java/quickstart
- ARCore – Anchors: https://developers.google.com/ar/develop/anchors
- ARCore – Hit test: https://developers.google.com/ar/develop/hit-test
- ML Kit – Barcode Scanning: https://developers.google.com/ml-kit/vision/barcode-scanning/android
- Projekt: [D3 – Analysis](../../projects/7ds/03.D3-Analysis/index.md), [D4 – Design](../../projects/7ds/04.D4-Design/index.md), [Knife Management](../../projects/7ds/01.D1-Management/02.Strategy/Knife_Management/index.md)

## Navigácia
- [↩️ Späť](../index.md)
