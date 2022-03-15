# Recherche-image-similaire
Projet réalisé dans le cadre d'une option pendant le semestre 4 de mon DUT Informatique. J'utilise la librairie Pelican Java pour la partie d'analyse d'images et Java Swing pour l'affichage.
L'application permet d'afficher des images similaires à une image en entrée.

# Installation
- Télécharger les sources
- Remplacer dans la class `ResearchPicture.java` la variable racine par le chemin du dossier contenant les images
- Remplacer dans la class `Vue.java` la variable dossier par `""` ou le dossier d'un type d'image
- Mettre les bonnes variables dans le constructeur de la base de données pour initialiser la connection à votre BDD (url, name, password, jdbc)

# Optimisation après installation
- Mettre en commentaire l'initialisation du model dans la class `Vue.java` : `//this.model.init(this.dossier);`

# Description des class

## Model :
- `BDD.java` :  base de donnée
- 3 class pour les différents processus de traitement d'image

## Vue
- `Vue.java` : vue de l'appli
- 2 class pour gérer la lecture d'image

## Controller
- controller pour la barre de recherche
- controller pour le type de traitement RGB ou HSV
- controller pour le nombre d'images souhaité en sortie (défaut 10)

## Test
- une class pour tester indépendamment de la vue, la partie model
