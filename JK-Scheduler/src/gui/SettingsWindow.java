package gui;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsWindow {
	private Stage stage = new Stage();
	private GridPane grid = new GridPane();
	private Scene scene = new Scene(grid, 300,300);
	private String[] labelStrings = {"Calendar Color", "Calendar Range"};
	
	public SettingsWindow() {
		stage.initModality(Modality.APPLICATION_MODAL);
		
		grid.setPadding(new Insets(10));
		grid.setHgap(10);
		grid.setVgap(10);
		
		for(int i = 0; i < labelStrings.length; i++) {
			grid.add(new Label(labelStrings[i]), 0, i);
		}
		
		ColorPicker colorPicker = new ColorPicker();
		grid.add(colorPicker, 1, 0);
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				String color = colorPicker.getValue().toString();
				Main.getWindowManager().getMainCalendarPane().setCalendarColor(color.substring(2,8));
				System.out.println(color.substring(2,8));
			}
		});
		
		
	}
	
	public void show() {
		stage.setScene(scene);
		stage.show();
	}
	
	

}
