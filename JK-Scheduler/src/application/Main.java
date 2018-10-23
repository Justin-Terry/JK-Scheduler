package application;
	
import database.Database;
import gui.SettingsWindow;
import gui.WindowManager;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	private static Settings settings = new Settings();
	private static WindowManager winMan;
	private static User currentUser;
	private static Database schedulerDB;
	private static UserController userCon;
	
	@Override
	public void start(Stage primaryStage) {
		winMan = new WindowManager(primaryStage);
		winMan.displayLogin(primaryStage);
		
		
	}
	
	public static void main(String[] args) {
		userCon = new UserController();
		schedulerDB = new Database();		
		launch(args);
	}
	
	public static WindowManager getWindowManager() {
		return winMan;
	}
	
	public static Settings getSettings() {
		return settings;
	}
	
	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static UserController getUserCon() {
		return userCon;
	}
	
	public static Database getDatabase() {
		return schedulerDB;
	}
	
	public static void setCurrentUser(User u) {
		currentUser = u;
	}
}
