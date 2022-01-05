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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.lang.reflect.Array;
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
     * The Module.
     */
    final List<Modul> Module = new ArrayList<>();
    /**
     * The Events.
     */
    final List<Event> Events = new ArrayList<>();
    /**
     * The Listbox.
     */
    final ListView<Button> listbox = new ListView<>();
    /**
     * The School time table.
     */
    final Calendar SchoolTimeTable = new Calendar("Stundenplan");
    /**
     * The Study plan.
     */
    final Calendar StudyPlan = new Calendar("Lernplan");
    /**
     * The Start time event.
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
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {

        CalendarView calendarView = new CalendarView();

        calendarEventHandler();


        SchoolTimeTable.setStyle(Style.STYLE2);
        StudyPlan.setStyle(Style.STYLE3);

        initializingCalenderView(calendarView);

        Button BtCreateEvent = getBtCreateEvent();
        Button BtCreateModul = getBtCreateModul();
        Button BtDeleteModul = getBtDeleteModul();
        Pane leftSideSplitPane = getLeftSideSplitPane(BtCreateEvent, BtCreateModul,BtDeleteModul);


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

    /**
     * Calendar event handler.
     * <p>
     * Adding the EventHandler to the Calendar´s
     *
     * @author Andreas Scheuer
     */
    public void calendarEventHandler() {
        //Eventhandler für alle arten von Events
        StudyPlan.addEventHandler(event -> {
            // ToDo: es ist kein check eingebaut falls der Eintrag den Kalender wechselt, muss noch eingebaut werden
            //check added -> Works without description, need to be changed. Module gets the uuid
            isEntryAdded(event);
            //check removed-> Works without description, need to be changed. Module gets the uuid
            isEntryRemoved(event);
            // Title changed
            isEntryTitleChanged(event);

            // Intervall changed, works fine
            isEntryIntervallChanged(event);
        });

        SchoolTimeTable.addEventHandler(event -> {
            //check added -> Works without description, need to be changed. Module gets the uuid
            isEntryAdded(event);
            //check removed-> Works without description, need to be changed. Module gets the uuid
            isEntryRemoved(event);
            // Title changed
            isEntryTitleChanged(event);

            // Intervall changed, works fine
            isEntryIntervallChanged(event);
        });
    }

    /**
     * Check´s if the Entry Intervall is Changed and if so it Updates the Event with the same UUID
     *
     * @param event the event
     */
    public void isEntryIntervallChanged(CalendarEvent event) {
        if (!event.isEntryAdded() && !event.isEntryRemoved() && !event.getOldInterval().getDuration().equals(event.getEntry().getInterval().getDuration())) {
            Events.stream().filter(e -> e.getId().equals(event.getEntry().getId())).forEach(e -> {
                e.setStartTime(event.getEntry().getStartTime().toString());
                e.setStarDate(event.getEntry().getStartDate().toString());
                e.setEndTime(event.getEntry().getEndTime().toString());
                e.setEndDate(event.getEntry().getEndDate().toString());
            });

            Module.stream().filter(e -> e.getUuid().contains(event.getEntry().getId())).forEach(e -> {
                        e.getEcts().setDuration(e.getEcts().getDuration().plus(event.getOldInterval().getDuration().minus(event.getEntry().getDuration())));

                        changeListBoxButtonText(e);
                    }
            );
        }
    }

    /**
     * Is entry title changed.
     *
     * @param event the event
     */
    public void isEntryTitleChanged(CalendarEvent event) {
        if (!event.isEntryAdded() && !event.isEntryRemoved() && event.getOldInterval() == null && !event.getOldText().equals(event.getEntry().getTitle())) {
            Events.stream().filter(e -> e.getId().equals(event.getEntry().getId())).forEach(e -> e.setTitle(event.getEntry().getTitle()));
        }
    }

    /**
     * Is entry removed.
     *
     * @param event the event
     */
    public void isEntryRemoved(CalendarEvent event) {
        if (event.isEntryRemoved()) {
            for (Modul modul : Module) {
                if (modul.getUuid().contains(event.getEntry().getId())) {
                    modul.getEcts().setDuration(modul.getEcts().getDuration().plus(event.getEntry().getDuration()));

                    changeListBoxButtonText(modul);

                    /*
                    removes the Events from the Eventlist
                     */
                    List<Event> events = Events.stream().filter(e -> e.getId().equals(event.getEntry().getId())).collect(Collectors.toList());
                    Events.removeAll(events);
                }
            }
        }
    }

    /**
     * Is entry added.
     *
     * @param event the event
     */
    public void isEntryAdded(CalendarEvent event) {
        if (event.isEntryAdded()) {
            for (Modul modul : Module) {
                if (modul.getUuid().contains(event.getEntry().getId())) {
                    modul.getEcts().setDuration(modul.getEcts().getDuration().minus(event.getEntry().getDuration()));

                    changeListBoxButtonText(modul);
                }
            }
        }
    }


    /**
     * Change list box button text.
     *
     * @param item the item
     */
    public void changeListBoxButtonText(Modul item) {
        listbox.getItems()
                .stream()
                .filter(e -> replaceButtonName(e.getText().trim()).equals(item.getModulname().trim()))
                .forEach(e -> e.setText(item.toString()));
    }

    /**
     * Initializing calender view.
     *
     * @param calendarView the calendar view
     */
    public void initializingCalenderView(CalendarView calendarView) {

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

    /**
     * Gets bt create event.
     *
     * @return the bt create event
     */
    public Button getBtCreateEvent() {

        Button BtCreateEvent = new Button("Erstellen");
        BtCreateEvent.setOnAction(
                event -> {
                    if (event.getSource() == BtCreateEvent) {
                        neuesEvent();
                    }
                });
        return BtCreateEvent;
    }

    /**
     * Gets bt create modul.
     *
     * @return the bt create modul
     */
    public Button getBtCreateModul() {

        Button BtCreateModul = new Button("Modul anlegen");
        BtCreateModul.setOnAction(
                event -> {
                    if (event.getSource() == BtCreateModul) {
                        neuesModul();
                    }
                });
        return BtCreateModul;
    }
    /**
     * Gets bt create moduldelte.
     *
     * @return the bt create moduldelte
     */
    public Button getBtDeleteModul() {

        Button BtDeleteModul = new Button("Modul löschen");
        BtDeleteModul.setOnAction(
                event -> {
                    if (event.getSource() == BtDeleteModul) {
                        modullöschen();
                    }
                });
        return BtDeleteModul;
    }


    /**
     * Gets left side split pane.
     *
     * @param BtCreateEvent the bt create event
     * @param BtCreateModul the bt create modul
     * @return the left side split pane
     */

    public Pane getLeftSideSplitPane(Button BtCreateEvent, Button BtCreateModul, Button BtDeleteModul) {

        BorderPane BPLayoutLeft = new BorderPane();
        VBox VbButtonBox = new VBox();
        VbButtonBox.getChildren().addAll(BtCreateEvent, BtCreateModul, BtDeleteModul);
        BPLayoutLeft.setTop(VbButtonBox);
        BPLayoutLeft.setBottom(listbox);
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

    /**
     * Neues event.
     */
    public void neuesEvent() {
        Stage stage = new Stage();
        VBox layout = new VBox();

        Text TxtModulName = new Text("Modulname");
        ChoiceBox<?> ChPickerModulName = getChPickerModulName();

        Text TxtCalendar = new Text("Kalender");
        ChoiceBox<?> ChPickerCalendar = getChPickerCalendar();
        // Datum des erst Eintrages
        Text TxtDate = new Text("Datum");
        DatePicker datePicker = getDatePicker();

        Text TxtStartTime = new Text("Anfangszeit");
        ChoiceBox<?> ChPickerStartTime = getChPickerStartTime();

        Text TxtEndTime = new Text("Endzeit");
        ChoiceBox<?> ChPickerEndTime = getChPickerEndTime();

        Text TxtRepetition = new Text("Wiederholungsrythmus in Tagen ");
        ChoiceBox  ChRepetition = getChRepetition();
        Text TxtRepetitionEnd =new Text(" Bitte Wählen sie aus bis zu welchem Datum der Wiederholungsrythmus durchgeführt werden soll  ");
        DatePicker datePickerRepetition = getDatePicker();




        Text TxtDescription = new Text("Beschreibung");
        TextField TxtFDescription = new TextField();

        Button BtSafeEvent = getBTSafeEventButton(TxtFDescription, datePicker, ChPickerCalendar, stage);

        layout.getChildren().addAll(TxtModulName, ChPickerModulName, TxtCalendar, ChPickerCalendar, TxtDate,
                datePicker, TxtStartTime, ChPickerStartTime, TxtEndTime, ChPickerEndTime,TxtRepetition,ChRepetition, TxtRepetitionEnd,datePickerRepetition, TxtDescription, TxtFDescription);

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
     * Neues modul.
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
        stage.setTitle("neues Modul anlegen");
        stage.show();


    }

    /**
     *  Modul löschen .
     * @max
     */
    public void modullöschen(){
        // Layout des aufgehenden Fensters
        Stage stage = new Stage();
        VBox layout = new VBox();
        Scene scene = new Scene(layout);

        // Texte die zur Steuerung angezeigt werden
        Text TxtModulLöschen = new Text("Bitte das zu löschende Modul auswählen ");
        Text TxtModulLöschenQuestion = new Text("Möchten sie diese Modul wirklich löschen ? ");
        // Button der zur Steuerung gebracuht wird
        Button delete = new Button("löschen");

        // CheckBox um sicher zugehen das es wir gelöscht werden soll
        CheckBox CBModulLöschen = new CheckBox("JA");

        getBtModulLöschenNachCheck(CBModulLöschen, delete);

        layout.getChildren().addAll(TxtModulLöschen,getChPickerModulName(),TxtModulLöschenQuestion,CBModulLöschen,delete);

        stage.setScene(scene);
        stage.setTitle("Modul löschen");
        stage.setWidth(250);
        stage.setHeight(150);
        stage.show();

    }

    private void getBtModulLöschenNachCheck(CheckBox CBModulLöschen, Button delete) {
        delete.setOnAction(action -> {
            boolean isCheck = CBModulLöschen.isSelected();

            if(isCheck ==true){

               // for (Modul modul : Module){
                 //   if(modul.getModulname().equals(getChPickerModulName()));
                      //  Module.remove(modul);
                //}
            }
        });
    }

    /**
     * Gets ch picker modul name.
     *
     * @return the ch picker modul name
     */
    public ChoiceBox<?> getChPickerModulName() {
        // Anfang das Feld anlegen Event

        ChoiceBox<Modul> ChPickerModulName = new ChoiceBox<>();
        for (Modul x : Module) {
            ChPickerModulName.getItems().addAll(x);
        }

        ChPickerModulName.setOnAction((event) -> {
            Modul x = ChPickerModulName.getSelectionModel().getSelectedItem();
            setModulNamefuerUebergabe(x);

        });
        return ChPickerModulName;
    }

    /**
     * Gets ch picker calendar.
     *
     * @return the ch picker calendar
     */
    public ChoiceBox<?> getChPickerCalendar() {

        ChoiceBox<String> ChPickerCalendar = new ChoiceBox<>();
        ChPickerCalendar.getItems().addAll(StudyPlan.getName());
        ChPickerCalendar.getItems().addAll(SchoolTimeTable.getName());

        ChPickerCalendar.setOnAction((event) -> ChPickerCalendar.getSelectionModel().getSelectedItem());
        return ChPickerCalendar;
    }

    public ChoiceBox<?> getChRepetition(){
        ChoiceBox ChRepetition = new ChoiceBox();
        int [] tage = {1,2,3,4,5,6,7,14,28};
            for(int i =0;i <=tage.length-1 ;i++ )
         ChRepetition.getItems().addAll(tage[i]);
        return ChRepetition;
    }





    /**
     * Gets ch picker start time.
     *
     * @return the ch picker start time
     */
    public ChoiceBox<?> getChPickerStartTime() {

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
            setStartTimeEvent(beginnzeit);
        });
        return ChPickerStartTime;
    }

    /**
     * Gets ch picker end time.
     *
     * @return the ch picker end time
     */
    public ChoiceBox<?> getChPickerEndTime() {

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
            setEndTimeEvent(zeitende);
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
     * Gets bt safe event button.
     *
     * @param txtFDescription  the txt f description
     * @param datePicker       the date picker
     * @param chPickerCalendar the ch picker calendar
     * @param stage            the stage
     * @return the bt safe event button
     */
    public Button getBTSafeEventButton(TextField txtFDescription, DatePicker datePicker, ChoiceBox<?>
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
     * Gets bt safe.
     *
     * @param stage     the stage
     * @param TxtFModul the txt f modul
     * @param TxtFEcts  the txt f ects
     * @return the bt safe
     */
    public Button getBtSafe(Stage stage, TextField TxtFModul, TextField TxtFEcts) {

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

                        BtModul.setOnAction(actionEvent -> editModul(modul, BtModul));
                        stage.close();
                    }
                });
        return BtSafe;
    }

    /**
     * Sets modul namefuer uebergabe.
     *
     * @param x the x
     */
    public void setModulNamefuerUebergabe(Modul x) {
        NameModul = x.getModulname();

    }

    /**
     * Sets start time event.
     *
     * @param x the x
     */
    public void setStartTimeEvent(LocalTime x) {
        StartTimeEvent = x;
    }

    /**
     * Sets end time event.
     *
     * @param x the x
     */
    public void setEndTimeEvent(LocalTime x) {
        EndTimeEvent = x;
    }

    /**
     * Listener.
     *
     * @param TxtFModul the txt f modul
     * @param TxtFEcts  the txt f ects
     * @param BtSafe    the bt safe
     */
    public void listener(TextField TxtFModul, TextField TxtFEcts, Button BtSafe) {

        TxtFModul.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                BtSafe.setDisable(true);
            } else if (!TxtFEcts.getText().equals("")) {
                BtSafe.setDisable(false);


            } else {
                TxtFEcts.textProperty().addListener((observableValue1, oldValue1, newValue1) -> BtSafe.setDisable(newValue1.trim().isEmpty()));

            }
        });
    }

    /**
     * Edit modul.
     *
     * @param editModul the edit modul
     * @param button    the button
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
