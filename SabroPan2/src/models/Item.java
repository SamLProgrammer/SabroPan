package models;

public class Item {

	private String code;
	private String name;
	
	public Item(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
