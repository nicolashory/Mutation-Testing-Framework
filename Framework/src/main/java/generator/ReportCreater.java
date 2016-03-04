package generator;

import java.io.*;
import java.util.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Nicolas HORY
 * @version 23/02/16.
 */
public class ReportCreater {
    private String filePath;
    private int nbTest;
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
            file.write("<!doctype html><html><head><meta charset=\"utf-8\"><title></title>");
            file.write("<meta name = \"description\" content = \"\" ><meta name = \"viewport\" content = \"width=device-width\">");
            file.write("<link rel = \"stylesheet\" href = \"bootstrap/css/bootstrap.css\"/>");
            file.write("<link rel = \"stylesheet\" href = \"bootstrap/css/bootstrap.min.css\"/>");
            file.write("<link rel = \"stylesheet\" href = \"bootstrap/css/bootstrap-theme.css\"/>");
            file.write("<link rel = \"stylesheet\" href = \"bootstrap/css/bootstrap-theme.min.css\"/>");
            file.write("<style>table {border-collapse: collapse;}" +
                    "table, tr, td {border: 1px solid black;border-spacing:20px;}\n" +
                    "        td {padding: 7px;}" +
                    "    </style>");
            file.write("</head><body>");
            file.write("<table style=\"border-collapse : collapse; border-spacing : 2px;\">");
        } catch (Exception e) {
            // Probleme dans la generation du rapport
        }
    }

    private void generateEndReport(FileWriter file, String frameworkPath) {
        try {
            file.write("</table>");
            file.write("<script src=\"bootstrap/js/bootstrap.js\"></script>");
            file.write("<script src=\"bootstrap/js/bootstrap.min.js\"></script>");
            file.write("</table>");
            file.write("</body ></html >");
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
                                if (Integer.parseInt(failure) == 0) { // Aucun fail: case verte
                                    out.write("<td style=\"background:green\"></td>");
                                } else {
                                    out.write("<td style=\"background:red\"></td>");
                                }

                            } catch (Exception e) {
                                //Problème lors de l'ouverture du xml
                            }
                        }
                    }
                    out.write("</tr>");
                } else {
                    out.write("<tr><td>" + repWithReport + "</td>");
                    for (int i = 0; i < nbTest; i++) {
                        out.write("<td style=\"background:red; text-align:center;\">Compilation Failure</td>");
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
        System.out.println(args[0]);
        ReportCreater creater = new ReportCreater(args[0]);
        creater.getRepertoriesInCurrentDir();
        creater.createReportFromXmls(args[1]);
    }
}
