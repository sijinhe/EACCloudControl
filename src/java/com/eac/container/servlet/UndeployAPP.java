/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eac.container.servlet;

import com.eac.tool.json.JSONContainer;
import com.eac.db.dao.ContainerDAO;
import com.eac.db.dao.ServerDAO;
import com.eac.db.entity.Container;
import com.eac.db.entity.ServerHasContainer;
import com.eac.entity.operation.APPAction;
import com.eac.entity.operation.ClusterAction;
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
public class UndeployAPP extends HttpServlet {

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

            String json = "";

            Container container = new Container(containerid);

            ContainerDAO cdao = new ContainerDAO();

            container = cdao.fetch(container);

            APPAction aact = new APPAction();

            JSONContainer jc = new JSONContainer();

            if (aact.notifyUndeploy(container)) {

                container = aact.undelpoyAPP(container);

                if (container != null) {
                    json = jc.createJSON(container, true, "Undeploy APP [DB Insertion Success]");
                } else {
                    json = jc.createJSON(container, false, "Undeploy APP [DB Insertion Failed]");
                }
            } else {
                json = jc.createJSON(container, false, "Undeploy APP [Apache Reload Failed]");
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
