package models;

public enum UserState {
	
	ACTIVE("Activo"), INACTIVE("Inactivo");
	private String value;

	private UserState(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
