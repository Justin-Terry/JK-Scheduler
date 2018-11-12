package gui;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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

public class MonthView extends CalendarView {
	private Button nextMonthButton, previousMonthButton;
	private int numberOfDays;
	private int numberOfColumns = 7;
	private int numberOfRows = 8;
	private String monthName;
	private GridPane monthGrid;
	private LocalDate firstDayOfMonth;
	private Insets cellMargin = new Insets(1);
	private ArrayList<DateBox> dateBoxes;

	public MonthView() {
		monthName = calendarsMonth.toString();
		firstDayOfMonth = getFirstDayOfMonth();
		createMonthScene(this.calendarsMonth);
	}

	public MonthView(LocalDate date) {
		calendarDate = date;
		calendarsMonth = date.getMonth();
		calendarDayOfWeek = date.getDayOfWeek();
		monthName = calendarsMonth.toString();
		firstDayOfMonth = getFirstDayOfMonth();
		createMonthScene(this.calendarsMonth);
	}

	public void createMonthScene(Month month) {
		dateBoxes = new ArrayList<DateBox>();
		nextMonthButton = new Button(" >>> ");
		previousMonthButton = new Button(" <<< ");
		monthGrid = new GridPane();
		monthGrid.setPadding(new Insets(25));
		int numberOfDaysAdded = 0;
		numberOfDays = calendarDate.lengthOfMonth();
		int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
		LocalDate addingDate = firstDayOfMonth;

		RowConstraints rowOneCons = new RowConstraints();
		monthGrid.getRowConstraints().add(rowOneCons);
		RowConstraints rowTwoCons = new RowConstraints();
		monthGrid.getRowConstraints().add(rowTwoCons);
		RowConstraints twoDownCons = new RowConstraints();
		twoDownCons.setFillHeight(true);
		twoDownCons.setVgrow(Priority.ALWAYS);
		ColumnConstraints colCons = new ColumnConstraints();
		colCons.setFillWidth(true);
		colCons.setHgrow(Priority.ALWAYS);
		monthGrid.getColumnConstraints().addAll(colCons, colCons, colCons, colCons, colCons, colCons, colCons);

		Label monthNameLabel = new Label(monthName + " " + calendarDate.getYear());
		monthNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
		monthGrid.add(monthNameLabel, 1, 0);
		monthGrid.setColumnSpan(monthNameLabel, 5);
		monthGrid.setHalignment(monthNameLabel, HPos.CENTER);
		monthGrid.add(previousMonthButton, 0, 0);
		monthGrid.add(nextMonthButton, 6, 0);
		monthGrid.setHalignment(previousMonthButton, HPos.CENTER);
		monthGrid.setHalignment(nextMonthButton, HPos.CENTER);

		boolean firstAdded = false;
		for (int j = 2; j < numberOfRows; j++) {
			monthGrid.getRowConstraints().add(twoDownCons);
			for (int i = 0; i < numberOfColumns; i++) {
				if (numberOfDaysAdded < numberOfDays) {
					if (i < firstDayOfWeek && !firstAdded && !(firstDayOfWeek == 7)) {
						DateBox blank = new DateBox();
						blank.setDateBoxColor("999999");
						monthGrid.add(blank.getDateBox(), i, j);
						monthGrid.setMargin(blank.getDateBox(), cellMargin);
					} else {
						firstAdded = true;
						DateBox temp = new DateBox(addingDate);
						monthGrid.add(temp.getDateBox(), i, j);
						dateBoxes.add(temp);
						addingDate = addingDate.plusDays(1);
						numberOfDaysAdded++;
						monthGrid.setMargin(temp.getDateBox(), cellMargin);

					}
				}
				if (j == 2) {
					Label temp = new Label(dayNames.get(i));
					temp.setFont(Font.font("Arial", FontWeight.BOLD, 16));
					monthGrid.add(temp, i, 1);
					monthGrid.setHalignment(temp, HPos.CENTER);
				}
			}
		}

		nextMonthButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().setCalendarView(getDate().plusDays(getDaysToEndOfMonth()+1));
			}
		});
		
		previousMonthButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Main.getWindowManager().setCalendarView(getDate().minusDays(calendarDate.getDayOfMonth()+1));
			}
		});
		

	}

	public GridPane getCalendarDisplay() {
		return monthGrid;
	}

	public LocalDate getFirstDayOfMonth() {
		LocalDate temp = calendarDate;
		long dayNumber = temp.getDayOfMonth();
		temp = temp.minusDays(dayNumber - 1);
		return temp;

	}

	private LocalDate getDate() {
		return calendarDate;
	}
	
	public ScrollPane getDayView() {
		ScrollPane foo = new ScrollPane();
		return foo;
	};

	private int getDaysToEndOfMonth() {
		int now = calendarDate.getDayOfMonth();
		int end = numberOfDays;
		return end - now;
	}

	public void setCalendarColor() {
		for (DateBox d : dateBoxes) {
			d.setDateBoxColor(Main.getSettings().getCalendarColor());
		}
	}

}
