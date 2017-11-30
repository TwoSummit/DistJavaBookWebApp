/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.jgl.bookwebapp.model;

import edu.wctc.distjava.jgl.bookwebapp.repository.AuthorRepository;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cssco
 */
@Service
public class AuthorService {

    private final String AUTHOR_TBL = "author";
    private final String AUTHOR_PK = "author_id";
    
    @Autowired
    private AuthorRepository authorRepo;
    
    public AuthorService() {
    }
    
    public List<Author> findAll(){
        return authorRepo.findAll();
    }
    
    public Author findById(String id){
        return authorRepo.findOne(Integer.parseInt(id));
    }
    
    public void addAuthor(String authorName) {
        Date dateAdded = new Date();
        Author author = new Author();
        author.setAuthorName(authorName);
        author.setDateAdded(dateAdded);
        
        authorRepo.saveAndFlush(author);
    }
    
    public void createAuthor(String authorName) {
        Author author = new Author();
        author.setAuthorName( authorName );
        author.setDateAdded( new Date() );
        this.create( author );
    }
    
    public void updateAuthorName(String authorId, String name) {
       Author author = this.findById( new Integer( authorId ) );
       author.setAuthorName( name );
       this.edit( author );
    }
    
    public void removeById(String id) {
        this.remove( this.getEm().find( Author.class, new Integer( id ) ) );
    }
    
    public Author findById(String id){
        return this.findById( new Integer( id ) );
    }
    
}
