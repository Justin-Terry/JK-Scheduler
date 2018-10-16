package gui;

import javafx.scene.Scene;

public class SceneController {
	CalendarScene calendar;
	Scene currentScene;

	public SceneController() {
		calendar = new CalendarScene();
	}
	
	public Scene getCalendarScene() {
		currentScene = calendar.getCalendarScene(2);
		return currentScene;
	}
}
