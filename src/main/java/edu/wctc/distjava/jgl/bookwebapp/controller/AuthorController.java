/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.jgl.bookwebapp.controller;

import edu.wctc.distjava.jgl.bookwebapp.model.Author;
import edu.wctc.distjava.jgl.bookwebapp.model.AuthorFacade;
import edu.wctc.distjava.jgl.bookwebapp.model.AuthorServiceOld;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jlombardo
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/authorController"})
public class AuthorController extends HttpServlet {
    /* Quick change to push*/
    public static final String DEFAULT_DESTINATION = "/authorList.jsp";
    public static final String CREATE_AUTHOR_FORM_DESTINATION = "/authorCreate.jsp";
    public static final String EDIT_AUTHORS_FORM_DESTINATION = "/authorEdit.jsp";
    
    public static final String ACTION = "action";
    public static final String LIST_ALL_AUTHORS_ACTION = "listAll";
    public static final String EDIT_ONE_AUTHOR_ACTION = "editOne";
    public static final String DELETE_ONE_AUTHOR_ACTION = "deleteOne";
    public static final String CREATE_ONE_AUTHOR_ACTION = "createOne";
    public static final String CREATE_ONE_AUTHOR_SEND_TO_FORM_ACTION = "createOneDirect";
    public static final String EDIT_AUTHORS_SEND_TO_FORM_ACTION = "editAuthorsDirect";

    public static final String PRIMARY_KEY_ID_PARAMATER = "pkValue";
    public static final String NAME_PARAMATER = "nameValue";

    public static final String AUTHORS_DELETED_ATTRIBUTE = "recsDeleted";
    public static final String AUTHOR_LIST_ATTRIBUTE = "authorList";
    public static final String AUTHORS_UPDATED_ATTRIBUTE = "authorsUpdated";
    public static final String AUTHORS_ADDED_ATTRIBUTE = "authorsUpdated";

    public static final String TABLE_COLUMN_AUTHOR_NAME = "author_name";
    
    //
    public static final String ONE_AUTHOR_OBJECT = "authorObject";
    
    @EJB
    private AuthorFacade authorFacade;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = DEFAULT_DESTINATION; // default

        try {

            // Get requested action by user
            String action = request.getParameter(ACTION);

            // User is attempting to fiew all authors in the table
            if (action.equalsIgnoreCase(LIST_ALL_AUTHORS_ACTION)) {
                // set attribute(s)
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, this.authorFacade.findAll());
                
                // set destination
                destination = DEFAULT_DESTINATION; // default
            } 
            
            // User is attempting to delete an author
            else if (action.equalsIgnoreCase(DELETE_ONE_AUTHOR_ACTION)) {
                // retreive paramaters
                String pkValue = request.getParameter(PRIMARY_KEY_ID_PARAMATER);
                
                // query
                authorFacade.removeById(pkValue);
                
                // set attribute(s)
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, authorFacade.findAll());
                
                // set destination
                destination = DEFAULT_DESTINATION; // default
                
                // example AuthorController?action=deleteOne&pkValue=8
                System.out.println("DELETE_ONE_AUTHOR_ACTION");
            } 
            
            // User is attempting to edit authors, direct them to form
            else if (action.equalsIgnoreCase(EDIT_AUTHORS_SEND_TO_FORM_ACTION)) {
                
                // retreive paramaters
                String pkValue = request.getParameter(PRIMARY_KEY_ID_PARAMATER);
                
                // set attribute(s)
                request.setAttribute(ONE_AUTHOR_OBJECT, this.authorFacade.findById(pkValue));
                
                // set destination
                destination = EDIT_AUTHORS_FORM_DESTINATION;
                
                // example AuthorController?action=createOne&name=Sally
                System.out.println("EDIT_AUTHORS_SEND_TO_FORM_ACTION");
            }
            
            // User is attempting to edit an author
            else if (action.equalsIgnoreCase(EDIT_ONE_AUTHOR_ACTION)) {
                // Expects: String pkValue, List<String> colNames, List<Object> colValues
                
                // retreive paramaters
                String pkValue = request.getParameter( PRIMARY_KEY_ID_PARAMATER );
                String name = request.getParameter( NAME_PARAMATER );
                
                // query
                this.authorFacade.updateAuthorName( pkValue, name );
                
                // set attribute(s)
                request.setAttribute( AUTHOR_LIST_ATTRIBUTE, authorFacade.findAll() );
                
                // set destination
                destination = DEFAULT_DESTINATION; // default

                // example AuthorController?action=editOne&idValue=5&nameValue=Jimmy
                System.out.println("EDIT_ONE_AUTHOR_ACTION");
            } 
            
            // User is attempting to create an author, direct them to form
            else if (action.equalsIgnoreCase(CREATE_ONE_AUTHOR_SEND_TO_FORM_ACTION)) {
                
                // set destination
                destination = CREATE_AUTHOR_FORM_DESTINATION;
                
                // example AuthorController?action=createOne&name=Sally
                System.out.println("CREATE_ONE_AUTHOR_SEND_TO_FORM_ACTION");
            }
            
            // User is attempting to create an author
            else if (action.equalsIgnoreCase(CREATE_ONE_AUTHOR_ACTION)) {
                // Expects: List<String> colNames, List<Object> colValues

                // retreive paramaters
                String name = request.getParameter(NAME_PARAMATER);

                // query
                authorFacade.createAuthor(name);
                
                // set attribute(s)
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, authorFacade.findAll());
                
                // set destination
                destination = DEFAULT_DESTINATION; // default
                
                // example AuthorController?action=createOne&name=Sally
                System.out.println("CREATE_ONE_AUTHOR_ACTION");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            destination = DEFAULT_DESTINATION; // default
            request.setAttribute("errMessage", e.getMessage());
        }

        RequestDispatcher view = request.getRequestDispatcher(destination);
        view.forward(request, response);

    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
