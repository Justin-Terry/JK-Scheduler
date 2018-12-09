package gui;

import application.AppointmentForm;
import application.Main;
import application.UserController;
import application.convert;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class CreateAppointmentWindow extends AppointmentWindow {
	
	CreateAppointmentWindow(){
		super();
		stage.setTitle("Create Appointment");
		layoutGrid();
                scene.getStylesheets().add(getClass().getResource("material-fx-v0_3.css").toExternalForm());
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

		submitButton.setOnAction(e->{
                                String name = nameText.getText(),
                                        type = (String)typeOptionBox.getValue(),
                                        location = locationText.getText();
                                
                                LocalDateTime start = dpStart.getValue()
                                                            .atTime(
                                                                convert.to24Hour(
                                                                    Integer.parseInt((String)SHB.getValue()),
                                                                    (String)SPB.getValue()),
                                                                    Integer.parseInt((String)SMB.getValue()));
                                LocalDateTime end = dpEnd.getValue()
                                                        .atTime(
                                                            convert.to24Hour(
                                                                Integer.parseInt((String)EHB.getValue()),
                                                                (String)EPB.getValue()),
                                                                Integer.parseInt((String)EMB.getValue()));
                                int alertBefore = Integer.parseInt(alertText.getText());
                                
				AppointmentForm form = new AppointmentForm(name, type, location, start, end, alertBefore);

				if (UserController.handledAppointmentCreation(form)) {
					Main.getWindowManager().setCalendarView();
					stage.close();
                                }
                                else {
                                    new Alert(AlertType.ERROR, "Invalid field(s).", ButtonType.OK)
                                            .showAndWait();
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
