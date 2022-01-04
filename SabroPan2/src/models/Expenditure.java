package models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Expenditure {

	private String reason;
	private int price;
	private Calendar date;

	public Expenditure(String reason, int price) {
		this.reason = reason;
		this.price = price;
		date = GregorianCalendar.getInstance();
	}

	public String getReason() {
		return reason;
	}

	public int getPrice() {
		return price;
	}

	public Calendar getDate() {
		return date;
	}

	public String[] dataAsVector() {
		String dateString = "" + date.get(Calendar.DAY_OF_MONTH) + "/" + (date.get(Calendar.MONTH) + 1) + "/"
				+ date.get(Calendar.YEAR);
		String[] data = { reason, String.valueOf(price), dateString };
		return data;
	}

}
