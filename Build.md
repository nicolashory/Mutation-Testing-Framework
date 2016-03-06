## Génération du Framework

Il faut commencer par inclure la dépendance maven de notre framework dans le projet maven que vous souhaiter tester:

        <dependency>
            <groupId>devops.2.3</groupId>
            <artifactId>mutation-testing-framework</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        
Il suffit ensuite d'utiliser notre script shell en indiquant le chemin absolu du dossier du projet que l'on souhaite tester.
(Notre script inclut l'installation du framework, il n'est donc pas nécessaire d'effectuer cette étape à la main).