Description de la chaîne de build

1. On génère les classes des sources de Bases ( en exécutant les tests)
2. On génère les classes nécessaires a notre script (et on exécute les tests)
3. Si les 2 builds suivants sont successfull, on applique nos mutations ( Choix de la mutation et du lieu d’application -> Génération des nouvelles classes -> Application du banc de test sur ces nouvelles classes (et donc génération des fichiers xml de rapport)
4. Une fois toutes les mutations effectuées et tout les rapports récupérés, on génère le fichier html synthèse.

    Artefacts manipulés

    Outils utilisés
- Pour passer des .java aux .class: javac
- Pour tester le projet actuel: JUnit
- Pour générer les classes mutation: Java
- Pour appliquer les mutations: Spoon
- Pour lancer les tests sur le code contenant les mutations: JUnit
- Pour récupérer tous les xml: Java
- Pour créer le rapport à partir des xml: Java

Pour exécuter la totalité de cette chaîne: Shell ou Plugin Maven.



Mutations possibles
Types de mutations:

x>y, x+y … :

Changement de valeurs en cas de tests
    → Permet de voir si tout les cas de tests sont gérés ou non

Changement du sens de test
    → Permet de voir si le test est exact et si il y a éventuellement un cas extrême qui pourrait poser problème

Changement de l’opérateur de test
→ Permet de voir si le test est unique (une seule manière de détecter la condition)

Remplacer une des valeurs par une constante
    → Permet de voir si les changements effectués sur les valeurs au cours de l'exécution du programme changent ou non cette condition

Boucle for:

Incrémentation altérée
    → Permet de voir si il n’y a pas un moyen d’optimiser la boucle, de voir si certains des cas utilisés sont utiles ou non, et si le programme fonctionne en cas de problème à certains endroits de la boucle.

Changement de la borne de début
    → Permet de voir si les éléments aux extrêmes sont utiles par la suite ou bien traités dans le programme.

Changement de la condition de fin
    → Permet également le test des cas extrêmes, et de voir si toutes les itérations sont utiles ou non.

If, switch:

Suppression d’un cas (suppression d’un else, suppression d’un cas d’un switch)
Suppression de tous les cas
Inversion de l’ordre des cas d’un if
-> ex: if (a > 2) {} else if (a > 2 && quelque chose) {}
    → Permet de voir si le cumul de conditions est bien testé dans le bon ordre.

Variables:
Modification du type des variables (tenter de mettre une variable improbable)
Modification aléatoire des valeurs
Changement entre public et private , protected etc…
Suppression de mots clefs comme static, super etc…
Renommer la variable

Cas extrême:
Test aux cas extrêmes (par ex 0 et borne max)

Implements au lieu de extends

Suppression de statements aléatoires dans le code


    Où
Aléatoire jusqu'à un certain seuil par classe
Une mutation par classe
Une mutation en tout
Une mutation par méthode

    Comment