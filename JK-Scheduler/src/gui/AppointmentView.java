package gui;

import java.time.format.DateTimeFormatter;

import application.Appointment;
import application.Main;
import application.convert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class AppointmentView {

    public AppointmentView(Appointment a) {
        Stage stage = new Stage();
        GridPane gp = new GridPane();
        Scene scene = new Scene(gp);
        scene.getStylesheets().add(getClass().getResource("material-fx-v0_3.css").toExternalForm());
        stage.setScene(scene);
        
        
        VBox box = new VBox();

        box.setPadding(new Insets(20));
        box.setSpacing(10);

        DateTimeFormatter date = DateTimeFormatter.ofPattern("EEEE, MMM d, uuuu"),
                time = DateTimeFormatter.ofPattern("hh:mm a");
        Label titleLabel = new Label("Event Info");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-background-color: rgb(195.0, 195.0, 195.0);");

        Label nameLabel = new Label("Name");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight:700;");
        Label nameInfo = new Label(a.getName());

        Label startLabel = new Label("Start");
        startLabel.setStyle("-fx-font-size: 16px; -fx-font-weight:700;");
        Label startDate = new Label(a.getStart().format(date) + "\n@ " + a.getStart().format(time));

        Label endLabel = new Label("End");
        endLabel.setStyle("-fx-font-size: 16px; -fx-font-weight:700;");
        Label endDate = new Label(a.getEnd().format(date) + "\n@ " + a.getEnd().format(time));

        Label typeLabel = new Label("Type");
        typeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight:700;");
        Label typeInfo = new Label(a.getType());

        Label locationLabel = new Label("Location");
        locationLabel.setStyle("-fx-font-size: 16px; -fx-font-weight:700;");
        Label locationInfo = new Label(a.getLocation());

        Button change = new CustomButton("Edit", scene);
//        change.getStyleClass().add("button-raised");
//        change.setOnMouseEntered(e->{
//            scene.setCursor(Cursor.HAND);
//        });
//        change.setOnMouseExited(e->{
//            scene.setCursor(Cursor.DEFAULT);
//        });
        change.setOnAction(e -> {
            ModifyAppointmentWindow maw = new ModifyAppointmentWindow(a);
            stage.close();
        });

        Button cancel = new CustomButton("Remove", scene);
//        cancel.getStyleClass().add("button-raised");
//        cancel.setOnMouseEntered(e->{
//            scene.setCursor(Cursor.HAND);
//        });
//        cancel.setOnMouseExited(e->{
//            scene.setCursor(Cursor.DEFAULT);
//        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent b) {
                Alert confirm = new Alert(AlertType.CONFIRMATION);
                confirm.setTitle("Delete Appointment");
                confirm.setHeaderText("Delete Appointment?");
                confirm.setContentText("Are you sure you want to delete this appointment?");
                confirm.showAndWait();

                if (confirm.getResult().equals(ButtonType.OK)) {
                    Main.getCurrentUser().cancelAppointment(a.getAppID());
                    System.out.println("Tried to cancel apptID " + a.getAppID());
                    Main.getWindowManager().setCalendarView();
                    stage.close();
                } else {
                    stage.close();
                }

            }
        });
        
        gp.add(titleLabel, 0,0,2,1);
        
        gp.add(nameLabel, 0,1);
        gp.add(nameInfo, 1,1);
                
        gp.add(startLabel, 0,2);
        gp.add(startDate, 1,2,2,1);
        
        gp.add(endLabel, 0,4);
        gp.add(endDate, 1,4,2,1);
        
        gp.add(typeLabel, 0,6);
        gp.add(typeInfo, 1,6);
        
        gp.add(locationLabel, 0,7);
        gp.add(locationInfo, 1,7);
        
        gp.add(change, 0,9);
        gp.add(cancel, 1,9);
        
        gp.setMinSize(1, 1);
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setVgap(15);
        gp.setHgap(15);
        gp.setAlignment(Pos.CENTER);
//        gp.setStyle("-fx-background-color: #FFFFFF;");
//        gp.setGridLinesVisible(true);
        titleLabel.requestFocus();
        stage.show();

    }

}
