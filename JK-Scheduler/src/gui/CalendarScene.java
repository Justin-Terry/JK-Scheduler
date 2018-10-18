package gui;

import application.Main;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class CalendarScene {
	Scene theScene;
	GridPane calendarGrid;
	int calendarLayout = 0; // 0 = day, 1 = week, 2 = month

	public CalendarScene() {

	}

	public Scene getCalendarScene(int n) {
		if (n == 2) {
			createMonthScene();
		}

		return theScene;
	}

	private void createMonthScene() {
		final int WIDTH = 800,
				HEIGHT = 600;
		int numRows = 5;
		int numCols = 7;
		calendarGrid = new GridPane();
		calendarGrid.setPrefSize(WIDTH, HEIGHT);
		calendarGrid.setPadding(new Insets(50));
		calendarGrid.setGridLinesVisible(true);
		theScene = new Scene(calendarGrid, HEIGHT, WIDTH);

		ColumnConstraints calendarBoxColCons = new ColumnConstraints();
		RowConstraints calendarBoxRowCons = new RowConstraints();
		calendarBoxColCons.setPercentWidth(100 / numCols);
		calendarBoxRowCons.setPercentHeight(100 / numRows);
		for (int i = 0; i < numRows; i++) {
			calendarGrid.getRowConstraints().add(calendarBoxRowCons);
		}
		for (int i = 0; i < numCols; i++) {
			calendarGrid.getColumnConstraints().add(calendarBoxColCons);
		}

		int d = 1;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				if (d < 32) {
					Label dateLabel = new Label(new CalendarBox(d).getDateNumberString());
					double cellWidth = WIDTH/numRows,
							cellHeight = HEIGHT/numCols;

					calendarGrid.add(dateLabel, j, i);
					calendarGrid.setMargin(dateLabel,
											new Insets(0, 0, cellHeight*1.0, cellWidth*0.05));
					d++;
				}
			}
		}

	}

}
