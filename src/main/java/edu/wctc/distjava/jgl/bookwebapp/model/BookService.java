/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.jgl.bookwebapp.model;

import edu.wctc.distjava.jgl.bookwebapp.repository.AuthorRepository;
import edu.wctc.distjava.jgl.bookwebapp.repository.BookRepository;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cssco
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private AuthorRepository authorRepo;
    
    public BookService() {
    }
    
    public void addNewBook(String title, String isbn, String authorId) {
        Author author = authorRepo.findOne(Integer.parseInt(authorId));
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthorId(author);
        
        bookRepo.save(book);
    }
    
//    public void addNewBook(String title, String isbn, String id) {
//        Book book = new Book();
//        book.setTitle(title);
//        book.setIsbn(isbn);
//        book.setAuthorId(getEm().find(Author.class, new Integer(id)));
//        getEm().merge(book);
//    }
//    
//    public void updateBookIsbn(String bookId, String isbn) {
//       Book book = this.findById(new Integer(bookId));
//       book.setIsbn(isbn);
//       this.edit(book);
//    }
//    
//    public void addOrUpdateBook(String bookId, String title, String isbn, String id) {
//        Book book = null;
//        Integer auhtorId = new Integer(id);
//        
//        if( bookId == null || bookId.isEmpty() ) {
//            // must be a new record
//            book = new Book();
//        } else {
//            book = new Book(new Integer(bookId));
//        }
//        
//        book.setTitle(title);
//        book.setIsbn(isbn);
//        book.setAuthorId(getEm().find(Author.class, new Integer(id)));
//        
//        getEm().merge(book);
//        
//    }
    
}
