package gui;

import java.util.Date;

import application.Main;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CalendarBox {
	Pane dateRec = new Pane();
	VBox container = new VBox();
	int dateNumber;
	Date boxDate;
	
	public CalendarBox(int n, String c) {
		container.setPadding(new Insets(10));		
		dateRec.getChildren().add(container);
		dateNumber = n;
		System.out.println(String.valueOf(dateNumber));
		Label dateLabel = new Label(String.valueOf(dateNumber));
		dateLabel.setTextFill(Color.WHITE);
		dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		container.getChildren().add(dateLabel);
		this.setDateBoxColor(c);
		addAppointmentPane();
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
	
	public void addAppointmentPane() {
		AppointmentBox apb = new AppointmentBox();
		container.getChildren().add(apb.getPane());
	}
	
	
}
