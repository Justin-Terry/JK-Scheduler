package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.paint.Color;

public class Settings {

	private String settingsLocation = "settings.txt";
	private String calendarRange;
	private Color calendarColor;

	public Settings() {
		// Read from existing settings file
	}

	public void setCalendarRange(String range) {
		calendarRange = range;
	}

	public void setCalendarColor(Color color) {
		calendarColor = color;
	}

	public String getCalendarRange() {
		return calendarRange;
	}

	public Color getCalendarColor() {
		return calendarColor;
	}
}
