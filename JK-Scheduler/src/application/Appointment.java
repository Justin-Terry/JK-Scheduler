package application;

import java.time.LocalDateTime;

import database.Database;
import java.util.Comparator;

public class Appointment implements Comparable<Appointment> {
    private String name;
    private EmailNotification notification;
    private String type;
    private String location;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private LocalDateTime notification_time;
    private int app_id;
    private int created_by;
    private User createdBy;
    
    public Appointment(String name, String type, String location, LocalDateTime start, LocalDateTime end, int minutesBefore, int userid) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.start_time = start;
        this.end_time = end;
        this.created_by = userid;    
        //-- This needs to be set programmatically
        this.notification_time = start_time.minusMinutes(minutesBefore);
    }

    public Appointment(AppointmentForm form, int userid) {
            this.name = form.getName();
            this.type = form.getType();
            this.location = form.getLocation();
            this.start_time = form.getStartTime();
            this.end_time = form.getEndTime();
            this.created_by = userid;
            //-- This needs to be set programmatically
            this.notification_time = form.getNotificationTime();
            
    }

    public void modify(AppointmentForm form) {
            this.name = form.getName();
            this.type = form.getType();
            this.location = form.getLocation();
            this.start_time = form.getStartTime();
            this.end_time = form.getEndTime();
            this.notification_time = form.getNotificationTime();
    }
    
    public EmailNotification createNotification() {
    	String sub = "Email Notification for: " + name;
    	String message = String.join(
        	    System.getProperty("line.separator"),
        	    "<h2><strong>Event Reminder</h2>",
        	    "<p>This email is to remind you of an upcoming event in your schedule.</p>",
        	    "<p>Event Name: " + name + "</p>",
        	    "<p>Event Type: " + type + "</p>",
        	    "<p>Event Location: " + location + "</p>",
        	    "<p>Event Time: " + String.format("%02d", start_time.getHour()) + ":" + String.format("%02d", start_time.getMinute()) + "</p>",
        	    "<p>Event Date: " + start_time.getMonth() + " " + start_time.getDayOfMonth() + ", " + start_time.getYear() + "</p>"
        	);
    	
    	//-- NEED TO REPLACE MY EMAIL ADDRESS WITH USER EMAIL
    	notification = new EmailNotification(sub, message, createdBy.getEmail(), notification_time);
    	return notification;
    }
    
    public EmailNotification getNotification() {
    	return notification;
    }

    public String getName() {
            return name;
    }

    public void setName(final String name) {
            this.name = name;
    }

    public String getType() {
            return type;
    }

    public void setType(final String type) {
            this.type = type;
    }

    public String getLocation() {
            return location;
    }

    public void setLocation(final String location) {
            this.location = location;
    }

    public LocalDateTime getStart() {
            return start_time;
    }

    public void setStart(final LocalDateTime start) {
            this.start_time = start;
    }

    public LocalDateTime getEnd() {
            return end_time;
    }

    public void setEnd(final LocalDateTime end) {
            this.end_time = end;
    }
    
    public LocalDateTime getNotificationTime() {
        return notification_time;
    }

    public int getCreator() {
            return created_by;
    }

    public void setCreator(final int id) {
            this.created_by = id;
    }

    public User getCreatedBy() {
            return createdBy;
    }

    public void setCreatedBy(final User createdBy) {
            this.createdBy = createdBy;
    }

    public final void setAppID(final int id) {
       this.app_id = id;
    }

    public final int getAppID() {
            return this.app_id;
    }

    @Override
    public final String toString() {
            return String.format(
                            "Event title=%s  |  Type=%s  |  Location=%s  |  Start=%s  |  End=%s  |  Notify=%s",
                            getName(), 
                            getType(), 
                            getLocation(), 
                            convert.toTimestampFormat(getStart()), 
                            convert.toTimestampFormat(getEnd()),
                            convert.toTimestampFormat(getNotificationTime()));
    }
    
    @Override
    public int compareTo(Appointment other) {
        return this.getStart().compareTo(other.getStart());
    }

//    public static void main(String[] args) {
//        Appointment app = new Appointment(
//                String.valueOf((char)6),
//                "idk",
//                "anywhere",
//                convert.toLocalDateTime("2018-11-09 04:44:00.0"),
//                                convert.toLocalDateTime("2018-11-09 04:44:00.0"),
//                6
//        );

        //System.out.println(app);
//    }	
}
