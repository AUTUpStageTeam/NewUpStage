package POJO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import servlets.signup_request;

/**
 * @Class ConnectToDatabase
 * @Version 1.0
 * @Date 03-09-2016: created
 * @Author Minju Park (13839910)
 * 
 * Created for database connection and for handling sql queries
 * creates if there is no database or table in the derby server.
 */
public class ConnectToDatabase {
    
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String DB_URL = "jdbc:derby://localhost:1527/UpStage; create=true";
    private static final String userName = "upstage_admin";
    private static final String pswd = "upstage2016";

    private Connection con;
    
    
    public ConnectToDatabase() throws ClassNotFoundException, SQLException {
        connectToDatabase();
    }
    
    
    public boolean insertToTable(String username, String password, String email, String introduction, String reason) throws SQLException {
        
        Statement stmt = con.createStatement();
        System.out.println("inserting inputs into the table.");
        
        String command = "INSERT INTO user_signup_request (username, password, email, "
                + "introduction, reason, request_date, approved) VALUES "
                + "('" + username +"', '" + password +"', '" + email +"', '" 
                + introduction + "', '" + reason + "', '" + today() + "', 'false')";
        
        int row = stmt.executeUpdate(command);
        
        if(row > 0) {
            System.out.println(row + " row updated.");
            return true;

         
        } else {
            System.out.println("insert failed.");
            return false;
        }
       
        
        
    }
    
   
    public boolean checkUsernameAvailability(String username) throws SQLException { 
        System.out.println("Checking Username Availability...");
        
        Statement stmt = con.createStatement();
        String command = "SELECT username FROM user_signup_request WHERE username= '"+ username +"'";
        
        ResultSet rs = stmt.executeQuery(command);
        
        if (!rs.next()) {
           return true;
        } else {
           return false;
        }
    }
    
    private String today() {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println("todays date: " + dateFormat.format(date));
        return dateFormat.format(date);
        
    }
    
    public boolean connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER); // load the database driver for MySQL
        con = DriverManager.getConnection(DB_URL, userName, pswd);
        checkTableExists();
        
        return true;
    }
    
    private boolean checkTableExists() throws SQLException {
        
        
        DatabaseMetaData dbmd = con.getMetaData();
        ResultSet rs = dbmd.getTables(null, "APP", "user_signup_request", null);
        if (rs.next()) {
            System.out.println("Table " +  rs.getString(3) + " exists");
            return true;
        } else {
            System.out.println("user_signup_request table does not exists!");
            if(createTable()) {
                return true;
            } else {
                System.out.println("Failed to created user_signup_request table!");
                return false;
            }
        }
            
    }
    private boolean createTable() {
        
        String sqlCreate = "CREATE TABLE user_signup_request( "
                + "id INTEGER generated always as identity, "
                + "username VARCHAR(25) NOT NULL PRIMARY KEY, "
                + "password VARCHAR(100) NOT NULL, "
                + "email VARCHAR(50) NOT NULL, "
                + "introduction VARCHAR(500) NOT NULL, "
                + "reason VARCHAR(500) NOT NULL, "
                + "request_date DATE NOT NULL, "
                + "approved BOOLEAN NOT NULL)";
        
        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sqlCreate);
            System.out.println("Successfully created user_signup_request table!");
            return true;
        
        } catch (SQLException ex) {
            Logger.getLogger(signup_request.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        
        
    }
}
