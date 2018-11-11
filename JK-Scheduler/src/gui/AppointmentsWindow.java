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
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;


public class AppointmentsWindow {
    private Stage stage;
    private Scene scene;
    private GridPane gp;
    private ArrayList<Appointment> list;

    public AppointmentsWindow(ArrayList<Appointment> appts) {
        this.list = appts;
        for (Appointment a : list) {
            Button b = new Button();
            b.setText("");
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    ModifyAppointmentWindow moo = new ModifyAppointmentWindow(a);
                    stage.close();
                }
            });
        }
    }


    public static void main(String[] args) {
    }
}
