package com.example.SIA.Controller;

import com.example.SIA.Controller.ExceptionHandling.NoSuchResourceException;
import com.example.SIA.DTO.AddSectionDTO;
import com.example.SIA.DTO.RemoveReportDTO;
import com.example.SIA.DTO.RemoveSectionDTO;
import com.example.SIA.DTO.ReportDTO;
import com.example.SIA.Services.HTMLService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
//import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;


import java.io.*;
import java.util.Scanner;

import static org.unbescape.html.HtmlEscape.escapeHtml4;

@Controller
public class SIA_Controller {
    private HTMLService htmlService;

    @Autowired
    public SIA_Controller( HTMLService htmlService) {
        this.htmlService = htmlService;
    }

    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public String renderpages(@PathVariable String page, HttpServletRequest request, Model model) throws NoSuchResourceException {
        model.addAttribute("classActive"+page,"active");
        return page;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) throws NoSuchResourceException {
        return "index";
    }


    @RequestMapping(value = "/{page}", method = RequestMethod.POST)
    public String renderpagesPOST(AddSectionDTO addSectionDTO, @PathVariable String page, HttpServletRequest request, Model model) throws NoSuchResourceException {

        String html = htmlService.insertTitleIntoTemplate(addSectionDTO.getSectionTitle()); //pass this as the parameter to the write
        htmlService.createNewHTMLfile(addSectionDTO.getSectionTitle(), html);
        htmlService.addNewHTMLtoNavBarString(addSectionDTO.getSectionTitle(), addSectionDTO.getIconTitle());
        model.addAttribute("classActive"+page,"active");
        //return addSectionDTO.getSectionTitle();
        return page;
    }

    @RequestMapping(value = "/RemoveSection", method = RequestMethod.GET)
    public String removesectionGET( HttpServletRequest request, Model model)throws NoSuchResourceException {
        model.addAttribute("classActiveRemoveSection","active");
        return "RemoveSection";
    }

    @RequestMapping(value = "/RemoveSection", method = RequestMethod.POST)
    public String removeSectionPOST(RemoveSectionDTO removeSectionDTO, HttpServletRequest request, Model model) throws NoSuchResourceException {
        System.out.println(removeSectionDTO.getSectionTitle());
        htmlService.removeSectionFromNav(removeSectionDTO.getSectionTitle());
        htmlService.deleteSectionHTMLfile(removeSectionDTO.getSectionTitle());

        model.addAttribute("classActiveRemoveSection","active");
        return "RemoveSection";
    }


    @RequestMapping(value = "/{section}/AddReport", method = RequestMethod.GET)
    public String HomeAddReport(@PathVariable String section, HttpServletRequest request, Model model) throws NoSuchResourceException {
        model.addAttribute("page",section);
        return "AddReport";
    }

    @RequestMapping(value = "/{section}/AddReport", method = RequestMethod.POST)
    public String HomeAddReportPOST(ReportDTO reportDTO, @PathVariable String section, HttpServletRequest request, Model model) throws IOException {

        String path = "src/main/resources/templates/"+section+".html";

        String newHTML = htmlService.generateHTML(reportDTO.getTitle(), reportDTO.getLink());
        String htmlfile = new Scanner(new File(path)).useDelimiter("\\A").next();
        htmlfile = htmlfile.replaceAll("<!--change-->", newHTML); //add the new dashboard html

        htmlService.overwriteFile(path, htmlfile, section);

        return "redirect:/{section}";
    }

    @RequestMapping(value = "/{section}/RemoveReport", method = RequestMethod.GET)
    public String RemoveReport(@PathVariable String section, HttpServletRequest request, Model model) throws NoSuchResourceException {
        model.addAttribute("page",section);
        return "RemoveReport";
    }

    @RequestMapping(value = "/{section}/RemoveReport", method = RequestMethod.POST)
    public String RemoveReportPOST(RemoveReportDTO removeReportDTO, @PathVariable String section, HttpServletRequest request, Model model) throws IOException {
        model.addAttribute("page",section);

        String title = removeReportDTO.getTitle().trim();
        String path = "src/main/resources/templates/"+section+".html";

        String htmlfile = new Scanner(new File(path)).useDelimiter("\\A").next();

        String lowerBound = "<!--START "+title+"-->";
        String upperBound = "<!--END "+title+"-->";

        String HTML_toRemove = htmlService.removeReportFromHTML(htmlfile, lowerBound, upperBound);

        htmlfile = htmlfile.replace(HTML_toRemove, "");
        htmlfile = htmlfile.replaceAll(lowerBound, "");
        htmlfile = htmlfile.replaceAll(upperBound, "");

        htmlService.overwriteFile(path, htmlfile, section);
        return "redirect:/{section}";
    }



//    @RequestMapping(value = "/Home", method = RequestMethod.GET)
//    public String home(HttpServletRequest request, Model model) {
//        model.addAttribute("classActiveHome","active");
//        return "Home";
//    }
//
//
//
//    @RequestMapping(value = "/MI", method = RequestMethod.GET)
//    public String MI(HttpServletRequest request, Model model) {
//        model.addAttribute("classActiveMI","active");
//
//        return "MI";
//    }
//
//    @RequestMapping(value = "/Renewals", method = RequestMethod.GET)
//    public String Renewals(HttpServletRequest request, Model model) {
//        model.addAttribute("classActiveRenewals","active");
//
//        return "Renewals";
//    }
//
//    @RequestMapping(value = "/TeleReports", method = RequestMethod.GET)
//    public String TeleReports(HttpServletRequest request, Model model) {
//        model.addAttribute("classActiveTeleReports","active");
//
//        return "TeleReports";
//    }
//
//    @RequestMapping(value = "/WebStats", method = RequestMethod.GET)
//    public String WebStats(HttpServletRequest request, Model model) {
//        model.addAttribute("classActiveWebStats","active");
//
//        return "WebStats";
//    }
//
//    @RequestMapping(value = "/Monitoring", method = RequestMethod.GET)
//    public String Monitoring(HttpServletRequest request, Model model) {
//        model.addAttribute("classActiveMonitoring","active");
//
//        return "Monitoring";
//    }
//
//    @RequestMapping(value = "/PolicySale", method = RequestMethod.GET)
//    public String PolicySale(HttpServletRequest request, Model model) {
//        model.addAttribute("classActivePolicySale","active");
//
//        return "PolicySale";
//    }
//
//    @RequestMapping(value = "/Sanctions", method = RequestMethod.GET)
//    public String Sanctions(HttpServletRequest request, Model model) {
//        model.addAttribute("classActiveSanctions","active");
//
//        return "Sanctions";
//    }
//
//    @RequestMapping(value = "/TravelReports", method = RequestMethod.GET)
//    public String TravelReports(HttpServletRequest request, Model model) {
//        model.addAttribute("classActiveTravelReports","active");
//
//        return "TravelReports";
//    }




    //------------------------

//    @RequestMapping(value = "old/navbar", method = RequestMethod.GET)
//    public String navbar(HttpServletRequest request, Model model) {
//        return "navigationbar";
//    }
//
//    @RequestMapping(value = "old/myindex", method = RequestMethod.GET)
//    public String myindex(HttpServletRequest request, Model model) {
//        return "old/myindex";
//    }
//
//
    @RequestMapping(value = "/old/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpServletRequest request, Model model) {
        return "old/dashboard2";
    }

    @RequestMapping(value = "/old/dashboard2", method = RequestMethod.GET)
    public String dashboard2(HttpServletRequest request, Model model) {
        return "old/dashboard2";
    }

    @RequestMapping(value = "/old/icons", method = RequestMethod.GET)
    public String icons(HttpServletRequest request, Model model) {
        return "old/icons";
    }

    @RequestMapping(value = "/old/icons2", method = RequestMethod.GET)
    public String icons2(HttpServletRequest request, Model model) {
        return "old/icons2";
    }

    @RequestMapping(value = "/old/maps", method = RequestMethod.GET)
    public String maps(HttpServletRequest request, Model model) {
        return "old/maps";
    }

    @RequestMapping(value = "/old/notifications", method = RequestMethod.GET)
    public String notifications(HttpServletRequest request, Model model) {
        return "old/notifications";
    }

    @RequestMapping(value = "/old/table", method = RequestMethod.GET)
    public String table(HttpServletRequest request, Model model) {
        return "old/table";
    }

    @RequestMapping(value = "/old/typography", method = RequestMethod.GET)
    public String typography(HttpServletRequest request, Model model) {
        return "old/typography";
    }

    @RequestMapping(value = "/old/upgrade", method = RequestMethod.GET)
    public String upgrade(HttpServletRequest request, Model model) {
        return "old/upgrade";
    }

    @RequestMapping(value = "/old/user", method = RequestMethod.GET)
    public String user(HttpServletRequest request, Model model) {
        return "old/user";
    }


}
