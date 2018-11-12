package gui;

import application.AppointmentForm;
import application.UserController;
import application.convert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

<<<<<<< HEAD
public class CreateAppointmentWindow {
	private Stage stage;
	private Scene scene;
	private Button createButton = new Button("Create");
	private GridPane gp;
	private String[] labelText = {"Name", "Type", "Location", "Date", "Start Time", "End Time"};
	private ObservableList<Integer> hourOptions = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
	private ObservableList<String> typeOptions = FXCollections.observableArrayList("Private", "Public");
	private static ObservableList<String> minuteOptions;
	private ObservableList<String> ampmOptions = FXCollections.observableArrayList("AM", "PM");
	private final ComboBox typeOptionBox = new ComboBox(typeOptions);
	private final ComboBox startHourBox = new ComboBox(hourOptions);
	private final ComboBox endHourBox = new ComboBox(hourOptions);
	private final ComboBox startMinuteBox;
	private final ComboBox endMinuteBox;
	private final ComboBox startAMPMBox = new ComboBox(ampmOptions);
	private final ComboBox endAMPMBox = new ComboBox(ampmOptions);
	private static TextField nameText = new TextField();
	private static TextField locationText = new TextField();
	
=======
public class CreateAppointmentWindow extends AppointmentWindow {
>>>>>>> branch 'master' of https://github.com/Justin-Terry/JK-Scheduler.git
	
	CreateAppointmentWindow(){
<<<<<<< HEAD
		setUpMinuteOptions();
		startMinuteBox = new ComboBox<String>(minuteOptions);
		endMinuteBox = new ComboBox<String>(minuteOptions);
		stage = new Stage();
=======
		super();
>>>>>>> branch 'master' of https://github.com/Justin-Terry/JK-Scheduler.git
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

				if (UserController.handledAppointmentSubmission(form))
					stage.close();
				
			}
		});
<<<<<<< HEAD
		
		
		
	}
	
	void setUpMinuteOptions() {
		minuteOptions = FXCollections.observableArrayList();
		for(int i = 0; i < 60; i++) {
			minuteOptions.add(String.format("%02d", i));
		}
=======
>>>>>>> branch 'master' of https://github.com/Justin-Terry/JK-Scheduler.git
	}
}
