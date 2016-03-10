# Architecture

## Code de test fourni

En clonant ce repository, vous obtiendrez un dossier Framework et un dossier Application. Le premier correspond évidemment à notre framework et contient
notre script ainsi que toutes nos mutations et notre générateur de rapport.
Le dossier Application comporte un code de base avec des opérations arithmétiques, et testé, avec un pom.xml déjà "opérationnel".
Une fois le repository cloné, vous pouvez donc lancer le framework sur ce code pour vérifier le bon fonctionnement, puis sur un code un peu plus développé.

## Mutation et Selecteur

L'architecture que nous avons voulu mettre en place se basait sur la génération de processeurs à partir de combinaisons de selecteurs et de mutations:
Chaque processeur associe un selecteur et une mutation (soit de manière prédéfinie, mais également par la suite de manière automatique en générant toutes les combinaisons possibles), puis applique cette combinaison à notre code, générant ainsi un mutant unique.

Nous avons cependant rencontrer des problèmes lorsque nous avons souhaités externaliser le code de nos selecteurs et mutations en dehors des processeurs, les sélecteurs ne sont par exemple jamais pris en compte, et certaines mutations donnaient des résultats non attendus.
C'est la le premier point à améliorer dans notre Framework afin de le rendre totalement opérationnel.

A l'heure actuelle, il ne fait qu'appliquer que des mutations prédéfinies dans les processeurs, et l'applique à la totalité des élements correspondant dans le code, sans prendre en compte les différents selecteurs générés.

## Génération du rapport

La génération du rapport final se décompose en plusieurs étapes:
- Tout d'abord, on récupère le nom de toutes nos mutations, ainsi que le nom de tous les fichiers de test, et on en crée un tableau.
- On parcourt ensuite les dossiers générés dans Result, et pour chacun de ses dossiers on parcourt les fichier xml générés par les tests.
- On récupère les informations pour chaque test, à savoir le nombre de succès et d'erreurs ou failures.
- En fonction de ces nombres, on remplit le tableau avec des cases vertes ou rouges (ou marron si il y a des compilation failure).
- On génère le diagramme Piechart à partir de ce tableau.

Nous avons ensuite décidé de rajouter quelques fonctionnalités au rapport pour le suivi des mutations.
De ce fait, un clic sur une case mène à un nouveau fichier contenant le code du fichier test, ainsi que les erreurs, et un lien vers le code mutant généré.

Toutes ces étapes sont réalisées à l'aide des classes de Parsage fournies par Java. Cela est fort utile pour les fichier xml
qui sont composés de balises, et pour lequel des méthodes sont implémentées.

Bien évidemment, puisque la génération se fait en java, on utilise des FileWriter et FileReader pour écrire et lire les fichiers,
ce qui fait que nous avons créé des méthodes assez importantes se résumant simplement à écrire des balises d'entêtes html
et de style.


## Chaine de Build

Notre chaine de build est automatisée par un script Shell qui execute la totalité des opérations nécessaires au bon fonctionnement du framework.
Il se base pour cela :
- sur des fonctionnalités Shell pour la création des dossiers, déplacement des dossiers au bon endroit, localisation de l'application à tester...
- sur les plugins maven pour l'installation du framework, son test, ainsi que la génération des sources des différents mutants générés, et la génération des rapports via JUnit.
Enfin, il utilise notre générateur de rapport Java sur les données produites par maven afin de produire le rapport final au format HTML qui contient la centralisation des résultats de toutes nos mutations.

# Améliorations Possibles

## Boucles Infinies

Il arrive que nos mutations génèrent des boucles infinies, qui ne sont pas toujours traités de bases dans les tests de l'utilisateur.
Nous assumons pour l'instant que l'utilisateur annote la totalité de ses tests contenant des fonctions utilisant des boucles avec l'option timeout de Junit.
Cependant, pour automatiser cela, nous pourrions effectuer une première mutation sur les tests de bases qui consisterait à rajouter ces annotations la ou elles ne sont pas présentes.
Une autre solution serait d'utiliser les threads, en en créant un qui calcule le temps d'execution de chaque fonction, et qui coupe le thread parti en boucle infini.

## Génération Aléatoire

La totalité de nos combinaisons mutations / selecteurs sont prédéfinies dans chaque classe de processeur.
Une amélioration possible serait de combiner aléatoirement les différents élements afin de générer un plus grand nombre de mutations qui renforceraient donc les tests du banc souhaité.

## Fonctionnement

Notre Framework se base principalement sur un script shell qui execute notre chaine de build au complet.
Nous sommes donc dépendants de la plateforme de l'utilisateur, et une personne souhaitant utiliser notre Framework sur Windows ou Mac aura un travail de fond à effectuer avant son utilisation.
Nous pourrions faire évoluer ce script vers une plugin maven, qui ne serait donc moins dépendant de la plateforme utilisée et simplifierait donc son utilisation.

## Incrémentation des utilisations

Si un utilisateur utilise notre frameweork plusieurs fois à la suite, il relance la chaine de build au complet, ce qui comprend donc l'installation de notre framework.
Il serait interessant de n'executer que les étapes nécessaires lors de la réutilisation du framework.

## Mise en avant des éditions

Actuellement, notre rapport sait indiquer quel test échoue pour chaque processor, et redirige vers les fichiers sources correspondants à ces mutations.
Il serait interessant pour la suite de sauvegarder directement les modifications exactes effectuées sur le code dans une fichier de log, afin de pouvoir mettre en avant ces données dans notre rapport, et ainsi simplifier la recherche du code muté pour l'utilisateur.

##Faiblesses du report
Le report est généré en Java, et parse les fichiers xml générés par JUnit. Notre framework est donc dépendant de la structure de ces fichiers report,
ce qui est une faiblesse puisque le framework ne fonctionnerait plus si la structure des reports changeait.
