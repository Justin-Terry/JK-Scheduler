package gui;

import java.awt.Color;
import java.time.format.DateTimeFormatter;

import application.Appointment;
import application.Main;
import application.convert;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class AppointmentBox {
	private Label nameLabel;
	private Pane apptPane;
	private String borderRadius = "-fx-background-radius: 5;";
	
	public AppointmentBox(Appointment a) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm");
                nameLabel = new Label(  "[" + a.getStart().format(dtf)+ " " + convert.toPeriod(a.getStart()) 
                                        + " - " + a.getEnd().format(dtf)+ " " + convert.toPeriod(a.getEnd()) + "]" 
                                        + " - " + a.getName());
//		nameLabel = new Label(a.getStart().format(dtf) + " - " + a.getName());
		apptPane = new Pane();
		apptPane.setPadding(new Insets(2));
		apptPane.getChildren().add(nameLabel);
		setApptColor();
                apptPane.setOnMouseEntered(e->{Main.getWindowManager().getMainStage().getScene().setCursor(Cursor.HAND);});
                apptPane.setOnMouseExited(e->{Main.getWindowManager().getMainStage().getScene().setCursor(Cursor.DEFAULT);});
		apptPane.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event e) {
				AppointmentView av = new AppointmentView(a);
			}
		});
		
		
	}
	
	private void setApptColor() {
		String color = Main.getSettings().getAppointmentColor();
		apptPane.setStyle("-fx-background-color: #"+color + "; -fx-background-radius: 5;");
	}
	
	public Pane getBox() {
		return apptPane;
	}
	
}
