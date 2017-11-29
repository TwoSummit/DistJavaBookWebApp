package edu.wctc.distjava.jgl.bookwebapp.model;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author jlombardo
 */
public class MockAuthorDao implements IAuthorDao {

    public MockAuthorDao() {
    }
    
    @Override
    public List<Author> getListOfAuthors() 
            throws SQLException, ClassNotFoundException {
        
        List<Author> list = Arrays.asList(
//                new Author(1, "John Doe", new Date()),
//                new Author(2, "Bob Smith", new Date())
        );
          
        return list;
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        IAuthorDao dao = new MockAuthorDao();
        
//        IAuthorDao dao = new AuthorDao(
//            "com.mysql.jdbc.Driver",
//            "jdbc:mysql://localhost:3306/book",
//            "root", "admin",
//            new MySqlDataAccess("com.mysql.jdbc.Driver",
//            "jdbc:mysql://localhost:3306/book",
//            "root", "admin")
//        );

        List<Author> list = dao.getListOfAuthors();
        
        for(Author a: list) {
            System.out.println(a.getAuthorId() + ", "
                + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
        }
    }

    @Override
    public int removeAuthorById(Integer id) throws ClassNotFoundException, SQLException {
        return 1;
    }

    @Override
    public int addAuthor(List<String> colNames, List<Object> colValues) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateAuthorById(Object pkColValue, List<String> colNames, List<Object> colValues) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
