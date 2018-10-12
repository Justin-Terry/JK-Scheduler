package application;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
	private String name;
	private String type;
	private String description;
	private Date date;
	private Time time;
	private User createdBy;
	
	public Appointment(String n, String t, String des, Date dat, Time tim, User creator) {
		name = n;
		type = t;
		description = des;
		date = dat;
		time = tim;
		createdBy = creator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	/* Leaving out setCreatedBy as the users
	 * shouldn't be able to modify that.
	 */
	
	
	
}
