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
    private String filePath;
    private int nbTest;
    private int nbFails;
    private int nbSuccess;
    private int nbCompileFail;
    private List<String> listRepertoriesToCheck;

    public ReportCreater(String path) {
        filePath = path;
        nbTest = 0;
    }

    private void getRepertoriesInCurrentDir() {
        File directory = new File(filePath);
        this.listRepertoriesToCheck= Arrays.asList(directory.list());
    }

    private void generateBeginReport(FileWriter file, String frameworkPath) {
        try {
            file.write("<!doctype html><html><head><meta charset=\"utf-8\"><title>Rapport sur les mutations</title>");
            file.write("<meta name = \"description\" content = \"\" ><meta name = \"viewport\" content = \"width=device-width\">");
            file.write("<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n");
            file.write("<link rel = \"stylesheet\" href = \"bootstrap/css/bootstrap.css\"/>");
            file.write("<link rel = \"stylesheet\" href = \"bootstrap/css/bootstrap.min.css\"/>");
            file.write("<link rel = \"stylesheet\" href = \"bootstrap/css/bootstrap-theme.css\"/>");
            file.write("<link rel = \"stylesheet\" href = \"bootstrap/css/bootstrap-theme.min.css\"/>");
            file.write("<style>table {border-collapse: collapse;}" +
                    "table, tr, td {border: 1px solid black;border-spacing:20px;}\n" +
                    "        td {padding: 7px;}" +
                    "        div, h1, table{margin:auto;}" +
                    "        .page-header{text-align: center;}" +
                    "    </style>");
            file.write("</head><body>");
            file.write("<div class=\"container\">\n" +
                    "  <div class=\"page-header\">\n" +
                    "    <h1>Rapport sur les mutations</h1>      \n" +
                    "    <h2>Cliquez sur une case du tableau pour avoir un aperçu du fichier de test concerné, ainsi que des indications sur d'où peuvent provenir les erreurs.</h2>\n" +
                    "  </div>\n" +
                    " <div id=\"tableResults\">");
            file.write("<table style=\"border-collapse : collapse; border-spacing : 2px;\">");
        } catch (Exception e) {
            // Probleme dans la generation du rapport
        }
    }

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

    private void generateEndReport(FileWriter file, String frameworkPath) {
        try {
            file.write("</table>");
            file.write("</div>");
            file.write("<div id=\"piechart\" style=\"width: 900px; height: 500px;\"></div>");
            file.write("</div");
            generatePiechart(file);
            file.write("<script src=\"bootstrap/js/bootstrap.js\"></script>");
            file.write("<script src=\"bootstrap/js/bootstrap.min.js\"></script>");
            file.write("</body></html >");
        } catch (Exception e) {
            // Probleme d'écriture dans le fichier
        }
    }

    private void generateFirstLineTable(FileWriter file) throws IOException {
            List<String> filesFromRep = Arrays.asList(new File(filePath+"/NoMutation/reports").list());
            file.write("<tr><td></td>");
            for (String repFile : filesFromRep) {
                if (repFile.endsWith("xml")) {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    try {
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(filePath + "/" + listRepertoriesToCheck.get(0) + "/reports/" + repFile);
                        doc.getDocumentElement().normalize();
                        Element root = doc.getDocumentElement();
                        String classe = root.getAttribute("name");
                        file.write("<td>" + classe + "</td>");
                        nbTest++;

                    } catch (Exception e) {
                        //Problème lors de l'ouverture du xml
                    }
                }
            }
            file.write("</tr>");
    }
    private void createReportFromXmls(String frameworkPath) {
        String xmlFile="";
        try {
            File finalReport = new File(filePath + "MutationReport.html");
            FileWriter out = new FileWriter(finalReport);
            generateBeginReport(out,frameworkPath);
            generateFirstLineTable(out);
            for (String repWithReport : listRepertoriesToCheck) {
                boolean hasReports = false;
                File repertory = new File(filePath + repWithReport);
                for (String subRep: Arrays.asList(repertory.list())) {
                    if (subRep.equalsIgnoreCase("reports")) {
                        hasReports = true;
                    }
                }
                if (hasReports) {
                    List<String> filesFromRep = Arrays.asList(new File(filePath + "/" + repWithReport + "/reports").list());
                    boolean hasNoFail = true;
                    out.write("<tr><td>" + repWithReport + "</td>");
                    for (String file : filesFromRep) {
                        if (file.endsWith("xml")) {
                            xmlFile = file;
                            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                            try {
                                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                                Document doc = dBuilder.parse(filePath + "/" + repWithReport + "/reports/" + xmlFile);
                                doc.getDocumentElement().normalize();

                                Element root = doc.getDocumentElement();

                                String failure = root.getAttribute("failures");
                                String errors = root.getAttribute("errors");
                                NodeList testsList = doc.getElementsByTagName("testcase");
                                for (int i = 0; i < testsList.getLength(); i++) {
                                    Element node = (Element)testsList.item(i);
                                    if (node.hasChildNodes()) {
                                        System.out.println("UNE FAILURE ICI");
                                        System.out.println(filePath + "/" + repWithReport + "/reports/" + xmlFile);
                                    }
                                }
                                if (Integer.parseInt(failure) == 0 && Integer.parseInt(errors) == 0) { // Aucun fail: case verte
                                    out.write("<td style=\"background:green\"></td>");
                                } else {
                                    out.write("<td style=\"background:red\"></td>");
                                    hasNoFail = false;
                                }

                            } catch (Exception e) {
                                //Problème lors de l'ouverture du xml
                            }
                        }
                    }
                    out.write("</tr>");
                    if (hasNoFail) nbSuccess++;
                    else nbFails++;
                } else {
                    out.write("<tr><td>" + repWithReport + "</td>");
                    nbCompileFail++;
                    for (int i = 0; i < nbTest; i++) {
                        out.write("<td style=\"background:#8A4B08;text-align:center;color:white;\"><b>COMPILATION FAILURE</b></td>");
                    }
                }
            }
            generateEndReport(out, frameworkPath);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        ReportCreater creater = new ReportCreater(args[0]);
        creater.getRepertoriesInCurrentDir();
        creater.createReportFromXmls(args[1]);
    }
}
