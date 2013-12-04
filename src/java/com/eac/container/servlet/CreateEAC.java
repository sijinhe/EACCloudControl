/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eac.container.servlet;

import com.eac.db.dao.StorageDAO;
import com.eac.tool.json.JSONContainer;
import com.eac.db.entity.Container;
import com.eac.db.entity.Storage;
import com.eac.entity.operation.APPAction;
import com.eac.entity.operation.ContainerAction;
import com.eac.entity.operation.StorageAction;
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
public class CreateEAC extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //http://localhost:8084/EACCloudControl/CreateEAC?login=sijin4&enableAPP=true&enableDB=true&containerName=dkfje&maxConnection=10
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {

            String containerName = request.getParameter("containerName");
            String login = request.getParameter("login");

            boolean enableAPP = Boolean.parseBoolean(request.getParameter("enableAPP"));
            boolean enableDB = Boolean.parseBoolean(request.getParameter("enableDB"));

            int maxConnection = 5; //default
            if (enableDB) {
                maxConnection = Integer.parseInt(request.getParameter("maxConnection"));
            }

            int inst = 1; //default
            if (enableAPP) {
                inst = Integer.parseInt(request.getParameter("instance"));
            }

            Container container = new Container();
            ContainerAction cact = new ContainerAction();
            String json = "";

            JSONContainer jc = new JSONContainer();

            if (!cact.checkContainerName(containerName)) {

                container = cact.createContainer(login, containerName, maxConnection, inst);


                if (container != null) {

                    json = jc.createJSON(container, true, "EAC Creation [Success]");
                    Container con1 = new Container();
                    Container con2 = new Container();

                    if (enableAPP) {

                        APPAction aact = new APPAction();
                        con1 = aact.enableAPP(container);
                    }


                    if (enableDB) {

                        container.setMaxConnection(maxConnection);

                        StorageAction dact = new StorageAction();

                        StorageDAO sdao = new StorageDAO();

                        Storage s = sdao.fetchRunningMySQLNode().get(0);

                        con2 = dact.enableDB(container, s);

                    }

                    if (con1 == null) {
                        json = jc.createJSON(container, false, "EAC Creation [DB Insertion Success] Enable APP [DB Insertion Failed]");
                    }

                    if (con2 == null) {
                        json = jc.createJSON(container, false, "EAC Creation [DB Insertion Success] Enable DB [DB Insertion Failed]");
                    }

                    if (con1 == null && con2 == null) {
                        json = jc.createJSON(container, false, "EAC Creation [DB Insertion Success] Enable APP [DB Insertion Failed] Enable DB [DB Insertion Failed]");
                    }

                } else {
                    json = jc.createJSON(container, false, "EAC Creation [DB Insertion Failed]");
                }
            } else {
                json = jc.createJSON(container, false, "Duplicated Container Name");
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
