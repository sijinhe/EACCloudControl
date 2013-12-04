/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eac.monitor.servlet;

import com.eac.db.entity.History;
import com.eac.entity.operation.HistoryAction;
import com.eac.tool.json.JSONContainer;
import com.eac.tool.json.JSONHistory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sijin
 */
public class APPHistory extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //http://barking01.doc.ic.ac.uk:8080/EACCloudControl/APPHistory?containerid=61e87356-a151-491c-9a0f-f887a810d28d&startdate=1334329660000&enddate=1334330380000&timeinterval=60000
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String containerid = request.getParameter("containerid");
            String startDate = request.getParameter("startdate"); //all in milli seconds
            String endDate = request.getParameter("enddate");
            String timeInterval = request.getParameter("timeinterval");

            HistoryAction ha = new HistoryAction();

            String json = "";

            JSONHistory jc = new JSONHistory();

            List<History> historyList = null;


            if (containerid != null && startDate != null && endDate != null && timeInterval != null) {

                historyList = ha.fetchHistoryList(containerid, startDate, endDate, timeInterval);

            //    System.out.println("ssss");


                json = jc.createJSON(historyList, true, "History Fetch [Success]");

          //      System.out.println("sssddddds");


            } else {
                json = jc.createJSON(historyList, false, "History Fetch [Insufficient number of Paramenters]");
            }

            out.println(json);



        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
