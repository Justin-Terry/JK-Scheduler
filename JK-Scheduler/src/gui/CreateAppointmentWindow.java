package gui;

import application.AppointmentForm;
import application.UserController;
import application.convert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CreateAppointmentWindow extends AppointmentWindow {
	
	CreateAppointmentWindow(){
		super();
		stage.setTitle("Create Appointment");
		layoutGrid();
		stage.setScene(scene);
		stage.show();
	}
	
	void layoutGrid() {
		// Event name, stype, location
		nameText.setPromptText("Event Name");
		typeOptionBox.getSelectionModel().select(0);
		locationText.setPromptText("Event Location");

		// Start hour, minute, period
		SHB.getSelectionModel().select(0);
		SMB.getSelectionModel().select(0);
		SPB.getSelectionModel().select(0);

		// End hour, minute, period
		EHB.getSelectionModel().select(0);
		EMB.getSelectionModel().select(0);
		EPB.getSelectionModel().select(0);

		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a){
				AppointmentForm form =
						new AppointmentForm(
							nameText.getText(),
							(String)typeOptionBox.getValue(),
							locationText.getText(),
							dpStart .getValue()
									.atTime(
											convert.to24Hour(
													Integer.parseInt((String)SHB.getValue()),
													(String)SPB.getValue()),
											Integer.parseInt((String)SMB.getValue())),
							dpEnd   .getValue()
									.atTime(
											convert.to24Hour(
													Integer.parseInt((String)EHB.getValue()),
													(String)EPB.getValue()),
											Integer.parseInt((String)EMB.getValue())));

				if (UserController.handledAppointmentCreation(form))
					stage.close();
				
			}
		});
	}
	
//	void setUpMinuteOptions() {
//		minuteOptions = FXCollections.observableArrayList();
//		for(int i = 0; i < 60; i++) {
//			minuteOptions.add(String.format("%02d", i));
//		}
//	}
}
