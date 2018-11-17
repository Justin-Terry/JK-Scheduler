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
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class LoginWindow {

    Stage window;
    Database database;

    public LoginWindow(Stage mainStage, Pane parentPane) {
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
        String[] fieldNames = {"Login", "Username", "Password"};
        Scene scene = new Scene(grid);

        TextField uf = new TextField();
        PasswordField pf = new PasswordField();
        
        grid.add(new Label("Login"), 0, 0);
        grid.add(new Label("Username"), 0, 1);
        grid.add(new Label("Password"), 0, 2);
        
        grid.add(uf, 1, 1);
        grid.add(pf, 1, 2);
        
        credentials.add(uf);
        credentials.add(pf);
        
//        for (int i = 0; i < fieldNames.length; i++) {
//            grid.add(new Label(fieldNames[i]), 0, i);
//            if (i > 0) {
//                if (i == 2) {
////                    pf = new PasswordField();
//                    grid.add(pf, 1, i);
//                    credentials.add(pf);
//                } else {
//                    TextField tf = new TextField();
//                    grid.add(tf, 1, i);
//                    credentials.add(tf);
//                }
//            }
//        }

        grid.add(loginButton, 0, 3);
        grid.add(newAccountButton, 1, 3);

        window.setScene(scene);

        newAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent a) {
                Main.getWindowManager().showCreateUser();
            }
        });

        uf.setOnKeyPressed(event -> {if (event.getCode() == KeyCode.ENTER) {performLogin(credentials, parentPane); }});
        pf.setOnKeyPressed(event -> {if (event.getCode() == KeyCode.ENTER) {performLogin(credentials, parentPane); }});
        loginButton.setOnAction(event -> { performLogin(credentials, parentPane); });

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
    
    private void performLogin(ArrayList<TextField> credentials, Pane pane) {
        Alert badLogin = new Alert(AlertType.ERROR, "Invalid Username or Password", ButtonType.OK);
                String username = credentials.get(0).getText();
                String password = credentials.get(1).getText();

                if (UserController.handledLogin(username, password)) {
                    // Inserting test appointment into arraylist, bypasses DB

//                                	Main.getCurrentUser().addAppointment(new Appointment("TEST", "Public", "TEST", LocalDateTime.now(), LocalDateTime.now().plusHours(2), Main.getCurrentUser().getID()));
//                                	Main.getCurrentUser().addAppointment(new Appointment("TEST2", "Public", "TEST2", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2), Main.getCurrentUser().getID()));
//                                	Main.getCurrentUser().addAppointment(new Appointment("TEST3", "Public", "TEST3", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3).plusHours(2), Main.getCurrentUser().getID()));
//                                	Main.getCurrentUser().addAppointment(new Appointment("TEST4", "Public", "TEST4", LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(3).plusHours(2), Main.getCurrentUser().getID()));
//                                	for(int i = 0; i < 50; i++) {
//                                		Main.getCurrentUser().addAppointment(new Appointment("TEST" + i, "Public", "TEST" + i, LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(i).plusHours(2), Main.getCurrentUser().getID()));
//                                	}
                    // Reset the calendar view to include new users appointments
//                                	Main.getWindowManager().setCalendarView();
                    pane.setEffect(new BoxBlur(0, 0, 0));
                    window.close();
                } else {
                    badLogin.showAndWait();
                }
    }
}
