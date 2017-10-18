package edu.wctc.distjava.jgl.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jlombardo
 */
public interface IAuthorDao {

   public abstract int removeAuthorById(Integer id) 
           throws ClassNotFoundException, 
           SQLException;
   
   public abstract List<Author> getListOfAuthors() 
           throws SQLException, 
           ClassNotFoundException;
   
   public abstract int addAuthor(List<String> colNames, List<Object> colValues) 
            throws ClassNotFoundException, 
            SQLException;
    
   public int updateAuthorById(Object pkColValue, List<String> colNames, List<Object> colValues)
            throws ClassNotFoundException, 
            SQLException;
}
