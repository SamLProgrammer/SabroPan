package observers;

import controller.Controller;

public class SearchProductListener {

	private Controller controller;

	public SearchProductListener(Controller controller) {
		this.controller = controller;
	}
	
	public void filterProducts(String subString) {
		controller.filterProducts(subString);
	}
	
	public void filterProductsOnStock(String subString) {
		controller.filterProductsOnStock(subString);
	}
	
}
