package gui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;

import application.Main;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public abstract class CalendarView {	
	protected LocalDate calendarDate = LocalDate.now();
	protected DayOfWeek calendarDayOfWeek = calendarDate.getDayOfWeek();
	protected Month	calendarsMonth = calendarDate.getMonth();
	protected HashMap<Integer, String> dayNames;
	
	public CalendarView() {
		dayNames = new HashMap<Integer, String>();
		dayNames.put(0, "Sunday");
		dayNames.put(1, "Monday");
		dayNames.put(2, "Tuesday");
		dayNames.put(3, "Wednesday");
		dayNames.put(4, "Thursday");
		dayNames.put(5, "Friday");
		dayNames.put(6, "Saturday");
		
	};
	
	public abstract GridPane getCalendarDisplay();
	public abstract ScrollPane getDayView();
	public abstract void setCalendarColor();
		
	
}
