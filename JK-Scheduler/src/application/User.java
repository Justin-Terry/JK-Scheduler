package application;

import java.util.ArrayList;

public class User {
	private String username,
			password,
			fName,
			lName,
			phone,
			userid;
	private Address address;

	/**
	 *  Pass an array of information fields from UserController 'createAccount' method
	 *  to create a new user.
	 * @param args	- username, password, and personal information
	 */
	public User(final ArrayList<String> args) {
		// Enable assertions option must be enabled for assertions to work
		final int NUM_ARGS = 9;
		assert args.size() == NUM_ARGS :
				String.format("User constructor expected %d arguments, received %d\n", NUM_ARGS, args.size());
		username = args.get(0);
		password = args.get(1);
		fName = args.get(2);
		lName = args.get(3);
		phone = args.get(4);

		address = new Address(
								args.get(5),	// Street
							  	args.get(6), 	// City
							  	args.get(7),	// State
							  	args.get(8)		// Zip
							 );

		/**
		 * 	ADD USER ID FUNCTIONALITY
		 */
		if (userid == null) {
			System.out.println("Error: User ID functionality not set.\n");
			return;
		}
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
