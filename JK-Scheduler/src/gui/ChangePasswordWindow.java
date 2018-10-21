package gui;

import application.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangePasswordWindow {
	private Stage stage;
	private Scene scene;
	private VBox vBox = new VBox();

	public ChangePasswordWindow() {
		stage = new Stage();
		// Keep stage on top
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Change Username");
		scene = new Scene(vBox, 300,225);
		vBox.setAlignment(Pos.CENTER);
		// Set Spacing between elements in the box
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10));
		
		Label currentPassLabel = new Label("Current Password");
		Label newPassLabel = new Label("New Password");
		Label confirmPassLabel = new Label("Confirm New Password");

		TextField oldPassField = new PasswordField();
		oldPassField.setPromptText("Enter current password");
		TextField newPassField = new PasswordField();
		newPassField.setPromptText("Enter new password");
		TextField confirmPassField = new PasswordField();
		confirmPassField.setPromptText("Re-enter new password");

		Button submitButton = new Button("Submit");
		
		vBox.getChildren().addAll(currentPassLabel, oldPassField, newPassLabel, newPassField,confirmPassLabel,confirmPassField, submitButton);
		stage.setScene(scene);
		stage.show();

		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				// Pass user submission to UserController for validation
				if ( UserController.handledPasswordChange( newPassField.getText() ) ) {
					// Green checkmark
				}
				else {
					/**
					 *	To-do: Create error messages in UserController
					 *		   and pass them in
					 */
					String msg = "";
					Label errorLabel = new Label(msg);
					vBox.getChildren().add(errorLabel);
				}
			}
		});
	}

}
