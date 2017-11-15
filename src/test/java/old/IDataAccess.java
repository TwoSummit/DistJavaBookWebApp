package edu.wctc.distjava.jgl.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jlombardo
 */
public interface IDataAccess {


    public abstract int deleteRecordById(String tableName, String pkColName, Object pkValue) 
            throws ClassNotFoundException, 
            SQLException;
    
    public abstract List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) 
            throws SQLException, 
            ClassNotFoundException;
    
    public abstract int createRecord(String tableName, List<String> colNames, List<Object> colValues) 
            throws SQLException;
    
    public int updateRecordById(String tableName, String pkColName, Object pkColValue, List<String> colNames, List<Object> colValues)
            throws ClassNotFoundException, 
            SQLException;

    public abstract void openConnection(String driverClass, String url, String userName, String password) 
            throws ClassNotFoundException, 
            SQLException;
    
    public abstract void closeConnection() 
            throws SQLException;
}
