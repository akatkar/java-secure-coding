package secure.coding.chapter01.ids.ids00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Login {

	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:mem:testdb";

//  Database credentials
	private static final String USER = "sa";
	private static final String PASS = "";

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

	String hashPassword(char[] password) {
		// create hash of password
		return new String(password);
	}

	public void doPrivilegedAction(String username, char[] password) throws SQLException {

		try (Connection connection = getConnection()) {
			String pwd = hashPassword(password);
//			String sqlString = "SELECT * FROM USERS";
            String sqlString = "SELECT * FROM USERS WHERE username = '"
                    + username + "' AND password = '" + pwd + "'";
            System.out.println(sqlString);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sqlString);
			if (!rs.next()) {
				throw new SecurityException("User name or password incorrect");
			}
			else {
				System.out.println("*** User Logged in succesfuly *** ");
			}
			// Authenticated; proceed
		}
	}
	
	public void showTable() throws SQLException {
		try (Connection connection = getConnection();
				Statement stmt = connection.createStatement()) {
			String sqlString = "SELECT * FROM USERS";
			ResultSet rs = stmt.executeQuery(sqlString);
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String uname = rs.getString("username");
				String pswrd = rs.getString("password");

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", username: " + uname);
				System.out.println(", password: " + pswrd);
			}
		}
	}
}