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
	private LocalDateTime start;
	private LocalDateTime end;
	private int id;
	private User createdBy;
	
	public Appointment(String n, String t, LocalDateTime start, LocalDateTime end, User creator, String location) {
		name = n;
		type = t;
		this.start = start;
		this.end = end;
		createdBy = creator;
	}

	public Appointment(AppointmentSubmissionForm form, User creator) {
		this.name = form.getName();
		this.type = form.getType();
		this.location = form.getLocation();
		this.start = form.getStartTime();
		this.end = form.getEndTime();
		this.createdBy = creator;
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
		return start;
	}

	public void setStart(final LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(final LocalDateTime end) {
		this.end = end;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final User createdBy) {
		this.createdBy = createdBy;
	}

	public static final String format(LocalDateTime l) {
		return l.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}


	/* Leaving out setCreatedBy as the users
	 * shouldn't be able to modify that.
	 */
	
	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();

//		System.out.println("Before : " + now);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String formatDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		System.out.println("After : " + formatDateTime);
		return;
	}
	
}
