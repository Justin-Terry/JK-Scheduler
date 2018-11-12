package gui;

import application.*;
import application.Appointment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

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
                end = a.getEnd();

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
        SHB.getSelectionModel().select(hours.get(startHour-1));
        SMB.getSelectionModel().select(minutes.get(startMinute-1));
        SPB.getSelectionModel().select(IntStream.range(0,periods.length)
                                                .filter(i -> startPeriod.equals(periods[i]))
                                                .findFirst()
                                                .orElse(0));
        dpStart.setValue(start.toLocalDate());

        // End hour, minute, period, date
        EHB.getSelectionModel().select(hours.get(endHour-1));
        EMB.getSelectionModel().select(minutes.get(endMinute-1));
        EPB.getSelectionModel().select(IntStream.range(0,periods.length)
                                                .filter(i -> endPeriod.equals(periods[i]))
                                                .findFirst()
                                                .orElse(-1));
        dpEnd.setValue(end.toLocalDate());
        
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a){
                AppointmentForm form =
                        new AppointmentForm(
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
    }
}
