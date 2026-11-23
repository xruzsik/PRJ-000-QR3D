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

# 📚 Knowledge Contribution

Toto deliverable je v systéme reprezentované ako **KNIFE** — samostatný
príspevok so štandardnou KNIFE štruktúrou (účel, princíp, aplikácia,
detailný článok, tipy, hodnota).

> 🚧 **Koncept.** Postup zodpovedá kódu aplikácie *Postavičky na trase* ([`src/`](https://github.com/xruzsik/PRJ-000-QR3D/tree/main/src)), otestovanej na Samsung Galaxy A53 pri stole. Výsledky merania vonku doplníme po experimente M5.

## 🔖 Názov a stručný popis
- **Téma:** Ako postaviť AR objekt vedľa vytlačeného QR kódu (Android, ARCore, ML Kit, Kotlin)
- **Prečo je dôležitá:** QR kód je najlacnejší spôsob, ako označiť miesto v teréne – stačí ho vytlačiť. Bežné návody ukazujú buď čítanie kódu, alebo AR, ale nie to, ako z kódu získať **presnú polohu v priestore** a postaviť vedľa neho objekt, ktorý pri pohybe neuteká. Navyše odporúčaný postup (hit test na rovinu) na telefónoch strednej triedy nemusí stačiť.

## 🎯 Čo rieši (účel, cieľ)
- Z obrazu kamery nájsť QR kód a určiť jeho **polohu a natočenie v priestore** – aj keď visí na stĺpiku alebo leží na zemi.
- Postaviť vedľa neho 3D objekt s **posunom** (napr. „20 cm vpravo, čelom k čitateľovi“), ktorý stojí zvislo.
- Udržať objekt na mieste, aj keď ARCore svoju polohu postupne opravuje („uteká“).

## 🧩 Ako to rieši (princíp)
1. **Veľkosť kódu je známa.** Kód tlačíme presne 10 × 10 cm. Z polohy jeho 4 rohov v obraze a z parametrov kamery sa dá vypočítať, kde kód je a ako je natočený – rovnako ako odhadneš vzdialenosť dverí, keď vieš, aké sú vysoké. Netreba roviny ani hĺbkovú mapu.
2. **Ktorý roh je ktorý, povie kód sám.** QR má veľké štvorce v troch rohoch; štvrtý roh je ten bez štvorca. Tak vieme, kde je „hore“, aj keď je kód otočený.
3. **Jednému meraniu neveríme.** Objekt sa ukáže až po niekoľkých zhodných meraniach; merania, ktoré nesedia na rohy, zahodíme.
4. **Kým kód vidíme, riadi sa ním.** Každé meranie je presné voči kamere, ktorá ho urobila. ARCore svoju predstavu o svete opravuje, preto model, ktorý by bol len na kotve ARCore, by sa posúval. Keď kód zmizne zo záberu, objekt drží kotva.
5. **Posun v „zvislom“ rámci kódu.** Posun nepočítame v rámci samotného kódu (kód na stĺpiku by položil objekt na bok), ale v rámci s osou Y hore, Z k čitateľovi a X vpravo od kódu.

## 🧪 Ako to použiť (aplikácia)
V hre *Postavičky na trase* nesie každý QR kód len `smvit-ar:<id>` (napr. `smvit-ar:fox`). Aplikácia podľa toho nájde v súbore trasy (`route.json`) model, veľkosť a posun a postaví líšku vedľa kódu. Keď hráč odíde, líška kód opustí a ide s ním. Rovnaký postup sa hodí všade, kde chceme AR obsah viazať na fyzické miesto: náučné chodníky, múzeá, hry v teréne, navigácia v budove.

## ⚡ Rýchly návod (Top)
1. Vytlač kód v **presne známej veľkosti** (napr. 10 cm bez bieleho okraja) a pridaj na papier kontrolné pravítko.
2. V ARCore session **vypni detekciu rovín a hĺbku**, ak ich nepotrebuješ (šetrí výkon).
3. Niekoľkokrát za sekundu (nie každý snímok) pošli `frame.acquireCameraImage()` do **ML Kit Barcode Scanning** – vráti obsah kódu a 4 rohy v pixeloch obrazu.
4. Zo 4 rohov, tlačenej veľkosti a `camera.imageIntrinsics` vypočítaj **polohu kódu voči kamere** (homografia + spresnenie).
5. Urči **správne poradie rohov** podľa rohových štvorcov QR.
6. Prenásob polohou kamery z ARCore → poloha kódu **vo svete**.
7. Objekt ukáž až po **3 zhodných meraniach**; potom ho posúvaj podľa každého ďalšieho merania a vyhladzuj.
8. Keď kód 1 s nevidíš, **vytvor kotvu** ARCore v poslednej polohe a drž objekt na nej.

## 📜 Detailný článok

### 1. Čítanie kódu: obraz ARCore → ML Kit
ARCore dáva obraz z kamery v rozlíšení CPU obrazu. ML Kit vráti `rawValue` a `cornerPoints` v pixeloch tohto obrazu. Dôležité: obraz posielame **bez otočenia** (rotácia 0), aby rohy boli v rovnakých súradniciach ako parametre kamery z ARCore. Tie platia pre `imageDimensions`, takže ich treba prepočítať na skutočnú veľkosť CPU obrazu.

```kotlin
val ci = camera.imageIntrinsics
val sx = image.width.toDouble() / ci.imageDimensions[0]
val sy = image.height.toDouble() / ci.imageDimensions[1]
val k = Intrinsics(fx * sx, fy * sy, cx * sx, cy * sy)
client.process(InputImage.fromMediaImage(image, 0))   // rotácia 0!
```

Skenujeme **8× za sekundu**, len kým postavička stojí na kóde alebo sa kód práve objavil, inak (cestou medzi zastávkami) **2×**; pri prehriatí telefónu ešte o polovicu menej. Vždy len jeden obraz naraz – každé skenovanie je ML Kit plus výpočet polohy a telefón sa inak prehrieva.

### 2. Poloha kódu zo 4 rohov
Kód je rovina so známymi rohmi (±5 cm pri 10 cm kóde). Zo 4 párov „roh na papieri ↔ roh v obraze“ vypočítame **homografiu** (8 lineárnych rovníc, 8 neznámych) a z nej natočenie a posun kódu voči kamere. Samotná homografia je pri šikmom pohľade citlivá na šum (pol pixela = desiatky milimetrov), preto výsledok **spresníme** metódou Levenberg–Marquardt, ktorá minimalizuje rozdiel medzi skutočnými a premietnutými rohmi (*reprojekčná chyba*). Táto chyba zároveň slúži ako miera kvality merania.

### 3. Ktorý roh je ktorý
ML Kit vracia rohy v smere hodinových ručičiek, ale **začína od rohu najbližšie k ľavému hornému rohu obrazu**, nie kódu. Kód videný bokom tak vyjde otočený o 90°. Riešenie: pri prvom pohľade prejdeme z každého rohu po uhlopriečke dovnútra a hľadáme vzor rohového štvorca (tmavá–svetlá–tmavá–svetlá–tmavá v pomere 1:1:3:1:1). Roh bez štvorca je pravý dolný. Pri ďalších pohľadoch (keď je obraz pri pohybe rozmazaný) vyberieme z 4 možných poradí to, ktoré najlepšie sedí na doterajšie natočenie.

### 4. Kedy meraniu veriť
- ARCore po spustení posúva svoj súradnicový systém až o meter, kým sa ustáli → merania používame až po **1,5 s nepretržitého sledovania**.
- Nový kód sa ukáže až po **3 meraniach** do 2 s, ktoré sa líšia najviac o 2 cm a 8°.
- Merania s reprojekčnou chybou nad **3 px** zahodíme.

### 5. Kód v zábere vs. mimo záberu
Na Galaxy A53 sa poloha z ARCore postupne posúvala až o **~20 cm**. Kotva ARCore by sa posúvala s ňou, ale meranie kódu je vždy presné voči kamere, ktorá ho urobila. Preto:
- **kód v zábere** → model ide za posledným meraním, plynulo vyhladený (časová konštanta 80 ms),
- **kód 1 s mimo záberu** → vytvoríme kotvu `session.createAnchor(...)` a model drží na nej,
- **kód znova v zábere** → kotvu odpojíme (`detach()`) a vrátime sa k meraniam.

### 6. Posun vedľa kódu, zvislo
Posun definujeme v „zvislom“ rámci kódu: +Y rovno hore (svet ARCore je zarovnaný s gravitáciou), +Z vodorovne k tomu, kto kód číta, +X vpravo od kódu. Pre kód na stĺpiku je „k čitateľovi“ normála kódu, pre kód ležiaci na zemi jeho spodná hrana.

Posun sa zapisuje do súboru trasy `route.json` – kód visí 1,2 m vysoko na stĺpiku, postavička stojí na zemi 0,8 m vpravo od neho:

```json
"offset": { "x": 0.8, "y": -1.2 }
```

```kotlin
// Catalog.kt z neho spraví RigidPose; poloha postavičky vo svete:
val world = Frames.upright(codePose) compose entry.offset
```

### 7. Výkon na telefóne strednej triedy (Galaxy A53)
| Problém | Riešenie |
|---------|----------|
| detekcia rovín a hĺbka zastavovali sledovanie niekoľkokrát za sekundu | vypnuté (`PlaneFindingMode.DISABLED`, `DepthMode.DISABLED`) |
| pohybové senzory chodili 6× za sekundu, sledovanie sa neustálilo | oprávnenie `HIGH_SAMPLING_RATE_SENSORS` |
| 120 Hz displej bral výkon trackeru | `preferredRefreshRate = 60f` |
| prehrievanie pri dlhom behu (najmä pri nabíjaní) | skenovanie 8×/s len pri kóde, inak 2×/s; pri prehriatí (`PowerManager`, stav *SEVERE*) polovica a upozornenie; lacnejší odhad svetla (`AMBIENT_INTENSITY`) |

Podlahu (kam postaviť postavičku, keď odíde od kódu) bez detekcie rovín odhadujeme z bodov, ktoré ARCore sleduje 0,7–2,2 m pod telefónom; kým ich nie je dosť, predpokladáme podlahu 1,3 m pod telefónom.

### 8. Prečo nie hit test alebo ARCore Augmented Images?
- **Hit test na rovinu** potrebuje detekciu rovín (výkon) a nájdenú rovinu – kód na stĺpiku či strome na žiadnej rovine nie je.
- **Augmented Images** sleduje vopred registrované obrázky; QR kód má málo charakteristických bodov a každý kód by sa musel registrovať zvlášť.

### 9. Výsledky experimentu
_(doplniť po M5: z akej vzdialenosti a uhla sa 10 cm kód nájde vonku, o koľko sa objekt posunie pri obchádzaní, slnko vs. tieň)_

## 💡 Tipy a poznámky
- **Tlač na 100 %.** Ak tlačiareň kód zmenší na 9,5 cm, aplikácia ho postaví o 5 % ďalej. Na papier daj pravítko a skontroluj ho.
- **Kód skenuj mierne zboku.** Pohľad presne kolmo je pre výpočet natočenia najmenej presný.
- **Nepoužívaj najmenší QR kód (21 × 21).** Nemá zarovnávací štvorček, takže čítačka štvrtý roh len odhadne, akoby sa na kód pozerala kolmo. Pri naklonení bol roh o 4–15 px vedľa a poloha sa zahodila. Od 25 × 25 (verzia 2) je roh presný; vyššia oprava chýb (Q) navyše pomôže pri špine.
- **Logiku drž mimo Androidu.** Výpočty polohy v čistom Kotline (vlastné `Vec3`, `Mat3`) sa dajú testovať na PC – v projekte ich overuje 57 unit testov.
- **Model nenačítavaj znova pri každom pohybe** – jeden uzol na kód, ktorý sa len posúva.
- **Animácie prepínaj ručne.** Automatické prehrávanie spustí všetky klipy v GLB naraz.

## ✅ Hodnota / Zhrnutie
Zo známej veľkosti vytlačeného QR kódu a jeho 4 rohov dostaneme presnú polohu v priestore bez detekcie rovín. Overenie viacerými meraniami, sledovanie kódu, kým je v zábere, a kotva, keď nie je, udržia objekt na mieste aj na telefóne strednej triedy. Postup je použiteľný všade, kde chceme AR obsah viazať na fyzické miesto.

## 🗂️ Taxonómia KNIFE
- **Kategória:** IT
- **Typ:** návod
- **Tagy:** knife, tutorial, android, arcore, qr, ar

## 🌍 Referencie
- Kód: [`src/app/src/main/java/com/smvit/ar/qr/`](https://github.com/xruzsik/PRJ-000-QR3D/tree/main/src/app/src/main/java/com/smvit/ar/qr) – `QrScanner`, `QrPoseSolver`, `QrOrientation`, `QrPoseFilter`, `Frames`
- ARCore – dokumentácia pre Android: https://developers.google.com/ar/develop/java/quickstart
- ARCore – Anchors: https://developers.google.com/ar/develop/anchors
- ML Kit – Barcode Scanning: https://developers.google.com/ml-kit/vision/barcode-scanning/android
- Projekt: [D3 – Analysis](../../projects/7ds/03.D3-Analysis/index.md), [D4 – Design](../../projects/7ds/04.D4-Design/index.md), [D5 – Implementation](../../projects/7ds/05.D5-Implementation/index.md), [Knife Management](../../projects/7ds/01.D1-Management/02.Strategy/Knife_Management/index.md)

## Navigácia
- [↩️ Späť](../index.md)
