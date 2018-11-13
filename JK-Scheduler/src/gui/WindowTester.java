package gui;

import application.Appointment;
import application.User;
import application.convert;
import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WindowTester extends Application {
    @Override
    public void start(Stage primaryStage) {
//        CreateAppointmentWindow caw = new CreateAppointmentWindow();
//        AppointmentListWindow aw = new AppointmentListWindow();
//        ModifyAppointmentWindow maw = new ModifyAppointmentWindow();


        User thisUser = new User("jim", 6);
        thisUser.setID(5);
        thisUser.addAppointment(new Appointment(String.valueOf((char)6),
                "idk",
                "anywhere",
                convert.toLocalDateTime("2018-11-09 04:44:00.0"),
                convert.toLocalDateTime("2018-11-09 04:44:00.0"),
                6));



        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Save schedule file");
        File dir = dc.showDialog(null);
        if (dir != null) {

            File schedule = new File(dir, "myschedule.txt");
            try {
                if (thisUser.getAppointments().size() == 0) {
                    System.out.println("UserController.exportScheduler() - user has no appointments");
                }

                schedule.createNewFile();

                FileWriter fw = new FileWriter(schedule);
                BufferedWriter writer = new BufferedWriter(fw);

                writer.write("UID=" + thisUser.getID() + "\n");

                for (Appointment a : thisUser.getAppointments())
                    writer.write(a.toString() + "\n");

                writer.close();
            } catch (IOException e) {
                System.out.print("UserController.exportSchedule() - ");
                e.printStackTrace();
            } finally {
            }
        }

    }

    public static void main(String[] args) {
        Application.launch(WindowTester.class, args);
    }
}
