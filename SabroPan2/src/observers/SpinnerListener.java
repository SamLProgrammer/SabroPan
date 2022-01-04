package observers;

import controller.Controller;

public class SpinnerListener {

	Controller controller;

	public SpinnerListener(Controller controller) {
		this.controller = controller;
	}

	public void calculateItemTotalPrice(int amount) {
		controller.calculateOnSellItemTotalPrice(amount);
	}

	public int getExistencesFromProduct(String id) {
		return controller.getExistencesFromProduct(id);
	}

	public void updateProductOnSellItemsAmount(String code, String amount) {
		controller.updateProductOnSellItemsAmount(code,amount);
	}

}
