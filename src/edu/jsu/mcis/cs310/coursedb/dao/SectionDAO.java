package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import com.github.cliftonlabs.json_simple.*;

public class SectionDAO {
    
    // INESERT YOUR CODE HERE
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]";
        //Variables for using querys
        String query;
        boolean hasResults;
        
        // Prepared Vars
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // Use prepared statement to stage the "SELECT * FROM " argument
                query = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, termid);
                ps.setString(2, subjectid);
                ps.setString(3, num);
                
                // Execute 
                hasResults = ps.execute();
                
                /* Get Results */
                
                System.out.println("Getting Results ...");
                
                // Check if results
                if (hasResults) {
                    
                    /* Get ResultSet Metadata */
                    
                    rs = ps.getResultSet();
                    
                    /* Serialize and Submit */
                    
                    result = DAOUtility.getResultSetAsJson(rs);
                }
                
                /* If no data available, print an error */

                else {

                    System.err.println("ERROR: No data returned!");

                }
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}