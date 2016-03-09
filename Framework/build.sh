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

#On conserve le path du framework
frameworkFolder=$(pwd)

#1: On lance le maven install
echo -e "Lancement de Maven Install pour le framework..."
mvn install &>/dev/null
echo -e "Maven Install: FAIT"
echo -e "--------------------------------------------------"

# 2. Pour chaque processor dans notre framework
#        Faire -> Editer le pom de l'application en mettant uniquement le bon processor dedant
#        Lancer le mvn compile de l'application avec en option le buildirectory souhaité pour cette version du mutant
#        Lancer le maven surefire test de l'application avec les bonnes options (en modifiant le dossier des sources par le buildirectory utilisé)

#Recuperation de toutes nos mutations
echo -e "Récupération de l'ensemble des mutations à appliquer..."
cd src/main/java/mutation
allMutation=($(ls | cut -f1 -d'.'))

# Affichage de toutes les mutations
echo -e "Mutations qui vont être appliquées:"
echo -e "${allMutation[*]}"
echo -e "--------------------------------------------------"

# Recuperation du nombre de mutations
nbMutation=${#allMutation[@]}

# On se place dans l'application où on doit appliquer les mutations
cd $1
# Si dossier Result deja present, on le supprime
if [ -d "./Result" ];then	\
                echo -e "Suppression du dossier Result existant..."
                rm -rf "./Result"	;	\
fi
# Creation du dossier résultat dans lequel on placera tous les dossiers générés pour les mutations
#   et le rapport
mkdir Result

mkdir Result/NoMutation
echo -e "Lancement des tests sur le code de base..."
mvn test -DreportDirectory=./Result/NoMutation/reports &>/dev/null
echo -e "Tests sur le code de base: FAIT"
echo -e "--------------------------------------------------"


#Boucle pour chacune de nos mutations avec
# - Création d'un dossier du nom de la mutation
# - Modification du pom.xml de l'application
# - Mvn compile redirige vers ce dossier
# - Mvn test redirige vers ce dossier
for mutation in ${allMutation[@]}
do
    mkdir Result/$mutation
    sed -i -e "s/<processors>.*<\/processors>/<processors><processor>mutation.$mutation<\/processor><\/processors>/g" $frameworkFolder/src/main/resources/pom.xml
    echo -e "Application de la mutation" $mutation "..."
    mvn compile -DbuildDirectory=./Result/$mutation &> /dev/null
    mvn test -DreportDirectory=./Result/$mutation/reports &> /dev/null
    echo -e "Suppression des dossiers classes, maven-status et spoon-maven-plugin..."
    rm -rf Result/$mutation/classes Result/$mutation/maven-status Result/$mutation/spoon-maven-plugin
    echo -e "Mutation" $mutation ": FAIT"
    echo -e "--------------------------------------------------"
done

#Remise en état d'origine du pom.xml
cd $1
sed -i -e "s/<processors>.*<\/processors>/<processors><\/processors>/g" $frameworkFolder/src/main/resources/pom.xml

# 3. Generer le rapport html a partir de tous les rapports générés: java ReportCreater
echo -e "Creation du rapport final..."
cd $frameworkFolder/target/classes
java generator.ReportCreater $1/Result/ $frameworkFolder

# 4. Clean l'ensemble des dossiers créés pour la génération et utilisation des mutants
rm -rf $1/target $frameworkFolder/target
cd $1/Result
#Ouverture du rapport dans le navigateur par défaut
xdg-open ./MutationReport.html &

