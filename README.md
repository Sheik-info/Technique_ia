# Technique IA

Projet Java de simulation multi-agents portant sur la vente de billets, la formation de coalitions et la gestion de budgets.

## Présentation

Ce projet modélise les interactions entre plusieurs catégories d'agents :

- des personnes disposant d'un budget et achetant des billets ;
- des fournisseurs proposant des billets et fixant les prix ;
- des organismes pouvant former des coalitions afin de mutualiser leur budget.

La simulation permet d'observer l'évolution des budgets et des gains sur plusieurs tours.

## Fonctionnalités

- Création de personnes, d'organismes et de fournisseurs
- Gestion du budget des différents agents
- Création et gestion d'offres de billets
- Proposition de billets aux personnes intéressées
- Formation de coalitions entre organismes
- Comparaison des stratégies disponibles
- Classement des gagnants à l'issue de la simulation
- Affichage des résultats dans la console

## Structure du projet

```text
Technique_ia/
├── src/
│   ├── Main.java
│   ├── agents/
│   │   ├── Agent.java
│   │   ├── Acheteur.java
│   │   ├── Personne.java
│   │   ├── Organisme.java
│   │   └── Fournisseur.java
│   └── tools/
│       ├── Billet.java
│       ├── Offre.java
│       ├── Coalition.java
│       └── Strategie.java
├── notes_article.txt
├── tech_ia.iml
└── README.md
