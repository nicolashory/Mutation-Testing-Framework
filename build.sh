#!/bin/bash
#Cas d'erreur si pas de paramètres passés
if [ ! $1 ]; then
    echo -e "Erreur: Pas de chemin passé en paramètre  pour le code à muter..."
    echo -e "Usage: ./build.sh cheminCodeSource"
    exit 1
fi

#Cas d'erreur si trop de paramètres passés
if [ $2 ]; then
    echo -e "Erreur: Trop d'arguments passés en paramètre pour le code à muter..."
    echo -e "Usage: ./build.sh cheminCodeSource"
    exit 1
fi

#On se place dans le dossier du framework
cp ReportCreater.class $1
cd Framework

#1: On lance le maven install
echo -e "Lancement de Maven Install pour le framework"
mvn install

# 2. Pour chaque processor dans notre framework (sauvegardés dans un fichier config?)
#        Faire -> Editer le pom de l'application en mettant uniquement le bon processor dedant
#        Lancer le mvn compile de l'application avec en option le buildirectory souhaité pour cette version du mutant
#        Lancer le maven surefire test de l'application avec les bonnes options (en modifiant le dossier des sources par le buildirectory utilisé)

#Recuperation de toutes nos mutations
echo -e "Récupération de l'ensemble des mutations à appliquer.."
cd src/main/java/mutation
allMutation=($(ls | cut -f1 -d'.'))
# Affichage de toutes les mutations : echo "${allMutation[*]}"
# Recuperation du nombre de mutations
nbMutation=${#allMutation[@]}

cd $1
mkdir NoMutation
mvn test -DreportDirectory=./NoMutation/reports

#Boucle pour chacune de nos mutations avec
# - Création d'un dossier du nom de la mutation
# - Modification du pom.xml de l'application
# - Mvn compile redirige vers ce dossier
# - Mvn test redirige vers ce dossier
for mutation in ${allMutation[@]}
do
    mkdir $mutation
    sed -i -e "s/<processors>.*<\/processors>/<processors><processor>mutation.$mutation<\/processor><\/processors>/g" "pom.xml"
    mvn compile -DbuildDirectory=./$mutation
    mvn test -DreportDirectory=./$mutation/reports
done

#mkdir FirstMutation
#sed -i -e "s/<processors>.*<\/processors>/<processors><processor>mutation.BinaryOperatorMutator<\/processor><\/processors>/g" "pom.xml"
#mvn compile -DbuildDirectory=./FirstMutation
#mvn test -DreportDirectory=./FirstMutation/reports

# 3. Generer le rapport html a partir de tous les rapports générés: java ReportCreater
cd $1
sed -i -e "s/<processors>.*<\/processors>/<processors><\/processors>/g" "pom.xml"
java ReportCreater

# 4. Clean l'ensemble des dossiers créés pour la génération et utilisation des mutants
rm ReportCreater.class
rm -rf target
