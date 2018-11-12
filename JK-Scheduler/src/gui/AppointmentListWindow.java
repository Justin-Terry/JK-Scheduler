package gui;

import application.*;

import application.Appointment;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;


public class AppointmentListWindow {
    private GridPane gp = new GridPane();
    private Scene scene = new Scene(gp);
    private Stage stage = new Stage();

    private ArrayList<Appointment> list = new ArrayList<>();

    public AppointmentListWindow(ArrayList<Appointment> appts) {
        this.list = appts;

        for (Appointment a : list) {
            Button b = new Button();
            b.setText(a.getName());
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    ModifyAppointmentWindow moo = new ModifyAppointmentWindow(a);
                    stage.close();
                }
            });
        }
    }

    /**
     * Ctor for standalone window testing
     */
//    public AppointmentListWindow() {
//        /**
//         * Remove this test code
//         */
//        for (int i = 0; i < 10; i++) {
//            Appointment app = new Appointment(
//                    "Yup",
//                    "idk",
//                    "anywhere",
//                    convert.toLocalDateTime("2018-11-09 04:44:00.0"),
//                    convert.toLocalDateTime("2018-11-09 04:44:00.0"),
//                    6
//            );
//            list.add(app);
//        }
//
//        int i = 0;
//        for (Appointment a : list) {
//            Button b = new Button();
//            b.setText(a.getName());
//            b.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    ModifyAppointmentWindow moo = new ModifyAppointmentWindow(a);
//                    stage.close();
//                }
//            });
//            gp.add(b, 0, i++);
//        }
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setMaximized(true);
//        stage.setScene(scene);
//        stage.show();
//    }
}
