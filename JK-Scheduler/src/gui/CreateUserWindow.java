package gui;

import application.Main;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateUserWindow {
	Stage stage;
	GridPane gp;
	Scene scene;

	private String[] fieldNames = { "Username", "Password", "First Name", "Last Name", "Street", "City", "State",
			"Zip Code", "Phone Number" };

	public CreateUserWindow() {
		stage = new Stage();
		stage.setTitle("Create User");

		gp = new GridPane();
		gp.setPadding(new Insets(10));
		gp.setVgap(10);
		gp.setHgap(10);
		scene = new Scene(gp, 300, 360);

		layoutGrid();
		stage.setScene(scene);
		stage.show();

	}

	public void layoutGrid() {
		// Keeps window on top of everything else
		stage.initModality(Modality.APPLICATION_MODAL);
		
		stage.setMinHeight(360);
		stage.setMinWidth(300);
		
		// ColumnConstraints to define how much of the scene each column should take up
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(33);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(66);
		gp.getColumnConstraints().addAll(col1, col2);
		
		Button submitButton = new Button("Submit");
		
		// Creating each field separately in order to make it easy to get text from them later
		TextField usernameField = new TextField();
		usernameField.setPromptText("Enter Username");
		TextField passwordField = new TextField();
		passwordField.setPromptText("Enter Password");
		TextField firstNameField = new TextField();
		firstNameField.setPromptText("Enter First Name");
		TextField lastNameField = new TextField();
		lastNameField.setPromptText("Enter Last Name");
		TextField streetField = new TextField();
		streetField.setPromptText("Enter Street Address");
		TextField cityField = new TextField();
		cityField.setPromptText("Enter City");
		TextField stateField = new TextField();
		stateField.setPromptText("Enter State");
		TextField zipField = new TextField();
		zipField.setPromptText("Enter Zip Code");
		TextField phoneField = new TextField();
		phoneField.setPromptText("Enter Phone Number");
		
		// Adding labels to the scene
		for (int i = 0; i < fieldNames.length; i++) {
			gp.add(new Label(fieldNames[i]), 0, i);
		}
		// Adding fields to the scene
		gp.add(usernameField, 1, 0);
		gp.add(passwordField, 1, 1);
		gp.add(firstNameField, 1, 2);
		gp.add(lastNameField, 1, 3);
		gp.add(streetField, 1, 4);
		gp.add(cityField, 1, 5);
		gp.add(stateField, 1, 6);
		gp.add(zipField, 1, 7);
		gp.add(phoneField, 1, 8);
		
		// Adding button and centering it under the other rows
		gp.add(submitButton, 0, 9);
		
		
		gp.setColumnSpan(submitButton, 2);
		gp.setHalignment(submitButton, HPos.CENTER);


	}
}
