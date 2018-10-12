package gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WindowManager {
	Stage mainStage;
	MenuController menuCon;
	
	public WindowManager(Stage primaryStage) {
		menuCon = new MenuController();
		mainStage = primaryStage;
		mainStage.setTitle("JK Scheduler");
		mainStage.setMaximized(true);
		BorderPane bp = new BorderPane();
		Scene mainScene = new Scene(bp, mainStage.getHeight(), mainStage.getWidth());
		bp.setTop(menuCon.getMenuBar());
		
		mainStage.setScene(mainScene);
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
	
	
	
}
