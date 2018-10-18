package gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class SceneController {
	CalendarScene calendar;
	Scene currentScene;

	public SceneController() {
		calendar = new CalendarScene();
	}	
	
	public BorderPane getCalenderPane(int n) {
		return calendar.getCalendarPane(n);
	}
	
	public CalendarScene getCalendar() {
		return calendar;
	}
}
