/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eac.container.servlet;

import com.eac.tool.json.JSONContainer;
import com.eac.db.dao.ContainerDAO;
import com.eac.db.entity.Container;
import com.eac.entity.operation.APPAction;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sijin
 */
public class ReloadAPP extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String containerid = request.getParameter("containerid");

            Container container = new Container(containerid);

            ContainerDAO cdao = new ContainerDAO();

            container = cdao.fetch(container);

            APPAction aact = new APPAction();

            String json = "";

            JSONContainer jc = new JSONContainer();

            if (aact.notifyReload(container)) {
                json = jc.createSimpleJSON(true);
            } else {
                json = jc.createSimpleJSON(false);
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

    private String reloadAPP(String containerid) {

        //send requests using container id

        String json = "";

        JSONContainer jc = new JSONContainer();

        json = jc.createSimpleJSON(true);

        return json;
    }
}
