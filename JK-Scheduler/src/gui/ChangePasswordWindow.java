package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
		TextField oldPassField = new TextField();
		oldPassField.setPromptText("Enter current password");
		TextField newPassField = new TextField();
		newPassField.setPromptText("Enter new password");
		TextField confirmPassField = new TextField();
		confirmPassField.setPromptText("Re-enter new password");
		Button submitButton = new Button("Submit");
		
		vBox.getChildren().addAll(currentPassLabel, oldPassField, newPassLabel, newPassField,confirmPassLabel,confirmPassField, submitButton);
		stage.setScene(scene);
		stage.show();
		
	}

}
