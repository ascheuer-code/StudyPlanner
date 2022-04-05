package View;

import DataAccess.ModulUpdateDB;
import DataAccess.SaveEventDB;
import i18n.i18n;
import Model.Event;
import Model.Modul;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static Helper.LocalDateTimeConverter.convertEventToEntry;

public class NewEvent {

    public void createNewEvent(List<Modul> modulelist, List<Event> eventlist,
            Calendar Studyplan, Calendar SchoolTimeTable, EntityManager entityManager,
            EntityTransaction entityTransaction) {
        Stage stage = new Stage();
        VBox layout = new VBox();

        Text requiredTxt = new Text(i18n.get("RequiredTxt"));
        Text requiredIntervallTxt = new Text(i18n.get("RequiredIntervallTxt"));

        Text modulNameRequiredTxt = new Text(i18n.get("ModulNameRequiredTxt"));
        ComboBox<?> ChPickerModulName = getChPickerModulName(modulelist);

        Text eventCalendarNameRequiredTxt = new Text(i18n.get("EventCalendarNameRequiredTxt"));
        ComboBox<?> ChPickerCalendar = getChPickerCalendar(Studyplan, SchoolTimeTable);

        Text eventDateRequiredTxt = new Text(i18n.get("EventDateRequiredTxt"));
        DatePicker datePicker = getDatePicker();

        Text eventTimeFromRequiredTxt = new Text(i18n.get("EventTimeFromRequiredTxt"));
        ComboBox<LocalTime> ChPickerStartTime = getChPickerStartTime();

        Text eventTimeToRequiredTxt = new Text(i18n.get("EventTimeToRequiredTxt"));
        ComboBox<LocalTime> ChPickerEndTime = getChPickerEndTime();

        Text eventRhytmusTxt = new Text(i18n.get("EventRhytmusTxt"));
        ComboBox<Integer> ChRepetition = getChRepetition();

        Text eventRhytmusDateTxt = new Text(
                i18n.get("EventRhytmusDateTxt"));
        DatePicker datePickerRepetition = getDatePicker();

        Text eventDescriptionTxt = new Text(i18n.get("EventDescriptionTxt"));
        TextField TxtFDescriptionField = new TextField();

        Button BtSafeEvent = getBTSafeEventButton(eventlist, ChPickerModulName, ChPickerStartTime, ChPickerEndTime,
                datePicker, ChPickerCalendar, stage, modulelist, Studyplan, SchoolTimeTable, ChRepetition,
                datePickerRepetition, TxtFDescriptionField, entityManager, entityTransaction);

        layout.getChildren().addAll(modulNameRequiredTxt, ChPickerModulName, eventCalendarNameRequiredTxt, ChPickerCalendar, eventDateRequiredTxt,
                datePicker, eventTimeFromRequiredTxt, ChPickerStartTime, eventTimeToRequiredTxt, ChPickerEndTime, eventRhytmusTxt, ChRepetition,
                eventRhytmusDateTxt, datePickerRepetition, eventDescriptionTxt, TxtFDescriptionField, requiredTxt,
                requiredIntervallTxt);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout);
        borderPane.setBottom(BtSafeEvent);
        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.setHeight(450);
        stage.setWidth(700);
        stage.show();
        scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());
    }

    public void createNewFillerEvent(List<Modul> modulelist, List<Event> eventlist,
            com.calendarfx.model.Calendar Studyplan, EntityManager entityManager, EntityTransaction entityTransaction) {
        Stage stage = new Stage();
        VBox layout = new VBox();

        Text fillerDateTxt = new Text(i18n.get("FillerDateTxt"));
        DatePicker datePicker = getDatePicker();

        Text fillerFromTxt = new Text(i18n.get("FillerFromTxt"));
        ComboBox<LocalTime> ChPickerStartTime = getChPickerStartTime();

        Text fillerToTxt = new Text(i18n.get("FillerToTxt"));
        ComboBox<LocalTime> ChPickerEndTime = getChPickerEndTime();

        Text fillerEventRhytmusTxt = new Text(i18n.get("FillerEventRhytmusTxt"));
        ComboBox<Integer> ChRepetition = getChRepetition();

        Text fillerEventRhytmusDateTxt = new Text(
                i18n.get("FillerEventRhytmusDateTxt"));
        DatePicker datePickerRepetition = getDatePicker();

        Button BtSafeFillerEvent = getBTSafeFillerEventButton(eventlist, ChPickerStartTime, ChPickerEndTime, datePicker,
                stage, modulelist, Studyplan, ChRepetition, datePickerRepetition, entityManager, entityTransaction);

        layout.getChildren().addAll(fillerDateTxt,
                datePicker, fillerFromTxt, ChPickerStartTime, fillerToTxt, ChPickerEndTime, fillerEventRhytmusTxt, ChRepetition,
                fillerEventRhytmusDateTxt, datePickerRepetition);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout);
        borderPane.setBottom(BtSafeFillerEvent);
        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.setHeight(450);
        stage.setWidth(600);
        stage.show();
        scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());
    }

    public static ComboBox<Modul> getChPickerModulName(List<Modul> modulliste) {
        // Anfang das Feld anlegen Event

        ComboBox<Modul> ChPickerModulName = new ComboBox<>();
        ChPickerModulName.getItems().addAll(modulliste);

        return ChPickerModulName;
    }

    /**
     * Gets ch picker calendar.
     *
     * @return the ch picker calendar
     */
    public static ComboBox<?> getChPickerCalendar(com.calendarfx.model.Calendar Studyplan,
            com.calendarfx.model.Calendar SchoolTimeTable) {

        ComboBox<String> ChPickerCalendar = new ComboBox<>();
        ChPickerCalendar.getItems().addAll(Studyplan.getName());
        ChPickerCalendar.getItems().addAll(SchoolTimeTable.getName());

        ChPickerCalendar.setOnAction((event) -> ChPickerCalendar.getSelectionModel().getSelectedItem());
        return ChPickerCalendar;
    }

    /**
     * Gets date picker.
     *
     * @return the date picker
     */
    public static DatePicker getDatePicker() {

        DatePicker datePicker = new DatePicker();
        Button btPickDate = i18n.buttonForKey("BtPickDate");
        btPickDate.setOnAction(action -> {

        });
        return datePicker;
    }

    /**
     * Gets ch picker start time.
     *
     * @return the ch picker start time
     */
    public static ComboBox<LocalTime> getChPickerStartTime() {

        ComboBox<LocalTime> ChPickerStartTime = new ComboBox<>();
        int startHour = 6;
        int startMinute = 0;
        LocalTime x = LocalTime.of(startHour, startMinute);
        for (int i = 0; i <= 95; i++) {
            ChPickerStartTime.getItems().addAll(x);
            x = x.plusMinutes(15);
        }

        ChPickerStartTime.setVisibleRowCount(10);

        return ChPickerStartTime;
    }

    /**
     * Gets ch picker end time.
     *
     * @return the ch picker end time
     */
    public static ComboBox<LocalTime> getChPickerEndTime() {

        ComboBox<LocalTime> ChPickerEndTime = new ComboBox<>();
        int endHour = 6;
        int endMinute = 15;
        LocalTime y = LocalTime.of(endHour, endMinute);
        for (int i = 0; i <= 95; i++) {
            ChPickerEndTime.getItems().addAll(y);
            y = y.plusMinutes(15);
        }
        ChPickerEndTime.setVisibleRowCount(10);

        return ChPickerEndTime;
    }

    public static ComboBox<Integer> getChRepetition() {
        ComboBox<Integer> ChRepetition = new ComboBox<>();
        int[] tage = { 1, 2, 3, 4, 5, 6, 7, 14 };
        for (int i = 0; i <= tage.length - 1; i++)
            ChRepetition.getItems().addAll(tage[i]);
        return ChRepetition;
    }

    public static Button getBTSafeEventButton(List<Event> eventListe, ComboBox<?> chPickerModulName,
            ComboBox<?> chPickerStartTime, ComboBox<LocalTime> chPickerEndTime, DatePicker datePicker,
            ComboBox<?> chPickerCalendar, Stage stage, List<Modul> Module, Calendar StudyPlan, Calendar SchoolTimeTable,
            ComboBox<Integer> chRepetition, DatePicker datePickerRepetition, TextField txtDescription,
            EntityManager entityManager, EntityTransaction entityTransaction) {

        Button btEventSave = i18n.buttonForKey("BtEventSave");
        btEventSave.setOnAction(action -> {
            CompletableFuture.runAsync(() -> {

                SaveEventDB saveEventDB = new SaveEventDB();

                if (chRepetition.getValue() == null || datePickerRepetition == null) {
                    Event ownEvent = new Event();
                    ownEvent.setTitle(
                            replaceName(chPickerModulName.getValue().toString()) + " \n" + txtDescription.getText());
                    ownEvent.setStartTime(chPickerStartTime.getValue().toString());
                    ownEvent.setEndTime(chPickerEndTime.getValue().toString());
                    ownEvent.setStarDate(datePicker.getValue().toString());
                    ownEvent.setEndDate(datePicker.getValue().toString());

                    Module.stream()
                            .filter(e -> e.getModulname().equals(replaceName(chPickerModulName.getValue().toString())))
                            .forEach(e -> {
                                e.getUuid().add(ownEvent.getId());
                                ModulUpdateDB modulUpdateDB = new ModulUpdateDB();
                                modulUpdateDB.Update(e, entityManager, entityTransaction);

                            });

                    if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Lernplan") {
                        ownEvent.setCalendar("Lernplan");
                        eventListe.add(ownEvent);
                        saveEventDB.insert(ownEvent, entityManager, entityTransaction);
                        Entry<?> entry = convertEventToEntry(ownEvent);
                        StudyPlan.addEntry(entry);

                    } else if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Stundenplan") {
                        ownEvent.setCalendar("Stundenplan");
                        eventListe.add(ownEvent);
                        saveEventDB.insert(ownEvent, entityManager, entityTransaction);
                        Entry<?> entry = convertEventToEntry(ownEvent);
                        SchoolTimeTable.addEntry((entry));

                    }

                }
                if (chRepetition.getValue() != null && datePickerRepetition != null) {
                    List<Event> newEvents = new ArrayList<>();

                    for (LocalDate date = datePicker.getValue(); date.isBefore(datePickerRepetition.getValue()
                            .plusDays(1)); date = date.plusDays(chRepetition.getValue())) {
                        Event ownEvent = new Event();
                        ownEvent.setTitle(replaceName(chPickerModulName.getValue().toString()) + " \n"
                                + txtDescription.getText());
                        ownEvent.setStartTime(chPickerStartTime.getValue().toString());
                        ownEvent.setEndTime(chPickerEndTime.getValue().toString());
                        ownEvent.setStarDate(date.toString());
                        ownEvent.setEndDate(date.toString());
                        // here will be the UUID from the Event added to the Modul

                        Module.stream().filter(
                                e -> e.getModulname().equals(replaceName(chPickerModulName.getValue().toString())))
                                .forEach(e -> {
                                    e.getUuid().add(ownEvent.getId());
                                    ModulUpdateDB modulUpdateDB = new ModulUpdateDB();
                                    modulUpdateDB.Update(e, entityManager, entityTransaction);
                                });

                        Entry<?> entry = convertEventToEntry(ownEvent);
                        if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Lernplan") {
                            ownEvent.setCalendar("Lernplan");
                            eventListe.add(ownEvent);
                            newEvents.add(ownEvent);
                            StudyPlan.addEntry(entry);

                        } else if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Stundenplan") {
                            ownEvent.setCalendar("Stundenplan");
                            eventListe.add(ownEvent);
                            newEvents.add(ownEvent);
                            SchoolTimeTable.addEntry(entry);

                        }

                    }

                    saveEventDB.insert(newEvents, entityManager, entityTransaction);

                }
            });
            stage.close();
        });
        return btEventSave;
    }

    public static Button getBTSafeFillerEventButton(List<Event> eventListe, ComboBox<?> chPickerStartTime,
            ComboBox<LocalTime> chPickerEndTime, DatePicker datePicker, Stage stage, List<Modul> Module,
            Calendar StudyPlan, ComboBox<Integer> chRepetition, DatePicker datePickerRepetition,
            EntityManager entityManager, EntityTransaction entityTransaction) {

        Button btEventSaveFiller = i18n.buttonForKey("BtEventSave");
        btEventSaveFiller.setOnAction(action -> {
            CompletableFuture.runAsync(() -> {


                SaveEventDB saveEventDB = new SaveEventDB();

                if (chRepetition.getValue() == null || datePickerRepetition == null) {
                    Event ownEvent = new Event();
                    ownEvent.setTitle("Filler");
                    ownEvent.setStartTime(chPickerStartTime.getValue().toString());
                    ownEvent.setEndTime(chPickerEndTime.getValue().toString());
                    ownEvent.setStarDate(datePicker.getValue().toString());
                    ownEvent.setEndDate(datePicker.getValue().toString());
                    ownEvent.setCalendar("Lernplan");

                    eventListe.add(ownEvent);
                    saveEventDB.insert(ownEvent, entityManager, entityTransaction);
                    Entry<?> entry = convertEventToEntry(ownEvent);
                    StudyPlan.addEntry(entry);

                }
                if (chRepetition.getValue() != null && datePickerRepetition != null) {
                    List<Event> newEvents = new ArrayList<>();

                    for (LocalDate date = datePicker.getValue(); date.isBefore(datePickerRepetition.getValue()
                            .plusDays(1)); date = date.plusDays(chRepetition.getValue())) {
                        Event ownEvent = new Event();
                        ownEvent.setTitle("Filler");
                        ownEvent.setStartTime(chPickerStartTime.getValue().toString());
                        ownEvent.setEndTime(chPickerEndTime.getValue().toString());
                        ownEvent.setStarDate(date.toString());
                        ownEvent.setEndDate(date.toString());
                        ownEvent.setCalendar("Lernplan");

                        eventListe.add(ownEvent);
                        newEvents.add(ownEvent);
                        Entry<?> entry = convertEventToEntry(ownEvent);
                        StudyPlan.addEntry(entry);

                    }
                    saveEventDB.insert(newEvents, entityManager, entityTransaction);

                }
            });
            stage.close();
        });
        return btEventSaveFiller;
    }

    public static String replaceName(String string) {
        return string.replaceAll("(?<=\\w)\\n.*", "");
    }

}
