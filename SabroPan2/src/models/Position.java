package models;

public enum Position {
	
	ADMIN("Administrador"), SELLER("Vendedor");
	private String value;

	private Position(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
