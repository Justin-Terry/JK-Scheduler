package application;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
	private String name;
	private String type;
	private String location;
	private LocalDateTime start_time;
	private LocalDateTime end_time;
        private int app_id;
	private int created_by;
	private User createdBy;
	
	public Appointment(String name, String type, String location, LocalDateTime start, LocalDateTime end, int userid) {
            this.name = name;
            this.type = type;
            this.location = location;
            this.start_time = start;
            this.end_time = end;
            this.created_by = userid;
	}

	public Appointment(AppointmentSubmissionForm form, int userid) {
		this.name = form.getName();
		this.type = form.getType();
		this.location = form.getLocation();
		this.start_time = form.getStartTime();
		this.end_time = form.getEndTime();
		this.created_by = userid;
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

	public static final String format(LocalDateTime l) {
		return l.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+":00.0";
	}

        /**
         * Parse string to convert to LocalDateTime object
         * @param str - the DateTime string
         *  patterns: 
         *          yyyy-MM-dd HH:mm 
         *          yyyy-MM-dd HH:mm:ss
         *          yyyy-MM-dd HH:mm:ss.f   <---- Timestamp format in the database
         * @return - the DateTime as an object
         */
	public static final LocalDateTime parse(String str) {
            if (str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}"))
                return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            else if (str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) 
                return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            else if (str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d*")) 
                return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0"));
            else {
                return null;
            }
	}

        @Override
        public final String toString() {
            return String.format(
                    "Event title: %s  |  Type: %s  |  Location: %s  |  Start: %s  |  End: %s",
                    getName(), getType(), getLocation(), format(getStart()), format(getEnd())
                    );
        }
	
	public static void main(String[] args) {
	}	
}
