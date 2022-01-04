package models;

import java.util.concurrent.CopyOnWriteArrayList;

public class SellManager {

	private CopyOnWriteArrayList<Sell> sellsList;
	
	public SellManager() {
		sellsList = new CopyOnWriteArrayList<Sell>();
	}
	
	public Sell createSell(String billString, int sellTotal) {
		return new Sell(billString, sellTotal);
	}
	
	public void addSell(Sell sell) {
		sellsList.add(sell);
	}

	public CopyOnWriteArrayList<Sell> getSellsList() {
		return sellsList;
	}
}
