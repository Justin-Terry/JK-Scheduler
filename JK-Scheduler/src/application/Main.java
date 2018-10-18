package application;
	
import gui.SettingsWindow;
import gui.WindowManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private static Settings settings = new Settings();
	private static WindowManager winMan;
	private static UserController userCon;
	
	@Override
	public void start(Stage primaryStage) {
		
		winMan = new WindowManager(primaryStage);
	}
	
	public static void main(String[] args) {
		userCon = new UserController();
		launch(args);
	}
	
	public static WindowManager getWindowManager() {
		return winMan;
	}
	
	public static Settings getSettings() {
		return settings;
	}
	
	public static UserController getUserCon() {
		return userCon;
	}
}
