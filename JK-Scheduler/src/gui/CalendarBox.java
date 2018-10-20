package gui;

import application.Main;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CalendarBox {
	Pane dateRec = new Pane();
	int dateNumber;
	
	public CalendarBox(int n, String c) {
		dateNumber = n;
		this.setDateBoxColor(c);
	}
	
	public String getDateNumberString() {
		return Integer.toString(dateNumber);
	}
	
	public Pane getCalendarBox() {
		return dateRec;
	}
	
	public void setDateBoxColor(String c) {
		dateRec.setStyle("-fx-background-color: #" + c + ";");
	}
	
}
