/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eac.cluster.servlet;

import com.eac.db.entity.Cluster;
import com.eac.entity.operation.ClusterAction;
import com.eac.tool.json.JSONCluster;
import com.eac.tool.json.JSONContainer;
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
public class CreateCluster extends HttpServlet {

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

            String publicIp = request.getParameter("publicIp");
            String publicPort = request.getParameter("publicPort");
            String privateIp = request.getParameter("privateIp");
            String privatePort = request.getParameter("privatePort");
            String homePath = request.getParameter("homePath");
            String type = request.getParameter("type");

            Cluster cluster = new Cluster();
            ClusterAction cact = new ClusterAction();
            String json = "";

            JSONCluster jc = new JSONCluster();

            cluster = cact.createCluster(publicIp, publicPort, privateIp, privatePort, homePath, type);

            if (cluster != null) {

                json = jc.createJSON(cluster, true, "Cluster Creation [Success]");
                Cluster con1 = new Cluster();

                con1 = cact.startCluster(cluster);

                if (con1 == null) {
                    json = jc.createJSON(cluster, false, "Cluster Creation [DB Insertion Success] Enable Cluster [DB Insertion Failed]");
                }

            } else {
                json = jc.createJSON(cluster, false, "Cluster Creation [DB Insertion Failed]");
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
