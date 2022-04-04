package View;

import Model.Modul;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * helper class
 */
public class Helper {

    /**
     * Change list box button text.
     *
     * @param item
     *         the item
     */
    public void changeListBoxButtonText(Modul item, ListView<Button> listbox) {
        Platform.runLater(() -> {

            listbox.getItems()
                    .stream()
                    .filter(e -> replaceButtonName(e.getText().trim()).equals(item.getModulname().trim()))
                    .forEach(e -> e.setText(item.toString2()));

        });
    }

    /**
     * Replace button name
     *
     * @param string
     *         the string
     *
     * @return the string
     */
    public String replaceButtonName(String string) {
        return string.replaceAll("(?<=\\w)\\n.*", "");
    }

    /**
     * Initializing calender view.
     *
     * @param calendarView
     *         the calendar view
     */
    public void initializingCalenderView(CalendarView calendarView, Calendar StudyPlan, Calendar SchoolTimeTable) {

        CalendarSource myCalendarSource = new CalendarSource("Planer");
        myCalendarSource.getCalendars().addAll(StudyPlan, SchoolTimeTable);
        calendarView.getCalendarSources().addAll(myCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @SuppressWarnings({"InfiniteLoopStatement", "BusyWait"})
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
    }
}
