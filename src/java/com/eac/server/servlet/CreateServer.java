/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eac.server.servlet;

import com.eac.db.entity.Server;
import com.eac.entity.operation.ServerAction;
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
public class CreateServer extends HttpServlet {
   
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


            String serverid = request.getParameter("serverid");
            String ip = request.getParameter("ip");
            String port = request.getParameter("port");
            String type = request.getParameter("type");
            String homePath = request.getParameter("homePath");
            String clusterid = request.getParameter("clusterid");

            Server server = new Server();
            ServerAction sact = new ServerAction();
            String json = "";

            JSONContainer jc = new JSONContainer();

            server = sact.createServer(serverid, ip, port, type, homePath, clusterid);

            if (server != null) {

                json = jc.createSimpleJSON(true);
               // json = jc.createJSON(server, true, "Server Creation [Success]");
                Server con1 = new Server();

                con1 = sact.startServer(server);

                if (con1 == null) {
                    json = jc.createSimpleJSON(false);
               //     json = jc.createJSON(server, false, "Server Creation [Server Start Failed]");
                }

            } else {
                json = jc.createSimpleJSON(false);
            //    json = jc.createJSON(server, false, "Server Creation [DB Insertion Failed]");
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
