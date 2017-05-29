import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class createTable {
	// pass connection into a variable
	Connection c = null;
	Statement stmt = null;

	// Handles inital creation and connection to a database
	public static Connection dbConnector() {
		// surround connection with try/catch to handle exceptions
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:database");
			conn.setAutoCommit(false);

			return conn;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}

	public void insertTable() {
		// surround connection with try/catch to handle exceptions
		try {
			// call JDBC connection class
			Class.forName("org.sqlite.JDBC");
			// instantiate connection variable and use deriver manager
			Connection c = DriverManager.getConnection("jdbc:sqlite:database");
			System.out.println("Opened Database successfully!!");

			Statement stmt = c.createStatement();
			String sql = "CREATE TABLE STUDENT" + "(Username TEXT NOT NULL, " + "Password TEXT NOT NULL, "
					+ "Passtwo TEXT NOT NULL, " + "Matric TEXT NOT NULL, " + "Dept TEXT NOT NULL, "
					+ "Level INT NOT NULL)";
			stmt.executeUpdate(sql);

			stmt.close();
			// close connection
			c.close();
		} catch (Exception ex) {
			// error handlers
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
			System.exit(0);
		}
		System.out.println("Records Created successfully!!");

	}
}
