package application;

import database.Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;

public class UserController {
	private static User thisUser;

	public UserController() {}

	/**
	 * Creates a 'Schedules' folder in the working directory
	 * and outputs the user's schedule in it.
	 * @param filename - The name of the output file
	 */
	public void exportSchedule(String filename) {
		try{
			String dirName = "./Schedules/";
			File dir = new File(dirName);
			if (!dir.exists())
				dir.mkdir();

			String fn = filename;
			File sched = new File(dirName + fn);
			sched.createNewFile();

			FileWriter fw = new FileWriter(sched);
			BufferedWriter writer = new BufferedWriter(fw);

			writer.write("UID=" + thisUser.getID() + "\n");
			for (Appointment a : thisUser.getAppointments()) {
				writer.write(a.toString() + "\n");
			}
			writer.close();
		}
		catch (IOException e){
			System.out.print("exportSchedule() - ");
			e.printStackTrace();
		}

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
	public final static boolean handledUsernameChange(String oldName, String newName) {


		if ( !Database.findUser(oldName)) {
			// User account doesn't exist error
			return false;
		}
		else if ( Database.findUser(newName) ) {
			// Username is taken error
			return false;
		}
		else {
			Database.changeUsername(Main.getCurrentUser(), newName);
			Main.getCurrentUser().setUsername(newName);
			return true;
		}
	}

	// Unfinished
	public final static void handledPasswordChange(String newPass) {
		User theUser = Main.getCurrentUser();
		Database.changePassword(theUser, newPass);
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

	// Untested
	// Possibly unfinished
	public static final boolean handledAppointmentSubmission(AppointmentSubmissionForm form) {
		// Time conflict with an existing appointment
		if (Database.findAppointment(thisUser.getID(), form.getStartTime(), form.getEndTime())) {
                    System.out.println("handledAppointmentSubmission() - time conflict with existing appt.");    
                    return false;
                }
		else {
			Appointment appt = new Appointment(form, thisUser.getID());
			thisUser.addAppointment(appt);
			return true;
		}
	}


	public static final boolean handledAppointmentCancel(int appointmentID) {
		// User doesn't any appointments
		if (thisUser.countAppointments() == 0) {
                    System.out.println("handledAppointmentCancel() - user has no appts.");
                    return false;
                }
                
                for (Appointment a : thisUser.getAppointments()) {
                    if (a.getAppID() == appointmentID) {
			thisUser.cancelAppointment(appointmentID);
			Database.cancelAppointment(appointmentID);
			return true;
                    }
		}
                System.out.println("handledAppointmentCancel() - cannot find appt. for this user");
                return false;
	}

	public static final boolean handledAppointmentChange(Appointment changeThis) {
                if (thisUser.countAppointments() == 0) {
                    System.out.println("handledAppointmentChange() - user has no appts.");
                    return false;
                }
                for (Appointment a : thisUser.getAppointments()) {
                    int id = changeThis.getAppID();
                    if (a.getAppID() == id) {
			thisUser.cancelAppointment(id);
			Database.cancelAppointment(id);
                        thisUser.addAppointment(changeThis);
                        Database.addAppointment(changeThis);
			return true;
                    }
		}
		return false;
	}

//	public User getUser() {
//		return thisUser;
//	}
//
//	public void setUser(User user) {
//		thisUser = user;
//	}

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
		
		/*String[] validPatterns=  new String[10];
		for (int i = 0; i < validPatterns.length; i++)
			validPatterns[i] = "b";

		assert args.size() == validPatterns.length : "Error - mismatched array size between " +
				"arguments and validation patterns\n";

		for (int i = 0; i < validPatterns.length; i++)
			if ( !args.get(i).matches(validPatterns[i]) ) {
				System.out.println("Is not valid submission");
				return false;
			}
			*/
		System.out.println("Is Valid Submission");
		return true;
	}

	private static final void createNewUser(final ArrayList<String> args) {
		thisUser = new User(args);
		Database.addUser(thisUser);
		thisUser.setID(Database.getUserID(thisUser.getUsername()));
	}

	// Put this in database interface
	public boolean checkForAccount(String username) {
		Database.findUser(username);
		return true;
	}

	// Put this in database interface
	public boolean checkCredentials(String user, String attempted) {
		String actual = Main.getDatabase().getUserCredentials(user);
		if(attempted.compareTo(actual) == 0) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
//		UserController uc = new UserController();
//
//		final int USERID = 7;
//		User me = new User(USERID);
//
//		for (int i = 0; i < 15; i++) {
//			Appointment app = new Appointment(
//					String.valueOf(i),
//					"idk",
//					"anywhere",
//					Appointment.parse("2018-11-09 04:44"),
//					Appointment.parse("2018-11-09 04:44"),
//					USERID
//			);
//			me.addAppointment(app);
//		}
//		uc.setUser(me);
//
//
//		uc.exportSchedule("schedule");
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
