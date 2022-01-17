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

import java.time.LocalTime;
import java.util.List;

import static Helper.LocalDateTimeConverter.convertEventToEntry;

public class NewEvent {
    /**
     * Neues event
     */
    public void neuesEvent(List Module,List Events ,String NameModul, Calendar StudyPlan, Calendar SchoolTimeTable, LocalTime StartTimeEvent, LocalTime EndTimeEvent) {
        NewEvent newEvent = new NewEvent();
        Stage stage = new Stage();
        VBox layout = new VBox();

        Text TxtModulName = new Text("Modulname");
        ChoiceBox<?> ChPickerModulName = newEvent.getChPickerModulName(Module, NameModul);
        Text TxtCalendar = new Text("Kalender");
        ChoiceBox<?> ChPickerCalendar = newEvent.getChPickerCalendar(StudyPlan , SchoolTimeTable);
        // Datum des erst Eintrages
        Text TxtDate = new Text("Datum");
        DatePicker datePicker = newEvent.getDatePicker();

        Text TxtStartTime = new Text("Anfangszeit");
        ChoiceBox<?> ChPickerStartTime = newEvent.getChPickerStartTime(StartTimeEvent);

        Text TxtEndTime = new Text("Endzeit");
        ChoiceBox<?> ChPickerEndTime = newEvent.getChPickerEndTime(EndTimeEvent);

        Text TxtRepetition = new Text("Wiederholungsrythmus in Tagen ");
        ChoiceBox ChRepetition = newEvent.getChRepetition();

        Text TxtRepetitionEnd = new Text(" Bitte Wählen sie aus bis zu welchem Datum der Wiederholungsrythmus durchgeführt werden soll  ");
        DatePicker datePickerRepetition = newEvent.getDatePicker();


        Text TxtDescription = new Text("Beschreibung");
        TextField TxtFDescription = new TextField();

        Button BtSafeEvent = getBTSafeEventButton(TxtFDescription, datePicker,
                ChPickerCalendar, stage, ChRepetition, datePickerRepetition, NameModul,StartTimeEvent,EndTimeEvent,Events,StudyPlan,SchoolTimeTable,Module);

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
    /**
     * Gets bt safe event button.
     *
     * @param txtFDescription      the txt f description
     * @param datePicker           the date picker
     * @param chPickerCalendar     the ch picker calendar
     * @param stage                the stage
     * @param chRepetition
     * @param datePickerRepetition
     * @return the bt safe event button
     */
    public Button getBTSafeEventButton(TextField txtFDescription, DatePicker datePicker, ChoiceBox<?>
            chPickerCalendar, Stage stage, ChoiceBox chRepetition, DatePicker datePickerRepetition, String NameModul,LocalTime StartTimeEvent,LocalTime EndTimeEvent,List Events,Calendar StudyPlan, Calendar SchoolTimeTable, List<Modul>Module) {

        Button button = new Button("Event sichern :");
        button.setOnAction(action -> {


            Event ownEvent = new Event();
            ownEvent.setTitle(NameModul + " " + txtFDescription.getText());
            ownEvent.setStartTime(StartTimeEvent.toString());
            ownEvent.setEndTime(EndTimeEvent.toString());
            ownEvent.setStarDate(datePicker.getValue().toString());
            ownEvent.setEndDate(datePicker.getValue().toString());

            // here will be the UUID from the Event added to the Modul
            Module.stream().filter(e -> e.getModulname().equals(NameModul)).forEach(e -> e.getUuid().add(ownEvent.getId()));

            Events.add(ownEvent);
            Entry<?> entry = convertEventToEntry(ownEvent);


            System.out.println(ownEvent + " " + entry);

            if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Lernplan") {
                StudyPlan.addEntry(entry);
            } else if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Stundenplan") {
                SchoolTimeTable.addEntry((entry));
            }

            stage.close();

        });
        return button;
    }

    /**
     * Gets ch picker modul name.
     *
     * @return the ch picker modul name
     */
    public ChoiceBox<?> getChPickerModulName(List Module,String NameModul) {
        // Anfang das Feld anlegen Event

        ChoiceBox<Object> ChPickerModulName = new ChoiceBox<>();
        for (Object x : Module) {
            ChPickerModulName.getItems().addAll(x);
        }

        ChPickerModulName.setOnAction((event) -> {
            Modul x = (Modul) ChPickerModulName.getSelectionModel().getSelectedItem();
            setModulNamefuerUebergabe(x,NameModul);

        });
        return ChPickerModulName;
    }


    public ChoiceBox<?> getChRepetition() {
        ChoiceBox ChRepetition = new ChoiceBox();
        int[] tage = {1, 2, 3, 4, 5, 6, 7, 14, 28};
        for (int i = 0; i <= tage.length - 1; i++)
            ChRepetition.getItems().addAll(tage[i]);
        return ChRepetition;
    }
    /**
     * Gets ch picker calendar.
     *
     * @return the ch picker calendar
     */
    public ChoiceBox<?> getChPickerCalendar( Calendar StudyPlan, Calendar SchoolTimeTable) {

        ChoiceBox<String> ChPickerCalendar = new ChoiceBox<>();
        ChPickerCalendar.getItems().addAll(StudyPlan.getName());
        ChPickerCalendar.getItems().addAll(SchoolTimeTable.getName());

        ChPickerCalendar.setOnAction((event) -> ChPickerCalendar.getSelectionModel().getSelectedItem());
        return ChPickerCalendar;
    }
    /**
     * Gets ch picker start time.
     *
     * @return the ch picker start time
     */
    public ChoiceBox<?> getChPickerStartTime(LocalTime StartTimeEvent) {

        ChoiceBox<LocalTime> ChPickerStartTime = new ChoiceBox<>();
        int stundeanfang = 8;
        int minuteanfang = 0;
        LocalTime x = LocalTime.of(stundeanfang, minuteanfang);
        for (int i = 0; i <= 24; i++) {
            ChPickerStartTime.getItems().addAll(x);
            x = x.plusMinutes(30);
        }
        ChPickerStartTime.setOnAction((event) -> {
            LocalTime beginnzeit = ChPickerStartTime.getSelectionModel().getSelectedItem();
            setStartTimeEvent(beginnzeit,StartTimeEvent);
        });
        return ChPickerStartTime;
    }
    /**
     * Gets ch picker end time.
     *
     * @return the ch picker end time
     */
    public ChoiceBox<?> getChPickerEndTime( LocalTime EndTimeEvent) {

        ChoiceBox<LocalTime> ChPickerEndTime = new ChoiceBox<>();
        int stundeende = 8;
        int minuteende = 30;
        LocalTime y = LocalTime.of(stundeende, minuteende);
        for (int i = 0; i <= 24; i++) {
            ChPickerEndTime.getItems().addAll(y);
            y = y.plusMinutes(30);
        }
        ChPickerEndTime.setOnAction((event) -> {
            LocalTime zeitende = ChPickerEndTime.getSelectionModel().getSelectedItem();
            setEndTimeEvent(zeitende, EndTimeEvent);
        });
        return ChPickerEndTime;
    }
    /**
     * Gets date picker.
     *
     * @return the date picker
     */
    public DatePicker getDatePicker() {

        DatePicker datePicker = new DatePicker();
        Button button1 = new Button("Datum wählen");
        button1.setOnAction(action -> {

        });
        return datePicker;
    }

    /**
     * Sets modul namefuer uebergabe.
     *
     * @param x the x
     */
    public void setModulNamefuerUebergabe(Modul x, String NameModul) {
        NameModul = x.getModulname();

    }
    /**
     * Sets start time event.
     *
     * @param x the x
     */
    public void setStartTimeEvent(LocalTime x,LocalTime StartTimeEvent) {
        StartTimeEvent = x;
    }

    /**
     * Sets end time event.
     *
     * @param x the x
     */
    public void setEndTimeEvent(LocalTime x,LocalTime EndTimeEvent) {
        EndTimeEvent = x;
    }
}
