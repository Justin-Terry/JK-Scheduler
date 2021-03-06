package gui;

	import java.time.format.DateTimeFormatter;

	import application.Appointment;
	import application.Main;
	import javafx.event.Event;
	import javafx.event.EventHandler;
	import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
	import javafx.scene.control.Label;
	import javafx.scene.control.ScrollPane;
	import javafx.scene.control.Alert;
	import javafx.scene.control.Alert.AlertType;
	import javafx.scene.control.ButtonType;
	import javafx.scene.layout.BorderPane;
	import javafx.scene.layout.GridPane;
	import javafx.scene.layout.Pane;
	import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

	public class ChangeAppointmentWindow {
		private Scene scene;
		private String[] labels = {"Name", "Type", "Location", "Start Date", "Start Time", "End Date", "End Time"};
		

		public ChangeAppointmentWindow() {
			Stage stage = new Stage();
			ScrollPane scroller = new ScrollPane();
			GridPane apptsPane = new GridPane();
			apptsPane.setVgap(10);
			apptsPane.setPadding(new Insets(0,0,20,0));
			scene = new Scene(scroller);
			scroller.setMaxHeight(300);
			scroller.setMinWidth(350);


			for (int i = 0; i < Main.getCurrentUser().getAppointments().size(); i++) {
				Appointment a = Main.getCurrentUser().getAppointments().get(i);
				VBox apptHolder = new VBox();
				apptHolder.setAlignment(Pos.CENTER);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm LLL dd, uuuu");
				String start = a.getStart().format(dtf);
				Label apptLabel = new Label(a.getName() + " " + start);
				if(i == 0) {
					Label lab = new Label("Click on appointment to modify");
					lab.setPadding(new Insets(20));
					apptHolder.getChildren().add(lab);
					lab.setFont(Font.font("Arial", FontWeight.BOLD, 16));
					
				}

				apptHolder.setOnMouseClicked(new EventHandler<Event>() {
					public void handle(Event e) {
						ModifyAppointmentWindow maw = new ModifyAppointmentWindow(a);	
						stage.close();
					}
				});

				apptHolder.getChildren().add(apptLabel);

				apptsPane.add(apptHolder, 0, i + 1);

			}
			scroller.setContent(apptsPane);
			stage.setScene(scene);
			stage.show();

		}
		

	}
	

		



