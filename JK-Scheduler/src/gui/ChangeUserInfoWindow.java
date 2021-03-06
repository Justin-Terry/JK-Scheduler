package gui;

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

import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ChangeUserInfoWindow {
    Stage stage;
    GridPane gp;
    CustomScene scene;

    private static final String[] fieldNames = {"First Name", "Last Name",
                                                "Phone Number", "E-Mail" ,
                                                "Street", "City", "State", "Zip Code"};

    public static final int getNumFields() {
        return fieldNames.length;
    };

    public ChangeUserInfoWindow() {
        stage = new Stage();
        stage.setTitle("Change User Info");

        gp = new GridPane();
        gp.setPadding(new Insets(10));
        gp.setVgap(10);
        gp.setHgap(10);
        scene = new CustomScene(gp, 420, 36*(fieldNames.length+2));

        layoutGrid();
        stage.setScene(scene);
        stage.show();
    }

    public void layoutGrid() {
        // Keeps window on top of everything else
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(436);
        stage.setMinHeight(472);
//        stage.setMinHeight(40*(fieldNames.length+1));
//        stage.setMinWidth(300);

        // ColumnConstraints to define how much of the scene each column should take up
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(66);
        gp.getColumnConstraints().addAll(col1, col2);

        CustomButton submitButton = new CustomButton("Submit", scene);

        int i = 0;
        ArrayList<TextField> textfields = new ArrayList<>();
        for (String str : fieldNames) {
            TextField tf = new TextField();
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


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                ArrayList<String> submission = new ArrayList<>();

                for (TextField tf : textfields)
                    submission.add(tf.getText());

                // Pass user submission to UserController for validation
                if ( UserController.handledUserInfoChange(submission) ) {
                    stage.close();
                }
                else {
                    new Alert(Alert.AlertType.ERROR, "Invalid field(s) entered.", ButtonType.OK)
                            .showAndWait();
                }
            }
        });
    }
}
