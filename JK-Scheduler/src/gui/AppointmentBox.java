package gui;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class AppointmentBox {
	private Pane appointmentPane;
	
	AppointmentBox(){
		appointmentPane = new Pane();
		appointmentPane.setStyle("-fx-background-color: #555555; -fx-background-radius: 10px;");
		Label appointmentLabel = new Label("Appointment");
		appointmentPane.getChildren().add(appointmentLabel);
	}
	
	public Pane getPane() {
		return appointmentPane;
		//comment
	}

}
