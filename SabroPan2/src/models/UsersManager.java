package models;

import java.util.Comparator;
import dao.UsersDAO;
import observers.TablePrinter;

public class UsersManager {

	private Tree<User> usersTree;
	private UsersDAO usersDAO;
	private Comparator<User> dataComparator;

	public UsersManager(TablePrinter<User> tablePrinter) {
		initComparators();
		usersTree = new Tree<User>(dataComparator, tablePrinter);
		usersDAO = new UsersDAO(dataComparator, tablePrinter);
		loadData();
	}
	
	public User createUser(String id, String name, String lastName, String password, UserState userState,
			Position position) {
		return new User(id, name, password, lastName, userState, position);
	}

	public void addUser(User user) {
		usersDAO.addNewUser(user);
		usersTree.addNode(new Node<User>(user));
		loadData();
	}
	
	public User findUser(String id) {
		return usersDAO.findUser(id);
	}

	public void editUser(String code, String name, String lastName, String password, UserState userState,
			Position position) {
		usersDAO.editUser(code, name, lastName, password, userState, position);
		loadData();
	}
	
	public void removeUser(String code) {
		User user = usersTree.findNodeByData(usersDAO.findUser(code)).getData();
		System.out.println(user.getName());
		usersTree.deleteNodeByData(user);
		usersDAO.deleteUser(code);
	}
	
	private void initComparators() {
		dataComparator = new Comparator<User>() {
			
			@Override
			public int compare(User o1, User o2) {
				int result = 0;
				if(o1.getCode() < o2.getCode()) {
					result = -1;
				}
				else if(o1.getCode() > o2.getCode()) {
					result = 1;
				}
				return result;
			}
		};
	}
	
	public void loadData() {
		this.usersTree = usersDAO.loadUsers();
	}

	public Tree<User> filteredProductsList(String subString) {
		return usersDAO.filterUsers(subString, usersTree);
	}

	public boolean subStringExist(String subString, String string) {
		boolean flag = false;
		for (int i = 0; i < string.length(); i++) {
			if (i + subString.length() <= string.length()
					&& subString.equalsIgnoreCase(string.substring(i, i + subString.length()))) {
				flag = true;
			}
		}
		return flag;
	}

	public boolean validUserLogin(String userCodeFromField, String userPasswordFromField) {
		return usersDAO.loginRequest(userCodeFromField, userPasswordFromField);
	}

	public Tree<User> getUsersTree() {
		return usersTree;
	}

}
