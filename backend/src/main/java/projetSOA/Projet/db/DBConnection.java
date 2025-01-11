package projetSOA.Projet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public Connection getConnection() throws SQLException {
	    try {
	        // Charger le driver JDBC explicitement
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        String url = "jdbc:mysql://localhost:3306/locationthemedb";
	        String username = "root";
	        String password = "imen";

	        return DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        throw new SQLException("MySQL JDBC driver not found", e);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}


}
