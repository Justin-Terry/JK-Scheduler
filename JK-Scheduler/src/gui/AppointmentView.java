package gui;

import java.time.format.DateTimeFormatter;

import application.Appointment;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppointmentView {

	public AppointmentView(Appointment a) {
		Stage stage = new Stage();
		stage.setTitle(a.getName());
		VBox box = new VBox();
		Button cancel = new Button("Cancel Appointment");
		box.setPadding(new Insets(20));
		box.setSpacing(10);
		Scene scene = new Scene(box);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MMM dd, uuuu hh:mm");

		Label title = new Label("Name: " + a.getName());
		Label start = new Label("Start: " + a.getStart().format(dtf));
		Label end = new Label("End: " + a.getEnd().format(dtf));
		Label type = new Label("Type: " + a.getType());
		Label location = new Label("Location: " + a.getLocation());

		cancel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent b) {
				Alert confirm = new Alert(AlertType.CONFIRMATION);
				confirm.setTitle("Delete Appointment");
				confirm.setHeaderText("Delete Appointment?");
				confirm.setContentText("Are you sure you want to delete this appointment?");
				confirm.showAndWait();

				if (confirm.getResult().equals(ButtonType.OK)) {
					Main.getCurrentUser().cancelAppointment(a.getAppID());
					System.out.println("Tried to cancel apptID " + a.getAppID());
					Main.getWindowManager().setCalendarView();
					stage.close();
				} else {
					stage.close();
				}

			}
		});

		box.getChildren().addAll(title, start, end, type, location, cancel);
		stage.setScene(scene);
		stage.show();

	}

}
