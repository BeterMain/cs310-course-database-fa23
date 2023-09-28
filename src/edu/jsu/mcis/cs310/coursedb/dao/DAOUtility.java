package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;

public class DAOUtility {
    
    public static final int TERMID_FA23 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        ResultSetMetaData rsmd = null;
        int columnCount;
        
        try {
        
            if (rs != null) {

                rsmd = rs.getMetaData();
                columnCount = rsmd.getColumnCount();
                    
                /* Loop through result set and append to json array as json objects */
                while (rs.next()) {
                        
                    JsonObject jsonObject = new JsonObject();
                        
                    for (int i = 1; i <= columnCount; ++i) {
                        jsonObject.put(rsmd.getColumnLabel(i), rs.getString(i));
                    }
                        
                    records.add(jsonObject);
                }

            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    }
    
}
