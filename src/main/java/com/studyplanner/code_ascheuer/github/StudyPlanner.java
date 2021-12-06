package com.studyplanner.code_ascheuer.github;

import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The type Study planner.
 */
public class StudyPlanner extends Application {

    /**
     * The entry point of application.
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        CalendarView calendarView = new CalendarView(); // <1>

        Calendar birthdays = new Calendar("Birthdays"); // <2>
        Calendar holidays = new Calendar("Holidays");

        birthdays.setStyle(Style.STYLE1); // <3>
        holidays.setStyle(Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars"); // <4>
        myCalendarSource.getCalendars().addAll(birthdays, holidays);

        calendarView.getCalendarSources().addAll(myCalendarSource); // <5>

        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        Scene scene = new Scene(calendarView);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1300);
        primaryStage.setHeight(1000);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}