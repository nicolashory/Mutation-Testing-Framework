package generator;

import java.io.*;
import java.util.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Nicolas HORY
 * @version 23/02/16.
 */
public class ReportCreater {
    private String filePath; // Chemin du dossier du code source/Result
    private int nbTest;     // Nombre de fichiers de tests
    private int nbFails;    // Nombre de mutants tués
    private int nbSuccess;  // Nombre de mutants encore en vie
    private int nbCompileFail; // Nombre de mutants morts-nés
    private List<String> listRepertoriesToCheck; // Liste des dossiers dans le dossier du code source/Result

    public ReportCreater(String path) {
        filePath = path;
        nbTest = 0;
    }

    /**
     * Initialise listRepertoriesToCheck avec les répertoires dans le dossier filePath
     */
    private void getRepertoriesInCurrentDir() {
        File directory = new File(filePath);
        this.listRepertoriesToCheck= Arrays.asList(directory.list());
    }

    /**
     * Génère le début du fichier html, avec les balises de style et l'entête
     * @param file Le FileWriter du rapport html
     */
    private void generateBeginReport(FileWriter file) {
        try {
            file.write("<!doctype html><html><head><meta charset=\"utf-8\"><title>Rapport sur les mutations</title>");
            file.write("<meta name = \"description\" content = \"\" ><meta name = \"viewport\" content = \"width=device-width\">");
            file.write("<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n");
            file.write("<style>table {border-collapse: collapse;}" +
                    "table, tr, td {border: 1px solid black;border-spacing:20px;}\n" +
                    "        td {padding: 7px;}" +
                    "        div, h1, table{margin:auto;}" +
                    "        .page-header{text-align: center;}" +
                    "        a{text-decoration:none;} " +
                    "    </style>");
            file.write("</head><body>");
            file.write("<div class=\"container\">\n" +
                    "  <div class=\"page-header\">\n" +
                    "    <h1>Rapport sur les mutations</h1>      \n" +
                    "    <h2>Cliquez sur une case du tableau pour avoir un aperçu du fichier de test concerné, ainsi que des indications sur d'où peuvent provenir les erreurs.</h2>\n" +
                    "  </div>\n" +
                    " <div id=\"tableResults\">");
            file.write("<table style=\"border-collapse : collapse; border-spacing : 2px;\">"); // On ouvre la balise du tableau
        } catch (Exception e) {
            // Probleme dans la generation du rapport
        }
    }

    /**
     * Génère le diagramme Piechart résumant les trois types de mutants (tués, vivants, morts-nés)
     * @param file Le FileWriter associé au fichier dans lequel générer le piechart
     */
    private void generatePiechart(FileWriter file) {
        try {
            file.write("<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                    "<script type=\"text/javascript\">\n" +
                    "    google.charts.load('current', {'packages':['corechart']});\n" +
                    "    google.charts.setOnLoadCallback(drawChart);\n" +
                    "    function drawChart() {\n" +
                    "\n" +
                    "        var data = google.visualization.arrayToDataTable([\n" +
                    "            ['Tests par mutations', 'Résultats'],\n" +
                    "            ['Mutants vivants'," + (nbSuccess - 1) + "],\n" +
                    "            ['Mutants tués'," + nbFails + "],\n" +
                    "            ['Mutants morts-nés'," + nbCompileFail +"],\n" +
                    "        ]);\n" +
                    "\n" +
                    "        var options = {\n" +
                    "            title: 'Diagramme des résultats:',\n" +
                    "            colors: ['#008000', '#FF0000', '#8A4B08']\n" +
                    "        };\n" +
                    "\n" +
                    "        var chart = new google.visualization.PieChart(document.getElementById('piechart'));\n" +
                    "\n" +
                    "        chart.draw(data, options);\n" +
                    "    }\n" +
                    "</script>");
        } catch(Exception e) {
            // Probleme generation piechart
        }
    }

    /**
     * Génère la fin du rapport html
     * @param file Le fichier html (FileWriter)
     */
    private void generateEndReport(FileWriter file) {
        try {
            file.write("</table>");
            file.write("</div>");
            file.write("<div id=\"piechart\" style=\"width: 900px; height: 500px;\"></div>");
            file.write("</div");
            generatePiechart(file);
            file.write("</body></html >");
        } catch (Exception e) {
            // Probleme d'écriture dans le fichier
        }
    }

    /**
     * Génère la première ligne du tableau récapitulatif, avec les noms des fichiers tests
     * @param file Le FileWriter associé au fichier rapport
     * @throws IOException
     */
    private void generateFirstLineTable(FileWriter file) throws IOException {
            List<String> filesFromRep = Arrays.asList(new File(filePath+"/NoMutation/reports").list()); // On récupère les fichiers dans le dossier NoMutation
            file.write("<tr><td></td>"); // Première case vide
            for (String repFile : filesFromRep) { // On parcourt les fichiers présents dans le dossier
                if (repFile.endsWith("xml")) { // Si il s'agit d'un fichier xml
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    try {
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(filePath + "/NoMutation/reports/" + repFile);
                        doc.getDocumentElement().normalize();
                        Element root = doc.getDocumentElement();
                        String classe = root.getAttribute("name"); // On récupère le nom du fichier test
                        file.write("<td>" + classe + "</td>"); // On crée une case avec ce nom
                        nbTest++; // On incrémente le nombre de tests
                    } catch (Exception e) {
                        //Problème lors de l'ouverture du xml
                    }
                }
            }
            file.write("</tr>"); // Fin de la ligne
    }

    /**
     * Ecrit le contenu d'un fichier dans un bloc html
     * @param writer Le FileWriter associé au fichier
     * @param input Le fichier que l'on lit et qu'on veut convertir en html
     */
    private void writeTestFileinHtml(FileWriter writer, String input) {
        try {
            FileReader reader=new FileReader(input);
            BufferedWriter writeBuffer = new BufferedWriter(writer);
            BufferedReader buffer = new BufferedReader(reader);
            writeBuffer.write("<div id=\"testFile\">");
            String line;
            int i = 1;
            // On lit ligne par ligne
            while((line=buffer.readLine()) != null) {
                writeBuffer.write(i + "&nbsp"); // On écrit le numéro de la ligne
                writeBuffer.write(line);    // On écrit la ligne
                writeBuffer.write("<br/>"); // Saut de ligne
                i++;
            }
            writeBuffer.write("</div>");
            reader.close();
            writeBuffer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée un fichier html relatif à une case du tableau récapitulatif
     * @param writer Le FileWriter pour écrire dans le fichier
     * @param lineError Le message disant la ligne d'erreur du test
     * @param msgError Le message correspondant a l'eventuelle erreur
     * @param hasError Booleen indiquant si il y a un message d'erreur a afficher ou pas
     * @param nameTestFile Le nom du fichier test
     */
    private void createHtmlFile(FileWriter writer, String lineError, String msgError, String nameTestFile, boolean hasError, String repertory) {
        try {
            writer.write("<!doctype html><html><head><meta charset=\"utf-8\"><title>Rapport sur les mutations</title>\n" +
                    "<meta name = \"description\" content = \"\" ><meta name = \"viewport\" content = \"width=device-width\">");
            writer.write("<style>div, h1 {margin:auto;}" +
                    "        .page-header, div{text-align: center;}" +
                    "        #testFile{text-align:left;}" +
                    "    </style>");
            writer.write("</head><body>");
            writer.write("<div class=\"container\">\n" +
                    "  <div class=\"page-header\">\n" +
                    "    <h1><a style=\"text-decoration:none;\" href=\"" + filePath+ "/" + repertory + "/generated-sources/spoon\">Cliquer ici pour afficher le dossier contenant le code muté.</a></h1>\n" );
            // Si il y a une erreur, on affiche ces deux lignes, sinon cela n'est pas nécessaire
            if (hasError) {
                writer.write("<p>Message(s) de failure ou d'erreur: <b style=\"color:red;\">" + msgError + "</b></p>\n" +
                             "<p>Ligne(s) associée(s): <b style=\"color:red;\">" + lineError + "</b></p>\n");
            }
            writer.write("</div>\n");

            writeTestFileinHtml(writer, nameTestFile); // Copie le contenu du fichier java concerné dans le fichier html
            writer.write("</body></html>");
            writer.close();
        } catch (Exception e) {
            // Erreur sur le writer
        }
    }

    /**
     * Crée le rapport final à partir des fichiers report xml
     */
    private void createReportFromXmls() {
        try {
            File finalReport = new File(filePath + "MutationReport.html"); // On crée le fichier MutationReport.html
            FileWriter out = new FileWriter(finalReport); // FileWriter pour écrire dans le fichier
            generateBeginReport(out); // On génère le début du fichier
            generateFirstLineTable(out); // On génère la première ligne du tableau
            for (String repWithReport : listRepertoriesToCheck) { // On parcourt les dossier dans Result
                boolean hasReports = false;
                File repertory = new File(filePath + repWithReport);
                for (String subRep: Arrays.asList(repertory.list())) { // On parcourt les sous-dossiers
                    if (subRep.equalsIgnoreCase("reports")) { // Si le dossier reports est présent
                        hasReports = true; // On passe le booléen a true;
                    }
                }
                if (hasReports) { // Si le dossier a des reports
                    // On récupère les fichiers dans reports
                    List<String> filesFromRep = Arrays.asList(new File(filePath + "/" + repWithReport + "/reports").list());
                    boolean hasNoFail = true;
                    out.write("<tr><td>" + repWithReport + "</td>"); // Première colonne du tableau
                    for (String file : filesFromRep) { // On parcourt les fichiers
                        if (file.endsWith("xml")) { // Si le fichier est un xml
                            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                            try {
                                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                                Document doc = dBuilder.parse(filePath + "/" + repWithReport + "/reports/" + file);
                                doc.getDocumentElement().normalize();
                                Element root = doc.getDocumentElement();
                                String failure = root.getAttribute("failures"); // On récupère le nombre de failures
                                String errors = root.getAttribute("errors"); // On récupère le nombre d'errors
                                NodeList testsList = doc.getElementsByTagName("testcase"); // Les balises testcase
                                String testFile = ((Element)testsList.item(0)).getAttribute("classname"); // On récupère le nom du fichier de test
                                testFile = testFile.replaceAll("\\.", "/"); // On remplace les "." par des "/"
                                testFile += ".java"; // On rajoute l'extension ".java"
                                String PathWithoutResult = filePath.replace("/Result", ""); // On supprime le /Result du filePath
                                String nameTestFile = PathWithoutResult + "/src/test/java/" + testFile; // Le fichier test .java
                                String msgOfError=""; // Message de l'erreur
                                String lineOfError=""; // Ligne de l'erreur
                                for (int i = 0; i < testsList.getLength(); i++) { // On parcourt les testcase
                                    Element node = (Element)testsList.item(i);
                                    if (node.getElementsByTagName("failure").getLength() > 0) { // Si il y a eu failure
                                        Element failElement = (Element)node.getElementsByTagName("failure").item(0); // Récupère la failure concernée
                                        // Récupère les messages pour l'utilisateur
                                        msgOfError = failElement.getAttribute("message");
                                        String tabContent[] = failElement.getTextContent().split("at ");
                                        lineOfError += tabContent[tabContent.length - 1] + " ";
                                    } else if (node.getElementsByTagName("error").getLength() > 0) { // Si il y a eu error
                                        Element errorElement = (Element)node.getElementsByTagName("error").item(0); // Récupère l'error concernée
                                        // Récupère les messages pour l'utilisateur
                                        msgOfError = errorElement.getAttribute("message");
                                        String tabContent[] = errorElement.getTextContent().split("at ");
                                        lineOfError += tabContent[tabContent.length - 1] + " ";
                                    }
                                }
                                String nameHtmlFile = filePath  + repWithReport + file; // Nom du fichier html lié au clic sur case de tableau
                                nameHtmlFile = nameHtmlFile.replace(".xml", ".html"); // On remplace l'extension xml par html
                                File htmlFile = new File(nameHtmlFile); // On crée le fichier html
                                FileWriter outHtml = new FileWriter(htmlFile); // FileWriter lié au fichier html
                                boolean hasError = (!msgOfError.isEmpty()); // Si message d'erreur vide, boolean a false
                                createHtmlFile(outHtml, lineOfError, msgOfError, nameTestFile, hasError, repWithReport); // On remplit le fichier html avec les informations pour l'utilisateur (si nécessaire)
                                if (Integer.parseInt(failure) == 0 && Integer.parseInt(errors) == 0) { // Aucun fail: case verte
                                    out.write("<td style=\"background:green;color:green;\"><a style=\"color:green;\" href=\"" + nameHtmlFile + "\" target=\"_blank\">\n" +
                                            "  <div>\n" +
                                            "     Fichier test\n" +
                                            "  </div>\n" +
                                            "</a></td>");
                                } else { // Au moins un fail ou une error: case rouge
                                    out.write("<td style=\"background:red;color:red;\"><a style=\"color:red;\" href=\"" + nameHtmlFile +"\" target=\"_blank\">\n" +
                                            "  <div>\n" +
                                            "     Fichier test\n" +
                                            "  </div>\n" +
                                            "</a></td>");
                                    hasNoFail = false;
                                }

                            } catch (Exception e) {
                                //Problème lors de l'ouverture du xml
                            }
                        }
                    }
                    out.write("</tr>");
                    if (hasNoFail) nbSuccess++; // Si aucun test n'a de failure ou d'erreur, alors mutant vivant
                    else nbFails++;
                } else { // Si pas de dossier reports, alors il s'agit d'une erreur de compilation
                    out.write("<tr><td>" + repWithReport + "</td>");
                    nbCompileFail++;
                    for (int i = 0; i < nbTest; i++) { // Sinon on met autant de balises "Compilation failure" qu'il y a de tests
                        out.write("<td style=\"background:#8A4B08;text-align:center;color:white;\"><b>COMPILATION FAILURE</b></td>");
                    }
                }
            }
            generateEndReport(out); // On génère la fin du rapport
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ReportCreater creater = new ReportCreater(args[0]);
        creater.getRepertoriesInCurrentDir();
        creater.createReportFromXmls();
    }
}
