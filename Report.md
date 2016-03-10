# Améliorations Possibles

## Boucles Infinies

Il arrive que nos mutations génèrent des boucles infinies, qui ne sont pas toujours traités de bases dans les tests de l'utilisateur.
Nous assumons pour l'instant que l'utilisateur annote la totalité de ses tests contenant des fonctions utilisant des boucles avec l'option timeout de Junit.
Cependant, pour automatiser cela, nous pourrions effectuer une première mutation sur les tests de bases qui consisterait à rajouter ces annotations la ou elles ne sont pas présentes.
Une autre solution serait d'utiliser les threads, en en créant un qui calcule le temps d'execution de chaque fonction, et qui coupe le thread parti en boucle infini.

## Selecteurs

## Mutations

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

# Architecture

L'architecture que nous avons voulu mettre en place se basait sur la génération de processeurs à partir de combinaisons de selecteurs et de mutations:
Chaque processeur associe un selecteur et une mutation (soit de manière prédéfinie, mais également par la suite de manière automatique en générant toutes les combinaisons possibles), puis applique cette combinaison à notre code, générant ainsi un mutant unique.

Nous avons cependant rencontrer des problèmes lorsque nous avons souhaités externaliser le code de nos selecteurs et mutations en dehors des processeurs, les sélecteurs ne sont par exemple jamais pris en compte, et certaines mutations donnaient des résultats non attendus.
C'est la le premier point à améliorer dans notre Framework afin de le rendre totalement opérationnel.

A l'heure actuelle, il ne fait qu'appliquer que des mutations prédéfinies dans les processeurs, et l'applique à la totalité des élements correspondant dans le code, sans prendre en compte les différents selecteurs générés.


