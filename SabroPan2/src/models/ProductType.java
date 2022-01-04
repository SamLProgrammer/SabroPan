package models;

public enum ProductType {

	COMESTIBLE("Comestible"), BEBIDA("Bebida"), ARTICULO("Art�culo");
	private String value;

	private ProductType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
