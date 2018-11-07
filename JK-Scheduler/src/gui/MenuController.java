package gui;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuController {
	private MenuBar menuBar = new MenuBar();
	
	public MenuController() {
		menuBar.getMenus().addAll(getAccountMenu(), getAppointmentMenu(), getSettingsMenu(), getHelpMenu());
	}
	
	public MenuBar getMenuBar() {
		return menuBar;
	}
	
	private Menu getAccountMenu() {
		Menu accountMenu = new Menu("Account");
		MenuItem create = new MenuItem("Create Account");
		
		create.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a) {
				Main.getWindowManager().showCreateUser();
			}
		});
		
		MenuItem changeUsername = new MenuItem("Change Username");
		changeUsername.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a) {
				Main.getWindowManager().showChangeUsername();
			}
			
		});
		
		MenuItem changePassword = new MenuItem("Change Password");		
		changePassword.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().showChangePassword();
			}
		});

		MenuItem changeUserInfo = new MenuItem("Change User Info");
		changeUserInfo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().showChangeUserInfo();
			}
		});

		accountMenu.getItems().addAll(create, changeUsername, changePassword, changeUserInfo);
		return accountMenu;
	}
	
	private Menu getAppointmentMenu() {
		Menu appointmentMenu = new Menu("Appointment");
		MenuItem makeAppointment = new MenuItem("Make Appointment");
		MenuItem cancelAppointment = new MenuItem("Cancel Appointment");
		MenuItem changeAppointment = new MenuItem("Change Appointment");
		
		
		makeAppointment.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().showCreateAppointment();
			}
		});

		appointmentMenu.getItems().addAll(makeAppointment, cancelAppointment, changeAppointment);
		return appointmentMenu;
	}
	
	private Menu getSettingsMenu() {
		Menu settingsMenu = new Menu("Settings");
		MenuItem setColor = new MenuItem("Change Settings");
		
		setColor.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().showSettingsWindow();
			}
		});
		
		settingsMenu.getItems().addAll(setColor);
		return settingsMenu;
	}
	
	private Menu getHelpMenu() {
		Menu helpMenu = new Menu("Help");
		MenuItem help = new MenuItem("Help");
		
		helpMenu.getItems().add(help);
		return helpMenu;
	}
}
