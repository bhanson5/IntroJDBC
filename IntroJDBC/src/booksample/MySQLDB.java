

package booksample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MySQLDB {
    
    private Connection conn;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    
    public void openConnection(String driverClass, String url, String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName (driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    public void closeConnection() throws SQLException {
        conn.close();
    }

    /**
     *
     * Read
     * 
     * @param tableName
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> findAllRecords(String tableName) throws SQLException {
        
        List<Map<String, Object>> records = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();
        
        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int i=1; i<=columnCount; i++) {
                record.put(metadata.getColumnName(i), rs.getObject(i));
            }
            records.add(record);
        }
        return records;
    }
    
    public static void main(String[] args) throws Exception{
        MySQLDB db = new MySQLDB();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://bit.glassfish.wctc.edu:3306/book", "advjava", "advjava");
        
        List<Map<String, Object>> records = db.findAllRecords("author");
        for (Map record : records) {
            System.out.println(record);
        }
    }
    
}
