package observers;

import controller.Controller;

public class SearchUsersListener {

	private Controller controller;
	
	public SearchUsersListener(Controller controller) {
		this.controller = controller;
	}
	
	public void filterUsers(String subString) {
		controller.filterUsers(subString);
	}
}
