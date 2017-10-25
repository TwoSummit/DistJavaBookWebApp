/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.jgl.bookwebapp.controller;

import edu.wctc.distjava.jgl.bookwebapp.model.Author;
import edu.wctc.distjava.jgl.bookwebapp.model.AuthorDao;
import edu.wctc.distjava.jgl.bookwebapp.model.AuthorService;
import edu.wctc.distjava.jgl.bookwebapp.model.IAuthorDao;
import edu.wctc.distjava.jgl.bookwebapp.model.MySqlDataAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    
    public static final String ACTION = "action";
    public static final String LIST_ALL_AUTHORS_ACTION = "listAll";
    public static final String EDIT_ONE_AUTHOR_ACTION = "editOne";
    public static final String DELETE_ONE_AUTHOR_ACTION = "deleteOne";
    public static final String CREATE_ONE_AUTHOR_ACTION = "createOne";
    public static final String CREATE_ONE_AUTHOR_SEND_TO_FORM_ACTION = "createOneDirect";

    public static final String PRIMARY_KEY_ID_PARAMATER = "pkValue";
    public static final String NAME_PARAMATER = "nameValue";

    public static final String AUTHORS_DELETED_ATTRIBUTE = "recsDeleted";
    public static final String AUTHOR_LIST_ATTRIBUTE = "authorList";
    public static final String AUTHORS_UPDATED_ATTRIBUTE = "authorsUpdated";
    public static final String AUTHORS_ADDED_ATTRIBUTE = "authorsUpdated";

    public static final String TABLE_COLUMN_AUTHOR_NAME = "author_name";
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

            // Pulled from sperate file in the future
            IAuthorDao dao = new AuthorDao(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root", "",
                new MySqlDataAccess()
            );

            // Instantiate the authorService
            AuthorService authorService = new AuthorService(dao);

            // Get requested action by user
            String action = request.getParameter(ACTION);

            // User is attempting to fiew all authors in the table
            if (action.equalsIgnoreCase(LIST_ALL_AUTHORS_ACTION)) {
                // Expects: nothing
                
                // set attributes
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, getAllAuthors(authorService));
                
                // set destination
                destination = DEFAULT_DESTINATION; // default
            } 
            
            // User is attempting to delete an author
            else if (action.equalsIgnoreCase(DELETE_ONE_AUTHOR_ACTION)) {
                // Expects: String pkValue
                
                // retreive paramaters
                String pkValue = request.getParameter(PRIMARY_KEY_ID_PARAMATER);
                
                // query
                int recsDeleted = authorService.removeAuthorById(pkValue);
                
                // set attributes
                request.setAttribute(AUTHORS_DELETED_ATTRIBUTE, recsDeleted);
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, getAllAuthors(authorService));
                
                // set destination
                destination = DEFAULT_DESTINATION; // default
                
                // example AuthorController?action=deleteOne&pkValue=8
                System.out.println("DELETE_ONE_AUTHOR_ACTION");
            } 
            
            // User is attempting to edit an author
            else if (action.equalsIgnoreCase(EDIT_ONE_AUTHOR_ACTION)) {
                // Expects: String pkValue, List<String> colNames, List<Object> colValues
                
                // retreive paramaters
                int pkValue = Integer.parseInt(request.getParameter(PRIMARY_KEY_ID_PARAMATER));
                String name = request.getParameter(NAME_PARAMATER);
                
                // query
                List<String> colNames = Arrays.asList(TABLE_COLUMN_AUTHOR_NAME);
                List<Object> colValues = Arrays.asList(name);

                int recsUpdated = authorService.editAuthorById(pkValue, colNames, colValues);
                
                // set attributes
                request.setAttribute(AUTHORS_UPDATED_ATTRIBUTE, recsUpdated);
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, getAllAuthors(authorService));
                
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
                List<String> colNames = new ArrayList<>();
                colNames.add(TABLE_COLUMN_AUTHOR_NAME);
                List<Object> colValues = new ArrayList<>();
                colNames.add(name);

                // query
                int recsAdded = authorService.addAuthor(colNames, colValues);
                
                // set attributes
                request.setAttribute(AUTHORS_UPDATED_ATTRIBUTE, recsAdded);
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, getAllAuthors(authorService));
                
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

    public final List<Author> getAllAuthors(AuthorService as) 
            throws SQLException, 
            ClassNotFoundException{
        
            System.out.println("LIST_ALL_AUTHORS_ACTION");
            return as.getAuthorList();
    }
    
    public final List<String> getColNames() {
        return Arrays.asList("");
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
