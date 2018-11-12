package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class WindowTester extends Application {
    @Override
    public void start(Stage primaryStage) {
        CreateAppointmentWindow caw = new CreateAppointmentWindow();
//        AppointmentListWindow aw = new AppointmentListWindow();
//        ModifyAppointmentWindow maw = new ModifyAppointmentWindow();
    }

    public static void main(String[] args) {
        Application.launch(WindowTester.class, args);
    }
}
