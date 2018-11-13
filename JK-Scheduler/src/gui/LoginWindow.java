package gui;

import java.time.LocalDateTime;
import java.util.ArrayList;

import application.Appointment;
import application.Main;
import application.User;
import application.UserController;
import database.Database;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class LoginWindow {

	Stage window;
	Database database;

	public LoginWindow(Stage mainStage) {
		window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		GridPane grid = new GridPane();
		Button loginButton = new Button("Login");
		Button newAccountButton = new Button("Create Account");
		grid.setVgap(25);
		grid.setHgap(25);
		grid.setPadding(new Insets(25));
		ArrayList<TextField> credentials = new ArrayList();
		String[] fieldNames = { "Login", "Username", "Password" };
		Scene scene = new Scene(grid);

		for (int i = 0; i < fieldNames.length; i++) {
			grid.add(new Label(fieldNames[i]), 0, i);
			if (i > 0) {
				if (i == 2) {
					PasswordField pf = new PasswordField();
					grid.add(pf, 1, i);
					credentials.add(pf);
				} else {
					TextField tf = new TextField();
					grid.add(tf, 1, i);
					credentials.add(tf);
				}
			}
		}

		grid.add(loginButton, 0, 3);
		grid.add(newAccountButton, 1, 3);
		window.setScene(scene);
		
		newAccountButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().showCreateUser();
			}
		});

		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Alert badLogin = new Alert(AlertType.ERROR, "Invalid Username or Password", ButtonType.OK);
				String username = credentials.get(0).getText();
				String password = credentials.get(1).getText();
				
                                    
//					if (Main.getUserCon().checkCredentials(username, password)) {
//						User user = Database.getUser(username);
//						if (user != null) {
//							Main.setCurrentUser(user);
//                                                        UserController.setUser(user);
//							user.populateAppointments(Database.retrieveAppointments(user.getID()));
//							System.out.println(Main.getCurrentUser().getfName());
//                                                        window.close();
//						}
//
//						
//					} 
//                                        else
//                                            badLogin.showAndWait();
                                    
                                if (UserController.handledLogin(username, password)) {
                                	
                                	Main.getCurrentUser().addAppointment(new Appointment("TEST", "Public", "TEST", LocalDateTime.now(), LocalDateTime.now().plusHours(2), Main.getCurrentUser().getID()));
                                	Main.getWindowManager().setCalendarView();
                                    window.close();
                                }
                                else
                                    badLogin.showAndWait();
                        }
                });

		// -- If the login window is closed so is the main window --//
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent w) {
				mainStage.close();
			}
		});
        }       

	public Stage getLoginWindow() {
		return window;
	}
}
