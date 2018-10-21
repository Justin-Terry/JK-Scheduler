package application;

import java.util.ArrayList;

public class User {
	private String username,
			password,
			fName,
			lName,
			phone,
			email,
			userid;
	private Address address;

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
		fName = args.get(i++);
		lName = args.get(i++);
		phone = args.get(i++);
		email = args.get(i++);
		address = new Address(
								args.get(i++),	// Street
							  	args.get(i++), 	// City
							  	args.get(i++),	// State
							  	args.get(i++)		// Zip
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
