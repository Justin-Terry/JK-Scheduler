package gui;

import application.*;
import application.Appointment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.IntStream;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ModifyAppointmentWindow extends AppointmentWindow {

    ModifyAppointmentWindow(Appointment appointment){
        super();
        stage.setTitle("Modify Appointment");
        layoutGrid(appointment);
        stage.setScene(scene);
        stage.show();
    }

    void layoutGrid(Appointment a) {
        final LocalDateTime
                start = a.getStart(),
                end = a.getEnd(),
                notify = a.getNotificationTime();

        final String
                name = a.getName(),
                type = a.getType(),
                location = a.getLocation(),
                startPeriod = convert.toPeriod(a.getStart()),
                endPeriod = convert.toPeriod(a.getEnd());

        final int
            startHour = a.getStart().getHour(),
            startMinute = a.getStart().getMinute(),
            endHour = a.getEnd().getHour(),
            endMinute = a.getEnd().getMinute();

        final int app_id = a.getAppID();

        // Event name, type, location
        nameText.setText(name);
        typeOptionBox.getSelectionModel().select(IntStream.range(0,periods.length)
                                            .filter(i -> type.equals(types[i]))
                                            .findFirst()
                                            .orElse(0));
        locationText.setText(location);

        // Start hour, minute, period, date
        SHB.getSelectionModel().select(hours.get(startHour%12-1));
        SMB.getSelectionModel().select(minutes.get(startMinute));
        SPB.getSelectionModel().select(IntStream.range(0,periods.length)
                                                .filter(i -> startPeriod.equals(periods[i]))
                                                .findFirst()
                                                .orElse(0));
        dpStart.setValue(start.toLocalDate());

        // End hour, minute, period, date
        EHB.getSelectionModel().select(hours.get(endHour%12-1));
        EMB.getSelectionModel().select(minutes.get(endMinute));
        EPB.getSelectionModel().select(IntStream.range(0,periods.length)
                                                .filter(i -> endPeriod.equals(periods[i]))
                                                .findFirst()
                                                .orElse(-1));
        dpEnd.setValue(end.toLocalDate());
        
        int alert = (int)ChronoUnit.MINUTES.between(notify, start);
        alertText.setText(Integer.toString(alert));
        
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a){
                String name = nameText.getText(),
                                        type = (String)typeOptionBox.getValue(),
                                        location = locationText.getText();
                                
                LocalDateTime start = dpStart.getValue()
                                            .atTime(
                                                convert.to24Hour(
                                                    Integer.parseInt((String)SHB.getValue()),
                                                    (String)SPB.getValue()),
                                                    Integer.parseInt((String)SMB.getValue()));
                LocalDateTime end = dpEnd.getValue()
                                        .atTime(
                                            convert.to24Hour(
                                                Integer.parseInt((String)EHB.getValue()),
                                                (String)EPB.getValue()),
                                                Integer.parseInt((String)EMB.getValue()));
                int alertBefore = Integer.parseInt(alertText.getText());
                
                
                
                AppointmentForm form = new AppointmentForm(name, type, location, start, end, alertBefore);

                if ( UserController.handledAppointmentChange(app_id, form) ) {
                	Main.getWindowManager().setCalendarView();
                        stage.close();
                }
                else {
                    new Alert(AlertType.ERROR, "Invalid field(s).", ButtonType.OK).showAndWait();
                }
            }
        });
    }
}
