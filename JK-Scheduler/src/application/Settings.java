package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.paint.Color;

public class Settings {
	private int numOfSettings = 2;
	private String[] settings;
	private String settingsLocation = "src/application/settings.txt";


	public Settings() {
		settings = new String[numOfSettings];
		try {
			File settingsFile = new File(settingsLocation);
			FileReader fr = new FileReader(settingsFile);
			BufferedReader settingsReader = new BufferedReader(fr);
			
			for(int i = 0; i < numOfSettings; i++) {
				settings[i] = settingsReader.readLine();
			}
			
			
			
			
		} catch (IOException e) {
			System.out.println("Error trying to open settings file.");
			System.out.println(System.getProperty("user.dir"));
		}
		
		

	}

	public void setCalendarRange(String range) {
		settings[0] = range;
	}

	public void setCalendarColor(String color) {
		settings[1] = color;
	}

	public String getCalendarRange() {
		return settings[0];
	}

	public String getCalendarColor() {
		return settings[1];
	}
}
