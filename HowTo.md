##HowTo.md : Tutoriel du framework

##1. Première utilisation du Framework sur un code

Il faut commencer par inclure la dépendance maven de notre framework dans le projet maven que vous souhaiter tester:

    <parent>
      <groupId>devops.2.3</groupId>
        <artifactId>master</artifactId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../../$cheminVersFrameWork/src/main/resources/pom.xml</relativePath>
    </parent>

Cela permet de lier notre pom de base à celui de votre projet, afin que notre script puisse s'exécuter sans problèmes.
Il faut cependant bien veiller à mettre le chemin relatif dans le relativePath et non pas un chemin absolu.

##2. Lancement du framework
Il suffit d'utiliser notre script shell en indiquant le chemin absolu du dossier du projet que l'on souhaite tester.
(Notre script inclut l'installation du framework, il n'est donc pas nécessaire d'effectuer cette étape à la main).
Il faut auparavant se placer dans le dossier du framework pour lancer le script.
Exemple d'utilisation: ./build.sh $cheminVersCode

##3. Déroulement du framework
Le framework commence par lancer le maven install sur notre framework.
Les tests sont ensuite lancés sur le code de base, et les reports stockés dans un dossier NoMutation.
Ensuite, toutes nos mutations sont appliquées sur le code source fourni, puis les tests relancés à chaque étape.

##4. Sortie du framework
En sortie du framework, le rapport est lancé automatiquemetn dans le navigateur par défaut.
De plus, un dossier Result est obtenu.
Il contient:
- un dossier NoMutation
- un dossier pour chaque mutation, contenant le code généré et les reports de test.

##5. Utilisation du rapport
La page principale se compose d'un tableau, avec sur chaque ligne une mutation, et chaque colonne correspond à un fichier de test.
Chaque case est:
- verte si tous les tests d'un fichier sont passés
- rouge si au moins un test a échoué sur ce fichier
- marron si il y a eu un problème de compilation après application de la mutation

De plus, il y a également un diagramme Piechart résumant le tableau. Il compte de la façon suivante:
- si une mutation est entièrement verte, alors le mutant est considéré comme vivant.
- si une mutation a au moins une case rouge, alors le mutant est considéré comme mort.
- si une mutation a eu une compilation failure, alors le mutant est considéré comme mort-né.

En cliquant sur une case, vous arrivez sur une page contenant:
- un lien vers le code source généré par spoon
- le fichier de test concerné par cette case
- les éventuels messages et lignes d'erreur pour les tests
