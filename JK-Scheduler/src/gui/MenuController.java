package gui;

import application.Main;
import application.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController {
	private MenuBar menuBar = new MenuBar();
	
	public MenuController() {
		menuBar.getMenus().addAll(getFileMenu(), getAccountMenu(), getAppointmentMenu(), getSettingsMenu(), getHelpMenu());
	}
	
	public MenuBar getMenuBar() {
		return menuBar;
	}

	private Menu getFileMenu() {
		Desktop desktop = Desktop.getDesktop();
		Menu fileMenu = new Menu("File");
		MenuItem Import = new MenuItem("Import Schedule"),
				Export = new MenuItem("Export Schedule");

		Import.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				final FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose schedule file(s) to Import");
				File file = fileChooser.showOpenDialog(null);
				
                                try {
                                    UserController.importSchedule(file);
                                } 
                                catch (Exception ex) {
                                        Logger.getLogger(
                                            MenuController.class.getName()).log(
                                            Level.SEVERE, null, ex
                                        );
                                }
			}
		});

		Export.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				DirectoryChooser dc = new DirectoryChooser();
				dc.setTitle("Save schedule file");
				File dir = dc.showDialog(null);
				if (dir != null) {
					UserController.exportSchedule(dir, "myschedule.txt");
				}
			}
		});

		fileMenu.getItems().addAll(Import, Export);
		return fileMenu;
	}

	private void openFile(File file) {

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
		
		changeAppointment.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().showModifyAppointment();
			}
		});
		
		cancelAppointment.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				CancelAppointmentWindow caw = new CancelAppointmentWindow();
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
