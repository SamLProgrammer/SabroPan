package models;

import java.util.concurrent.CopyOnWriteArrayList;

import persistence.Deco;

public class ExpendituresManager {

	private CopyOnWriteArrayList<Expenditure> expendituresList;
	private Deco deco;

	public ExpendituresManager(Deco deco) {
		this.deco = deco;
		expendituresList = new CopyOnWriteArrayList<Expenditure>();
		loadExpendituresList();
	}

	public void addExpenditure(Expenditure expenditure) {
		expendituresList.add(expenditure);
		saveChangesOnPersistence();
	}

	public Expenditure createExpenditure(String reason, int price) {
		return new Expenditure(reason, price);
	}

	public CopyOnWriteArrayList<Expenditure> getExpendituresList() {
		return expendituresList;
	}

	private void saveChangesOnPersistence() {
		for (Expenditure expenditure : expendituresList) {
			System.out.println(expenditure.getReason() + ", " + expenditure.getPrice());
		}
		deco.writeInFile(deco.encodeExpendituresList(expendituresList), "4");
	}

	private void loadExpendituresList() {
		if (deco.loadExpendituresData() != null) {
			expendituresList = deco.loadExpendituresData();
		}
	}

}
