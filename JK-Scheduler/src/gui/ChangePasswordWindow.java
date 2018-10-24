package gui;

import application.Main;
import application.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
				String newPass = newPassField.getText();
				
				if((newPass.compareTo(confirmPassField.getText()) == 0) && (oldPassField.getText().compareTo(Main.getCurrentUser().getPassword()) == 0)) {
					UserController.handledPasswordChange(newPass);
					if(newPass.compareTo(Main.getCurrentUser().getPassword()) == 0) {
						Alert successful = new Alert(AlertType.CONFIRMATION, "Successfully updated password", ButtonType.OK);
						successful.showAndWait();
						if(successful.getResult() == ButtonType.OK) {
							stage.close();
						}
	
					}else {
						Alert err = new Alert(AlertType.ERROR, "Error changing password", ButtonType.OK);
						err.showAndWait();
					}
				}
			}
		});
	}

}
