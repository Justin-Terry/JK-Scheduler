package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import database.Database;
import gui.SettingsWindow;
import gui.WindowManager;
import java.io.File;
import java.time.LocalDateTime;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private static Settings settings = new Settings();
	private static HashMap<LocalDateTime, EmailNotification> notifications = new HashMap<LocalDateTime, EmailNotification>();
	private static WindowManager winMan;
	private static User currentUser = null;
	private static Database schedulerDB;
	private static UserController userCon;

	@Override
	public void start(Stage primaryStage) {
		System.out.println("Project working directory: " + new File(".").getAbsolutePath());
		winMan = new WindowManager(primaryStage);
		winMan.displayLogin(primaryStage);
		winMan.setCalendarView();
	}

	public static void main(String[] args) {

		userCon = new UserController();
		schedulerDB = new Database();
		// Notifications thread
		Thread thread = new Thread(runNotifier());
		thread.start();

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
	
	public static HashMap getNotifications() {
		return notifications;
	}
	
	public static Runnable runNotifier() {
		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
					System.out.println("Running thread");
					if (notifications.containsKey(now)) {
						try {
							
							notifications.get(now).sendNotification();
							schedulerDB.removeNotification(notifications.get(now).getID());
							notifications.remove(now);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		return runnable;
	}

}
