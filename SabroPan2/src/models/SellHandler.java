package models;

import java.util.concurrent.CopyOnWriteArrayList;

public class SellHandler {
	
	private CopyOnWriteArrayList<Product> productsOnSellList;
	
	public SellHandler() {
		productsOnSellList = new CopyOnWriteArrayList<Product>();
	}
	
	public void addProductOnSell(Product product) {
		if(!productExists(product.getId())) {
		productsOnSellList.add(product);
		}
	}
	
	private boolean productExists(String code) {
		boolean flag = false;
		for (Product product : productsOnSellList) {
			if(product.getId().equals(code)) {
				flag = true;
			}
		}
		return flag;
	}
	
	public void removeProductOnSell(String code) {
		for (Product product : productsOnSellList) {
			if(product.getId().equalsIgnoreCase(code)) {
				productsOnSellList.remove(product);
			}
		}
	}
	
	public String getBillStructureFromActualSell() {
		StringBuilder billString = new StringBuilder();
		String productName = "";
		String productId = "";
		int counter = 1;
		for (Product product : productsOnSellList) {
			productName = product.getName();
			productId = product.getId();
			if(productId.length() < 5) {
				for (int i = productId.length(); i < 5; i++) {
					productId += " ";
				}
			}
			billString.append(productId);
			billString.append("  ");
			if(productName.length() > 16) {
				billString.append(product.getName().substring(0,16));				
			} else {
				for (int i = productName.length(); i < 16; i++) {
					productName += " ";
				}
			}
			billString.append(productName);
			billString.append("  ");
			billString.append(product.getItemsList().size());
			billString.append("  ");
			billString.append(product.getItemsList().size()*product.getPrice());
			billString.append("\n");
			if(counter == productsOnSellList.size()) {
				billString.append("------------------------------------------------\n");
				billString.append("TOTAL:                      " + calculateTotalPriceOfActualSell());
			}
			counter++;
		}
		return billString.toString();
	}
	
	private int calculateTotalPriceOfActualSell() {
		int totalPrice = 0;
		for (Product product : productsOnSellList) {
			totalPrice += product.getPrice()*product.getItemsList().size();
		}
		return totalPrice;
	}

	public CopyOnWriteArrayList<Product> getProductsOnSellList() {
		return productsOnSellList;
	}

	public void clean() {
		productsOnSellList = new CopyOnWriteArrayList<Product>();
	}

	public void updateItemAmountOnSell(String selectedItemCodeFromSellTable, int amount) {
		for (Product product : productsOnSellList) {
			if(product.getId().equals(selectedItemCodeFromSellTable)) {
				product.getItemsList().clear();
				product.addItems(amount);
			}
		}
	}
}
