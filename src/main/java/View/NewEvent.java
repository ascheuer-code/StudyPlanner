package View;

import Model.Event;
import Model.Modul;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static Helper.LocalDateTimeConverter.convertEventToEntry;

public class NewEvent {


    public static void createNewEvent(List<Modul> modulelist, List<Event> eventlist, com.calendarfx.model.Calendar Studyplan, Calendar SchoolTimeTable) {
        Stage stage = new Stage();
        VBox layout = new VBox();

        Text TxtModulName = new Text("Modulname");
        ChoiceBox<?> ChPickerModulName = getChPickerModulName(modulelist);

        Text TxtCalendar = new Text("Kalender");
        ChoiceBox<?> ChPickerCalendar = getChPickerCalendar(Studyplan, SchoolTimeTable);
        // Datum des erst Eintrages
        Text TxtDate = new Text("Datum");
        DatePicker datePicker = getDatePicker();

        Text TxtStartTime = new Text("Anfangszeit");
        ChoiceBox<?> ChPickerStartTime = getChPickerStartTime();

        Text TxtEndTime = new Text("Endzeit");
        ChoiceBox<?> ChPickerEndTime = getChPickerEndTime();

        Text TxtRepetition = new Text("Wiederholungsrythmus in Tagen ");
        ChoiceBox<Integer> ChRepetition = getChRepetition();

        Text TxtRepetitionEnd = new Text(" Bitte Wählen sie aus bis zu welchem Datum der Wiederholungsrythmus durchgeführt werden soll  ");
        DatePicker datePickerRepetition = getDatePicker();


        Text TxtDescription = new Text("Beschreibung");
        TextField TxtFDescription = new TextField();

        Button BtSafeEvent = getBTSafeEventButton(eventlist, ChPickerModulName, ChPickerStartTime, ChPickerEndTime, datePicker, ChPickerCalendar, stage, modulelist, Studyplan, SchoolTimeTable, ChRepetition, datePickerRepetition, TxtDescription);

        layout.getChildren().addAll(TxtModulName, ChPickerModulName, TxtCalendar, ChPickerCalendar, TxtDate,
                datePicker, TxtStartTime, ChPickerStartTime, TxtEndTime, ChPickerEndTime, TxtRepetition, ChRepetition, TxtRepetitionEnd, datePickerRepetition, TxtDescription, TxtFDescription);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout);
        borderPane.setBottom(BtSafeEvent);
        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.setHeight(450);
        stage.setWidth(600);
        stage.show();

    }

    public static ChoiceBox<Modul> getChPickerModulName(List<Modul> modulliste) {
        // Anfang das Feld anlegen Event

        ChoiceBox<Modul> ChPickerModulName = new ChoiceBox<>();
        ChPickerModulName.getItems().addAll(modulliste);

        return ChPickerModulName;
    }

    /**
     * Gets ch picker calendar.
     *
     * @return the ch picker calendar
     */
    public static ChoiceBox<?> getChPickerCalendar(com.calendarfx.model.Calendar Studyplan, com.calendarfx.model.Calendar SchoolTimeTable) {

        ChoiceBox<String> ChPickerCalendar = new ChoiceBox<>();
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
        Button button1 = new Button("Datum wählen");
        button1.setOnAction(action -> {

        });
        return datePicker;
    }

    /**
     * Gets ch picker start time.
     *
     * @return the ch picker start time
     */
    public static ChoiceBox<?> getChPickerStartTime() {

        ChoiceBox<LocalTime> ChPickerStartTime = new ChoiceBox<>();
        int stundeanfang = 8;
        int minuteanfang = 0;
        LocalTime x = LocalTime.of(stundeanfang, minuteanfang);
        for (int i = 0; i <= 24; i++) {
            ChPickerStartTime.getItems().addAll(x);
            x = x.plusMinutes(30);
        }

        return ChPickerStartTime;
    }

    /**
     * Gets ch picker end time.
     *
     * @return the ch picker end time
     */
    public static ChoiceBox<?> getChPickerEndTime() {

        ChoiceBox<LocalTime> ChPickerEndTime = new ChoiceBox<>();
        int stundeende = 8;
        int minuteende = 30;
        LocalTime y = LocalTime.of(stundeende, minuteende);
        for (int i = 0; i <= 24; i++) {
            ChPickerEndTime.getItems().addAll(y);
            y = y.plusMinutes(30);
        }

        return ChPickerEndTime;
    }

    public static ChoiceBox<Integer> getChRepetition() {
        ChoiceBox<Integer> ChRepetition = new ChoiceBox();
        int[] tage = {1, 2, 3, 4, 5, 6, 7, 14, 28};
        for (int i = 0; i <= tage.length - 1; i++)
            ChRepetition.getItems().addAll(tage[i]);
        return ChRepetition;
    }

    public static Button getBTSafeEventButton(List<Event> eventListe, ChoiceBox<?> chPickerModulName, ChoiceBox<?> chPickerStartTime, ChoiceBox chPickerEndTime, DatePicker datePicker, ChoiceBox<?>
            chPickerCalendar, Stage stage, List<Modul> Module, Calendar StudyPlan, Calendar SchoolTimeTable, ChoiceBox<Integer> chRepetition, DatePicker datePickerRepetition, Text txtDescription) {

        Button button = new Button("Event sichern :");
        button.setOnAction(action -> {

            if (chRepetition.getValue() == null || datePickerRepetition == null) {
                Event ownEvent = new Event();
                ownEvent.setTitle(replaceName(chPickerModulName.getValue().toString()) + " " + txtDescription.getText());
                ownEvent.setStartTime(chPickerStartTime.getValue().toString());
                ownEvent.setEndTime(chPickerEndTime.getValue().toString());
                ownEvent.setStarDate(datePicker.getValue().toString());
                ownEvent.setEndDate(datePicker.getValue().toString());
                // here will be the UUID from the Event added to the Modul

                Module.stream().filter(e -> e.getModulname().equals(replaceName(chPickerModulName.getValue().toString()))).forEach(e -> e.getUuid().add(ownEvent.getId()));
                eventListe.add(ownEvent);
                Entry<?> entry = convertEventToEntry(ownEvent);
                if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Lernplan") {
                    StudyPlan.addEntry(entry);
                } else if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Stundenplan") {
                    SchoolTimeTable.addEntry((entry));
                }
            }
            if (chRepetition.getValue() != null && datePickerRepetition != null) {

                for (LocalDate date = datePicker.getValue(); date.isBefore(datePickerRepetition.getValue().plusDays(1)); date = date.plusDays(chRepetition.getValue())) {
                    Event ownEvent = new Event();
                    ownEvent.setTitle(replaceName(chPickerModulName.getValue().toString()) + " " + txtDescription.getText());
                    ownEvent.setStartTime(chPickerStartTime.getValue().toString());
                    ownEvent.setEndTime(chPickerEndTime.getValue().toString());
                    ownEvent.setStarDate(date.toString());
                    ownEvent.setEndDate(date.toString());
                    // here will be the UUID from the Event added to the Modul

                    Module.stream().filter(e -> e.getModulname().equals(replaceName(chPickerModulName.getValue().toString()))).forEach(e -> e.getUuid().add(ownEvent.getId()));
                    eventListe.add(ownEvent);
                    Entry<?> entry = convertEventToEntry(ownEvent);
                    if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Lernplan") {
                        StudyPlan.addEntry(entry);
                    } else if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Stundenplan") {
                        SchoolTimeTable.addEntry((entry));
                    }
                }
            }


            stage.close();
        });
        return button;
    }

    public static String replaceName(String string) {
        return string.replaceAll("(?<=\\w)\\n.*", "");
    }

}
