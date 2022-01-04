package models;

import java.util.concurrent.CopyOnWriteArrayList;

import persistence.Deco;

public class ProductsManager {

	private Deco deco;
	private CopyOnWriteArrayList<Product> productsList;

	public ProductsManager(Deco deco) {
		this.deco = deco;
		productsList = new CopyOnWriteArrayList<Product>();
		loadProductsList();
	}

	public void addItem(String productCode, int amount) {
		for (Product product : productsList) {
			if (product.getId().equals(productCode)) {
				product.addItems(amount);
			}
		}
		saveChangesOnPersistence();
	}

	public void editProduct(String code, String name, int price, float f, MeasureType measureType,
			ProductType productType) {
		for (Product product : productsList) {
			if (product.getId().equals(code)) {
				product.setName(name);
				product.setPrice(price);
				product.setMeasure(f);
				product.setMeasureType(measureType);
				product.setType(productType);
			}
		}
	}

	public void sellItem(String productCode, int amount) {
		for (Product product : productsList) {
			if (product.getId().equals(productCode)) {
				product.removeItems(amount);
			}
		}
		saveChangesOnPersistence();
	}

	public Product findProduct(String id) {
		Product product = null;
		for (Product auxProduct : productsList) {
			if (auxProduct.getId().equalsIgnoreCase(id)) {
				product = auxProduct;
			}
		}
		return product;
	}

	public Product createProduct(String name, float f, MeasureType measureType, int price, ProductType type) {
		return new Product(idGenerator(), name, f, measureType, price, type);
	}

	public Product copyProduct(Product product) {
		return new Product(product.getId(), product.getName(), product.getMeasure(), product.getMeasureType(),
				product.getPrice(), product.getType());
	}

	public void addProduct(Product product) {
		productsList.add(product);
		saveChangesOnPersistence();
	}

	private void saveChangesOnPersistence() {
		deco.writeInFile(deco.encodeProductsList(productsList), "1");
	}

	private void loadProductsList() {
		if (deco.loadProductsData() != null) {
			productsList = deco.loadProductsData();
		}
	}

	// ======================================= LOGIC
	// ========================================

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

	private String idGenerator() {
		String id = "";
		do {
			for (int i = 0; i < 2; i++) {
				id += (char) (65 + (int) (Math.random() * 26));
			}
			id += String.valueOf((int) (Math.random() * 10));
		} while (idExists(id));
		return id;
	}

	private boolean idExists(String id) {
		boolean flag = false;
		for (Product product : productsList) {
			if (product.getId().equalsIgnoreCase(id)) {
				flag = true;
			}
		}
		return flag;
	}

	public CopyOnWriteArrayList<Product> filteredProductsList(String subString) {
		CopyOnWriteArrayList<Product> filteredList = new CopyOnWriteArrayList<Product>();
		for (Product product : productsList) {
			if (subStringExist(subString, product.getName())) {
				filteredList.add(product);
			}
		}
		return filteredList;
	}

	// ================================= GETTERS && SETTERS
	// ==================================

	public CopyOnWriteArrayList<Product> getProductsList() {
		return productsList;
	}
}
