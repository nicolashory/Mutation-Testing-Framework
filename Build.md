##Build.md : Comment construire notre framework

Notre framework n'a pas besoin d'être construit puisqu'il s'agit d'un script shell automatisant toutes les étapes.
En revanche, il y a plusieurs contraintes liées aux dépendances à l'environnement d'exécution.

# Dépendances

- Java: utilisé pour la génération du rapport final
- Bash: notre script utilise de nombreuses commandes bash
- Maven: utilisation de pom.xml et modification de celui du code sur lequel le framework est appliqué
- JUnit: lancement des tests avant et après mutation