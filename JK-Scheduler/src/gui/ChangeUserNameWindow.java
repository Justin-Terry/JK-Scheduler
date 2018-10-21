package gui;

import application.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ChangeUserNameWindow {
	private Stage stage;
	private Scene scene;
	private VBox vBox = new VBox();
	
	public ChangeUserNameWindow() {
		stage = new Stage();
		// Keep stage on top
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Change Username");
		scene = new Scene(vBox, 300,200);
		vBox.setAlignment(Pos.CENTER);
		// Set Spacing between elements in the box
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10));
		
		Label oldNameLabel = new Label("Current Username");
		Label newNameLabel = new Label("New Username");
		Label oldNameField = new Label("GET FROM DB");
		TextField newNameField = new TextField();
		newNameField.setPromptText("Enter new username");
		Button submitButton = new Button("Submit");
		
		vBox.getChildren().addAll(oldNameLabel, oldNameField, newNameLabel, newNameField, submitButton);
		stage.setScene(scene);
		stage.show();

		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				// Pass user submission to UserController for validation
				if ( UserController.handledUsernameChange( newNameField.getText() ) ) {
					// Green checkmark
				}
				else {
					/**
					 *	To-do: Create error messages in UserController
					 *		   and pass them in
					 */
					Label error = new Label("Error - Invalid field(s)");
					vBox.getChildren().add(error);
				}
			}
		});
	}
	
}
