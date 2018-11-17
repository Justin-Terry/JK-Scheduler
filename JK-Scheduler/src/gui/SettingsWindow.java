package gui;

import application.Main;
import application.Settings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsWindow {
	private Stage stage = new Stage();
	private GridPane grid = new GridPane();
	private Scene scene = new Scene(grid);
	private String[] labelStrings = {"Calendar Color", "Calendar Range"};
	private Settings settings;
	
	public SettingsWindow() {
		stage.initModality(Modality.APPLICATION_MODAL);
		settings = Main.getSettings();
		grid.setPadding(new Insets(20));
                
		MenuButton calendarRangeButton = new MenuButton("Select Calendar Range");
		MenuItem dayRange = new MenuItem("Day");
		MenuItem weekRange = new MenuItem("Week");
		MenuItem monthRange = new MenuItem("Month");
		Button doneButton = new Button("Submit Changes");
		
		calendarRangeButton.getItems().addAll(dayRange,weekRange,monthRange);
		
		dayRange.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a) {
				settings.setCalendarRange(0);
			}
		});
		
		weekRange.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a) {
				settings.setCalendarRange(1);
			}
		});
		
		monthRange.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a) {
				settings.setCalendarRange(2);
			}
		});		
		
		doneButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				settings.writeSettings();
				Main.getWindowManager().getCalenderView().setCalendarColor();
				Main.getWindowManager().setCalendarView(Main.getWindowManager().getCalendarView().calendarDate);
				stage.close();
			}
		});
		
		grid.setPadding(new Insets(10));
		grid.setHgap(10);
		grid.setVgap(10);
		
		for(int i = 0; i < labelStrings.length; i++) {
			grid.add(new Label(labelStrings[i]), 0, i);
		}
		
		ColorPicker colorPicker = new ColorPicker();
                colorPicker.setValue(Color.web(settings.settings[1]));
		grid.add(colorPicker, 1, 0);
		grid.add(calendarRangeButton, 1, 1);
		ColorPicker textPicker = new ColorPicker();
                textPicker.setValue(Color.web(settings.settings[2]));
		grid.add(textPicker,1,2);
		grid.add(new Label("Text Color"), 0, 2);
		ColorPicker apptPicker = new ColorPicker();
                apptPicker.setValue(Color.web(settings.settings[3]));
		grid.add(apptPicker,1,3);
		grid.add(new Label("Appointment Color"), 0, 3);
		grid.add(doneButton, 0, 4);
		grid.setColumnSpan(doneButton, 2);
		grid.setHalignment(doneButton, HPos.CENTER);
		
		
		
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				String color = colorPicker.getValue().toString();
				settings.setCalendarColor(color.substring(2,8));
			}
		});
		
		textPicker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				String color = textPicker.getValue().toString();
				settings.setTextColor(color.substring(2,8));
			}
		});
		
		apptPicker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				String color = apptPicker.getValue().toString();
				settings.setAppointmentColor(color.substring(2,8));
			}
		});
		
		
		
	}
	
	public void show() {
		stage.setScene(scene);
		stage.show();
	}
	
	

}
