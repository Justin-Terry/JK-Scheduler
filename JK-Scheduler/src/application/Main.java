package application;
	
import gui.WindowManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private Settings settings = new Settings();
	private static WindowManager winMan;
	
	@Override
	public void start(Stage primaryStage) {
		winMan = new WindowManager(primaryStage);
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
	
	public static WindowManager getWindowManager() {
		return winMan;
	}
}
