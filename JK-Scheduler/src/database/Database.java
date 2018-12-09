package database;

import application.Appointment;
import application.EmailNotification;
import application.Main;
import application.User;
import application.convert;
import static java.lang.System.exit;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public final class Database {
	private static Connection connection = null;
	//private static String dbPath = "jdbc:derby://localhost:1527/SchedulerDB;create=true";
    private static String dbPath = "jdbc:derby:SchedulerDB;create=true";
	private HashMap<String, String> creds = new HashMap<String, String>();// <username, password>

	public Database() {
		createUsersTable();
		createAppointmentsTable();
		createNotificationsTable();
		populateCredentials();
		populateNotifications();
	}

	private static final void createUsersTable() {
		try {
			if (connection == null) {
				getConnection();
			}

			String str = "CREATE TABLE Users (\n" + "    userid      INT \n"
					+ "                GENERATED ALWAYS AS IDENTITY \n" + "                NOT NULL ,\n"
					+ "    username    VARCHAR(255) NOT NULL,\n" + "    password    VARCHAR(255) NOT NULL,\n"
					+ "    fname       VARCHAR(255) NOT NULL,\n" + "    lname       VARCHAR(255) NOT NULL,\n"
					+ "    phone       VARCHAR(255) NOT NULL,\n" + "    email       VARCHAR(255) NOT NULL,\n"
					+ "    street      VARCHAR(255) NOT NULL,\n" + "    city        VARCHAR(255) NOT NULL,\n"
					+ "    state       VARCHAR(255) NOT NULL,\n" + "    zipcode     INT NOT NULL,\n" + "\n"
					+ "    CONSTRAINT userPK PRIMARY KEY(userid),\n" + "    CONSTRAINT userCK UNIQUE(email),\n"
					+ "    CONSTRAINT userCK2 UNIQUE(phone)\n" + " )";

			Statement stmt = connection.createStatement();
			stmt.execute(str);

		} catch (SQLException e) {
			if (e.getSQLState().compareTo("X0Y32") == 0) {
				System.out.println("Database.createUsersTable() - Table 'Users' already exists");
			} else {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	// Untested
	private static final void createAppointmentsTable() {
		try {
			if (connection == null)
				getConnection();

			String str =      "CREATE TABLE Appointments (\n" 
                                        + "    app_id      INT GENERATED ALWAYS AS IDENTITY,\n"
					+ "    name        VARCHAR(255) NOT NULL,\n" 
                                        + "    type        VARCHAR(255) NOT NULL,\n"
					+ "    location    VARCHAR(255) NOT NULL,\n" 
                                        + "    start_time  TIMESTAMP NOT NULL,\n"
					+ "    end_time    TIMESTAMP NOT NULL,\n"
                                        + "    notify_time TIMESTAMP NOT NULL,\n"
                                        + "    created_by  INT NOT NULL,\n" + "\n"
					+ "    PRIMARY KEY(app_id),\n" 
                                        + "    FOREIGN KEY(created_by) REFERENCES Users(userid)\n" + ")";

			Statement stmt = connection.createStatement();
			stmt.execute(str);

		} catch (SQLException e) {
			if (e.getSQLState().compareTo("X0Y32") == 0) {
				System.out.println("Database.createAppointmentsTable() - Table 'Appointments' already exists");
			} else {
				System.exit(0);
			}
		}
	}

	private static final void createNotificationsTable() {
		try {
			if (connection == null) {
				getConnection();
			}

			String str = "CREATE TABLE Notifications (" + "not_Id INT GENERATED ALWAYS AS IDENTITY,\n"
					+ "not_Time TIMESTAMP NOT NULL,\n" + "not_To VARCHAR(255) NOT NULL, \n"
					+ "not_Sub VARCHAR(120) NOT NULL, \n" + "not_Mes VARCHAR(500) NOT NULL, \n" + "PRIMARY KEY(not_Id)"
					+ ")";

			Statement stmt = connection.createStatement();
			stmt.execute(str);

		} catch (SQLException e) {
			if (e.getSQLState().compareTo("X0Y32") == 0) {
				System.out.println("Database.createNotificationsTable() - Table 'Notifications' already exists");
			} else {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	public static final void addNotification(EmailNotification noti) {
		try {
			if (connection == null) {
				getConnection();
			}

			PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO Notifications (not_Time, not_To, not_Sub, not_Mes) " + "VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, convert.toTimestampFormat(noti.getTime()));
			stmt.setString(2, noti.getToEmail());
			stmt.setString(3, noti.getSub());
			stmt.setString(4, noti.getMes());
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			noti.setID(rs.getInt(1));
		} catch (SQLException e) {
			System.out.println("addNotification() - " + e.getMessage());
		}
	}

	/**
	 * Obtain a connection to the database.
	 */
	private static final void getConnection() {
		try {
			// Replace with actual database implementation
			String host = dbPath;
			System.out.println("Creating connection to database");
			connection = DriverManager.getConnection(host);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Find the user in the database
	 * 
	 * @param username
	 *            - The username of the user to find
	 * @return - Whether or not the action was successful
	 */
	public static final boolean findUser(String username) {
		try {
			if (connection == null)
				getConnection();

			String query = "SELECT USERNAME " + "FROM Users " + "WHERE username = \'" + username + "\'";

			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery(query);

			result.last();
			return (result.getRow() == 1 ? true : false);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static final boolean findUser(int userid) {
		try {
			if (connection == null)
				getConnection();

			String query = "SELECT userid " + "FROM Users " + "WHERE userID = " + Integer.toString(userid);

			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery(query);

			result.last();
			return (result.getRow() == 1 ? true : false);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public final static User getUser(String username) {
		try {
			if (connection == null) {
				getConnection();
			}
			ArrayList<String> userAL = new ArrayList<String>();
			String query = "SELECT * FROM users WHERE username = \'" + username + "\'";
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			ResultSetMetaData rsmd = result.getMetaData();
			int numOfCols = rsmd.getColumnCount();
			if (result.next()) {
				for (int i = 2; i <= numOfCols; i++) {
					if (i == 4) {
						userAL.add("");
						userAL.add(result.getString(i));
					} else {
						userAL.add(result.getString(i));
					}
				}
			}

			User tempUser = new User(userAL);
			return tempUser;

		} catch (SQLException e) {
			e.getErrorCode();
		}
		return null;
	}

	public final void populateNotifications() {
		try {
			if (connection == null) {
				getConnection();
			}

			String query = "SELECT * FROM Notifications";
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			ResultSetMetaData rsmd = result.getMetaData();
			int numOfCols = rsmd.getColumnCount();

			while (result.next()) {
				int id = result.getInt(1);
				LocalDateTime time = convert.toLocalDateTime(result.getString(2));
				String toEmail = result.getString(3);
				String sub = result.getString(4);
				String mes = result.getString(5);
				if (time.isAfter(LocalDateTime.now())) {
					EmailNotification noti = new EmailNotification(sub, mes, toEmail, time);
					noti.setID(id);
					Main.getNotifications().put(time, noti);
				}else {
					EmailNotification noti = new EmailNotification(sub, mes, toEmail, time);
					noti.setID(id);
					try {
						noti.sendNotification();
						removeNotification(noti.getID());
					}catch(Exception e) {};
					
					
				}
			}
		} catch (SQLException e) {
			e.getErrorCode();
		}
	}

	public final void populateCredentials() {
		try {
			if (connection == null) {
				getConnection();
			}

			String query = "SELECT username,password FROM users";
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
			ResultSetMetaData rsmd = result.getMetaData();
			int numOfCols = rsmd.getColumnCount();

			while (result.next()) {
				String user = result.getString(1);
				String pass = result.getString(2);
				creds.put(user, pass);
			}
		} catch (SQLException e) {
			e.getErrorCode();
		}
	}

	public final String getUserCredentials(String username) {
		return creds.get(username);
	}

	// Unfinished
	/**
	 * Return the User ID of the user
	 * 
	 * @param username
	 *            - The user to search for
	 * @return - The User ID
	 */
	public static final int getUserID(String username) {
		try {
			if (connection == null)
				getConnection();

			assert (findUser(username));

			String query = "SELECT userid " + "FROM Users " + "WHERE username = \'" + username + "\'";

			PreparedStatement stmt = connection.prepareStatement("SELECT userid FROM users WHERE username = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, username);
			ResultSet result = stmt.executeQuery();

			// Need to check for correctness
			result.last();
			if (result.getRow() == 0) {
				System.out.println("Database.getUserID() - No user ID found for this username");
				return -1;
			} else if (result.getRow() > 1) {
				System.out.println("Database.getUserID() - Multiple user IDs found for this username");
				return -1;
			} else
				return Integer.parseInt(result.getString("USERID"));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}
	

	/**
	 * Add a new user to the database. UserID is auto-generated.
	 * 
	 * @param user
	 *            - The user to add
	 */
	public static final void addUser(User user) {
		try {
			if (connection == null)
				getConnection();

			assert (!findUser(user.getUsername()));

			PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO users (username, password, fName, lName, phone, email, street, city, state, zipcode) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword()); /** DO NOT DO THIS **/
			stmt.setString(3, user.getfName());
			stmt.setString(4, user.getlName());
			stmt.setString(5, user.getPhone());
			stmt.setString(6, user.getEmail());
			stmt.setString(7, user.getStreet());
			stmt.setString(8, user.getCity());
			stmt.setString(9, user.getState());
			stmt.setString(10, user.getZip());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Change the username of the user in the database
	 * 
	 * @param user
	 *            - The user to change names
	 * @param newName
	 *            - The new name the user wants to change to
	 */
	public static final void changeUsername(User user, String newName) {
		try {
			if (connection == null)
				getConnection();

			assert (findUser(user.getUsername()));

			String namechange = "UPDATE users SET username = \'" + newName + "\' WHERE username = \'"
					+ user.getUsername() + "\'";

			PreparedStatement stmt = connection.prepareStatement(namechange);
			stmt.executeUpdate();
			user.setUsername(newName);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Change the password of the user in the database
	 * 
	 * @param user
	 *            - The user to change passwords
	 * @param newPass
	 *            - The new password the user wants to change to
	 */
	public static final void changePassword(User user, String newPass) {
		try {
			if (connection == null)
				getConnection();

			assert (findUser(user.getUsername()));

			String passchange = "UPDATE users SET password = \'" + newPass + "\' WHERE username = \'"
					+ user.getUsername() + "\'"; /** DO NOT DO THIS **/

			PreparedStatement stmt = connection.prepareStatement(passchange);
			stmt.executeUpdate();
			user.setPassword(newPass);
		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}
	}

	/**
	 * Checks if new appointment conflicts with an existing appointment's timeslot
	 * 
	 * @param userid
	 *            - The user's ID
	 * @param newStart
	 *            - The new appointment's start
	 * @param newEnd
	 *            - The new appointment's end
	 * @return - Whether or not the user has an appointment in conflict
	 */
	public static final boolean findAppointment(int userid, LocalDateTime newStart, LocalDateTime newEnd) {
            try {
                if (connection == null) getConnection();

                if (!findUser(userid)) return false;
                
                PreparedStatement stmt = 
                    connection.prepareStatement("SELECT COUNT(*) AS rowcount \n" +
                            "FROM Appointments INNER JOIN Users \n" +
                            "ON Appointments.created_by = Users.userid \n" +
                            "WHERE (? >= Appointments.start_time AND ? <= Appointments.end_time) \n" +
                            "OR (? >= Appointments.start_time AND ? <= Appointments.end_time) \n" +
                            "OR (? <= Appointments.start_time AND ? >= Appointments.end_time) \n" +
                            "GROUP BY created_by \n" +
                            "HAVING created_by = ?");
                
                String startTime = convert.toTimestampFormat(newStart),
                        endTime = convert.toTimestampFormat(newEnd);
                
                stmt.setString(1, startTime);
                stmt.setString(2, startTime);
                stmt.setString(3, endTime);
                stmt.setString(4, endTime);
                stmt.setString(5, startTime);
                stmt.setString(6, endTime);
                stmt.setInt(7, userid);
                
                ResultSet result = stmt.executeQuery();
                int count = 0;
                if ( result.next() ) count = result.getInt("rowcount");
                
                if (count > 1)
                   System.out.println("findAppoint() - database contains more than 1 appointment in this timeslot");
                
                result.close();
                return count >= 1;
            } 
            catch (SQLException e) {
                System.out.println("findAppointment() - " + e.getMessage());
                return true;
            }
	}
        
        public static final int countConflicts(int userid, LocalDateTime start, LocalDateTime end) {
            try {
                if (connection == null) getConnection();
                
                if (!findUser(userid)) return -1;
                
                PreparedStatement stmt = 
                    connection.prepareStatement("SELECT COUNT(*) AS rowcount \n" +
                            "FROM Appointments INNER JOIN Users \n" +
                            "ON Appointments.created_by = Users.userid \n" +
                            "WHERE (? >= Appointments.start_time AND ? <= Appointments.end_time) \n" +
                            "OR (? >= Appointments.start_time AND ? <= Appointments.end_time) \n" +
                            "OR (? <= Appointments.start_time AND ? >= Appointments.end_time) \n" +
                            "GROUP BY created_by \n" +
                            "HAVING created_by = ?");
                
                String startTime = convert.toTimestampFormat(start),
                        endTime = convert.toTimestampFormat(end);
                
                stmt.setString(1, startTime);
                stmt.setString(2, startTime);
                stmt.setString(3, endTime);
                stmt.setString(4, endTime);
                stmt.setString(5, startTime);
                stmt.setString(6, endTime);
                stmt.setInt(7, userid);
                
                ResultSet result = stmt.executeQuery();
                int count = 0;
                if (result.next()) {
                    count = result.getInt("rowcount");
                }
                result.close();
                return count;
            } 
            catch (SQLException e) {
                System.out.println("findAppointment() - " + e.getMessage());
                return -1;
            }
        }
        
	public static final int getAppID(int userid, LocalDateTime newStart, LocalDateTime newEnd) {
		int id = -1;
		try {
			if (connection == null)
				getConnection();

			if (!findUser(userid)) {
				System.out.println("Database.getAppID() - User ID not found.");
				return id;
			}

			String startTime = convert.toTimestampFormat(newStart), endTime = convert.toTimestampFormat(newEnd);

			String query = "SELECT app_id FROM Appointments INNER JOIN Users \n"
					+ "ON Appointments.created_by = Users.userid \n" + "WHERE Appointments.start_time = ? \n"
					+ "AND Appointments.end_time = ? \n";

			PreparedStatement stmt = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, startTime);
			stmt.setString(2, endTime);

			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				id = result.getInt("app_id");
				System.out.println("Database.getAppID() - New appointment with ID: " + id + ".");
			} else {
				System.out.println("Database.getAppID() - Appointment for this start & end not found.");
			}
		} catch (SQLException e) {
			System.out.println("Database.getAppID() - " + e.getMessage());
		} finally {
			return id;
		}
	}

	/**
	 * Add a new appointment to the database
	 * 
	 * @param appointment
	 *            - The appointment to add
	 */
	public static final void addAppointment(Appointment appointment) {
		try {
			if (connection == null) {
				getConnection();
			}

			if (!findUser(appointment.getCreator())) {
				System.out.println("Database.addAppointment() - could not find user");
				return;
			}

			PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO Appointments (name, type, location, start_time, end_time, notify_time, created_by) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, appointment.getName());
			stmt.setString(2, appointment.getType());
			stmt.setString(3, appointment.getLocation());
			stmt.setString(4, convert.toTimestampFormat(appointment.getStart()));
			stmt.setString(5, convert.toTimestampFormat(appointment.getEnd()));
                        stmt.setString(6, convert.toTimestampFormat(appointment.getNotificationTime()));
			stmt.setString(7, Integer.toString(appointment.getCreator()));
			stmt.execute();
			addNotification(appointment.createNotification());
		} catch (SQLException e) {
			System.out.println("addAppointment() - " + e.getMessage());
		}
	}

	/**
	 * Change an existing appointment
	 * 
	 * @param app_id
	 *            - The appointment ID in the database
	 * @param appointment
	 *            - The new appointment changes (as an object)
	 */
	public static final void changeAppointment(int app_id, Appointment appointment) {
		try {
			if (connection == null)
				getConnection();

			if (!findUser(appointment.getCreator())) {
				System.out.println("changeAppointment() - could not find user");
				return;
			}

			PreparedStatement stmt = connection.prepareStatement("UPDATE Appointments \n"
					+ "SET name = ?, type = ?, location = ?, start_time = ?, end_time = ?, notify_time = ? \n" + "WHERE app_id = ?");
			stmt.setString(1, appointment.getName());
			stmt.setString(2, appointment.getType());
			stmt.setString(3, appointment.getLocation());
			stmt.setString(4, convert.toTimestampFormat(appointment.getStart()));
			stmt.setString(5, convert.toTimestampFormat(appointment.getEnd()));
                        stmt.setString(6, convert.toTimestampFormat(appointment.getNotificationTime()));
			stmt.setString(7, Integer.toString(app_id));

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("changeAppointment() - " + e.getMessage());
		}
	}

	public static void cancelAppointment(int appointmentID) {
		try {
			if (connection == null)
				getConnection();

			PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM Appointments " + "WHERE app_id = " + appointmentID);

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("cancelAppointment() - " + e.getMessage());
		}
	}

	public static void removeNotification(int notificationId) {
		try {
			if (connection == null)
				getConnection();

			PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM Notifications " + "WHERE not_Id = " + notificationId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("removeNotification() - " + e.getMessage());
		}
	}

	// Untested
	public static final ArrayList<Appointment> retrieveAppointments(int userid) {
		ArrayList<Appointment> appointments = new ArrayList<>();
		int rowCount = 0;
		try {
			if (connection == null)
				getConnection();

			String query = "SELECT * \n" + "FROM Appointments INNER JOIN Users \n" + "ON created_by = userid \n"
					+ "WHERE created_by = " + userid;

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int numCols = rsmd.getColumnCount();

				do {
                                        LocalDateTime start = convert.toLocalDateTime(rs.getString("start_time")),
                                                end = convert.toLocalDateTime(rs.getString("end_time")),
                                                notify = convert.toLocalDateTime(rs.getString("notify_time"));
                                        
                                        int timeDiff = (int)ChronoUnit.MINUTES.between(notify, start);
                                        
					rowCount++;
					Appointment a = new Appointment(rs.getString("name"), rs.getString("type"),
							rs.getString("location"), start, end, timeDiff, userid);
					a.setAppID(rs.getInt("app_id"));
					appointments.add(a);
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println("Datebase.retrieveAppointments() - " + e.getMessage());
		}
		System.out.printf("\nDatabase.retrieveAppointments() - Query returned " + rowCount + " results.\n");
		return appointments;
	}

	// Unfinished
	public static void changeUserInfo(User user, ArrayList<String> args) {
		try {
			if (connection == null)
				getConnection();

			String passchange = String
					.format("UPDATE Users " + "SET fname = %s, lname = %s, " + "phone = %s, email = %s, "
							+ "street = %s, city = %s, state = %s, zipcode = %s " + "WHERE username = ");

			PreparedStatement stmt = connection.prepareStatement(passchange);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		System.out.println("Done");
	}
}
