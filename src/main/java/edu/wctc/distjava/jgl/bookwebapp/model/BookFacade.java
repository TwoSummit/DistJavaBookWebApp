/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.jgl.bookwebapp.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cssco
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {

    @PersistenceContext(unitName = "book_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEm() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }
    
    public void addNewBook(String title, String isbn, String id) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthorId(getEm().find(Author.class, new Integer(id)));
        getEm().merge(book);
    }
    
    public void updateBookIsbn(String bookId, String isbn) {
       Book book = this.findById(new Integer(bookId));
       book.setIsbn(isbn);
       this.edit(book);
    }
    
    public void addOrUpdateBook(String bookId, String title, String isbn, String id) {
        Book book = null;
        Integer auhtorId = new Integer(id);
        
        if( bookId == null || bookId.isEmpty() ) {
            // must be a new record
            book = new Book();
        } else {
            book = new Book(new Integer(bookId));
        }
        
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthorId(getEm().find(Author.class, new Integer(id)));
        
        getEm().merge(book);
        
    }
    
}
