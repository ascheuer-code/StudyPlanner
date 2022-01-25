package com.studyplanner.code_ascheuer.github;

import DataAccess.*;
import Model.Event;
import Model.Modul;
import View.*;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
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
    ButtonAndElement buttonAndElement= new ButtonAndElement();
    Helper helper = new Helper();
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
            EditandDeleteModul editandDeleteModul= new EditandDeleteModul();
            bt.setOnAction(actionEvent -> editandDeleteModul.editModul(modul,bt,entityManager, entityTransaction, Module, listbox, Events, SchoolTimeTable,StudyPlan));

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


        helper.initializingCalenderView(calendarView,StudyPlan,SchoolTimeTable);

        Button BtCreateEvent = buttonAndElement.getBtCreateEvent( Module, Events, StudyPlan, SchoolTimeTable, entityManager, entityTransaction);
        Button BtCreateModul = buttonAndElement.getBtCreateModul(Module,listbox,entityManager,entityTransaction,Events,SchoolTimeTable,StudyPlan);
        Button BtDeleteModul = buttonAndElement.getBtDeleteModul(Module,Events,SchoolTimeTable,StudyPlan,entityManager,entityTransaction,listbox);
        Pane leftSideSplitPane = buttonAndElement.getLeftSideSplitPane(BtCreateEvent, BtCreateModul, BtDeleteModul,listbox);


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

                        helper.changeListBoxButtonText(e,listbox);
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

                    helper.changeListBoxButtonText(modul,listbox);

                    /*
                    removes the Events from the Eventlist
                     */
                    List<Event> events = Events.stream().filter(e -> e.getId().equals(event.getEntry().getId())).collect(Collectors.toList());
                    EventsDeleteDB eventsDeleteDB = new EventsDeleteDB();
                    eventsDeleteDB.EventDelete(events, entityManager, entityTransaction);
                    Events.removeAll(events);


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

                    helper.changeListBoxButtonText(modul,listbox);
                }
            }
        }
    }
}
