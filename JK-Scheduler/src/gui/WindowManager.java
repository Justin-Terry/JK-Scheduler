package gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WindowManager {
	private Stage mainStage;
	MenuController menuCon;
	SceneController sceneCon;
	
	public WindowManager(Stage primaryStage) {
		menuCon = new MenuController();
		sceneCon = new SceneController();
		mainStage = primaryStage;
		mainStage.setTitle("JK Scheduler");
		mainStage.setMaximized(true);
		BorderPane bp = new BorderPane();
		Scene mainScene = new Scene(bp, mainStage.getHeight(), mainStage.getWidth());
		bp.setTop(menuCon.getMenuBar());
		
		setMainStage(sceneCon.getCalendarScene());
		mainStage.show();		
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
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	public void setMainStage(Scene s) {
		mainStage.setScene(s);
	}
	
	
}
