package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

import javax.swing.JOptionPane;
import models.Position;
import models.Tree;
import models.User;
import models.UserState;
import observers.TablePrinter;
import persistence.DataBaseConnection;

public class UsersDAO {
	
	private Comparator<User> usersComparator;
	private TablePrinter<User> tablePrinter;
	
	public UsersDAO(Comparator<User> usersComparator, TablePrinter<User> tablePrinter) {
		this.usersComparator = usersComparator;
		this.tablePrinter = tablePrinter;
	}

	public void addNewUser(User user) {
		DataBaseConnection dataBaseConnection = new DataBaseConnection();
		Connection conn = dataBaseConnection.getConnection();
		String sqlCommand = "INSERT INTO USERS (USER_ID, USER_FIRST_NAME, USER_LAST_NAME,"
				+ " USER_PASSWORD, USER_STATE, USER_POSITION) VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement pst = conn.prepareStatement(sqlCommand);
			pst.setString(1, user.getId());
			pst.setString(2, user.getName());
			pst.setString(3, user.getLastName());
			pst.setString(4, user.getPassword());
			pst.setString(5, user.getUserState().name());
			pst.setString(6, user.getPosition().name());
			pst.execute();
			loadUsers();
			JOptionPane.showMessageDialog(null, "User Successfully Added");
			pst.close();
			conn.commit();
		} catch (SQLException e) {
			System.out.println("Adding User Error : " + e.getMessage());
		}
	}

	public void editUser(String code, String name, String lastName, String password, UserState userState,
			Position position) {
		DataBaseConnection dataBaseConnection = new DataBaseConnection();
		Connection conn = dataBaseConnection.getConnection();
		String sqlCommand = "UPDATE USERS SET USER_FIRST_NAME = ?, "
				+ "USER_LAST_NAME = ?, USER_PASSWORD = ?, USER_STATE = ?, USER_POSITION = ? "
				+ "WHERE USER_ID LIKE ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sqlCommand);
			pst.setString(1, name);
			pst.setString(2, lastName);
			pst.setString(3, password);
			pst.setString(4, userState.name());
			pst.setString(5, position.name());
			pst.setString(6, code);
			pst.execute();
			loadUsers();
			JOptionPane.showMessageDialog(null, "User Successfully Edited");
			pst.close();
			conn.commit();
		} catch (SQLException e) {
		}
	}
	public void deleteUser(String code) {
		DataBaseConnection dataBaseConnection = new DataBaseConnection();
		Connection conn = dataBaseConnection.getConnection();
		String sqlCommand = "DELETE FROM USERS WHERE USER_ID LIKE ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sqlCommand);
			pst.setString(1, code);
			pst.execute();
			JOptionPane.showMessageDialog(null, "User Successfully Deleted");
			pst.close();
			conn.commit();
			loadUsers();
		} catch (SQLException e) {
			System.out.println("Deleting User Error : " + e.getMessage());
		}
	}
	
	public User findUser(String userId) {
		User user = null;
		DataBaseConnection dataBaseConnection = new DataBaseConnection();
		Connection conn = dataBaseConnection.getConnection();
		String sqlCommand = "SELECT * FROM USERS WHERE USER_ID LIKE '" + userId + "'";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(sqlCommand);
			ResultSet resultSet = pst.executeQuery();
			while (resultSet.next()) {
				user = new User(resultSet.getString("USER_ID"), 
						resultSet.getString("USER_FIRST_NAME"), 
						resultSet.getString("USER_PASSWORD"),
						resultSet.getString("USER_LAST_NAME"),
						UserState.valueOf(resultSet.getString("USER_STATE")), 
						Position.valueOf(resultSet.getString("USER_POSITION")));
			}
		} catch (SQLException e) {
			System.out.println("Error Loading Users Data " + e.getMessage());
		}
		return user;
	}
	
	public Tree<User> filterUsers(String string, Tree<User> usersTree) {
		DataBaseConnection dataBaseConnection = new DataBaseConnection();
		Connection conn = dataBaseConnection.getConnection();
		String sqlCommand = "SELECT * FROM USERS WHERE UPPER(USER_FIRST_NAME) LIKE UPPER(?)";
		PreparedStatement pst;
		Tree<User> filteredUsersTree= new Tree<User>(usersTree.getComparator(), usersTree.getTablePrinter());
		try {
			pst = conn.prepareStatement(sqlCommand);
			pst.setString(1, "%" + string + "%");
			ResultSet resultSet = pst.executeQuery();
			while (resultSet.next()) {
				filteredUsersTree.addNode(usersTree.createNode(new User(resultSet.getString("USER_ID"), 
						resultSet.getString("USER_FIRST_NAME"), 
						resultSet.getString("USER_PASSWORD"),
						resultSet.getString("USER_LAST_NAME"),
						UserState.valueOf(resultSet.getString("USER_STATE")), 
						Position.valueOf(resultSet.getString("USER_POSITION")))));
			}
		} catch (SQLException e) {
			System.out.println("Error Loading Users Data " + e.getMessage());
		}
		return filteredUsersTree;
	}
	
	public Tree<User> loadUsers() {
		DataBaseConnection dataBaseConnection = new DataBaseConnection();
		Connection conn = dataBaseConnection.getConnection();
		String sqlCommand = "SELECT * FROM USERS";
		PreparedStatement pst;
		Tree<User> usersTree = new Tree<User>(usersComparator, tablePrinter);
		try {
			pst = conn.prepareStatement(sqlCommand);
			ResultSet resultSet = pst.executeQuery();
			while (resultSet.next()) {
				usersTree.addNode(usersTree.createNode(new User(resultSet.getString("USER_ID"), 
						resultSet.getString("USER_FIRST_NAME"), 
						resultSet.getString("USER_PASSWORD"),
						resultSet.getString("USER_LAST_NAME"),
						UserState.valueOf(resultSet.getString("USER_STATE")), 
						Position.valueOf(resultSet.getString("USER_POSITION")))));
			}
		} catch (SQLException e) {
			System.out.println("Error Loading Users Data " + e.getMessage());
		}
		return usersTree;
	}
	
	public boolean loginRequest(String userId, String userPassword) {
		DataBaseConnection dataBaseConnection = new DataBaseConnection();
		Connection conn = dataBaseConnection.getConnection();
		String sqlCommand = "SELECT * FROM USERS WHERE USER_ID LIKE ? AND USER_PASSWORD LIKE ?";
		PreparedStatement pst;
		User user = null;
		try {
			pst = conn.prepareStatement(sqlCommand);
			pst.setString(1, userId);
			pst.setString(2, userPassword);
			ResultSet resultSet = pst.executeQuery();
			while (resultSet.next()) {
				user = new User(resultSet.getString("USER_ID"), 
						resultSet.getString("USER_FIRST_NAME"), 
						resultSet.getString("USER_PASSWORD"),
						resultSet.getString("USER_LAST_NAME"),
						UserState.valueOf(resultSet.getString("USER_STATE")), 
						Position.valueOf(resultSet.getString("USER_POSITION")));
			}
		} catch (SQLException e) {
			System.out.println("Error Loading Users Data " + e.getMessage());
		}
		return user != null;
	}
}
