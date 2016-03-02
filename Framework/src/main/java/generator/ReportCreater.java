package generator;

import java.io.*;
import java.util.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * @author Nicolas HORY
 * @version 23/02/16.
 */
public class ReportCreater {
    private String filePath;
    private List<String> listRepertoriesToCheck;

    public ReportCreater(String path) {
        filePath = path;
    }

    private void getRepertoriesInCurrentDir() {
        File directory = new File(filePath);
        this.listRepertoriesToCheck= selectWantedDirectories(Arrays.asList(directory.list()));
    }

    private List<String> selectWantedDirectories(List<String> repertories) {
        System.out.println(repertories);
        List<String> wantedReps = new ArrayList<>();
        for (String entry : repertories) {
                if (!entry.contains(".")) { // Consider that it's a directory if has no extension
                    File subFolder = new File(filePath+"/"+entry);
                    for (String sub : Arrays.asList(subFolder.list())) {
                        if (sub.equalsIgnoreCase("reports")) {
                            wantedReps.add(entry);
                        }
                    }
                }
            }
        return wantedReps;
    }

    private void printRepertoriesToCheck() {
        for(String rep : listRepertoriesToCheck){
            System.out.println(rep);
        }
    }

    private void generateBeginReport(FileWriter file, String frameworkPath) {
        try {
            file.write("<!doctype html><html class=\"no-js\"><head><meta charset=\"utf-8\"><title></title>");
            file.write("<meta name = \"description\" content = \"\" ><meta name = \"viewport\" content = \"width=device-width\">");
            file.write("<link rel = \"stylesheet\" href = \""+ frameworkPath +"/bootstrap/css/bootstrap.css\"/>");
            file.write("<link rel = \"stylesheet\" href = \""+ frameworkPath +"/bootstrap/css/bootstrap.min.css\"/>");
            file.write("<link rel = \"stylesheet\" href = \""+ frameworkPath +"/bootstrap/css/bootstrap-theme.css\"/>");
            file.write("<link rel = \"stylesheet\" href = \""+ frameworkPath +"/bootstrap/css/bootstrap-theme.min.css\"/>");
            file.write("</head>");
        } catch (Exception e) {
            // Probleme dans la generation du rapport
        }
    }

    private void createReportFromXmls(String frameworkPath) {
        String xmlFile="";
        Scanner scanner = null;
        try {
            File finalReport = new File(filePath + "MutationReport.html");
            FileWriter out = new FileWriter(finalReport);
            generateBeginReport(out,frameworkPath);
            out.write("<body><table>");
            out.write("<tr><td>Classe</td><td>Tests</td><td>Errors</td><td>Skipped</td><td>Failures</td><td>Time</td>");

            for (String repWithReport : listRepertoriesToCheck) {
                List<String> filesFromRep = Arrays.asList(new File(filePath+"/"+repWithReport+"/reports").list());
                for (String file : filesFromRep) {
                    if (file.endsWith("xml")) {
                        xmlFile = file;
                    }
                }
                System.out.println(repWithReport + "/" + xmlFile);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                try {
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(filePath + "/" + repWithReport + "/reports/" + xmlFile);
                    doc.getDocumentElement().normalize();

                    Element root = doc.getDocumentElement();
                    System.out.println("Root element :" + root.getNodeName());

                    String classe = root.getAttribute("name");
                    String time = root.getAttribute("time");
                    String tests = root.getAttribute("tests");
                    String errors = root.getAttribute("errors");
                    String skipped = root.getAttribute("skipped");
                    String failure = root.getAttribute("failures");

                    out.write("<tr><td>" + classe + "</td><td>"+ tests + "</td><td>" + errors + "</td><td>" + skipped + "</td><td>" + failure + "</td><td>" + time + "</td></tr>" );

                } catch (Exception e) {
                    //Problème lors de l'ouverture du xml
                }
            }
            out.write("</table></body></html>");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
       // String reportXml;
        System.out.println(args[0]);
        ReportCreater creater = new ReportCreater(args[0]);
        creater.getRepertoriesInCurrentDir();
        creater.printRepertoriesToCheck();
        creater.createReportFromXmls(args[1]);
    }
}
