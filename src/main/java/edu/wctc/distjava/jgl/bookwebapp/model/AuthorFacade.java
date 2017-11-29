/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.jgl.bookwebapp.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cssco
 */
@Stateless
public class AuthorFacade extends AbstractFacade<Author> {

    private final String AUTHOR_TBL = "author";
    private final String AUTHOR_PK = "author_id";
    
    @PersistenceContext(unitName = "book_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEm() {
        return em;
    }

    public AuthorFacade() {
        super(Author.class);
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
