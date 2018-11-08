package application;

import java.sql.Date;
import java.sql.Time;

abstract class SubmissionForm{}

class AppointmentSubmissionForm extends SubmissionForm {
    private final String name;
    private final String type;
    private final String location;
    private final Date date;
    private final Time startTime;
    private final Time endTime;
//    private User createdBy;

    public AppointmentSubmissionForm(String name, String type, String location, Date date, Time start, Time end) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.date = date;
        this.startTime = start;
        this.endTime = end;
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

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }
}
