package gui;

import java.time.format.DateTimeFormatter;

import application.Appointment;
import application.Main;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CancelAppointmentWindow {

	public CancelAppointmentWindow() {
		Stage stage = new Stage();
		ScrollPane scroller = new ScrollPane();
		GridPane apptsPane = new GridPane();
		apptsPane.setVgap(10);
		apptsPane.setPadding(new Insets(20));
		Scene scene = new Scene(scroller, 300, 300);

		for (int i = 0; i < Main.getCurrentUser().getAppointments().size(); i++) {
			Appointment a = Main.getCurrentUser().getAppointments().get(i);
			VBox apptHolder = new VBox();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm LLL dd, uuuu");
			String start = a.getStart().format(dtf);
			Label apptLabel = new Label(a.getName() + " " + start);

			apptHolder.getChildren().add(new Label("Click on appointment to delete"));
			apptHolder.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event e) {
					Alert confirm = new Alert(AlertType.CONFIRMATION);
					confirm.setTitle("Cancel Appointment");
					confirm.setContentText("Are you sure you want to delete the appointment?");
					confirm.setHeaderText("Delete Appointment");
					confirm.showAndWait();
					
					if (confirm.getResult() == ButtonType.OK) {

						Appointment appt = a;
						Main.getCurrentUser().cancelAppointment(appt.getAppID());
						System.out.println("Trying to cancel " + appt.getName());
						Main.getWindowManager().setCalendarView();
						stage.close();
					}
				}
			});

			apptHolder.getChildren().add(apptLabel);

			apptsPane.add(apptHolder, 0, i + 1);

		}
		scroller.setContent(apptsPane);
		stage.setScene(scene);
		stage.show();

	}

}