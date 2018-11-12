package application;

import java.util.ArrayList;

public class User {
	private String username, password,
					fName, lName, phone,
					email;
	private int userid;
	private ArrayList<Appointment> appointments = new ArrayList<Appointment>();
	private Address address;

	public User(final int userid) { this.userid = userid; }
        public User(final String username, final int userid) { 
            this.username = username;
            this.userid = userid; 
        }

	/**
	 *  Pass an array of information fields from UserController 'createAccount' method
	 *  to create a new user.
	 * @param args	- username, password, and personal information
	 */
	public User(final ArrayList<String> args) {
		// Enable assertions option must be enabled for assertions to work
		final int NUM_ARGS = 10;
		assert args.size() == NUM_ARGS : String.format(
														"User constructor expected %d arguments, received %d\n",
														NUM_ARGS,
														args.size()
													  );

		int i = 0;
		username = args.get(i++);
		password = args.get(i++);
		i++;
		fName = args.get(i++);
		lName = args.get(i++);
		phone = args.get(i++);
		email = args.get(i++);
		address = new Address(
								args.get(i++),	// Street
							  	args.get(i++), 	// City
							  	args.get(i++),	// State
							  	args.get(i++)   // Zip
							 );

		/**
		 * 	ADD USER ID FUNCTIONALITY
		 */
//		if (userid == null) {
//			System.out.println("Error: User ID functionality not set.\n");
//			return;
//		}
	}

	public void addAppointment(Appointment appointment) {
		if (appointment != null) appointments.add(appointment);
	}

	public void cancelAppointment(int appointmentID) {
		for (Appointment a : appointments) {
			if (appointmentID == a.getCreator()) {
				appointments.remove(a);
				return;
			}
		}
	}

//	public void changeAppointment(AppointmentForm form) {
//		this.
//	}
        
	public int countAppointments() {
return this.appointments.size();
	}

	public ArrayList<Appointment> getAppointments() {
		return this.appointments;
        }
        
	public void populateAppointments(ArrayList<Appointment> list) {
		this.appointments = list;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(final String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(final String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(final String lName) {
		this.lName = lName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}
	public String getEmail() { return email; }
	public void setEmail(final String email) { this.email = email; }
	public void setID(final int userid) { this.userid = userid; }
	public int getID() { return userid;	}

	public String getStreet() {
		return this.address.getStreet();
	}
	public String getCity() {
		return this.address.getCity();
	}
	public String getState() { return this.address.getState(); }
	public String getZip() {
		return this.address.getZip();
	}
}
