package gui;

import application.Main;
import application.User;
import application.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CreateUserWindow {
	Stage stage;
	GridPane gp;
	Scene scene;

	private static final String[] fieldNames = {  "Username", "Password", "Password Confirmation",
											"First Name", "Last Name",
											"Phone Number", "E-Mail" ,
											"Street", "City", "State", "Zip Code"};

	public static final int getNumFields() {
		return fieldNames.length;
	};


	public CreateUserWindow() {
		stage = new Stage();
		stage.setTitle("Create User");

		gp = new GridPane();
		gp.setPadding(new Insets(10));
		gp.setVgap(10);
		gp.setHgap(10);
		scene = new Scene(gp, 420, 36*(fieldNames.length+2));

		layoutGrid();
		stage.setScene(scene);
		stage.show();
	}

	public void layoutGrid() {
		// Keeps window on top of everything else
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMinHeight(40*(fieldNames.length+1));
		stage.setMinWidth(300);
		
		// ColumnConstraints to define how much of the scene each column should take up
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(33);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(66);
		gp.getColumnConstraints().addAll(col1, col2);
		
		Button submitButton = new Button("Submit");

		int i = 0;
		ArrayList<TextField> textfields = new ArrayList<>();
		for (String str : fieldNames) {
			// Mask text field if it's a password
			TextField tf = str.matches("(.*)?[Pp]assword(.*)") ? new PasswordField() : new TextField();

			// Creating each field separately in order to make it easy to get text from them later
			tf.setPromptText("Enter " + str);
			textfields.add(tf);

			// Add labels and label names
			gp.add(new Label(str), 0, i);
			gp.add(tf, 1, i);
			i++;
		}

		// Adding button and centering it under the other rows
		gp.add(submitButton, 0, i++);
		gp.setColumnSpan(submitButton, 2);
		gp.setHalignment(submitButton, HPos.CENTER);

		/**
		 * 	Cases:
		 * 	Successful submit - replace submit button with green checkmark
		 * 						(to prevent resubmission and notify user
		 * 						of success)
		 *	Unsuccessful submit - add error message box and allow user to
		 *						  correct field(s)
		 */
		/*submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				ArrayList<String> submission = new ArrayList<>();

				for (TextField tf : textfields)
					submission.add(tf.getText());

				// Pass user submission to UserController for validation
				if ( UserController.handledAccountCreation(submission) ) {
					// Green checkmark
				}
				else {
					// Submission error handling
					Label error = new Label("Error - Invalid field(s)");
					*//**
					 * add error message below submit button;
					 * number of rows = all fields + submit button + error message
					 *//*
					gp.add(error, 0, getNumFields() + 1 + 1);
					gp.setHalignment(error, HPos.CENTER);
				}
			}
		});*/
	}

	private int longestField() {
		int longest = 0;
		for (String str : fieldNames) {
			if (str.length() > 0)
				longest = str.length();
		}
		return longest;
	}
}
