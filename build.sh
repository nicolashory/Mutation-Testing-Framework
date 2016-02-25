#!/bin/bash
mvn clean
mvn validate 
if [ ! -d Base ]; then
  mkdir Base
else 
    echo "Base folder already exists..."
fi
mvn compile -DbuildDirectory=Base
mvn test -DreportDirectory=Base/reports
#for (mutation [i]) {
#    créer un dossier mutation[i] -> code mutant généré (.class) + xml
#}
#xml généré 
#html synthèse
java ReportCreater

# 1. On mvn install le Framework
# 2. Pour chaque processor dans notre framework (sauvegardés dans un fichier config?)
#        Faire -> Editer le pom de l'application en mettant uniquement le bon processor dedant
#        Lancer le mvn compile de l'application avec en option le buildirectory souhaité pour cette version du mutant
#        Lancer le maven surefire test de l'application avec les bonnes options (en modifiant le dossier des sources par le buildirectory utilisé)
# 3. Generer le rapport html a partir de tous les rapports générés