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