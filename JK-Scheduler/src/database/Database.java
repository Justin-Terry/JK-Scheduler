package database;

import application.User;

import java.sql.*;
import java.util.ArrayList;

public final class Database {
    private static Connection connection = null;

    private Database() {}

    /**
     * Obtain a connection to the database.
     */
    private static final void getConnection() {
        try {
            // Replace with actual database implementation
            String host = "jdbc:derby://localhost:1527/JK-Scheduler",
                    username = "username",
                    password = "password";

            connection = DriverManager.getConnection(host, username, password);
        }
        catch (SQLException e) {
            System.out.println( e.getMessage() );
        }
    }

    /**
     * Find the user in the database
     * 
     * @param username - The username of the user to find
     * @return - Whether or not the action was successful
     */
    public static final boolean findUser(String username) {
        try {
            if (connection == null)
                getConnection();

            String query =
                    "SELECT USERNAME " +
                    "FROM Users " +
                    "WHERE username = \'" + username + "\'";

            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                        ResultSet.CONCUR_READ_ONLY);
            ResultSet result = stmt.executeQuery(query);

            result.last();
            return ( result.getRow() == 1 ? true : false );
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
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

            assert(findUser(username));

            String query =
                    "SELECT userid " +
                            "FROM Users " +
                            "WHERE username = \'" + username + "\'";

            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);

            // Need to check for correctness
            return Integer.parseInt(result.getString(1));
        }
        catch (SQLException e) {
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
            "INSERT INTO Users (username, password, fname, lname, phone, email, street, city, state, zipcode) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
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
        }
        catch (SQLException e) {
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

            assert( findUser(user.getUsername()) );

            String namechange = "UPDATE Users " +
                                "SET username = " + newName + " " +
                                "WHERE username = " + user.getUsername();

            PreparedStatement stmt = connection.prepareStatement(namechange);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
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

            assert(findUser(user.getUsername()));

            String passchange = "UPDATE Users " +
                                "SET Password = " + newPass + " " +
                                "WHERE Password = " + user.getPassword(); /** DO NOT DO THIS **/

            PreparedStatement stmt = connection.prepareStatement(passchange);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Unfinished
    public static void changeUserInfo(User user, ArrayList<String> args) {
//        try {
//            if (connection == null)
//                getConnection();
//
//            //String passchange = String.format("UPDATE Users SET fname = %s, lname = %s, phone = %s, email = %s, street = %s, city = %s, state = %s, zipcode = %s WHERE username = ");
//
//            PreparedStatement stmt = connection.prepareStatement(passchange);
//            stmt.executeUpdate();
//        }
//        catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
    }

    public static void main(String[] args) {
        System.out.println(findUser("user"));
    }
}
