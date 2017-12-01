/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.jgl.bookwebapp.controller;

import edu.wctc.distjava.jgl.bookwebapp.model.Author;
import edu.wctc.distjava.jgl.bookwebapp.model.AuthorFacade;
import edu.wctc.distjava.jgl.bookwebapp.model.Book;
import edu.wctc.distjava.jgl.bookwebapp.model.BookFacade;
import java.io.IOException;
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
@WebServlet(name = "BookController", urlPatterns = {"/books"})
public class BookController extends HttpServlet {
    /* Quick change to push*/
    public static final String DEFAULT_DESTINATION = "/bookList.jsp";
    public static final String CREATE_BOOK_FORM_DESTINATION = "/bookCreate.jsp";
    public static final String EDIT_BOOKS_FORM_DESTINATION = "/bookEdit.jsp";
    
    public static final String ACTION = "action";
    public static final String LIST_ALL_BOOKS_ACTION = "listAllBooks";
    public static final String EDIT_ONE_BOOK_ACTION = "editOne";
    public static final String DELETE_ONE_BOOK_ACTION = "deleteOne";
    public static final String CREATE_ONE_BOOK_ACTION = "createOne";
    
    public static final String CREATE_ONE_BOOK_SEND_TO_FORM_ACTION = "createOneDirect";
    public static final String EDIT_BOOKS_SEND_TO_FORM_ACTION = "editBooksDirect";

    public static final String PRIMARY_KEY_ID_PARAMATER = "pkValue";
    public static final String TITLE_PARAMATER = "titleValue";
    public static final String ISBN_PARAMATER = "isbnValue";
    public static final String AUTHOR_ID_PARAMATER = "authorId";

    public static final String BOOK_LIST_ATTRIBUTE = "bookList";
    public static final String AUTHOR_LIST_ATTRIBUTE = "authorList";
    public static final String BOOKS_DELETED_ATTRIBUTE = "recsDeleted";
    public static final String BOOKS_UPDATED_ATTRIBUTE = "booksUpdated";
    public static final String BOOKS_ADDED_ATTRIBUTE = "booksUpdated";

    public static final String TABLE_COLUMN_BOOK_ID = "book_id";
    public static final String TABLE_COLUMN_BOOK_TITLE = "title";
    public static final String TABLE_COLUMN_BOOK_ISBN = "isbn";
    public static final String TABLE_COLUMN_BOOK_AUTHOR_ID = "author_id";
    
    public static final String ONE_BOOK_OBJECT = "bookObject";
    
    @EJB
    private BookFacade bookFacade;
    
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

            // User is attempting to fiew all books in the table
            if (action.equalsIgnoreCase(LIST_ALL_BOOKS_ACTION)) {
                // set attribute(s)
                request.setAttribute(BOOK_LIST_ATTRIBUTE, this.bookFacade.findAll());
                
                // set destination
                destination = DEFAULT_DESTINATION; // default
            } 
            
            // User is attempting to delete a book
            else if (action.equalsIgnoreCase(DELETE_ONE_BOOK_ACTION)) {
                // retreive paramaters
                String pkValue = request.getParameter(PRIMARY_KEY_ID_PARAMATER);
                
                // query
                bookFacade.removeById(pkValue);
                
                // set attribute(s)
                request.setAttribute(BOOK_LIST_ATTRIBUTE, bookFacade.findAll());
                
                // set destination
                destination = DEFAULT_DESTINATION; // default
                
                // example Books?action=deleteOne&pkValue=8
                System.out.println("DELETE_ONE_BOOK_ACTION");
            } 
            
            // User is attempting to edit books, direct them to form
            else if (action.equalsIgnoreCase(EDIT_BOOKS_SEND_TO_FORM_ACTION)) {
                
                // retreive paramaters
                String pkValue = request.getParameter(PRIMARY_KEY_ID_PARAMATER);
                
                // set attribute(s)
                Book book = this.bookFacade.findById( pkValue );
                List<Author> authorList = this.authorFacade.findAll();
                
                request.setAttribute(ONE_BOOK_OBJECT, book);
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, authorList);
                
                // set destination
                destination = EDIT_BOOKS_FORM_DESTINATION;
                
                // example Books?action=createOne&name=Sally
                System.out.println("EDIT_BOOKS_SEND_TO_FORM_ACTION");
            }
            
            // User is attempting to edit a book
            else if (action.equalsIgnoreCase(EDIT_ONE_BOOK_ACTION)) {
                // Expects: String pkValue, List<String> colNames, List<Object> colValues
                
                // retreive paramaters
                String pkValue = request.getParameter( PRIMARY_KEY_ID_PARAMATER );
                String title = request.getParameter( TITLE_PARAMATER );
                String isbn = request.getParameter( ISBN_PARAMATER );
                String authorId = request.getParameter( AUTHOR_ID_PARAMATER );
                
                // query
                this.bookFacade.addOrUpdateBook( pkValue, title, isbn, authorId );
                
                // set attribute(s)
                request.setAttribute( BOOK_LIST_ATTRIBUTE, bookFacade.findAll() );
                
                // set destination
                destination = DEFAULT_DESTINATION; // default

                // example Books?action=editOne&idValue=5&nameValue=Jimmy
                System.out.println("EDIT_ONE_BOOK_ACTION");
            } 
            
            // User is attempting to create a book, direct them to form
            else if (action.equalsIgnoreCase(CREATE_ONE_BOOK_SEND_TO_FORM_ACTION)) {
                
                // set attribute(s)
                List<Author> authorList = this.authorFacade.findAll();
                request.setAttribute(AUTHOR_LIST_ATTRIBUTE, authorList);
                
                // set destination
                destination = CREATE_BOOK_FORM_DESTINATION;
                
                // example Books?action=createOne&name=Sally
                System.out.println("CREATE_ONE_BOOK_SEND_TO_FORM_ACTION");
            }
            
            // User is attempting to create a book
            else if (action.equalsIgnoreCase(CREATE_ONE_BOOK_ACTION)) {
                // Expects: List<String> colNames, List<Object> colValues

                // retreive paramaters
                String bookId = null;
                String title = request.getParameter( TITLE_PARAMATER );
                String isbn = request.getParameter( ISBN_PARAMATER );
                String authorId = request.getParameter( AUTHOR_ID_PARAMATER );

                // query
                bookFacade.addOrUpdateBook(bookId, title, isbn, authorId);
                
                // set attribute(s)
                request.setAttribute(BOOK_LIST_ATTRIBUTE, bookFacade.findAll());
                
                // set destination
                destination = DEFAULT_DESTINATION; // default
                
                // example Books?action=createOne&name=Sally
                System.out.println("CREATE_ONE_BOOK_ACTION");
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
