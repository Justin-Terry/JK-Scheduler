package gui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateAppointmentWindow {
	private Stage stage;
	private Scene scene;
	private Button createButton = new Button("Create");
	private GridPane gp;
	private String[] labelText = {"Name", "Type", "Description", "Date", "Start Time", "End Time"};
	private ObservableList<Integer> hourOptions = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
	private ObservableList<String> typeOptions = FXCollections.observableArrayList("Private", "Public");
	private ObservableList<String> minuteOptions;
	private ObservableList<String> ampmOptions = FXCollections.observableArrayList("AM", "PM");
	private final ComboBox typeOptionBox = new ComboBox(typeOptions);
	private final ComboBox startHourBox = new ComboBox(hourOptions);
	private final ComboBox endHourBox = new ComboBox(hourOptions);
	private static ComboBox startMinuteBox;
	private final ComboBox endMinuteBox = new ComboBox(minuteOptions);
	private final ComboBox startAMPMBox = new ComboBox(ampmOptions);
	private final ComboBox endAMPMBox = new ComboBox(ampmOptions);
	private static TextArea descriptionText = new TextArea();
	private static TextField nameText = new TextField();
	
	
	CreateAppointmentWindow(){
		setUpMinuteOptions();
		stage = new Stage();
		stage.setTitle("Create Appointment");

		gp = new GridPane();
		gp.setPadding(new Insets(10));
		gp.setVgap(10);
		gp.setHgap(10);
		scene = new Scene(gp, 420, 36*(labelText.length+2));

		layoutGrid();
		stage.setScene(scene);
		stage.show();
	}
	
	void layoutGrid() {
		DatePicker dp = new DatePicker();
		HBox startHolder = new HBox();
		HBox endHolder = new HBox();
		typeOptionBox.setPromptText("Appointment Type");
		startHourBox.setPromptText("Hour");
		startMinuteBox.setPromptText("Minute");
		startAMPMBox.setPromptText("AM/PM");
		endHourBox.setPromptText("Hour");
		endMinuteBox.setPromptText("Minute");
		endAMPMBox.setPromptText("AM/PM");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMinHeight(40*(labelText.length+1));
		stage.setMinWidth(300);
		
		for(int i = 0; i < labelText.length; i++) {
			gp.add(new Label(labelText[i]), 0, i);
		}
		
		gp.add(nameText, 1, 0);
		gp.add(typeOptionBox, 1, 1);		
		gp.add(descriptionText, 1, 2);
		descriptionText.setMaxWidth(300);
		gp.add(dp, 1, 3);
		startHolder.getChildren().addAll(startHourBox, new Label(":"), startMinuteBox, startAMPMBox);
		endHolder.getChildren().addAll(endHourBox, new Label(":"), endMinuteBox, endAMPMBox);
		gp.add(startHolder, 1, 4);
		gp.add(endHolder, 1, 5);
		
		gp.add(createButton, 0, 6);
		gp.setColumnSpan(createButton, 2);

		
		
		
		
	}
	
	void setUpMinuteOptions() {
		minuteOptions = FXCollections.observableArrayList();
		for(int i = 1; i < 60; i++) {
			minuteOptions.add(String.format("%02d", i));
		}
		startMinuteBox = new ComboBox<String>(minuteOptions);
	}
}
