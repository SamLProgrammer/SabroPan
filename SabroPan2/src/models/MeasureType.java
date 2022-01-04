package models;

public enum MeasureType {

	NO_REGISTRA("No Registra"), LIBRAS("(lb)"), KILOGRAMOS("(Kg)"), GRAMOS("(g)"), MILIGRAMOS("(mg)"), LITROS("(L)"), MILILITROS("(mL)");
	private String value;

	private MeasureType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
