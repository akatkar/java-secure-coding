package secure.coding.chapter01.ids.ids00.solution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateUser {

	// JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem:testdb";
    
//  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";
	
    public Connection getConnection() throws SQLException {
    	return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void create(String username, String password) throws SQLException {
    	
    	try( Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
    		String sql = "CREATE TABLE USERS "
                    + " (id INTEGER not NULL, "
                    + " username VARCHAR(255), "
                    + " password VARCHAR(255), "
                    + " PRIMARY KEY ( id ))";
            
            statement.execute(sql);
            
         // insert first record
            sql = "INSERT INTO USERS " +
                   "VALUES (100, '"+username+"', '" +password+"')";            
            statement.executeUpdate(sql);
    	}

        
    }
}
