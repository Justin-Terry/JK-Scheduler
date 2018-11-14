package gui;

import java.time.LocalDate;

import application.Main;
import application.Settings;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WindowManager {
	private Stage mainStage;
	private MenuController menuCon;
	private BorderPane bp;
	private SceneController sceneCon;
	private Settings settings;
	private CalendarView calendarView;
	
	public WindowManager(Stage primaryStage) {
		settings = Main.getSettings();
		menuCon = new MenuController();
		sceneCon = new SceneController();
		mainStage = primaryStage;
		mainStage.setTitle("JK Scheduler");
		mainStage.setMaximized(true);
		bp = new BorderPane();
		Scene mainScene = new Scene(bp, mainStage.getHeight(), mainStage.getWidth());
		bp.setTop(menuCon.getMenuBar());
		setMainStage(mainScene);
		showMainStage();	
	}
	
	public void showMainStage() {
		mainStage.show();
	}
	
	public void showPopUpStage(Scene sce, String title) {
		Stage popUp = new Stage();
		popUp.setTitle(title);
		popUp.setScene(sce);
		popUp.show();
	}
	
	public void showCreateUser() {
		CreateUserWindow newUser = new CreateUserWindow();
	}
	
	public void showChangeUsername() {
		ChangeUserNameWindow changeUser = new ChangeUserNameWindow();
	}
	
	public void showChangePassword() {
		ChangePasswordWindow changePass = new ChangePasswordWindow();
	}

	public void showChangeUserInfo() { ChangeUserInfoWindow changeInfo = new ChangeUserInfoWindow(); }
	
	public void showSettingsWindow() {
		SettingsWindow settingsWindow = new SettingsWindow();
		settingsWindow.show();
	}
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	public void setMainStage(Scene s) {
		mainStage.setScene(s);
	}
	
	public void displayLogin(Stage parentStage) {
                bp.setEffect(new BoxBlur(3,3,10));
		LoginWindow login = new LoginWindow(parentStage, bp);
		login.getLoginWindow().show();
	}
	
	public void showCreateAppointment() {
		CreateAppointmentWindow caw = new CreateAppointmentWindow();
	}
	
	public void showCancelAppointment() {
		CancelAppointmentWindow caw = new CancelAppointmentWindow();
	}
	
	public void showModifyAppointment() {
//		ModifyAppointmentWindow maw = new ModifyAppointmentWindow();
	}
	
	public CalendarView getCalendarView() {
		return calendarView;
	}
	
	public void setCalendarView() {
		int viewType = Main.getSettings().getCalendarRange();
		switch(viewType) {
		case 0:
			calendarView = new DayView();
			bp.setCenter(calendarView.getDayView());
			break;
		case 1:
			calendarView = new WeekView();
			bp.setCenter(calendarView.getCalendarDisplay());
			break;
		case 2:
			calendarView = new MonthView();
			bp.setCenter(calendarView.getCalendarDisplay());
			break;
                default:
                        calendarView = new MonthView();
                        bp.setCenter(calendarView.getCalendarDisplay());
                        break;
		}
	}
	
	public void setCalendarView(LocalDate date) {
		int viewType = Main.getSettings().getCalendarRange();
		switch(viewType) {
		case 0:
			calendarView = new DayView(date);
			bp.setCenter(calendarView.getDayView());
			break;
		case 1:
			calendarView = new WeekView(date);
			bp.setCenter(calendarView.getCalendarDisplay());
			break;
		case 2:
			calendarView = new MonthView(date);
			bp.setCenter(calendarView.getCalendarDisplay());
			break;
			
		}
	}
	
	public CalendarView getCalenderView() {
		return calendarView;
	}
	
	
}
