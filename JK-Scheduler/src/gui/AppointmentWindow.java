package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AppointmentWindow {
    protected Stage stage = new Stage();
    protected GridPane gp = new GridPane();
    protected Scene scene = new CustomScene(gp);
    protected Button submitButton = new CustomButton("Submit", scene);
    
    protected final String[]
            labels = {"Name", "Type", "Location", "Start", "End"},
            types = {"Private", "Public"},
            periods = {"AM", "PM"};

    protected final ObservableList<String>
            typeOptions = FXCollections.observableArrayList(types),
            ampmOptions = FXCollections.observableArrayList(periods);


    protected final ObservableList<String>
            hours = FXCollections
                        .observableArrayList(
                                IntStream.rangeClosed(1, 12)
                                        .mapToObj(i -> (i < 10 ? "0" : "") + i)
                                        .collect(Collectors.toList())),

            minutes = FXCollections
                        .observableArrayList(
                                IntStream.rangeClosed(0, 59)
                                        .mapToObj(i -> (i < 10 ? "0" : "") + i)
                                        .collect(Collectors.toList()));

    protected final ComboBox
            typeOptionBox = new ComboBox(typeOptions),

            SHB = new ComboBox(hours), // start hr
            SMB = new ComboBox(minutes), // start min
            SPB = new ComboBox(ampmOptions), // start period

            EHB = new ComboBox(hours), // end hr
            EMB = new ComboBox(minutes), // end min
            EPB = new ComboBox(ampmOptions); // end period
            
//            AlertBox = new ComboBox(minutes);

    protected final TextField
            nameText = new TextField(),
            locationText = new TextField(),
            alertText = new TextField();

    protected final DatePicker
            dpStart = new DatePicker(),
            dpEnd = new DatePicker();

    protected final HBox
            startHolder = new HBox(),
            endHolder = new HBox();

    public AppointmentWindow() {
        startHolder.getChildren().addAll(SHB,SMB, SPB);
        endHolder.getChildren().addAll(EHB,EMB, EPB);

        /**
         * Add labels, fields, and button(s)
         */
        for(int i = 0; i < labels.length; i++)
            gp.add(new Label(labels[i]), 0, labels[i].equals("End") ? i+1 : i);
        
        gp.add(nameText, 1, 0);
        gp.add(typeOptionBox, 1, 1);
        gp.add(locationText, 1, 2);

        gp.add(startHolder, 1, 3);
        gp.add(dpStart, 1, 4);

        gp.add(endHolder, 1, 5);
        gp.add(dpEnd, 1, 6);

        gp.add(new Label("Alert"), 0, 7);
        alertText.toFront();
        gp.add(alertText, 1,7);
        Label after = new Label("                 minutes before");
        after.toBack();
        gp.add(after, 1, 7);
//        AlertBox.setMinWidth(50.0);
        
        gp.add(submitButton, 1, 8);
//        gp.setColumnSpan(submitButton, 2);

        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(20));
        gp.setVgap(10);
        gp.setHgap(10);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.sizeToScene();
//        stage.setMinHeight(60*(labels.length+1));
//        stage.setMinWidth(600);
    }
}
