# Améliorations Possibles

## Boucles Infinies

Il arrive que nos mutations génèrenet des boucles infinies, qui ne sont pas toujours traités de bases dans les tests de l'utilisateur.
Nous assumons pour l'instant que l'utilisateur annote la totalité de ses tests contenant des fonctions utilisant des boucles avec l'option timeout de Junit.
Cependant, pour automatiser cela, nous pourrions effectuer une première mutation sur les tests de bases qui consisterait à rajouter ces annotations la ou elles ne sont pas présentes.
Une autre solution serait d'utiliser les threads, en en créant un qui calcule le temps d'execution de chaque fonction, et qui coupe le thread parti en boucle infini.

## Selecteurs

## Mutations

## Génération Aléatoires

La totalité de nos combinaisons mutations / selecteurs sont prédéfinies dans chaque classe de processeur.
Une amélioration possible serait de combiner aléatoirement les différents élements afin de générer un plus grand nombre de mutations qui renforceraient donc les tests du banc souhaité.

## Fonctionnement

Notre Framework se base principalement sur un script shell qui execute notre chaine de build au complet.
Nous sommes donc dépendant de la plateforme de l'utilisateur, et une personne souhaitant utiliser notre Framework sur Windows ou Mac aura un travail de fond à effectuer avant son utilisation.
Nous pourrions faire évoluer ce script vers une plugin maven, qui ne serait donc moins dépendant de la plateforme utilisée et simplifierait donc son utilisation.

## Incrémentation des utilisations

Si un utilisateur utilise notre frameweork plusieurs fois à la suite, il relance la chaine de build au complet, ce qui comprends donc l'installation de notre framework.
Il serait interessant de n'executer que les étapes nécessaires lors de la réutilisation du framework.

## Mise en avant des éditions

Actuellement, notre rapport sait indiquer quel test échoue pour chaque processor, et redirige vers les fichiers sources correspondants à ces mutations.
Il serait interessant pour la suite de sauvegarder directement les modifications exactes effectuées sur le code dans une fichier de log, afin de pouvoir mettre en avant ces données dans notre rapport, et ainsi simplifier la recherche du code muté pour l'utilisateur.
