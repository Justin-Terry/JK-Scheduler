package application;

import database.Database;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UserController {

    private static User thisUser;

    public UserController() {
    }

    /**
     * Creates a 'Schedules' folder in the working directory and outputs the
     * user's schedule in it.
     *
     * @param filename - The name of the output file
     */
    public static void exportSchedule(File dir, String filename) {
        /**
         * Code to test writing a schedule using the export menu option
         */
//		thisUser = new User("jim", 6);
//		thisUser.setID(5);
//		thisUser.addAppointment(new Appointment(String.valueOf((char)6),
//				"idk",
//				"anywhere",
//				convert.toLocalDateTime("2018-11-09 04:44:00.0"),
//				convert.toLocalDateTime("2018-11-09 04:44:00.0"),
//				6));

        File schedule = new File(dir, filename);
        try {
            if (thisUser.getAppointments().size() == 0) {
                System.out.println("UserController.exportScheduler() - user has no appointments");
            }

            schedule.createNewFile();

            FileWriter fw = new FileWriter(schedule);
            BufferedWriter writer = new BufferedWriter(fw);

            /**
             * Newline included at start of text file as a "hack fix,"
             * BufferedReader/Scanner skips first line in file when reading.
             */
            writer.write("UID=" + thisUser.getID() + "\n");

            for (Appointment a : thisUser.getAppointments()) {
                writer.write(a.toString() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.print("UserController.exportSchedule() - ");
            e.printStackTrace();
        } finally {
        }
    }

    public static void importSchedule(File filename) throws FileNotFoundException, IOException {
        Scanner reader = null;
        try {
            reader = new Scanner(filename);
            String ID_FIELD = reader.nextLine();
            if (!ID_FIELD.matches("UID=\\d+")) {
                System.out.println("UserController.importSchedule() - File content format is incorrect (Expected \"UID=1234\")");
                return;
            } else {
//                int created_by = Integer.parseInt(ID_FIELD.substring(4));
                
                while (reader.hasNextLine()) {
                    String eventLine = reader.nextLine();
//                    String pattern = "Event title=(.*)? \\| Type=(Private|Public) \\| Location=(.*)? \\| Start=\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d \\| End=\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d";
                    String pattern = "Event title=.+?  [|]  Type=(Private|Public)  [|]  Location=.+?  [|]  Start=\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}[.]\\d  [|]  End=\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}[.]\\d";
                    if (!eventLine.matches(pattern)) {
                        System.out.println("UserController.importSchedule() - File format is incorrect");
                        return;
                    }

                    String[] args = eventLine.split("  [|]  ");

                    if (args.length != 5) {
                        System.out.println("UserController.importSchedule() - Mismatched number of arguments");
                        return;
                    }

                    
                    
                    String s = args[3].substring(6),
                            e = args[4].substring(4);
                    LocalDateTime start = convert.toLocalDateTime(s),
                                end = convert.toLocalDateTime(e);
                    
                    AppointmentForm addThis = new AppointmentForm(args[0].substring(12), args[1].substring(5), args[2].substring(9), start, end);

                    if (Database.findAppointment(thisUser.getID(), start, end)) {
                        System.out.println("UserController.importSchedule() - Appointment time conflict");
                    } 
                    else {
                        handledAppointmentCreation(addThis);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("UserController.importSchedule() - probably a problem with the Appointment ctor");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.print("UserController.importSchedule() - ");
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static final boolean handledLogin(String username, String password) {
        if (checkCredentials(username, password)) {
            int id = Database.getUserID(username);
            User user = Database.getUser(username);
            user.setID(id); // hack fix
            Main.setCurrentUser(user);
            user.populateAppointments(Database.retrieveAppointments(user.getID()));
            thisUser = user;
            return true;
        } else {
            System.out.println("UserController.handledLogin() - Invalid username or password.");
            return false;
        }
    }

    public static final boolean handledAccountCreation(final ArrayList<String> args) {
        if (isValidSubmission(args)) {
            createNewUser(args);
            return true;
        } else {
            return false;
        }
    }

    public final static boolean handledUsernameChange(String oldName, String newName) {

        if (!Database.findUser(oldName)) {
            System.out.println("UserController.handledUsernameChange() - Old username could not be found.");
            // User account doesn't exist error
            return false;
        } else if (Database.findUser(newName)) {
            System.out.println("UserController.handledUsernameChange() - Username is already taken.");
            // Username is taken error
            return false;
        } else {
            Database.changeUsername(Main.getCurrentUser(), newName);
            Main.getCurrentUser().setUsername(newName);
            System.out.println("UserController.handledUsernameChange() - Username changed to " + newName);
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
            System.out.println("UserController.handledUserInfoChange() - Invalid field(s) entered.");
            // Invalid fields error
            return false;
        } else if (!Database.findUser(thisUser.getUsername())) {
            // User account doesn't exist error
            System.out.println("UserController.handledUserInfoChange() - Cannot find user for username: " + thisUser.getUsername() + ".");
            return false;
        } else {
//			Database.changeUserInfo(args);
            return true;
        }
    }

    // Untested
    public static final boolean handledAppointmentCreation(AppointmentForm form) {
        LocalDateTime start = form.getStartTime(),
                end = form.getEndTime();

        if (!Database.findUser(thisUser.getID())) {
            System.out.println("UserController.handledAppointmentSubmission() - Could not find user ID: [" + thisUser.getID() + "]");
            return false;
        } // Time conflict with an existing appointment
        else if (Database.findAppointment(thisUser.getID(), start, end)) {
            System.out.println("UserController.handledAppointmentSubmission() - Time conflict with existing appt.");
            return false;
        } // End date/time before start date/time
        else if (end.isBefore(start) || end.isEqual(start)) {
            System.out.println("UserController.handledAppointmentSubmission() - Start date & time must be before end date & time.");
            return false;
        } else {
            Appointment appt = new Appointment(form, thisUser.getID());
            Database.addAppointment(appt);
            int newID = Database.getAppID(thisUser.getID(), start, end);
            appt.setAppID(newID);
            thisUser.addAppointment(appt);
            return true;
        }
    }

    public static final boolean handledAppointmentCancel(int appointmentID) {
        // User doesn't any appointments
        if (thisUser.countAppointments() == 0) {
            System.out.println("UserController.handledAppointmentCancel() - User has no appts.");
            return false;
        }

        for (Appointment a : thisUser.getAppointments()) {
            if (a.getAppID() == appointmentID) {
                thisUser.cancelAppointment(appointmentID);
                Database.cancelAppointment(appointmentID);
                return true;
            }
        }

        System.out.println("UserController.handledAppointmentCancel() - cannot find appt. for this user");
        return false;
    }

    public static final boolean handledAppointmentChange(int app_id, AppointmentForm changeThis) {
        LocalDateTime start = changeThis.getStartTime(),
                end = changeThis.getEndTime();
        
        if (thisUser.countAppointments() == 0) {
            System.out.println("UserController.handledAppointmentChange() - user has no appts.");
            return false;
        }
        if (start.isEqual(end) || end.isBefore(start)) {
            System.out.println("UserController.handledAppointmentChange() - End time before start time");
            return false;
        }
        /**
         * Find ANY appointment within the desired timeslot
         */
        if (Database.findAppointment(thisUser.getID(), start, end)) {
            // Find an exact appointment (the one to be modified)
            int conflicts = Database.countConflicts(thisUser.getID(), start, end);
            /**
             * BUG: If the new start and end conflict with an entirely different appointment,
             * the database table WILL have conflicting appointments. No checking is done.
             */
            if (conflicts == 1) {
                for (Appointment a : thisUser.getAppointments()) {
                    if (a.getAppID() == app_id) {
                        a.modify(changeThis);
                        Database.changeAppointment(app_id, a);
                        return true;
                    }
                }
                System.out.println("UserController.handledAppointmentChange() - Couldn't find appointment ID.");
                return false;
            }
            else {
                System.out.println("UserController.handledAppointmentChange() - " + conflicts + " time conflicts with existing appointment");
                return false;
            }
        }
        return false;
    }

    public static void setUser(User user) {
        thisUser = user;
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
    public static boolean checkCredentials(String user, String attempted) {
        String actual = Main.getDatabase().getUserCredentials(user);

        return attempted.equals(actual);

//		if(attempted.compareTo(actual) == 0) {
//			return true;
//		}
//		return false;
    }

    // Input validation patterns
    private static final String USER = "[a-zA-Z]\\w{12}",
            PASS = "\\w{7,20}",
            FNAME = ".+",
            LNAME = ".+",
            PHONE = "[0-9]{10}",
            EMAIL = "\\w+@\\w+[.]\\w{3}",
            STREET = ".+",
            CITY = ".+",
            STATE = "[a-zA-Z][a-zA-Z ]{3,}",
            ZIP = "\\d{5}(-\\d{4})?";
}