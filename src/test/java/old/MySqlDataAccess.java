package edu.wctc.distjava.jgl.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Vector;

public class MySqlDataAccess implements IDataAccess {
    private final int ALL_RECORDS = 0;
    private final boolean DEBUG = false;

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
//    public int addAuthor(List<Author> author){
//        return 0;
//    }
    
    
    public final void openConnection(String driverClass, String url, String userName, String password) 
            throws ClassNotFoundException, 
            SQLException {
        
        Class.forName (driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    public final void closeConnection() 
            throws SQLException {
        if(conn !=null) conn.close();
    }
    
    public final int createRecord(String tableName, List<String> colNames, List<Object> colValues) 
            throws SQLException {
        
        /*  -- SQL for a insert into statement --
            INSERT INTO table_name (column1, column2, column3, ...)
            VALUES (value1, value2, value3, ...);
        */
        
        // Start sql statement
        String sql = "INSERT INTO " + tableName + " ";
        StringJoiner sj = new StringJoiner(", ", "( ", " )");
        
        // add col names to sql statement
        for(String col : colNames) {
            sj.add(col);
        }
        
        // Test output
        if( DEBUG ){
            System.out.println("Column Names: " + sj.toString());
        }
        
        // add values to sql statement
        sql += sj.toString();
        sql += " VALUES ";
        sj = new StringJoiner(", ", "( ", " )");
        
        for( Object value : colValues ){
            sj.add("?");
        }
        
        sql += sj.toString();
        
        // Test output
        if( DEBUG ){
            System.out.println("Column Values: " + sj.toString());
            System.out.println("SQL Statement: " + sql);
        }
        
        // Create prepare statement 
        pstmt = conn.prepareStatement(sql);
        
        // Update ? with real values
        for( int i = 1; i <= colValues.size(); i++){
            pstmt.setObject(i, colValues.get(i - 1) );
        }
        
        // Execute Query
        int recsDeleted = pstmt.executeUpdate();
        
        // Return return numbered of records deleted
        return recsDeleted;
    }
    
    public final int updateRecordById(String tableName, String pkColName, Object pkColValue, List<String> colNames, List<Object> colValues)
            throws ClassNotFoundException, 
            SQLException {

        /*  -- SQL for update statement --
            UPDATE table_name
            SET column1 = value1, column2 = value2, ...
            WHERE condition;
        */
        
        // Start sql statement
        String sql = " UPDATE " + tableName + " SET ";
        
        // Loop through all column names and set values to ?
        for (int i = 0; i < colNames.size(); i++) {
            
            sql += colNames.get(i) + " = ?";
            
            // all but the last values need a ,
            if( i < colNames.size() - 1 ){
                sql += ",";
            }
            
            // continue creating sql statement
            sql += " WHERE " + pkColName + " = ?";
            
        }
        
        // Test output
        if( DEBUG ){
            System.out.println(sql);
        }
        
        // Create prepare statement 
        pstmt = conn.prepareStatement(sql);
        
        // Update ? with real values
        // This gets all ? except for the last one
        for( int i = 1; i <= colValues.size(); i++){
            pstmt.setObject(i, colValues.get(i - 1) );
        }
        // Update the last one for the where statement
        pstmt.setObject(colValues.size()+1, pkColValue);
        
        // Test output
        if( DEBUG ){
            System.out.println(pstmt.toString());
        }
        
        // Execute Query
        int recsUpdated = pstmt.executeUpdate();
        
        return recsUpdated;
    }
    
    public final int deleteRecordById(String tableName, String pkColName, Object pkValue) 
            throws ClassNotFoundException, 
            SQLException {
        
        String sql = "DELETE FROM " + tableName + " WHERE " 
                + pkColName + " = ?";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, pkValue);
        int recsDeleted = pstmt.executeUpdate();
        
        return recsDeleted;
    }
    
    /**
     * Returns records from a table. Requires and open connection.
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException 
     */
    public final List<Map<String,Object>> getAllRecords(String tableName, int maxRecords)
            throws SQLException, 
            ClassNotFoundException {
        
        List<Map<String,Object>> rawData = new Vector<>();
        String sql = "";
        
        if(maxRecords > ALL_RECORDS) {
            sql = "select * from " + tableName + " limit " + maxRecords;
        } else {
            sql = "select * from " + tableName;
        }
        
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        Map<String,Object> record = null;
        
        while( rs.next() ) {
            record = new LinkedHashMap<>();
            for(int colNum=1; colNum <= colCount; colNum++) {
                record.put(rsmd.getColumnName(colNum), rs.getObject(colNum));
            }
            rawData.add(record);
        }
        
        return rawData;
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        IDataAccess db = new MySqlDataAccess();
        // Open connection
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "");
        
        // Utilize Connection

//        int recsAdded = db.createRecord("author",
//                Arrays.asList("author_name","date_added"),
//                Arrays.asList("Test Guy","2017-10-05"));

//        int recsDeleted = db.deleteRecordById("author", "author_id", new Integer(52));
//        System.out.println("No. of Recs Deleted: " + recsDeleted);

//        List<String> colNames = Arrays.asList("author_name");
//        List<Object> colValues = Arrays.asList("UpdatedName");
//        db.updateRecordById("author", "author_id", 1, colNames, colValues);

        List<Map<String,Object>> list = db.getAllRecords("author", 0);
        for(Map<String,Object> rec : list) {
            System.out.println(rec);
        }

        // Close Connection
        db.closeConnection();
        
//        System.out.println( "Recs Created: " + recsAdded );
        
//        db.closeConnection();
    }
    
}
