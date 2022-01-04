package models;

import java.util.concurrent.CopyOnWriteArrayList;

public class Product {

	private String id;
	private String name;
	private int price;
	private ProductType type;
	private float measure;
	private MeasureType measureType;
	private boolean enabled;
	private CopyOnWriteArrayList<Item> itemsList;

	public Product(String id, String name, float measure, MeasureType measureType, int price, ProductType type) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.type = type;
		this.measure = measure;
		this.measureType = measureType;
		enabled = true;
		itemsList = new CopyOnWriteArrayList<Item>();
	}

	public String[] dataAsVector() {
		String auxMeasure = "";
		if (measure != 0) {
			auxMeasure = String.valueOf(measure) + measureType.toString();
		}
		String[] data = { id, name, auxMeasure, String.valueOf(price), type.toString(),
				String.valueOf(itemsList.size()) };
		return data;
	}

	public String[] dataAsVectorToStock() {
		String auxMeasure = "";
		if (measure != 0) {
			auxMeasure = String.valueOf(measure) + measureType.toString();
		}
		String[] data = { id, name + " " + auxMeasure, String.valueOf(price), String.valueOf(itemsList.size())};
		return data;
	}
	
	public String[] dataAsVectorToSell() {
		String auxMeasure = "";
		if (measure != 0) {
			auxMeasure = String.valueOf(measure) + measureType.toString();
		}
		String[] data = { id, name + " " + auxMeasure, String.valueOf(price)};
		return data;
	}

	public void addItems(int amount) {
		for (int i = 0; i < amount; i++) {
			itemsList.add(createItem());
		}
	}

	public void removeItems(int amount) {
		for (int i = amount; i > 0; i--) {
			itemsList.remove(i);
		}
	}

	public Item createItem() {
		return new Item(generateItemCode(), name + " " + measure + measureType);
	}

	private String generateItemCode() {
		String id = this.id;
		do {
			id += String.valueOf((int) (Math.random() * 10));
		} while (idExists(id));
		return id;
	}

	private boolean idExists(String id) {
		boolean flag = false;
		for (Item item : itemsList) {
			if (item.getCode().equalsIgnoreCase(id)) {
				flag = true;
			}
			
		}
		return flag;
	}

	//================================== GETTERS && SETTERS ==================================

	public ProductType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public int getPrice() {
		return price;
	}

	public float getMeasure() {
		return measure;
	}

	public MeasureType getMeasureType() {
		return measureType;
	}
	
	public ProductType getProductType() {
		return type;
	}

	public CopyOnWriteArrayList<Item> getItemsList() {
		return itemsList;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public void setMeasure(float measure) {
		this.measure = measure;
	}

	public void setMeasureType(MeasureType measureType) {
		this.measureType = measureType;
	}
	
	public void setEnabled(boolean flag) {
		enabled = flag;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
}
