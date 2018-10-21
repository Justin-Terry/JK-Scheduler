package application;

import database.Database;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserController {
	private static User thisUser;

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
		if (!arg.matches(USER)) {
			// Invalid username error
			return false;
		}
		else if ( !Database.findUser(thisUser.getUsername()) ) {
			// User account doesn't exist error
			return false;
		}
		else if ( Database.findUser(arg) ) {
			// Username is taken error
			return false;
		}
		else {
			Database.changeUsername(thisUser, arg);
			thisUser.setUsername(arg);
			return true;
		}
	}

	// Unfinished
	public final static boolean handledPasswordChange(final String arg) {
		/**
		 *	To-do: produce error messages in password window
		 *		   for each failure case
 		 */
		if (!arg.matches(PASS)) {
			// Invalid password error
			return false;
		}
		else if (!Database.findUser( thisUser.getUsername() )) {
			// User account doesn't exist error
			return false;
		}
		else if ( arg == thisUser.getPassword() ) { /** DO NOT DO THIS **/
			// Same password error
			return false;
		}
		else {
			Database.changePassword(thisUser, arg);
			thisUser.setPassword(arg); /** DON'T DO THIS **/
			return true;
		}
	}

	public final static boolean handledUserInfoChange(final ArrayList<String> args) {
		if (!(args.get(0).matches(FNAME) && args.get(1).matches(LNAME)
			&& args.get(2).matches(PHONE) && args.get(3).matches(EMAIL)
			&& args.get(4).matches(STREET) && args.get(5).matches(CITY)
			&& args.get(6).matches(STATE) && args.get(7).matches(ZIP))) {
			// Invalid fields error
			return false;
		}
		else if ( !Database.findUser( thisUser.getUsername() )) {
			// User account doesn't exist error
			return false;
		}
		else {
//			Database.changeUserInfo(args);
			return true;
		}
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
		thisUser = new User(args);
		Database.addUser(thisUser);
		thisUser.setID(Database.getUserID(thisUser.getUsername()));
	}

	// Put this in database interface
	public boolean checkForAccount(User u) {
		// Check database for account
		return true;
	}

	// Put this in database interface
	public boolean checkCredentials(User u) {
		// Check username and password match
		return true;
	}

	public static void main(String[] args) {
	}

	// Input validation patterns
	private static final String
	USER = "[a-zA-Z]\\w{12}",
	PASS = "\\w{7,20}",
	FNAME = ".+",
	LNAME = ".+",
	PHONE = "(\\d{10})",
	EMAIL = "\\w+@\\w\\.\\w{3}",
	STREET = "\\d{1,5}\\s\\w.\\s(\\b\\w*\\b\\s){1,2}\\w*\\.",
	CITY = "\\w+",
	STATE = "[a-zA-Z][a-zA-Z ]{3,}",
	ZIP = "\\d{5}(-\\d{4})?";
}
