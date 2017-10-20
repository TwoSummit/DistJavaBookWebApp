package edu.wctc.distjava.jgl.bookwebapp.model;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jlombardo
 */
public class AuthorDao implements IAuthorDao {
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private IDataAccess db;
    
    // Table name
    private final String AUTHOR_TBL = "author";
    
    // Table columns
    private final String AUTHOR_PK = "author_id";
    private final String AUTHOR_NAME = "author_name";
    private final String DATE_ADDED = "date_added";
    
    // All table columns
    private final List AUTHOR_TABLE_COLUMN_NAMES = Arrays.asList(AUTHOR_PK, AUTHOR_NAME, DATE_ADDED);
    
    

    public AuthorDao(String driverClass, String url, 
            String userName, String password,
            IDataAccess db) {
        
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
        setDb(db);
    }
    
    public final int removeAuthorById(Integer id) 
            throws ClassNotFoundException, 
            SQLException {
        if(id == null || id < 1) {
            throw new IllegalArgumentException("id must be a Integer greater than 0");
        }
        
        // Open Connection
        db.openConnection(driverClass, url, userName, password);
        
        // Utilize Connection
        int recsDeleted = db.deleteRecordById(AUTHOR_TBL, AUTHOR_PK, id);
        
        // Close Connection
        db.closeConnection();
        
        return recsDeleted;
    }
    
    public final int updateAuthorById(Object pkColValue, List<String> colNames, List<Object> colValues)
            throws ClassNotFoundException, 
            SQLException{
        
        // Open Connection
        db.openConnection(driverClass, url, userName, password);
        
        // Utilize Connection
        int recsDeleted = db.updateRecordById(AUTHOR_TBL, AUTHOR_PK, pkColValue, colNames, colValues);
        
        // Close Connection
        db.closeConnection();
        
        return recsDeleted;
    }
    
    @Override
    public final List<Author> getListOfAuthors() 
            throws SQLException, 
            ClassNotFoundException {
        
        // Open Connection
        db.openConnection(driverClass, url, userName, password);
            
        List<Author> list = new Vector<>();
        List<Map<String,Object>> rawData = 
        db.getAllRecords(AUTHOR_TBL, 0);
        
        Author author = null;
        
        for(Map<String,Object> rec : rawData) {
            author = new Author(); 
            
            // Utilize Connection
            Object objRecId = rec.get(AUTHOR_PK);
            Integer recId = objRecId == null ? 
                    0 : Integer.parseInt(objRecId.toString());
            author.setAuthorId(recId);

            Object objName = rec.get(AUTHOR_NAME);
            String authorName = objName == null ? "" : objName.toString();
            author.setAuthorName(authorName);

            Object objRecAdded = rec.get(DATE_ADDED);
            Date recAdded = objRecAdded == null ? null : (Date)objRecAdded;
            author.setDateAdded(recAdded);

            list.add(author); 

        }
          
            
        // Close Connection
        db.closeConnection();
        return list;
    }
    
    @Override
    public final int addAuthor(List<String> colNames, List<Object> colValues) 
            throws ClassNotFoundException, 
            SQLException{
        
        // Determine if date added was passed or not
        boolean dateAdded = false;
        int index = 0;
        for( String colName : colNames ){
            if( colName.equalsIgnoreCase(DATE_ADDED)){
                // Get index
                index = colNames.indexOf(colName);
                dateAdded = true;
            }
        }
        
        if( dateAdded ) {
            try {
                // Assuming colNames in order of colValues, set value to right now
                colValues.set(index, getTodaysDate());
            } catch (ParseException ex) {
                Logger.getLogger(AuthorDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
//            try {
                colValues.add(new Date());
//            } catch (ParseException ex) {
//                Logger.getLogger(AuthorDao.class.getName()).log(Level.SEVERE, null, ex);
//            }
            colNames.add(DATE_ADDED);
        }
        
        // Open Connection
        db.openConnection(driverClass, url, userName, password);
        
        // Utilize Connection
        int recsAdded = db.createRecord("author", colNames, colValues);
        
        // Close Connection
        db.closeConnection();
        
        return 0;
    }
    
    public final IDataAccess getDb() {
        return db;
    }

    public final void setDb(IDataAccess db) {
        this.db = db;
    }
    
    public final String getDriverClass() {
        return driverClass;
    }

    public final void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public final String getUrl() {
        return url;
    }

    public final void setUrl(String url) {
        this.url = url;
    }

    public final String getUserName() {
        return userName;
    }

    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public final String getPassword() {
        // Replace all characters in password with *
        String sanitizedPassword = password.replaceAll(".", "*");
        
        // Return password containing all stars
        return sanitizedPassword;
    }

    public final void setPassword(String password) {
        this.password = password;
    }
    
    private Date getTodaysDate() 
            throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date dateWithoutTime = sdf.parse(sdf.format(new Date()));

        return new Date();
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        IAuthorDao dao = new AuthorDao(
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://localhost:3306/book",
            "root", "",
            new MySqlDataAccess()
        );
        
//        int recsDeleted = dao.removeAuthorById(20);

//        List<String> colNames = Arrays.asList("author_name");
//        List<Object> colValues = Arrays.asList("UpdatedName");
//        int recsUpdated = dao.updateAuthorById(1, colNames, colValues);

    
//        List<String> colNames = new ArrayList<>();
//        colNames.add("author_name");
//        
//        List<Object> colValues = new ArrayList<>();
//        colValues.add("Test Guy");
//        
//        int recsAdded = dao.addAuthor(colNames, colValues);
        
        List<Author> list = dao.getListOfAuthors();
        for(Author a: list) {
            System.out.println(a.getAuthorId() + ", "
                + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
        }
    }
    
}
