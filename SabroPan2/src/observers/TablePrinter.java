package observers;

import controller.Controller;
import models.User;

public class TablePrinter<T> {
	
	Controller controller;
	
	public TablePrinter (Controller controller) {
		this.controller = controller;
	}
	
	public void addRowToTable(T data) {
		controller.addUserToUsersTable((User) data);
	}
}
