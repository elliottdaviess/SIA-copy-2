package com.example.SIA.Services;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.unbescape.html.HtmlEscape;

import javax.swing.text.html.HTML;
import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.unbescape.html.HtmlEscape.escapeHtml4;
@Service
public class HTMLService {


    public String generateHTML(String title, String link){

        title = title.trim();

//        String templateoutofbox = escapeHtml4("<!--START "+title+"-->\n" +
//                "                    <div class=\"card card-plain\">\n" +
//                "                        <div class=\"card-header card-header-primary\">\n" +
//                "                            <h4 class=\"card-title\">Title Placeholder</h4>\n" +
//                "                            <p class=\"card-category\">Shared Reports</p>\n" +
//                "                            \n" +
//                "                        </div>\n" +
//                "                        <div class=\"row\">\n" +
//                "                            <div class=\"col-lg-12 col-md-12\" >\n" +
//                "                                Link Placeholder\n" +
//                "                            </div>\n" +
//                "                        </div>\n" +
//                "                    </div>\n" +
//                "                    <!--END "+title+"-->\n" +
//                "                    <!--change-->");


        String template = escapeHtml4("<!--START "+title+"-->\n" +
                "                    <div class=\"card card-plain\">\n" +
                "                        <div class=\"card-header card-header-primary\">\n" +
                "                            <h4 class=\"card-title\">Title Placeholder</h4>\n" +
                "                            <p class=\"card-category\">Shared Reports</p>\n" +
                "                            <div class=\"row\">\n" +
                "                                <div class=\"col-lg-12 col-md-12\" >\n" +
                "                                    Link Placeholder\n" +
                "                                </div>\n" +
                "\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <!--END "+title+"-->\n" +
                "                    <!--change-->");
        String newHTML = template.replaceAll("Title Placeholder", title);
        newHTML = newHTML.replaceAll("Link Placeholder", link);
        newHTML = HtmlEscape.unescapeHtml(newHTML);
        newHTML = newHTML.replaceAll("width=\"640\"", "width = \"100%\"");
        newHTML = newHTML.replaceAll("height=\"480\"", "height = \"100%\"");
        return newHTML;
    }



    public String removeReportFromHTML(String htmlfile, String lowerBound, String upperBound){

        String HTML_toRemove = StringUtils.substringBetween(htmlfile, lowerBound, upperBound);
        return HTML_toRemove;
    }

    public void removeSectionFromNav(String title){
        title = title.replaceAll("\\s+","");
        String lowerBound = "<!--START "+title+"-->";
        String upperBound = "<!--END "+title+"-->";

        String path = "src/main/resources/templates/navbar.html";
            String htmlfile = "";
        try {
            htmlfile = new Scanner(new File(path)).useDelimiter("\\A").next();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String HTML_toRemove = StringUtils.substringBetween(htmlfile, lowerBound, upperBound);
        System.out.println(HTML_toRemove);

        htmlfile = htmlfile.replace(HTML_toRemove, "");
        htmlfile = htmlfile.replaceAll(lowerBound, "");
        htmlfile = htmlfile.replaceAll(upperBound, "");
        overwriteFile(path, htmlfile, title);
    }

    public void deleteSectionHTMLfile(String title){
        title = title.replaceAll("\\s+","");

        try
        {
            Files.deleteIfExists(Paths.get("src/main/resources/templates/"+title+".html"));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }

        System.out.println("Deletion successful.");
    }



    public void overwriteFile(String path, String htmlfile, String section){
        try{

            File file = new File(path);

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(htmlfile);
            bw.close();

            System.out.println("Change made to the " + section + " page");
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public String insertTitleIntoTemplate(String title){

        title = title.trim();

        String path = "src/main/resources/templates/template.html";
        String htmlfile = null;
        try {
            htmlfile = new Scanner(new File(path)).useDelimiter("\\A").next();
            htmlfile = htmlfile.replaceAll("Title Placeholder", title);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        System.out.println("HTML file: "+ htmlfile);
        return htmlfile;

    }


    public void createNewHTMLfile(String title,String HTML){

        title = title.replaceAll("\\s+","");

        try{

            File file = new File("src/main/resources/templates/"+title+".html");

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(HTML);
            bw.close();
            System.out.println(title + " page created");
        }catch(IOException e){
            e.printStackTrace();
        }



    }

    public void addNewHTMLtoNavBarString(String title, String icon) {
        title = title.trim();
        icon = icon.trim();
        String titleNoSpaces = title.replaceAll("\\s+","");
        String path = "src/main/resources/templates/navbar.html";

//        String templateoutofbox = escapeHtml4("<!--START "+title+"-->\n" +
//                "                    <div class=\"card card-plain\">\n" +
//                "                        <div class=\"card-header card-header-primary\">\n" +
//                "                            <h4 class=\"card-title\">Title Placeholder</h4>\n" +
//                "                            <p class=\"card-category\">Shared Reports</p>\n" +
//                "                            \n" +
//                "                        </div>\n" +
//                "                        <div class=\"row\">\n" +
//                "                            <div class=\"col-lg-12 col-md-12\" >\n" +
//                "                                Link Placeholder\n" +
//                "                            </div>\n" +
//                "                        </div>\n" +
//                "                    </div>\n" +
//                "                    <!--END "+title+"-->\n" +
//                "                    <!--change-->");
        String navTemplate = "                <!--START "+ titleNoSpaces+"-->\n" +
                "                <li th:class=\"'nav-item ' + ${classActive"+titleNoSpaces+"}\">\n" +
                "                    <a class=\"nav-link\" href=\"/"+titleNoSpaces+"\">\n" +
                "                        <i class=\"material-icons\">"+icon+"</i>\n" +
                "                        <p>"+title+"</p>\n" +
                "                    </a>\n" +
                "                </li>\n" +
                "                <!--END "+ titleNoSpaces+"-->"+
                "\n" +
                "                <!--change-->";

        String htmlfile = null;
        try {
            htmlfile = new Scanner(new File(path)).useDelimiter("\\A").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        htmlfile = htmlfile.replace("<!--change-->", navTemplate);

        overwriteFile(path, htmlfile, title);

        System.out.println(navTemplate);
    }



}
