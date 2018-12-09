package application;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class AppointmentForm {
    private final String name;
    private final String type;
    private final String location;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final LocalDateTime notificationTime;
//    private User createdBy;

    public AppointmentForm(String name, String type, String location, LocalDateTime start, LocalDateTime end, int minutesBefore) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.startTime = start;
        this.endTime = end;
        this.notificationTime = this.startTime.minusMinutes(minutesBefore);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }
}
