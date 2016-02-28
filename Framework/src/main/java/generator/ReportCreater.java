package generator;

import java.io.*;
import java.util.*;

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
        //String currentDir = System.getProperty("user.dir" );
        File directory = new File(filePath);
        //String [] listDirectories;
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

    private void createReportFromXmls() {
        String xmlFile="";
        Scanner scanner = null;
        try {
            File finalReport = new File(filePath + "MutationReport.html");
            FileWriter out = null;
            out = new FileWriter(finalReport);
            out.write("<html><body><table>");
            out.write("<tr><td>Classe</td><td>Tests</td><td>Errors</td><td>Skipped</td><td>Failures</td><td>Time</td>");

            for (String repWithReport : listRepertoriesToCheck) {
                List<String> filesFromRep = Arrays.asList(new File(filePath+"/"+repWithReport+"/reports").list());
                for (String file : filesFromRep) {
                    if (file.endsWith("xml")) {
                        xmlFile = file;
                    }
                }
                System.out.println(repWithReport + "/" + xmlFile);
                scanner = new Scanner(new File(filePath+"/"+repWithReport + "/reports/" + xmlFile)).useDelimiter("\\Z");
                String xmlContent = scanner.next();
                xmlContent = xmlContent.trim();
                String subStr = xmlContent.substring(xmlContent.indexOf("name"), xmlContent.indexOf("\">"));
                System.out.println(subStr);
                String attributes[] = subStr.split(" ");
                ArrayList<String> finalValues = new ArrayList<>();
                for (String att : attributes) {
                    if (att.charAt(att.length()-1) == '\"') {
                        att = att.substring(0, att.length()-1);
                        finalValues.add(att.substring(att.indexOf("\"")+1));
                    } else {
                        finalValues.add(att.substring(att.indexOf("\"")+1));
                    }
                }
                String classe = finalValues.get(0);
                String time = finalValues.get(1);
                String tests = finalValues.get(2);
                String errors = finalValues.get(3);
                String skipped = finalValues.get(4);
                String failure = finalValues.get(5);

                out.write("<tr><td>" + classe + "</td><td>"+ tests + "</td><td>" + errors + "</td><td>" + skipped + "</td><td>" + failure + "</td><td>" + time + "</td></tr>" );
                scanner.close();
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
        creater.createReportFromXmls();
    }
}
