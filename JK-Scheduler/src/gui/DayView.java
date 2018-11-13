package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.Appointment;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
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

public class DayView extends CalendarView{
	private ScrollPane sp= new ScrollPane();
	private RowConstraints rowCons = new RowConstraints();
	private RowConstraints row1Cons = new RowConstraints();
	private ColumnConstraints col1 = new ColumnConstraints();
	private ColumnConstraints col2 = new ColumnConstraints();
	private ColumnConstraints col3 = new ColumnConstraints();
	private ColumnConstraints appointmentsCols = new ColumnConstraints();
	private StackPane dayStack;
	private GridPane dayGrid, appointmentsGrid;
	private Button previousDayButton, nextDayButton;
	
	public DayView() {
		createDisplay();
	}
	
	public DayView(LocalDate date) {
		calendarDate = date;
		createDisplay();
	}
	
	private void createDisplay() {
		dayGrid = new GridPane();
		dayGrid.setVgap(1);
		appointmentsGrid = new GridPane();
		appointmentsGrid.setVgap(1);
		dayStack = new StackPane();
		dayStack.getChildren().addAll(dayGrid, appointmentsGrid);
		setConstraints();
		DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("MMM dd, uuuu");
		Label dayLabel = new Label(calendarDate.format(dayFormat));
		dayLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
		previousDayButton = new Button("<<<");
		nextDayButton = new Button(">>>");
		dayGrid.add(dayLabel, 1, 0);
		appointmentsGrid.add(previousDayButton, 0, 0);
		appointmentsGrid.add(nextDayButton, 5, 0);
		int gridBox = 1;
		for(int i = 0; i < 24;i++) {
			DateBox db1 = new DateBox(calendarDate, i,00);
			DateBox db2 = new DateBox(calendarDate, i,30);		
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a");
			DateBox blank = new DateBox();
			blank.setDateBoxColor("");
			dayGrid.add(new Label(db1.getBoxTime().format(dtf)), 0, gridBox);
			appointmentsGrid.add(blank.getDateBox(), 0, gridBox);
			dayGrid.add(db1.getDateBox(), 1, gridBox++);
			blank = new DateBox();
			blank.setDateBoxColor("");
			dayGrid.add(new Label(db2.getBoxTime().format(dtf)), 0, gridBox);
			appointmentsGrid.add(blank.getDateBox(), 0, gridBox);
			dayGrid.add(db2.getDateBox(), 1, gridBox++);
			
		}
		
		nextDayButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().setCalendarView(calendarDate.plusDays(1));
			}
		});
		
		previousDayButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().setCalendarView(calendarDate.minusDays(1));
			}
		});	
		
		setupAppts();
	}
	
	
	public GridPane getCalendarDisplay() {
		return dayGrid;
	}
	
	private void setConstraints() {
		col1.setHgrow(Priority.ALWAYS);
		col1.setPercentWidth(10);
		col1.setHalignment(HPos.CENTER);
		col2.setHgrow(Priority.ALWAYS);
		col2.setPercentWidth(80);
		col2.setHalignment(HPos.CENTER);
		col3.setHgrow(Priority.ALWAYS);
		col3.setPercentWidth(10);
		col3.setHalignment(HPos.CENTER);
		
		appointmentsCols.setHgrow(Priority.ALWAYS);
		appointmentsCols.setPercentWidth(20);
		
		rowCons.setVgrow(Priority.ALWAYS);
		rowCons.setValignment(VPos.CENTER);
		rowCons.setMaxHeight(20);
		rowCons.setMinHeight(20);
		
		row1Cons.setVgrow(Priority.ALWAYS);
		row1Cons.setValignment(VPos.CENTER);
		row1Cons.setPercentHeight(5);
		
		appointmentsGrid.getColumnConstraints().addAll(col1, appointmentsCols,appointmentsCols,appointmentsCols,appointmentsCols, col3);
		dayGrid.getColumnConstraints().addAll(col1, col2,col3);
		
		for(int i = 0; i < 100; i++) {
			if(i == 0) {
				dayGrid.getRowConstraints().add(row1Cons);
				appointmentsGrid.getRowConstraints().add(row1Cons);
			}else {
				dayGrid.getRowConstraints().add(rowCons);
				appointmentsGrid.getRowConstraints().add(rowCons);
			}
		}
		
		
		
	}
	
	public void setCalendarColor() {
		
	}
	
	public ScrollPane getDayView() {
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		sp.setContent(dayStack);
		return sp;
	}
	
	//12:00AM = grid 1
	public void setupAppts() {
		// (Node, row, col)
		int apptsAdded = 0;
		for(Appointment a: Main.getCurrentUser().getAppointments()) {
			if(a.getStart().toLocalDate().equals(calendarDate)) {
				int startHr = a.getStart().getHour();
				int endHr = a.getEnd().getHour();
				int startM = a.getStart().getMinute();
				int endM = a.getEnd().getMinute();
				
				int hours = endHr - startHr;
				int mins = endM - startM;
				
				int span = hours*2;
				if(mins >= 30) {
					span += 1;
				}
				
				
				startHr = startHr*2;
				System.out.println("Span " + span);
				if(a.getStart().getMinute() < 30) {
					AppointmentBox apb = new AppointmentBox(a);
					appointmentsGrid.add(apb.getBox(), 1, startHr);
					appointmentsGrid.setRowSpan(apb.getBox(), span);
				}else {
					AppointmentBox apb = new AppointmentBox(a);
					appointmentsGrid.add(apb.getBox(), 1, startHr + 1);
				}
			}
		}

	}
}
