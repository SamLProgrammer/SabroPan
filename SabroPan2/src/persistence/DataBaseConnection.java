package persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DataBaseConnection {

	private String login = "samuel";
	private String password = "samuel";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, login, password);
			if(conn != null) {
			conn.setAutoCommit(false);
			System.out.println("Conexión Exitosa Bby!");
			} else {
				System.out.println("Conexión Erronea");
			}
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, "Conexión Fallida " + e.getMessage());
		}
		return conn;
	}
	public static void main(String[] args) {
		DataBaseConnection sqlDAO  = new DataBaseConnection();
		sqlDAO.getConnection();
	}
}
