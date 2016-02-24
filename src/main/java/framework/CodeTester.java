package framework;

import junit.textui.TestRunner;

/**
 * Class that will execute all the operation
 * Created by lpotages on 24/02/2016.
 */
public class CodeTester
{
    private TestRunner testRunner; // Class that will permit to apply all the tests to the code


    public void execute()
    {
        System.out.println("Execution du code");

        // 1 ere etape: On charge les sources à tester

        // 2 eme etape: On genere les mutants sur ce code et on les stock dans des dossiers spécifique

        // 3 eme etape : On applique le mvn test du code source de base de chacun des dossiers
        /*
            Ressource:  https://maven.apache.org/surefire/maven-surefire-plugin/test-mojo.html

            Pour chaque dossier créé
            On change le classdirectory et on lance le maven test
            Ajout de l'option redirectTestOutputToFile
            reportFormat pour un report plus complet
            reportsDirectory pour changer le lieu d'ecriture des reports
            testFailureIgnore pour ignorer les echecs de tests
         */

        /*
        Utilisation du Spoon maven plugin : https://github.com/SpoonLabs/spoon-maven-plugin
         */

        // 4 eme étape:
    }

}
