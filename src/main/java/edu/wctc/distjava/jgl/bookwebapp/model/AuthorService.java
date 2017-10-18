package edu.wctc.distjava.jgl.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jlombardo
 */
public class AuthorService {
    private IAuthorDao authorDao;
    private final String AUTHOR_TBL = "author";
    private final String AUTHOR_PK = "author_id";

    public AuthorService(IAuthorDao authorDao) {
        setAuthorDao(authorDao);
    }

    public final int removeAuthorById(String id) 
            throws ClassNotFoundException, 
            SQLException, 
            NumberFormatException {
        
        if (id == null) {
            throw new IllegalArgumentException("id must be a Integer greater than 0");
        }
        
        Integer value = Integer.parseInt(id);

        return authorDao.removeAuthorById(value);
    }

    public final List<Author> getAuthorList()
            throws SQLException, 
            ClassNotFoundException {

        return authorDao.getListOfAuthors();
    }

    public final int addAuthor(List<String> colNames, List<Object> colValues) 
            throws ClassNotFoundException, 
            SQLException {
        
        return authorDao.addAuthor(colNames, colValues);
    }
    
    public final int editAuthorById(int pkValue, List<String> colNames, List<Object> colValues) 
            throws ClassNotFoundException, 
            SQLException{
        
        int recsUpdated = authorDao.updateAuthorById(pkValue, colNames, colValues);
        
        return recsUpdated;
    }

    public final IAuthorDao getAuthorDao() {
        return authorDao;
    }

    public final void setAuthorDao(IAuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public static void main(String[] args)
            throws SQLException, ClassNotFoundException {

        IAuthorDao dao = new AuthorDao(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root", "",
                new MySqlDataAccess()
        );

        AuthorService authorService
                = new AuthorService(dao);
        
        //int recsDeleted = authorService.removeAuthorById("53");

        List<String> colNames = new ArrayList<>();
        colNames.add("author_name");
        
        List<Object> colValues = new ArrayList<>();
        colValues.add("Author Service");
        
//        int recsUpdated = authorService.editAuthorById(5, colNames, colValues);
        
        int recsAdded = authorService.addAuthor(colNames, colValues);
        
        List<Author> list = authorService.getAuthorList();
        for (Author a : list) {
            System.out.println(a.getAuthorId() + ", "
                    + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
        }
    }
}
