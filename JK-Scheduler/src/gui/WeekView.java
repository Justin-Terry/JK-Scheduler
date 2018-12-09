package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WeekView extends CalendarView{
	private CustomButton nextWeekButton, previousWeekButton;
	private int numberOfDays;
	private int numberOfColumns = 7;
	private int numberOfRows = 3;
	private String weekHeader;
	private GridPane weekGrid;
	private LocalDate firstDayOfWeek;
	private LocalDate lastDayOfWeek;
	private Insets cellMargin = new Insets(1);
	private ArrayList<DateBox> dateBoxes;
	
	public WeekView() {
		firstDayOfWeek = getFirstDayOfWeek();
		lastDayOfWeek = getLastDayOfWeek();
		createDisplay();
		
	}
	
	public WeekView(LocalDate date) {
		calendarDate = date;
		firstDayOfWeek = getFirstDayOfWeek();
		lastDayOfWeek = getLastDayOfWeek();
		createDisplay();
		
	}
	
	private void createDisplay() {
		weekGrid = new GridPane();
		weekGrid.setPadding(new Insets(25));
		weekGrid.setVgap(2);
		
		DateTimeFormatter weekFormat = DateTimeFormatter.ofPattern("MMM dd");
		weekHeader = firstDayOfWeek.format(weekFormat) + "-" + lastDayOfWeek.format(weekFormat);
		
		nextWeekButton = new CustomButton(">>>", Main.getWindowManager().getMainStage().getScene());
		previousWeekButton = new CustomButton("<<<", Main.getWindowManager().getMainStage().getScene());
		
		weekGrid.add(previousWeekButton, 0, 0);
		weekGrid.add(nextWeekButton, 6, 0);		
		Label weekHeaderLabel = new Label(weekHeader);
		weekGrid.add(weekHeaderLabel, 1, 0);
		weekGrid.setColumnSpan(weekHeaderLabel, 5);
		weekHeaderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
		RowConstraints rowOneCons = new RowConstraints();
		RowConstraints rowTwoCons = new RowConstraints();
		RowConstraints rowThreeCons = new RowConstraints();
		rowThreeCons.setVgrow(Priority.ALWAYS);
		weekGrid.getRowConstraints().addAll(rowOneCons, rowTwoCons, rowThreeCons);
		ColumnConstraints colCons = new ColumnConstraints();
		colCons.setHgrow(Priority.ALWAYS);
		colCons.setHalignment(HPos.CENTER);
		
		
		
		for(int i = 0; i < 7; i++) {
			DateBox temp = new DateBox(firstDayOfWeek.plusDays(i));
			weekGrid.getColumnConstraints().add(colCons);
			weekGrid.add(temp.getDateBox(), i, 2);
			weekGrid.setMargin(temp.getDateBox(), cellMargin);
			
			Label tempLabel = new Label(dayNames.get(i));
			tempLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			weekGrid.add(tempLabel, i, 1);
			weekGrid.setHalignment(tempLabel, HPos.CENTER);
		}
		
		nextWeekButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().setCalendarView(getLastDayOfWeek().plusDays(1));
			}
		});
		
		previousWeekButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().setCalendarView(getFirstDayOfWeek().minusDays(1));
			}
		});
		
		
	}
	
	public GridPane getCalendarDisplay() {
		return weekGrid;
	}
	
	public ScrollPane getDayView() {
		ScrollPane foo = new ScrollPane();
		return foo;
	};
	
	public LocalDate getFirstDayOfWeek() {
		LocalDate temp;
		if(calendarDate.getDayOfWeek().getValue() == 7) {
			temp = calendarDate;
		}else {
			temp = calendarDate.minusDays((calendarDate.getDayOfWeek().getValue()));
		}
		return temp;
	}
	
	public LocalDate getLastDayOfWeek() {
		
		LocalDate temp;
		if(calendarDate.getDayOfWeek().getValue() == 7) {
			temp = calendarDate.plusDays(6);
		}else {
			temp = calendarDate.plusDays((6-calendarDate.getDayOfWeek().getValue()));
		}
		return temp;
	}
	
	public void setCalendarColor() {
		
	}
}
