package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Settings {
	private int numOfSettings = 4;
	File settingsFile;
	private String[] settings;
	private String settingsLocation = "./";

	public Settings() {
		settings = new String[numOfSettings];
		try {
			settingsFile = new File(settingsLocation+"settings.txt");
			if(!settingsFile.isFile()) {
				settings[0] = "1";
				settings[1] = "cc8033";
				settings[2] = "ffffff";
				settings[3] = "000000";
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
	
	public void setTextColor(String color) {
		settings[2] = color;
	}
	
	public void setAppointmentColor(String color) {
		settings[3] = color;
	}

	public int getCalendarRange() {
		int temp = settings.length > 0 ? Integer.parseInt(settings[0]) : 2;
		return temp;
	}

	public String getCalendarColor() {
		return settings[1];
	}
	
	public String getAppointmentColor() {
		return settings[3];
	}
	
	public String getTextColor() {
		String color = "#" + settings[2];
		return color;
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
