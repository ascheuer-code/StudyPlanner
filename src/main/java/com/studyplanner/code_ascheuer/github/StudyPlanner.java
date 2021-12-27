package com.studyplanner.code_ascheuer.github;

import Model.Event;
import Model.Modul;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import impl.com.calendarfx.view.NumericTextField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Helper.LocalDateTimeConverter.convertEventToEntry;


/**
 * The type Study planner.
 */
public class StudyPlanner extends Application {
    /**
     * The Fenster schon offen zähler.
     */
    int FENSTER_SCHON_OFFEN_ZÄHLER = 0;

    /**
     * Konstanten
     */
    LocalTime StartTimeEvent;
    /**
     * The End time event.
     */
    LocalTime EndTimeEvent;
    /**
     * The Name modul.
     */
    String NameModul;
    /**
     * The Module.
     */
    List<Modul> Module = new ArrayList();
    /**
     * The Events.
     */
    List<Event> Events = new ArrayList();
    /**
     * The Listbox.
     */
    ListView<Button> listbox = new ListView();
    /**
     * The School time table.
     */
    Calendar SchoolTimeTable = new Calendar("Stundenplan");
    /**
     * The Study plan.
     */
    Calendar StudyPlan = new Calendar("Lernplan");

    /**
     * The entry point of application.
     *
     * @param args
     *         the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param stage
     *
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        CalendarView calendarView = new CalendarView();

        //Eventhandler für alle arten von Events
        StudyPlan.addEventHandler(event -> {

            //check added -> Works without description, need to be changed. Module gets the uuid
            if (event.isEntryAdded()) {
                for (Modul modul : Module) {
                    if (modul.getUuid().contains(event.getEntry().getId())) {
                        modul.getEcts().setDuration(modul.getEcts().getDuration().minus(event.getEntry().getDuration()));

                        changeListBoxButtonText(event, modul);
                    }
                }
            }
            //check removed-> Works without description, need to be changed. Module gets the uuid
            if (event.isEntryRemoved()) {
                for (Modul modul : Module) {
                    if (modul.getUuid().contains(event.getEntry().getId())) {
                        modul.getEcts().setDuration(modul.getEcts().getDuration().plus(event.getEntry().getDuration()));

                        changeListBoxButtonText(event, modul);

                        /*
                        removes the Events from the Eventlist
                         */
                        List events = Events.stream().filter(e -> e.getId().equals(event.getEntry().getId())).collect(Collectors.toList());
                        Events.removeAll(events);
                    }
                }
            }
            // Title changed
            if (!event.isEntryAdded() && !event.isEntryRemoved() && event.getOldInterval() == null && !event.getOldText().equals(event.getEntry().getTitle())) {
                Events.stream().filter(e -> e.getId().equals(event.getEntry().getId())).forEach(e -> e.setTitle(event.getEntry().getTitle()));
            }

            // Intervall changed, works fine
            if (!event.isEntryAdded() && !event.isEntryRemoved() && !event.getOldInterval().getDuration().equals(event.getEntry().getInterval().getDuration())) {
                Events.stream().filter(e -> e.getId().equals(event.getEntry().getId())).forEach(e -> {
                    e.setStartTime(event.getEntry().getStartTime().toString());
                    e.setStarDate(event.getEntry().getStartDate().toString());
                    e.setEndTime(event.getEntry().getEndTime().toString());
                    e.setEndDate(event.getEntry().getEndDate().toString());
                });

                Module.stream().filter(e -> e.getUuid().contains(event.getEntry().getId())).forEach(e -> {
                            e.getEcts().setDuration(e.getEcts().getDuration().plus(event.getOldInterval().getDuration().minus(event.getEntry().getDuration())));

                            changeListBoxButtonText(event, e);
                        }
                );
            }
        });


        /**
         * Style der Kalender
         */
        SchoolTimeTable.setStyle(Style.STYLE2);
        StudyPlan.setStyle(Style.STYLE3);

        initializingCalenderView(calendarView);

        Button BtCreateEvent = getBtCreateEvent();
        Button BtCreateModul = getBtCreateModul();

        Pane leftSideSplitPane = getLeftSideSplitPane(BtCreateEvent, BtCreateModul);

        /**
         *  Anlegen und Design des Splitpane.
         *  Erzeugt die beiden Hälften des Frontends.
         */
        SplitPane split = new SplitPane(leftSideSplitPane, calendarView);
        split.setDividerPosition(0, 0.18);
        Scene sceneO = new Scene(split);
        stage.setScene(sceneO);
        stage.setMinWidth(1000);
        stage.setHeight(780);
        stage.centerOnScreen();
        stage.setTitle("Study Planer");
        stage.show();
    }


    private void changeListBoxButtonText(CalendarEvent evt, Modul item) {
        listbox.getItems()
                .stream()
                .filter(e -> replaceButtonName(e.getText().trim()).equals(item.getModulname().trim()))
                .forEach(e -> e.setText(item.toString()));
    }

    /**
     * Generates a new EventHandler for the Calender
     *
     * @return EventHandler
     * @author Andreas Scheuer
     */


    private void initializingCalenderView(CalendarView calendarView) {

        CalendarSource myCalendarSource = new CalendarSource("Planer");
        myCalendarSource.getCalendars().addAll(StudyPlan, SchoolTimeTable);
        calendarView.getCalendarSources().addAll(myCalendarSource);
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
    }

    private Button getBtCreateEvent() {
        /**
         *  Hier wird dem button  BtCreateEvent seine Funktion zugewiesen.
         *  Das heißt er ruft die Methode neuesEvent(Sting string) auf
         */
        Button BtCreateEvent = new Button("Erstellen");
        BtCreateEvent.setOnAction(
                event -> {
                    if (event.getSource() == BtCreateEvent) {
                        neuesEvent();
                    }
                });
        return BtCreateEvent;
    }

    private Button getBtCreateModul() {
        /**
         *  Hier wird dem button  BtCreateModul seine Funktion zugewiesen.
         *  Das heißt er ruft die Methode neuesModul() auf
         */
        Button BtCreateModul = new Button("Modul anlegen");
        BtCreateModul.setOnAction(
                event -> {
                    if (event.getSource() == BtCreateModul) {
                        neuesModul();
                    }
                });
        return BtCreateModul;
    }

    private Pane getLeftSideSplitPane(Button BtCreateEvent, Button BtCreateModul) {
        /**
         *  Anelgen und design der linken Seite des Splitpane.
         *  hir muss alles rein was in unsere seite vom Calender sein soll
         */

        BorderPane BPLayoutLeft = new BorderPane();
        VBox VbButtonBox = new VBox();
        VbButtonBox.getChildren().addAll(BtCreateEvent, BtCreateModul);
        BPLayoutLeft.setTop(VbButtonBox);
        BPLayoutLeft.setBottom(listbox);
        Pane PBar = new Pane(BPLayoutLeft);// ist die toolbar
        VbButtonBox.setMinWidth(300);
        return PBar;
    }

    /**
     * Replace button name string.
     *
     * @param string
     *         the string
     *
     * @return the string
     */
    public String replaceButtonName(String string) {
        string.replaceAll("\\s", "");
        String result = string.replaceAll("(?<=\\w)\\s.*", "");
        return result;
    }

    /**
     * @ max/ marc
     * realisiert die Funktion des Buttons Event_Eintragen
     * Es wird ein neues Fenster erstellt, in dem die Eintragedaten abgefragt werden.
     */
    public void neuesEvent() {
        Stage stage = new Stage();
        VBox layout = new VBox();

        Text TxtModulName = new Text("Modulname");
        ChoiceBox ChPickerModulName = getChPickerModulName();

        Text TxtCalendar = new Text("Kalender");
        ChoiceBox ChPickerCalendar = getChPickerCalendar();

        Text TxtStartTime = new Text("Anfangszeit");
        ChoiceBox ChPickerStartTime = getChPickerStartTime();

        Text TxtEndTime = new Text("Endzeit");
        ChoiceBox ChPickerEndTime = getChPickerEndTime();

        Text TxtDate = new Text("Datum");
        DatePicker datePicker = getDatePicker();

        Text TxtDescription = new Text("Beschreibung");
        TextField TxtFDescription = new TextField();

        Button BtSafeEvent = getBTSafeEventButton(TxtFDescription, datePicker, ChPickerCalendar, stage);

        layout.getChildren().addAll(TxtModulName, ChPickerModulName, TxtCalendar, ChPickerCalendar, TxtStartTime, ChPickerStartTime, TxtEndTime, ChPickerEndTime, TxtDate,
                datePicker, TxtDescription, TxtFDescription);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout);
        borderPane.setBottom(BtSafeEvent);
        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.setHeight(310);
        stage.setWidth(600);
        stage.show();
    }

    /**
     * @ max
     * realisiert die Funktion des Buttons Modul_Anlegen
     * Es wird ein neues Fenster erstellt in dem die Anlege Daten abgefragt werden
     * @ param Modulname
     * @ param EctsWert
     */
    public void neuesModul() {
        // Layout des aufgehenden Fensters
        Stage stage = new Stage();
        BorderPane layout = new BorderPane();
        VBox box = new VBox();

        // Texte die zur Steuerung angezeigt werden
        Text TxtModul = new Text("Modulname :");
        Text TxtEcts = new Text("Ects Wert des Moduls:");
        // Eingabe Felder

        TextField TxtFModul = new TextField();
        TextField TxtFEcts = new NumericTextField();

        Button BtSafe = getBtSafe(stage, TxtFModul, TxtFEcts);

        box.getChildren().addAll(TxtModul, TxtFModul, TxtEcts, TxtFEcts, BtSafe);
        layout.setCenter(box);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();


    }

    private ChoiceBox getChPickerModulName() {
        // Anfang der Feld anlegen Event
        /**
         * @Max @Marc
         * ChoiceBox
         * Bei jedem Event besteht die Möglichkeit jedes Modul auszuwählen
         */
        ChoiceBox ChPickerModulName = new ChoiceBox();
        for (Object x : Module) {
            ChPickerModulName.getItems().addAll(x);
        }

        ChPickerModulName.setOnAction((event) -> {
            Modul x = (Modul) ChPickerModulName.getSelectionModel().getSelectedItem();
            setModulNamefürÜbergabe(x);

        });
        return ChPickerModulName;
    }

    private ChoiceBox getChPickerCalendar() {
        /**
         * @Marc
         * ChoiceBox
         * Auswählen der Kalender
         */
        ChoiceBox ChPickerCalendar = new ChoiceBox();
        ChPickerCalendar.getItems().addAll(StudyPlan.getName());
        ChPickerCalendar.getItems().addAll(SchoolTimeTable.getName());

        ChPickerCalendar.setOnAction((event) -> {
            ChPickerCalendar.getSelectionModel().getSelectedItem();
        });
        return ChPickerCalendar;
    }

    private ChoiceBox getChPickerStartTime() {
        /**
         * @Max
         * ChoiceBox
         * Anfangs Uhrzeit auswählen für Event
         */
        ChoiceBox ChPickerStartTime = new ChoiceBox();
        int stundeanfang = 8;
        int minuteanfang = 0;
        LocalTime x = LocalTime.of(stundeanfang, minuteanfang);
        for (int i = 0; i <= 24; i++) {
            ChPickerStartTime.getItems().addAll(x);
            x = x.plusMinutes(30);
        }
        ChPickerStartTime.setOnAction((event) -> {
            int selectedIndex = ChPickerStartTime.getSelectionModel().getSelectedIndex();
            LocalTime beginnzeit = (LocalTime) ChPickerStartTime.getSelectionModel().getSelectedItem();
            setStartTimeEvent(beginnzeit);
        });
        return ChPickerStartTime;
    }

    private ChoiceBox getChPickerEndTime() {
        /**
         * @Max
         * ChoiceBox
         * End Uhrzeit auswählen für Event
         */
        ChoiceBox ChPickerEndTime = new ChoiceBox();
        int stundeende = 8;
        int minuteende = 30;
        LocalTime y = LocalTime.of(stundeende, minuteende);
        for (int i = 0; i <= 24; i++) {
            ChPickerEndTime.getItems().addAll(y);
            y = y.plusMinutes(30);
        }
        ChPickerEndTime.setOnAction((event) -> {
            int selectedIndex = ChPickerEndTime.getSelectionModel().getSelectedIndex();
            LocalTime zeitende = (LocalTime) ChPickerEndTime.getSelectionModel().getSelectedItem();
            setEndTimeEvent(zeitende);
        });
        return ChPickerEndTime;
    }

    private DatePicker getDatePicker() {
        /**
         * @Max
         * DatePicker
         * Datum auswählen für Event
         */
        DatePicker datePicker = new DatePicker();
        Button button1 = new Button("Datum wählen");
        button1.setOnAction(action -> {
            LocalDate Datum = datePicker.getValue();

        });
        return datePicker;
    }

    private Button getBTSafeEventButton(TextField txtFDescription, DatePicker datePicker, ChoiceBox
            chPickerCalendar, Stage stage) {
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
            Entry entry = new Entry();
            entry = convertEventToEntry(ownEvent);

            System.out.println(ownEvent + " " + entry);


            /**
             * @Marc
             * Speichert das Event in dem davor gesehenen Kalender
             */
            if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Lernplan") {
                StudyPlan.addEntry(entry);
            } else if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Stundenplan") {
                SchoolTimeTable.addEntry((entry));
            }

            stage.close();

        });
        return button;
    }

    private Button getBtSafe(Stage stage, TextField TxtFModul, TextField TxtFEcts) {
        /**
         * @Max
         * Speichert die Eingabefeder in einem Modul Objekt und legt sie in der Liste Module ab.
         */


        Button BtSafe = new Button("Speichern ");
        BtSafe.setDisable(true);

        listener(TxtFModul, TxtFEcts, BtSafe);


        BtSafe.setOnAction(
                event -> {
                    if (event.getSource() == BtSafe) {
                        Modul modul = new Modul(TxtFModul.getText(),
                                Integer.parseInt(TxtFEcts.getText()));


                        Module.add(modul);

                        Button BtModul = new Button(modul.toString());
                        listbox.getItems().add(BtModul);

                        BtModul.setOnAction(actionEvent -> {
                            editModul(modul, BtModul);
                        });
                        stage.close();
                    }
                });
        return BtSafe;
    }

    /**
     * Set modul namefür übergabe.
     *
     * @param x
     *         the x
     */
    public void setModulNamefürÜbergabe(Modul x) {
        NameModul = x.getModulname();

    }

    /**
     * Sets start time event.
     *
     * @param x
     *         the x
     */
    public void setStartTimeEvent(LocalTime x) {
        StartTimeEvent = x;

    }

    /**
     * Sets end time event.
     *
     * @param x
     *         the x
     */
    public void setEndTimeEvent(LocalTime x) {
        EndTimeEvent = x;
    }

    private void listener(TextField TxtFModul, TextField TxtFEcts, Button BtSafe) {
        /**
         * @Marc
         * Prüft die Felder auf Inhalt, Button Speichern geht erst wenn Felder ausgefüllt sind.
         * Fehlerhaft!! Trotzdem ok Marc
         */
        TxtFModul.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.trim().isEmpty()) {
                    BtSafe.setDisable(true);
                } else if (!newValue.trim().equals("") && !TxtFEcts.getText().equals("")) {
                    BtSafe.setDisable(false);


                } else {
                    TxtFEcts.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                            BtSafe.setDisable(newValue.trim().isEmpty());
                        }
                    });

                }
            }
        });
    }

    /**
     * Edit modul.
     *
     * @param editModul
     *         the edit modul
     * @param button
     *         the button
     *
     * @Marc Event bearbeiten
     */
    public void editModul(Modul editModul, Button button) {

        // Layout des aufgehenden Fensters
        Stage stage = new Stage();
        BorderPane layout = new BorderPane();
        VBox box = new VBox();


        // Texte die zur Steuerung angezeigt werden
        Text modulText = new Text("Modulname :");
        Text etcText = new Text("Ects Wert des Moduls:");
        // Eingabe Felder + VorhandenDaten
        TextField readModulName = new TextField(editModul.getModulname());
        TextField readEcts = new TextField(editModul.getEcts().toString2());

        /**
         * @Marc
         *Ändert das Modul und speichert es neu ab
         */
        Button BtEditModul = new Button("Ändern ");
        BtEditModul.setOnAction(
                event -> {
                    if (event.getSource() == BtEditModul) {
                        int index = Module.indexOf(editModul);
                        editModul.setModulname(readModulName.getText());
                        editModul.setEcts(Integer.parseInt(readEcts.getText()));
                        Module.set(index, editModul);
                        //erstellt einen anderen Button

                        button.setText(editModul.toString());

                        listbox.getItems().set(index, button);


                    }
                    stage.close();
                });


        box.getChildren().addAll(modulText, readModulName, etcText, readEcts, BtEditModul);
        layout.setCenter(box);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }

}
