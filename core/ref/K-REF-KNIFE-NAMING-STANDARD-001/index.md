---
id: "K-REF-KNIFE-NAMING-STANDARD-001"
guid: "123e4567-e89b-12d3-a456-426614174000"
dao: "knife"
title: "Štandard pomenovania a taxonómia KNIFE"
description: "Komplexný štandard pre konvencie pomenovania a taxonómiu v rámci rámca KNIFE na zabezpečenie konzistencie, jasnosti a interoperability."
author: "Roman Kazicka"
authors:
  - "Dániel Ruzsik, Lukáš Šebök"
category: "referencia"
type: "štandard"
priority: "HI"
tags: ["pomenovanie", "taxonómia", "štandard", "KNIFE"]
created: "2024-06-01"
modified: "2024-06-01"
status: "inprogress"
locale: "sk"
#sidebar_label: "Štandard pomenovania KNIFE"
rights_holder_content: "Roman Kazicka"
rights_holder_system: "Roman Kazička,KNIFE, CAA"
license: "CC-BY-NC-SA-4.0"
disclaimer: "Tento dokument je poskytovaný „tak, ako je“ bez akejkoľvek záruky."
copyright: "© 2025 Roman Kazicka"
author_id: "romankazicka"
author_did: "did:example:romankazicka"
---

## Pôvodný text
🧩 KNIFE Taxonómia – návrh názvoslovia

Každý dokument (alebo balíček tém) začína prefixom K-, ktorý označuje, že ide o Knowledge unit (teda KNIFE).
Za ním nasleduje typ, ktorý určuje jeho funkciu v ekosystéme, a potom oblasť alebo kategória.

💡 Hlavné typy
Prefix	Typ dokumentu	Účel / príklad použitia
K-TASK	Praktický návod / postup („How-To“)	napr. K-TASK-GIT-UPSTREAM-001, K-TASK-DOCUSAURUS-BUILD-001
K-REF	Referenčný dokument / štandard / pravidlo	napr. K-REF-KNIFE-STANDARD-001, K-REF-FRONTMATTER-001
K-GOV	Governance / metodický rámec – pravidlá, procesy, šablóny	napr. K-GOV-PROMOTION-001 (pravidlá pre PR → UAT)
K-TOPIC	Tematická kapitola – obsahový príspevok (ako tvoje bežné KNIFEs)	napr. K-TOPIC-ESG-001, K-TOPIC-LO-INTEGRATION-001
K-REFK	Referenčný KNIFE – slúži ako vzorový alebo ilustračný obsah	napr. K-REFK-018 (tvoj K18 User Guide)
K-LAB	Cvičenie / Úloha pre študentov – „Learning by doing“	napr. K-LAB-GIT-PR-001 alebo K-LAB-MD-FRONTMATTER-001
📁 Odporúčaná štruktúra priečinkov
docs/sk/
 ├─ knifes/                ← bežné KNIFE príspevky
 ├─ ref/                   ← referencie, štandardy, tasky
 │   ├─ K-TASK-GIT-UPSTREAM-001/
 │   ├─ K-GOV-UAT-PROMOTION-001/
 │   ├─ K-REF-KNIFE-STANDARD-001/
 │   └─ ...
 ├─ lab/                   ← cvičenia pre študentov
 │   ├─ K-LAB-GIT-PR-001/
 │   └─ ...
 └─ overview/              ← tabuľky a prehľady

🧭 Vzťahy medzi typmi

K-REF = definuje pravidlá, ktoré ostatné typy musia rešpektovať

K-TASK = aplikuje K-REF do praxe (postup krok po kroku)

K-GOV = určuje metodiku a zodpovednosti

K-TOPIC = obsahový príspevok, nie systémový

K-LAB = overenie pochopenia (Task + Reflection)

📘 Príklady
Názov súboru	Účel	Väzba
K-GOV-UAT-PROMOTION-001.md	riadený proces PR→UAT→MAIN	governance pre workflow
K-TASK-GIT-UPSTREAM-001.md	vysvetlenie upstreamu v GITe	praktický „How-To“
K-REF-FRONTMATTER-001.md	definícia všetkých povinných polí FM	norma pre KNIFE
K-TOPIC-ESG-001.md	obsahový článok o ESG	bežný KNIFE príspevok
K-LAB-GIT-PR-001.md	cvičenie pre študentov s PR do UAT	praktická úloha