package gui;

import java.time.format.DateTimeFormatter;

import application.Appointment;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppointmentView {
	
	public AppointmentView(Appointment a) {
		Stage stage = new Stage();
		stage.setTitle(a.getName());
		VBox box = new VBox();
		box.setPadding(new Insets(20));
		box.setSpacing(10);
		Scene scene = new Scene(box);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MMM dd, uuuu hh:mm");
		
		Label title = new Label("Name: " + a.getName());
		Label start = new Label("Start: " + a.getStart().format(dtf));
		Label end = new Label("End: " + a.getEnd().format(dtf));
		Label type = new Label("Type: " + a.getType());
		Label location = new Label("Location: " + a.getLocation());
		
		box.getChildren().addAll(title, start, end, type, location);
		stage.setScene(scene);
		stage.show();
		
		
	}
	
}
