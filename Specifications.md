#Spécifications de notre projet

##Description de la chaîne de build

    Notre chaîne de build se décompose en plusieurs étapes:

        1. Générer les classes des sources de Bases ( en exécutant les tests)
        2. Générer les classes nécessaires à notre script (et exécuter les tests)
        3. Si les 2 builds suivants sont successfull, appliquer nos mutations ( Choix de la mutation et du lieu                    d’application -> Génération des nouvelles classes -> Application du banc de test sur ces nouvelles classes (et donc         génération des fichiers xml de rapport)
        4. Une fois toutes les mutations effectuées récupérer tous les rapports. 
        5. Générer le fichier html synthèse.

    Afin d'automatiser les différentes étapes de notre chaîne de build, nous allons mettre en place un script Shell.


    ##Artefacts manipulés
    
    Au cours de ce projet, les artefacts manipulés seront principalement les sources desquelles nous allons partir, ainsi que les tests s'appliquant sur ces sources. En effet, ce projet consiste à introduire des mutations au sein d'un code source fourni, afin d'observer le comportement et le résultat du banc de tests après avoir appliqué ces modifications.

    ##Outils associés
    
    Nous allons utiliser différents outils afin de réaliser ce projet, parmi lesquels:
        - Java: Pour passer des .java aux .class, générer les classes mutation, récupérer les fichiers xml et créer le rapport html.
        - Bash: Script permettant d'automatiser toutes les étapes.
        - JUnit: Lancer les tests sur le code initial, ainsi que le code après insertion des mutations.
        - Spoon: Appliquer les mutations.
        - Bootstrap: Mise en page et affichage du fichier synthèse. 
        - HighChart: Mise en page et affichage du fichier synthèse.
        
     Nous allons également reprendre des étapes de build Maven afin d'aider à l'automatisation de notre chaîne de build.


#Mutations possibles

##Types de mutations:

- x>y, x+y … :

    Changement de valeurs en cas de tests
        → Permet de voir si tout les cas de tests sont gérés ou non
    
    Changement du sens de test
        → Permet de voir si le test est exact et si il y a éventuellement un cas extrême qui pourrait poser problème
    
    Changement de l’opérateur de test
    → Permet de voir si le test est unique (une seule manière de détecter la condition)
    
    Remplacer une des valeurs par une constante
        → Permet de voir si les changements effectués sur les valeurs au cours de l'exécution du programme changent ou non cette condition

- Boucle for:

    Incrémentation altérée
        → Permet de voir si il n’y a pas un moyen d’optimiser la boucle, de voir si certains des cas utilisés sont utiles ou non, et si le programme fonctionne en cas de problème à certains endroits de la boucle.
    
    Changement de la borne de début
        → Permet de voir si les éléments aux extrêmes sont utiles par la suite ou bien traités dans le programme.
    
    Changement de la condition de fin
        → Permet également le test des cas extrêmes, et de voir si toutes les itérations sont utiles ou non.

- If, switch:

    Suppression d’un cas (suppression d’un else, suppression d’un cas d’un switch)
    Suppression de tous les cas
    Inversion de l’ordre des cas d’un if
    -> exemple: if (a > 2) {} else if (a > 2 && quelque chose) {}
        → Permet de voir si le cumul de conditions est bien testé dans le bon ordre.

- Variables:
    Modification du type des variables (tenter de mettre une variable improbable)
    Modification aléatoire des valeurs
    Changement entre public et private , protected etc…
    Suppression de mots clefs comme static, super etc…
    Renommer la variable

- Cas extrême:
    Test aux cas extrêmes (par exemple 0 et borne max)

- Suppression de statements aléatoires dans le code

##Où et Comment les appliquer

    En ce qui concerne les lieux d'application de nos mutations, nous avons distingué les quatre possibilités suivantes:
        - Aléatoire jusqu'à un certain seuil par classe
        - Une mutation par classe
        - Une mutation en tout
        - Une mutation par méthode
        
    Cela permet de commencer par une mutation générale, puis de préciser le lieu d'application afin d'aller tester le code un peu plus en profondeur, afin d'avoir une idée plus claire d'où vient un problème lorsque les tests échouent.
    
    Nous appliquerons donc nos mutations de manières incrémentales, afin de veiller à tester chacun des tests du banc de l'utilisateur, et qu'il soit testé de manière complète (en testant un grand nombre de mutants différents censés mettre en echec chaque test).

Il ne faut cependant pas appliquer un trop grand nombre de mutation au même mutant, et vérifier la compatibilité entre les différentes mutations appliquées (si on modifie la manière dont est parcourue la boucle en modifiant l'incrementeur et la condition de fin, on peut obtenir une fonction équivalente, qui va donc introduire un mutant viable, qui n'est pas dans les objectifs premiers de notre étude).
