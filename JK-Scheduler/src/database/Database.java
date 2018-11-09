package database;

import application.Appointment;
import application.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class Database {
    private static Connection connection = null;
//    private static String dbPath = "jdbc:derby:SchedulerDB;create=true";
    private static String dbPath = "jdbc:derby://localhost:1527/JKS Test DB";
    private HashMap<String, String> creds = new HashMap<String, String>();//<username, password>

    public Database() {
//        createUsersTable();
//        createAppointmentsTable();
        populateCredentials();
    }
    
    private static final void createUsersTable() {
        try {
            if(connection == null) {
                getConnection();
            }

            String str = 
                    "CREATE TABLE Users (\n" +
                    "    userid      INT \n" +
                    "                GENERATED ALWAYS AS IDENTITY \n" +
                    "                NOT NULL ,\n" +
                    "    username    VARCHAR(255) NOT NULL,\n" +
                    "    password    VARCHAR(255) NOT NULL,\n" +
                    "    fname       VARCHAR(255) NOT NULL,\n" +
                    "    lname       VARCHAR(255) NOT NULL,\n" +
                    "    phone       VARCHAR(255) NOT NULL,\n" +
                    "    email       VARCHAR(255) NOT NULL,\n" +
                    "    street      VARCHAR(255) NOT NULL,\n" +
                    "    city        VARCHAR(255) NOT NULL,\n" +
                    "    state       VARCHAR(255) NOT NULL,\n" +
                    "    zipcode     INT NOT NULL,\n" +
                    "\n" +
                    "    CONSTRAINT userPK PRIMARY KEY(userid),\n" +
                    "    CONSTRAINT userCK UNIQUE(email),\n" +
                    "    CONSTRAINT userCK2 UNIQUE(phone)\n" +
                    " );";

            Statement stmt = connection.createStatement();
            stmt.execute(str);

        }catch(SQLException e) {
            if(e.getSQLState().compareTo("X0Y32") == 0 ) {
                System.out.println("Table already exists");
            }else {
                System.exit(0);
            }
        }
    }

    // Untested
    private static final void createAppointmentsTable() {
        try {
            if(connection == null) getConnection();

            String str =
                    "CREATE TABLE Appointments (\n" +
                    "    app_id      INT GENERATED ALWAYS AS IDENTITY,\n" +
                    "    name        VARCHAR(255) NOT NULL,\n" +
                    "    type        VARCHAR(255) NOT NULL,\n" +
                    "    location    VARCHAR(255) NOT NULL,\n" +
                    "    start_time  TIMESTAMP NOT NULL,\n" +
                    "    end_time    TIMESTAMP NOT NULL,\n" +
                    "    created_by  INT NOT NULL,\n" +
                    "\n" +
                    "    PRIMARY KEY(app_id),\n" +
                    "    FOREIGN KEY(created_by) REFERENCES Users(userid)\n" +
                    ");";

            Statement stmt = connection.createStatement();
            stmt.execute(str);

        }catch(SQLException e) {
            if(e.getSQLState().compareTo("X0Y32") == 0 ) {
                System.out.println("Table already exists");
            }else {
                System.exit(0);
            }
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

			connection = DriverManager.getConnection(host,"app","app");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Find the user in the database
	 * @param username - The username of the user to find
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

			String query = "SELECT userid " + "FROM Users " + "WHERE userid = " + Integer.toString(userid) + "";

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
					if(i == 4) {
						userAL.add("");
						userAL.add(result.getString(i));
					}else {
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
	 * @param username - The user to search for
	 * @return - The User ID
	 */
	public static final int getUserID(String username) {
		try {
			if (connection == null)
				getConnection();

			assert (findUser(username));

			String query = "SELECT userid " + "FROM Users " + "WHERE username = \'" + username + "\'";

			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);

			// Need to check for correctness
			return Integer.parseInt(result.getString(1));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	/**
	 * Add a new user to the database. UserID is auto-generated.
	 * @param user - The user to add
	 */
	public static final void addUser(User user) {
		try {
			if (connection == null)
				getConnection();

			assert (!findUser(user.getUsername()));

			PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO users (username, password, fName, lName, phone, email, street, city, state, zipcode) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
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
	 * @param user - The user to change names
	 * @param newName - The new name the user wants to change to
	 */
	public static final void changeUsername(User user, String newName) {
		try {
			if (connection == null)
				getConnection();

			assert (findUser(user.getUsername()));

			String namechange = "UPDATE users SET username = \'" + newName + "\' WHERE username = \'"
					+ user.getUsername()+"\'";

			PreparedStatement stmt = connection.prepareStatement(namechange);
			stmt.executeUpdate();
			user.setUsername(newName);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Change the password of the user in the database
	 * @param user - The user to change passwords
	 * @param newPass - The new password the user wants to change to
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
	 * @param userid - The user's ID
	 * @param newStart - The new appointment's start
	 * @param newEnd - The new appointment's end
	 * @return - Whether or not the user has an appointment in conflict
	 */
	public static final boolean findAppointment(int userid, LocalDateTime newStart, LocalDateTime newEnd) {
            try {
                if (connection == null) getConnection();

                if (!findUser(userid)) return false;
                
                String startTime = Appointment.format(newStart),
                        endTime = Appointment.format(newEnd);

              
                String query = String.format(
                            "SELECT COUNT(*) AS rowcount \n" +
                            "FROM Appointments INNER JOIN Users\n" +
                            "ON Appointments.created_by = Users.userid \n" +
                            "WHERE ('%s' > Appointments.start_time AND '%s' < Appointments.end_time) \n" +
                            "OR ('%s' > Appointments.start_time AND '%s' < Appointments.end_time) \n" +
                            "OR ('%s' < Appointments.start_time AND '%s' > Appointments.end_time) \n" +
                            "GROUP BY created_by\n" +
                            "HAVING created_by = %d",
                            startTime, startTime, 
                            endTime, endTime, 
                            startTime, endTime, 
                            userid
                        );

                Statement stmt = connection.createStatement();
                ResultSet result = stmt.executeQuery(query);
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

    /**
     * Add a new appointment to the database
     * @param appointment - The appointment to add
     */
    public static final void addAppointment(Appointment appointment) {
        try {
            if (connection == null) {
                getConnection();
            }

            if (!findUser(appointment.getCreator())) {
                System.out.println("Error - could not find user");
                return;
            }

            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO Appointments (name, type, location, start_time, end_time, created_by) "
                    + "VALUES (?, ?, ?, ?, ?, ?)"
            );
            stmt.setString(1, appointment.getName());
            stmt.setString(2, appointment.getType());
            stmt.setString(3, appointment.getLocation());
            stmt.setString(4, Appointment.format(appointment.getStart()));
            stmt.setString(5, Appointment.format(appointment.getEnd()));
            stmt.setString(6, Integer.toString(appointment.getCreator()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addAppointment() - " + e.getMessage());
        }
    }
	
    /**
     * Change an existing appointment
     * @param app_id - The appointment ID in the database
     * @param appointment - The new appointment changes (as an object)
     */
    public static final void changeAppointment(int app_id, Appointment appointment) {
        try {
            if (connection == null) getConnection();

            if (!findUser(appointment.getCreator())) {
                    System.out.println("changeAppointment() - could not find user");
                    return;
                }
            
            PreparedStatement stmt = connection.prepareStatement(
                            "UPDATE Appointments \n" +
                            "SET name = ?, type = ?, location = ?, start_time = ?, end_time = ? \n" +
                            "WHERE app_id = ?"
            );
            stmt.setString(1, appointment.getName());
            stmt.setString(2, appointment.getType());
            stmt.setString(3, appointment.getLocation());
            stmt.setString(4, Appointment.format(appointment.getStart()));
            stmt.setString(5, Appointment.format(appointment.getEnd()));
            stmt.setString(6, Integer.toString(app_id));

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("changeAppointment() - " + e.getMessage());
        }
    }

    public static final void cancelAppointment(int appointmentID) {
        try {
            if (connection == null) getConnection();
            
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM Appointments " +
                    "WHERE app_id = " + appointmentID);

            stmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.out.println("cancelAppointment() - " + e.getMessage());
        }
    }

    // Untested
    public static final ArrayList<Appointment> retrieveAppointments(int userid) {
        ArrayList<Appointment> appointments = new ArrayList<>();
            try {
                if (connection == null) {
                    getConnection();
                }

            String query = 
                    "SELECT * \n" + 
                    "FROM Appointments INNER JOIN Users \n" +
                    "ON created_by = userid \n" +
                    "WHERE created_by = " + userid;

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int numCols = rsmd.getColumnCount();

                do {
                    Appointment a = new Appointment(
                                    rs.getString("name"),
                                    rs.getString("type"),
                                    rs.getString("location"),
                                    Appointment.parse(rs.getString("start_time")),
                                    Appointment.parse(rs.getString("end_time")),
                                    userid
                            );

                    appointments.add(a);
                } while (rs.next());
            } else {
                System.out.printf("\nQuery returned 0 results.\n");
            }

        } catch (SQLException e) {
            
            System.out.println("retrieveAppointments() - " + e.getMessage());
        }
        return appointments;
    }
    

	// Unfinished
	public static void changeUserInfo(User user, ArrayList<String> args) {
		// try {
		// if (connection == null)
		// getConnection();
		//
		// //String passchange = String.format("UPDATE Users SET fname = %s, lname = %s,
		// phone = %s, email = %s, street = %s, city = %s, state = %s, zipcode = %s
		// WHERE username = ");
		//
		// PreparedStatement stmt = connection.prepareStatement(passchange);
		// stmt.executeUpdate();
		// }
		// catch (SQLException e) {
		// System.out.println(e.getMessage());
		// }
	}

    public static void main(String[] args) {
        
        for (Appointment appt : retrieveAppointments(1)) {
            System.out.println(appt);
        }

        System.out.println("Done");
    }
}
