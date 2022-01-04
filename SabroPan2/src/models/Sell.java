package models;

import java.util.Calendar;

public class Sell {
	
	private Calendar date;
	private String  sellBill;
	private int sellTotal;
	
	public Sell(String sellBill, int sellTotal) {
		this.sellBill = sellBill;
		this.sellTotal = sellTotal;
		date = Calendar.getInstance();
	}

	public Calendar getDate() {
		return date;
	}

	public String getSellBill() {
		return sellBill;
	}

	public int getSellTotal() {
		return sellTotal;
	}
}
