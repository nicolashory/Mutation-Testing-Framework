# Mutation-Testing-Framework

###Projet DevOps - SI4 Polytech Nice Sophia
###Equipe 3 : Loryn Fontaine, Nicolas Hory, Loïc Potages

Durant ce projet, nous allons implémenter un environnement de tests par mutation
capable d'analyser un banc de tests en Java.

Le but est de vérifier que les tests mis en place pour un programme donné sont 
pertinents et qu'ils couvrent un maximum de cas. 

Pour cela, des mutations sont appliquées sur le programme initial afin d'y intégrer
des erreurs, et le banc de tests est relancé sur le programe  modifié (le "mutant"),
l'objectif étant de "tuer" un maximum de mutants.

Les résultats de l'analyse seront synthétisés dans un fichier html qui permettra de
voir le pourcentage de mutants "tués" et d'avoir accès au code des mutants qui ont
résistés aux tests, afin de voir pourquoi. Ce fichier permettra de mettre en
avant la pertinence et l'efficacité du banc de tests fourni. 
