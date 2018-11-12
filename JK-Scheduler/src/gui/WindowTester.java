package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class WindowTester extends Application {
    @Override
    public void start(Stage primaryStage) {
        CreateAppointmentWindow caw = new CreateAppointmentWindow();
//        AppointmentsWindow aw = new AppointmentsWindow();
//        ModifyAppointmentWindow maw = new ModifyAppointmentWindow();
    }

    public static void main(String[] args) {
        Application.launch(WindowTester.class, args);
    }
}
