package edu.wctc.distjava.jgl.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class AuthorServiceOld implements Serializable{

    private final String AUTHOR_TBL = "author";
    private final String AUTHOR_PK = "author_id";
    
    @PersistenceContext(unitName = "book_PU")
    private EntityManager em;
    
    public AuthorServiceOld() {
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

//    public void removeAuthor(Author author){
//        getEm().remove(getEm().merge(author));
//    }
    
    // Updated for JPA
    public int removeAuthorById(String id) 
            throws ClassNotFoundException, 
            SQLException, 
            NumberFormatException {
        
//        Author author = getEm().find(Author.class, id);
//        getEm().remove(author);
        
        String jpql = "delete from Author a where a.id = :id";
        Query q = getEm().createQuery(jpql);
        q.setParameter("id", new Integer(id));
        return q.executeUpdate();
    }
    
    // Updated for JPA
    public List<Author> getAuthorList() 
            throws Exception {
        
//        List<Author> authorList = new ArrayList();
//        String jpql = "select a from Author a";
//        TypedQuery<Author> q = getEm().createQuery(jpql,Author.class);
//        q.setMaxResults(500); // completely optional
//        authorList = q.getResultList();
//        return authorList;
        
        String jpql = "select a from Author a";
        TypedQuery<Author> q = getEm().createQuery(jpql,Author.class);
        q.setMaxResults(500); // completely optional
        
        return q.getResultList();
        
    }
    
    public Author getAuthorById(String id)
            throws NumberFormatException{
        
        return getEm().find(Author.class, new Integer(id));
        
    }
    
    public void addAuthor(String name) 
            throws ClassNotFoundException, 
            NumberFormatException,
            SQLException {
        Author author = new Author();
        author.setAuthorName(name);
        author.setDateAdded(new Date());
        getEm().merge(author);
        
    }
    
    public int editAuthorById(int pkValue, List<String> colNames, List<Object> colValues) 
            throws ClassNotFoundException, 
            SQLException{
        
        // Start jpql statement
        String jpql = "update Author a set";
        
        // Loop through all column names and set values to ?
        for (int i = 0; i < colNames.size(); i++) {
            
            jpql += colNames.get(i) + " = ?";
            
            // all but the last values need a ,
            if( i < colNames.size() - 1 ){
                jpql += ",";
            }
            
        }
            
        // continue creating jpql statement for the where clause
        jpql += " WHERE " + AUTHOR_PK + " = ?";
        
        // Create query out of jpql
        Query q = getEm().createQuery(jpql);
        
        // Update ? with real values
        // This gets all ? except for the last one
        for( int i = 1; i <= colValues.size(); i++){
            q.setParameter(i, colValues.get(i - 1) );
        }
        
        // Update the last one for the where statement
        q.setParameter(colValues.size()+1, pkValue);
        
        return q.executeUpdate();
    }
    
    public void editAuthorByIdBetter(String id, String name) 
            throws ClassNotFoundException, 
            NumberFormatException,
            SQLException{
        Author author = getEm().find(Author.class, new Integer(id));
        author.setAuthorName(name);
        getEm().merge(author);
        
//        Author author = new Author();
//        author.setAuthorName(name);
//        author.setAuthorId(new Integer(id));
    }

}
