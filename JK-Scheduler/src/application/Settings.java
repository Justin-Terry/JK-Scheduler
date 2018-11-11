package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class Settings {
	private int numOfSettings = 2;
	File settingsFile;
	private String[] settings;
	private String settingsLocation = "./settings.txt";

	public Settings() {
		settings = new String[numOfSettings];
		try {
			settingsFile = new File(settingsLocation);
			if(settingsFile.createNewFile()) {
				settings[0] = "1";
				settings[1] = "cc8033";
			}else {
			
				FileReader fr = new FileReader(settingsFile);
				BufferedReader settingsReader = new BufferedReader(fr);

				for (int i = 0; i < numOfSettings; i++) {
					settings[i] = settingsReader.readLine();
				}
				fr.close();
			}
			
		} catch (IOException e) {
			System.out.println("Error trying to open settings file.");
			
		}

	}

	public void setCalendarRange(int range) {
		settings[0] = Integer.toString(range);
	}

	public void setCalendarColor(String color) {
		settings[1] = color;
	}

	public int getCalendarRange() {
		int temp = Integer.parseInt(settings[0]);
		return temp;
	}

	public String getCalendarColor() {
		return settings[1];
	}

	public void writeSettings() {
		try {
			FileWriter fw = new FileWriter(settingsFile);
			BufferedWriter writer = new BufferedWriter(fw);

			for (int i = 0; i < numOfSettings; i++) {
				writer.write(settings[i]);
				writer.newLine();
			}
			writer.close();

		} catch (IOException e) {
			System.out.println("Error writing to settings file");
		}

	}
}
