package gui;

import java.time.LocalDate;
import java.time.LocalDateTime;

import application.Main;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DateBox{
	LocalDate boxDate;
	LocalDateTime boxDateTime;
	Pane theBox;
	Label dateLabel;
	String boxColor;
	String borderRadius = "-fx-background-radius: 5;";
	VBox boxContainer;
	VBox appointmentsContainer;
	
	public DateBox(LocalDate date) {
		boxDate = date;
		appointmentsContainer = new VBox();
		boxContainer = new VBox();
		boxContainer.setPadding(new Insets(2));
		boxColor = Main.getSettings().getCalendarColor();
		dateLabel = new Label(String.valueOf(date.getDayOfMonth()));
		dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		dateLabel.setTextFill(Color.WHITE);
		theBox = new Pane();
		appointmentsContainer.setFillWidth(true);
		boxContainer.setFillWidth(true);
		theBox.getChildren().add(boxContainer);
		boxContainer.getChildren().add(dateLabel);
		boxContainer.getChildren().add(appointmentsContainer);
		
	}
	
	public DateBox(LocalDate date, int hour, int min) {
		boxDate = date;
		boxDateTime = boxDate.atTime(hour, min);
		appointmentsContainer = new VBox();
		boxContainer = new VBox();
		boxContainer.setPadding(new Insets(2));
		boxColor = Main.getSettings().getCalendarColor();
		dateLabel = new Label(String.valueOf(date.getDayOfMonth()));
		dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		dateLabel.setTextFill(Color.WHITE);
		theBox = new Pane();
		appointmentsContainer.setFillWidth(true);
		boxContainer.setFillWidth(true);
		theBox.getChildren().add(boxContainer);
		boxContainer.getChildren().add(appointmentsContainer);
	}
	
	public DateBox() {
		appointmentsContainer = new VBox();
		boxContainer = new VBox();
		boxContainer.setPadding(new Insets(2));
		boxColor = Main.getSettings().getCalendarColor();
		theBox = new Pane();
		appointmentsContainer.setFillWidth(true);
		boxContainer.setFillWidth(true);
		theBox.getChildren().add(boxContainer);
		boxContainer.getChildren().add(appointmentsContainer);
	}
	
	public Pane getDateBox() {
		theBox.setStyle("-fx-background-color: #" + boxColor + ";" + borderRadius);
		return theBox;
	}
	
	public LocalDateTime getBoxTime() {
		return boxDateTime;
	}
	
	public void setDateBoxColor(String c) {
		boxColor = c;
		theBox.setStyle("-fx-background-color: #" + c + ";" + borderRadius);

	}
}
