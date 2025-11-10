# Jeu de Démineur (console)

## Table des matières

1. [Introduction](#introduction)
2. [Installation](#installation)
3. [Utilisation](#utilisation)
4. [Règles-du-jeu](#règles-du-jeu)
5. [Fonctionnalités](#fonctionnalités)
6. [Structure-du-code](#structure-du-code)

---

## Introduction

Implémentation console d’un **démineur simplifié** en Java.
Grille par défaut **6×12**, **9 bombes** aléatoires. Le joueur saisit des coordonnées et découvre les cases.

---

## Installation

**Prérequis** : Java 11+

```bash
javac minesweeper/MineSweeper.java
java minesweeper.MineSweeper
```

---

## Utilisation

* Entrer **x** (0–11) puis **y** (0–5).
* Taper `quit` pour sortir.
* Affichage des cellules : `x` (non découverte), nombre (bombes adjacentes), `B` (bombe).

> Note actuelle (code) : les bombes sont visibles pendant la partie.

---

## Règles-du-jeu

* 9 bombes placées aléatoirement (sans doublon).
* Un tir sur une bombe → **Game over** et fin.
* Comptage des **8** voisins pour les cases découvertes.
* La condition de **victoire** (toutes les cases sûres découvertes) n’est pas encore gérée.

---

## Fonctionnalités

* Placement aléatoire sans collisions.
* Lecture sécurisée des entrées (`quit`, bornes, numériques).
* Affichage de la grille et du nombre de bombes adjacentes.

---

## Structure-du-code

* `minesweeper/MineSweeper.java`

    * `main` : génération des bombes, boucle d’entrées, affichage.
    * `printGrid` : rendu de la grille.
    * `readIntInRange` / `isAllDigits` : saisies utilisateur.
    * `calculateBombPosition` : positions uniques.
    * `isaminedtile` / `isBomb` / `isDiscovered` : tests d’état.
    * `nbBombsAround` : comptage des voisins (8-neighbours).

