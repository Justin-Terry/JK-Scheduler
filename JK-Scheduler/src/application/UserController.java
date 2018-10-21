package application;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController {
	public UserController() {
	}

	public static final boolean handledAccountCreation(final ArrayList<String> args) {
		if (isValidSubmission(args)) {
			createNewUser(args);
			return true;
		}
		else
			return false;
	}

	// Unfinished
	public final static boolean handledUsernameChange(final String arg) {
		/**
		 *	To-do: produce error messages in password window
		 *		   for each failure case
		 */

		if (!arg.matches(FieldValidator.USER)) {
			// Invalid username error
			return false;
		}
		else if ( !DatabaseInterface.findUser(user) ) {
			// User account doesn't exist error
			return false;
		}
		else if ( UsernameIsTaken() ) {
			// Username is taken error
			return false;
		}
		else {
			changeUsername(user, arg);
			return true;
		}
	}

	// Unfinished
	public final static boolean handledPasswordChange(final String arg) {
		/**
		 *	To-do: produce error messages in password window
		 *		   for each failure case
 		 */

		if (!arg.matches(FieldValidator.PASS)) {
			// Invalid password error
			return false;
		}
		else if ( !DatabaseInterface.findUser(user) ) {
			// User account doesn't exist error
			return false;
		}
		else if ( PasswordIsSame() ) {
			// Same password error
			return false;
		}
		else {
			changePassword(user, arg)
			return true;
		}
	}

	// Unfinished
	// This should be a database interface method
	private static final void changeUsername(User user, String name) {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		String username = "",
			   password = "",
		connection = DriverManager.getConnection(
													"jdbc:derby://localhost:1527/test database",
													user,
													password
												);

		String query =
		"UPDATE users" +
		"SET user.username = " + name +
		"WHERE user.username = " + user.getUsername();

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.executeUpdate();
	}

	// Unfinished
	private static final void changePassword(User user, String password) {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		String username = "",
			   password = "",
			   connection = DriverManager.getConnection(
															"jdbc:derby://localhost:1527/test database",
															user,
															password
														);

		String query =
				"UPDATE users" +
				"SET user.password = " + password +
				"WHERE user.username = " + user.getUsername();

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.executeUpdate();
	}

	// Unfinished
	private static final boolean isValidSubmission(final ArrayList<String> args) {

//		String[] validPatterns = {
//									"[a-zA-Z]\\w{12}", // user
//									"\\w{7,20}",	// pass
//									".+",	// first name
//									".+", // last name
//									"(\\d{10})",	// phone
//									"\\w+@\\w\\.\\w{3}", // email
//									"\\d{1,5}\\s\\w.\\s(\\b\\w*\\b\\s){1,2}\\w*\\.", // street
//									"\\w+", // city
//									"[a-zA-Z][a-zA-Z ]{3,}", //state
//									"\\d{5}(-\\d{4})?", // zip
//								 };
		/**
		 * Test
		 */
		String[] validPatterns=  new String[10];
		for (int i = 0; i < validPatterns.length; i++)
			validPatterns[i] = "b";

		assert args.size() == validPatterns.length : "Error - mismatched array size between " +
				"arguments and validation patterns\n";

		for (int i = 0; i < validPatterns.length; i++)
			if ( !args.get(i).matches(validPatterns[i]) )
				return false;

		return true;
	}

	private static final void createNewUser(final ArrayList<String> args) {
		User newAccount = new User(args);

		/**
		 * 	Add the user to the database
		 */
		// DatabaseInterface.addUser(newAccount);
		// checkForAccount(newAccount);
	}

	public boolean checkForAccount(User u) {
		// Check database for account
		return true;
	}
	
	public boolean checkCredentials() {
		// Check username and password match
		return true;
	}

	public static void main(String[] args) {

	}
}
