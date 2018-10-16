package gui;

public class CalendarBox {
	
	int dateNumber;
	
	public CalendarBox(int n) {
		dateNumber = n;
	}
	
	public String getDateNumberString() {
		return Integer.toString(dateNumber);
	}
	
}
