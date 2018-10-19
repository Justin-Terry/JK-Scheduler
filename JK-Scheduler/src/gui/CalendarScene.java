package gui;

import java.util.ArrayList;

import application.Main;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CalendarScene {
	private GridPane calendarGrid;
	private GridPane dayLabels;
	private BorderPane borderPane;
	private int calendarLayout = 0; // 0 = day, 1 = week, 2 = month
	private Insets cellMargin = new Insets(1);
	private Insets gridPadding = new Insets(10);
	private String[] dayNames = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	private String[] hourStrings = { "12:00am", "1:00am", "2:00am", "3:00am", "4:00am", "5:00am", "6:00am", "7:00am",
			"8:00am", "9:00am", "10:00am", "11:00am", "12:00pm", "1:00pm", "2:00pm", "3:00pm", "4:00pm", "5:00pm",
			"6:00pm", "7:00pm", "8:00pm", "9:00pm", "10:00pm", "11:00pm" };

	private String calendarColor;
	private ArrayList<CalendarBox> boxes;

	public CalendarScene() {
		calendarColor = Main.getSettings().getCalendarColor();
	}

	public BorderPane getCalendarPane(int n) {
		if (n == 2) {
			createMonthScene("January");

		}
		if (n == 1) {
			createWeekScene();

		}
		if (n == 0) {
			createDayScene();
		}
		return borderPane;

	}


	private void createMonthScene(String month) {
		boxes = new ArrayList<CalendarBox>();
		Label monthLabel = new Label(month);
		monthLabel.setStyle("-fx-font: 64 arial;");

		int numRows = 6;
		int numCols = 7;

		calendarGrid = new GridPane();
		borderPane = new BorderPane();
		VBox container = new VBox();
		container.setPadding(new Insets(20));
		container.setAlignment(Pos.CENTER);
		borderPane.setTop(monthLabel);
		monthLabel.setAlignment(Pos.CENTER);
		borderPane.setCenter(container);
		container.getChildren().addAll(monthLabel, calendarGrid);

		ColumnConstraints calendarBoxColCons = new ColumnConstraints();
		RowConstraints calendarBoxRowCons = new RowConstraints();
		RowConstraints dayNameRowConstraints = new RowConstraints();

		calendarBoxColCons.setFillWidth(true);
		calendarBoxColCons.setHgrow(Priority.ALWAYS);
		calendarBoxRowCons.setPercentHeight(100 / (numRows - 3));
		calendarBoxRowCons.setFillHeight(true);
		dayNameRowConstraints.setPercentHeight(5);

		calendarGrid.getRowConstraints().add(dayNameRowConstraints);

		for (int i = 1; i < numRows + 1; i++) {
			calendarGrid.getRowConstraints().add(calendarBoxRowCons);
		}
		for (int i = 0; i < numCols; i++) {
			calendarGrid.getColumnConstraints().add(calendarBoxColCons);
		}

		int d = 1;

		for (int i = 0; i < dayNames.length; i++) {
			Label dayLabel = new Label(dayNames[i]);
			calendarGrid.add(dayLabel, i, 0);
			calendarGrid.setHalignment(dayLabel, HPos.CENTER);
			calendarGrid.setFillWidth(dayLabel, true);
		}

		for (int i = 1; i < numRows; i++) {
			for (int j = 0; j < 7; j++) {
				if (d < 32) {

					CalendarBox temp = new CalendarBox(d, calendarColor);
					boxes.add(temp);
					Pane tempRec = temp.getCalendarBox();
					calendarGrid.add(tempRec, j, i);
					calendarGrid.setMargin(tempRec, cellMargin);
					d++;
				}
			}
		}

	}

	private void createWeekScene() {
		boxes = new ArrayList<CalendarBox>();
		int numRows = 2;
		int numCols = 7;
		borderPane = new BorderPane();
		calendarGrid = new GridPane();
		calendarGrid.setStyle("-fx-background-color: #ffff55;");
		calendarGrid.setPadding(gridPadding);

		borderPane.setCenter(calendarGrid);
		borderPane.getCenter().setStyle("-fx-background-color:#efefef;");
		ColumnConstraints calendarBoxColCons = new ColumnConstraints();
		RowConstraints calendarBoxRowCons = new RowConstraints();
		RowConstraints dayNameRowConstraints = new RowConstraints();

		calendarBoxColCons.setFillWidth(true);
		calendarBoxColCons.setPercentWidth(100);
		calendarBoxColCons.setHgrow(Priority.ALWAYS);
		calendarBoxRowCons.setVgrow(Priority.ALWAYS);
		calendarBoxRowCons.setFillHeight(true);

		calendarGrid.getRowConstraints().add(dayNameRowConstraints);
		calendarGrid.getRowConstraints().add(calendarBoxRowCons);

		for (int i = 0; i < numCols; i++) {
			calendarGrid.getColumnConstraints().add(calendarBoxColCons);
		}

		int d = 0;

		for (int i = 0; i < dayNames.length; i++) {
			Label dayLabel = new Label(dayNames[i]);
			calendarGrid.add(dayLabel, i, 0);
			calendarGrid.setHalignment(dayLabel, HPos.CENTER);
			calendarGrid.setFillWidth(dayLabel, true);
		}

		for (int i = 1; i < numRows; i++) {
			for (int j = 0; j < 7; j++) {
				if (d < numCols) {
					CalendarBox temp = new CalendarBox(d, calendarColor);
					boxes.add(temp);
					Pane tempRec = temp.getCalendarBox();
					calendarGrid.add(tempRec, j, i);
					calendarGrid.setMargin(tempRec, cellMargin);
					d++;
				}
			}
		}

	}

	private void createDayScene() {
		boxes = new ArrayList<CalendarBox>();
		int numRows = 24;
		int numCols = 2;
		borderPane = new BorderPane();
		calendarGrid = new GridPane();
		calendarGrid.setPadding(gridPadding);

		borderPane.setCenter(calendarGrid);
		ColumnConstraints calendarBoxColCons = new ColumnConstraints();
		RowConstraints calendarBoxRowCons = new RowConstraints();
		calendarBoxRowCons.setFillHeight(true);
		calendarBoxRowCons.setPercentHeight(100);
		ColumnConstraints hourColConstraints = new ColumnConstraints();
		hourColConstraints.setPercentWidth(5);
		calendarBoxColCons.setPercentWidth(95);
		calendarBoxColCons.setFillWidth(true);
		


		for (int i = 0; i < hourStrings.length; i++) {
			calendarGrid.getRowConstraints().add(calendarBoxRowCons);
			Label temp = new Label(hourStrings[i]);
			calendarGrid.add(temp, 0, i);
			CalendarBox tempBox = new CalendarBox(i,calendarColor);
			calendarGrid.add(tempBox.getCalendarBox(), 1, i);	
			calendarGrid.setMargin(tempBox.getCalendarBox(), cellMargin);
			boxes.add(tempBox);
		}
		calendarGrid.getColumnConstraints().add(hourColConstraints);
		for(int i = 1; i < numCols;i++) {
			calendarGrid.getColumnConstraints().add(calendarBoxColCons);
		}

	}

	public void setCalendarColor(String c) {
		calendarColor = c;
		for (CalendarBox i : boxes) {
			i.setDateBoxColor(c);
		}
	}

}
