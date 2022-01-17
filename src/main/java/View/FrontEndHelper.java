package View;

import Model.Event;
import Model.Modul;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FrontEndHelper {
    /**
     * Initializing calender view.
     *
     * @param calendarView the calendar view
     */
    public void initializingCalenderView(CalendarView calendarView, Calendar calstudy, Calendar calschool) {

        CalendarSource myCalendarSource = new CalendarSource("Planer");
        myCalendarSource.getCalendars().addAll(calstudy, calschool);
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

    /**
     * Gets left side split pane.
     * @param BtCreateEvent the bt create event
     * @param BtCreateModul the bt create modul
     * @return the left side split pane
     */

    public Pane getLeftSideSplitPane(Button BtCreateEvent, Button BtCreateModul, Button BtDeleteModul, ListView listbox) {

        BorderPane BPLayoutLeft = new BorderPane();
        VBox VbButtonBox = new VBox();
        VbButtonBox.getChildren().addAll(BtCreateEvent, BtCreateModul, BtDeleteModul);
        BPLayoutLeft.setTop(VbButtonBox);
        BPLayoutLeft.setBottom( listbox);
        Pane PBar = new Pane(BPLayoutLeft);// ist die toolbar
        VbButtonBox.setMinWidth(300);
        return PBar;
    }



    /**
     * Replace button name string.
     *
     * @param string the string
     * @return the string
     */
    public String replaceButtonName(String string) {
        return string.replaceAll("(?<=\\w)\\n.*", "");
    }









}