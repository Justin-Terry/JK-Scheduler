package gui;

import application.Main;
import application.Settings;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WindowManager {
	private Stage mainStage;
	MenuController menuCon;
	BorderPane bp;
	SceneController sceneCon;
	Settings settings;
	
	public WindowManager(Stage primaryStage) {
		settings = Main.getSettings();
		menuCon = new MenuController();
		sceneCon = new SceneController();
		mainStage = primaryStage;
		mainStage.setTitle("JK Scheduler");
		mainStage.setMaximized(true);
		bp = new BorderPane();
		Scene mainScene = new Scene(bp, mainStage.getHeight(), mainStage.getWidth());
		bp.setTop(menuCon.getMenuBar());
		bp.setCenter(sceneCon.getCalenderPane(settings.getCalendarRange()));
		setMainStage(mainScene);
		showMainStage();	
	}
	
	public void showMainStage() {
		mainStage.show();
	}
	
	public void setCalendarPane(int n) {
		bp.setCenter(sceneCon.getCalenderPane(n));
	}
	
	public void showPopUpStage(Scene sce, String title) {
		Stage popUp = new Stage();
		popUp.setTitle(title);
		popUp.setScene(sce);
		popUp.show();
	}
	
	public void showCreateUser() {
		CreateUserWindow newUser = new CreateUserWindow();
	}
	
	public void showChangeUsername() {
		ChangeUserNameWindow changeUser = new ChangeUserNameWindow();
	}
	
	public void showChangePassword() {
		ChangePasswordWindow changePass = new ChangePasswordWindow();
	}
	
	public void showSettingsWindow() {
		SettingsWindow settingsWindow = new SettingsWindow();
		settingsWindow.show();
	}
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	public void setMainStage(Scene s) {
		mainStage.setScene(s);
	}
	
	public CalendarScene getMainCalendarPane() {
		return sceneCon.getCalendar();
	}
	
	
}
