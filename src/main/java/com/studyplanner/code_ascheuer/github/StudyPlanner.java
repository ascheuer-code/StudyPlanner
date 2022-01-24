package com.studyplanner.code_ascheuer.github;

import DataAccess.*;
import Model.Event;
import Model.Modul;
import View.NewEvent;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static Helper.LocalDateTimeConverter.convertEventToEntry;


/**
 * The type Study planner.
 */
public class StudyPlanner extends Application {

    public final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("StudyPlanner");
    public final EntityManager entityManager = entityManagerFactory.createEntityManager();
    public final EntityTransaction entityTransaction = entityManager.getTransaction();

    /**
     * The Module.
     */
    final List<Modul> Module = new ArrayList<>();
    /**
     * The Events.
     */
    final List<Event> Events = new ArrayList<>();
    /**
     * The School time table.
     */
    final Calendar SchoolTimeTable = new Calendar("Stundenplan");
    /**
     * The Study plan.
     */
    final Calendar StudyPlan = new Calendar("Lernplan");
    /**
     * The Listbox.
     */
    ListView<Button> listbox = new ListView<>();

    /**
     * The entry point of application.
     *
     * @param args
     *         the input arguments
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


        /*
        @Marc Load Data from DB and add Module to listbox
         */
        LoadModulDDB loadModulDDB = new LoadModulDDB();
        for (Modul modul : loadModulDDB.zeigemodul(entityManager, entityTransaction)) {
            Module.add(modul);
            modul.setEcts(modul.gettEcts());
            Button bt = new Button(modul.toString2());
            listbox.getItems().add(bt);
            bt.setOnAction(actionEvent -> editModul(modul, bt));

        }
        LoadEventDB loadEventDB = new LoadEventDB();
        for (Event event : loadEventDB.zeigeEvent(entityManager, entityTransaction)) {
            Events.add(event);
            Entry<?> entry = convertEventToEntry(event);
            if (Objects.equals(event.getCalendar(), "Stundenplan")) {
                SchoolTimeTable.addEntry(entry);
            } else if (Objects.equals(event.getCalendar(), "Lernplan")) {
                StudyPlan.addEntry(entry);
            }

        }


        initializingCalenderView(calendarView);

        Button BtCreateEvent = getBtCreateEvent();
        Button BtCreateModul = getBtCreateModul();
        Button BtDeleteModul = getBtDeleteModul();
        Pane leftSideSplitPane = getLeftSideSplitPane(BtCreateEvent, BtCreateModul, BtDeleteModul);


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
     * @param event
     *         the event
     */
    public void isEntryIntervallChanged(CalendarEvent event) {

        // Update Event aus der Datenbank  1
        EventUpdateDB eventUpdateDB = new EventUpdateDB();
        if (!event.isEntryAdded() && !event.isEntryRemoved() && !(event.getOldInterval() == null) && !event.getOldInterval().getDuration().equals(event.getEntry().getInterval().getDuration())) {
            Events.stream().filter(e -> e.getId().equals(event.getEntry().getId())).forEach(e -> {
                e.setStartTime(event.getEntry().getStartTime().toString());
                e.setStarDate(event.getEntry().getStartDate().toString());
                e.setEndTime(event.getEntry().getEndTime().toString());
                e.setEndDate(event.getEntry().getEndDate().toString());
                eventUpdateDB.updateEvent(e, entityManager, entityTransaction);
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
     * @param event
     *         the event
     */
    public void isEntryTitleChanged(CalendarEvent event) {
        if (!event.isEntryAdded() && !event.isEntryRemoved() && event.getOldInterval() == null && !event.getOldText().equals(event.getEntry().getTitle())) {

            // ToDo: hier stimmt noch was nicht der Titel wird nicht geändert aber bei Intervall Changed klappt es warum auch immer
            EventUpdateDB eventUpdateDB = new EventUpdateDB();

            Events.stream().filter(e -> e.getId().equals(event.getEntry().getId())).forEach(e -> {
                e.setTitle(event.getEntry().getTitle());

                eventUpdateDB.updateEvent(e, entityManager, entityTransaction);

            });
        }
    }

    /**
     * Is entry removed.
     *
     * @param event
     *         the event
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
                    EventsDeleteDB eventsDeleteDB = new EventsDeleteDB();
                    events.forEach(e -> eventsDeleteDB.EventDelete(e, entityManager, entityTransaction));
                    Events.removeAll(events);

                    // löschen Liste von Events aus Datenbank 2

                }
            }

        }
    }

    /**
     * Is entry added.
     *
     * @param event
     *         the event
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
     * @param item
     *         the item
     */
    public void changeListBoxButtonText(Modul item) {
        listbox.getItems()
                .stream()
                .filter(e -> replaceButtonName(e.getText().trim()).equals(item.getModulname().trim()))
                .forEach(e -> e.setText(item.toString2()));
    }


    /**
     * Initializing calender view.
     *
     * @param calendarView
     *         the calendar view
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
                        NewEvent.createNewEvent(Module, Events, StudyPlan, SchoolTimeTable, entityManager, entityTransaction);
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
     * @param BtCreateEvent
     *         the bt create event
     * @param BtCreateModul
     *         the bt create modul
     *
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
     * @param string
     *         the string
     *
     * @return the string
     */
    public String replaceButtonName(String string) {
        return string.replaceAll("(?<=\\w)\\n.*", "");
    }

    public String getEventDescription(String string) {
        return string.replaceAll("^.*\\n", "");
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
     * Gets bt safe.
     *
     * @param stage
     *         the stage
     * @param TxtFModul
     *         the txt f modul
     * @param TxtFEcts
     *         the txt f ects
     *
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

                        // Modul in datenbank speichern 3
                        modul.setId();
                        SaveModulDB saveModulDB = new SaveModulDB();
                        saveModulDB.insert(modul, entityManager, entityTransaction);

                        Module.add(modul);

                        Button BtModul = new Button(modul.toString2());
                        listbox.getItems().add(BtModul);


                        BtModul.setOnAction(actionEvent -> editModul(modul, BtModul));
                        stage.close();
                    }
                });
        return BtSafe;
    }

    /**
     * Listener.
     *
     * @param TxtFModul
     *         the txt f modul
     * @param TxtFEcts
     *         the txt f ects
     * @param BtSafe
     *         the bt safe
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
     * @param editModul
     *         the edit modul
     * @param button
     *         the button
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
        // Modul in Datenbank ändern 4
        ModulUpdateDB modulUpdateDB = new ModulUpdateDB();


        Button BtEditModul = new Button("Ändern ");
        BtEditModul.setOnAction(
                event -> {
                    if (event.getSource() == BtEditModul) {
                        int index = Module.indexOf(editModul);
                        editModul.setModulname(readModulName.getText());
                        editModul.setEcts(Integer.parseInt(readEcts.getText()));
                        Module.set(index, editModul);
                        //erstellt einen anderen Button

                        button.setText(editModul.toString2());

                        listbox.getItems().set(index, button);
                        modulUpdateDB.Update(editModul, entityManager, entityTransaction);

                        ArrayList<Event> temp = new ArrayList<>(Events);

                        for (Modul modul : Module) {
                            for (String uuid : modul.getUuid()) {
                                for (Event eventa : temp) {
                                    if (uuid.equals(eventa.getId()) && editModul.equals(modul)) {
                                        SchoolTimeTable.findEntries(eventa.getTitle().trim()).forEach(e -> e.setTitle(editModul.getModulname() + "\n" + getEventDescription(eventa.getTitle())));
                                        StudyPlan.findEntries(eventa.getTitle().trim()).forEach(e -> e.setTitle(editModul.getModulname() + "\n" + getEventDescription(eventa.getTitle())));

                                    }
                                }
                            }
                        }


                    }
                    stage.close();
                });


        box.getChildren().addAll(modulText, readModulName, etcText, readEcts, BtEditModul);
        layout.setCenter(box);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Modul löschen .
     *
     * @max
     */
    public void modullöschen() {
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


        ChoiceBox<Modul> test = new ChoiceBox<>();
        test.getItems().addAll(Module);
        getBtModulLöschenNachCheck(CBModulLöschen, delete, test, stage);

        layout.getChildren().addAll(TxtModulLöschen, test, TxtModulLöschenQuestion, CBModulLöschen, delete);

        stage.setScene(scene);
        stage.setTitle("Modul löschen");
        stage.setWidth(300);
        stage.setHeight(200);
        stage.show();

    }

    /**
     * @param CBModulLöschen
     * @param delete
     * @param chPickerModulName
     * @param stage
     *
     * @Marc Delte Modul in DB and ListBox
     */
    private void getBtModulLöschenNachCheck(CheckBox CBModulLöschen, Button delete, ChoiceBox<Modul> chPickerModulName, Stage stage) {
        delete.setOnAction(action -> {
            boolean isCheck = CBModulLöschen.isSelected();

            if (isCheck == true) {
                ArrayList<Event> temp = new ArrayList<>(Events);

                Modul modultest = chPickerModulName.getValue();

                for (Modul modul : Module) {
                    for (String uuid : modul.getUuid()) {
                        for (Event event : temp) {
                            if (uuid.equals(event.getId()) && modultest.equals(modul)) {
                                SchoolTimeTable.removeEntries(SchoolTimeTable.findEntries(event.getTitle().trim()));
                                StudyPlan.removeEntries(StudyPlan.findEntries(event.getTitle().trim()));

                            }
                        }
                    }
                }

                ModulDeleteDB modulDeleteDB = new ModulDeleteDB();
                modulDeleteDB.ModulDelete(modultest, entityManager, entityTransaction);


                int index = Module.indexOf(modultest);
                listbox.getItems().remove(index);
                listbox.refresh();
                Module.remove(modultest);

                stage.close();

            }

        });
    }

}
