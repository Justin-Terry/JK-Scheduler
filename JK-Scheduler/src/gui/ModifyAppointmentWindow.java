package gui;

import application.*;
import application.Appointment;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ModifyAppointmentWindow {
    private Stage stage;
    private Scene scene;
    private Button submitButton = new Button("Submit");
    private GridPane gp;
    private String[] labels = {"Name", "Type", "Location", "Start", "End"},
                        types = {"Private", "Public"},
                        periods = {"AM", "PM"};
    private ObservableList<String>
            typeOptions = FXCollections.observableArrayList(types),
            ampmOptions = FXCollections.observableArrayList(periods);


    private ObservableList<String>
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

    private final ComboBox
            typeOptionBox = new ComboBox(typeOptions),

            SHB = new ComboBox(hours), // start hr
            SMB = new ComboBox(minutes), // start min
            SPB = new ComboBox(ampmOptions), // start period

            EHB = new ComboBox(hours), // end hr
            EMB = new ComboBox(minutes), // end min
            EPB = new ComboBox(ampmOptions); // end period

    private static TextField
            nameText = new TextField(),
            locationText = new TextField();

    private AppointmentForm form = null;


    ModifyAppointmentWindow(Appointment appointment){
        stage = new Stage();
        stage.setTitle("Modify Appointment");
        gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(20));
        gp.setVgap(10);
        gp.setHgap(10);
        scene = new Scene(gp);
        scene.getStylesheets()
            .add(
                ModifyAppointmentWindow
                    .class
                    .getResource("gui.css")
                    .toExternalForm());

        //-- SET COMBO BOXES TO THE APPOINTMENT'S VALUES!

        layoutGrid(appointment);
        stage.setScene(scene);
        stage.show();
    }

    void layoutGrid(Appointment a) {
        LocalDateTime start = a.getStart(),
                        end = a.getEnd();

        String name = a.getName(),
                type = a.getType(),
                location = a.getLocation(),
                startPeriod = convert.toPeriod(a.getStart()),
                endPeriod = convert.toPeriod(a.getEnd());

        int startHour = a.getStart().getHour(),
            startMinute = a.getStart().getMinute(),
            endHour = a.getEnd().getHour(),
            endMinute = a.getEnd().getMinute();

        int app_id = a.getAppID();

        nameText.setPromptText(name);
        typeOptionBox.setPromptText(type);
        typeOptionBox.setPrefWidth(90);
        typeOptionBox.getStyleClass().add("center-aligned");
        locationText.setPromptText(location);

        /**
         * Start time & date
         */
        SHB.setPromptText(Integer.toString(startHour));
        SHB.setPrefWidth(60);
        SHB.getStyleClass().add("center-aligned");
        SHB.getStyleClass().add("time-width");
        SHB.getSelectionModel().select(hours.get(startHour-1));

        SMB.setPromptText(Integer.toString(startMinute));
        SMB.setPrefWidth(70);
        SMB.getStyleClass().add("center-aligned");
        SMB.getSelectionModel().select(minutes.get(startMinute-1));

        SPB.setPromptText(startPeriod);
        SPB.setPrefWidth(80);
        SPB.getStyleClass().add("center-aligned");
        SPB.getSelectionModel().select(IntStream.range(0,periods.length)
                                                .filter(i -> startPeriod.equals(periods[i]))
                                                .findFirst()
                                                .orElse(-1));
        HBox startHolder = new HBox();
        startHolder.getChildren().addAll(SHB, new Label(" : "), SMB, SPB);

        DatePicker dpStart = new DatePicker();
        dpStart.setValue(start.toLocalDate());


        /**
         * End time & date
         */
        EHB.setPromptText(Integer.toString(endHour));
        EHB.getSelectionModel().select(hours.get(endHour-1));

        EMB.setPromptText(Integer.toString(endMinute));
        EMB.getSelectionModel().select(minutes.get(endMinute-1));

        EPB.setPromptText(endPeriod);
        EPB.getSelectionModel().select(IntStream.range(0,periods.length)
                                                .filter(i -> endPeriod.equals(periods[i]))
                                                .findFirst()
                                                .orElse(-1));

        HBox endHolder = new HBox();
        endHolder.getChildren().addAll(EHB, new Label(" : "), EMB, EPB);

        DatePicker dpEnd = new DatePicker();
        dpEnd.setValue(end.toLocalDate());


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

        gp.add(submitButton, 0, 7);
        gp.setColumnSpan(submitButton, 2);

        
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a){

                //-- Submit the modification
                form = new AppointmentForm(
                                nameText.getText(),
                                (String)typeOptionBox.getValue(),
                                locationText.getText(),
                                dpStart .getValue()
                                        .atTime(
                                                convert.to24Hour(
                                                    Integer.parseInt((String)SHB.getValue()),
                                                    (String)SPB.getValue()),
                                                Integer.parseInt((String)SMB.getValue())),
                                dpEnd   .getValue()
                                        .atTime(
                                                convert.to24Hour(
                                                    Integer.parseInt((String)EHB.getValue()),
                                                    (String)EPB.getValue()),
                                                Integer.parseInt((String)EMB.getValue())));

                if ( UserController.handledAppointmentChange(app_id, form) )
                    stage.close();
            }
        });

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinHeight(60*(labels.length+1));
        stage.setMinWidth(600);
    }


    public static void main(String[] args) {
        Application.launch(args);
    }

    protected AppointmentForm getForm() {
        return this.form;
    }
}
