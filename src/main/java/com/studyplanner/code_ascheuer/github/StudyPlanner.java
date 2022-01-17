package com.studyplanner.code_ascheuer.github;

import Model.Event;
import Model.Modul;
import View.DeleteModul;
import View.FrontEndHelper;
import View.NewEvent;
import View.NewModulAndConfig;
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
    FrontEndHelper helper = new FrontEndHelper();
    NewEvent newEvent = new NewEvent();
    NewModulAndConfig newModulAndConfig = new NewModulAndConfig();
    DeleteModul deleteModul = new DeleteModul();

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
    LocalTime StartTimeEvent= LocalTime.now();
    /**
     * The End time event.
     */
    LocalTime EndTimeEvent= LocalTime.now();
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

        helper.initializingCalenderView(calendarView, StudyPlan, SchoolTimeTable);

        Button BtCreateEvent = getBtCreateEvent();
        Button BtCreateModul = getBtCreateModul();
        Button BtDeleteModul = getBtDeleteModul();

        Pane leftSideSplitPane = helper.getLeftSideSplitPane(BtCreateEvent, BtCreateModul, BtDeleteModul,listbox);


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
     * Gets bt create event.
     *
     * @return the bt create event
     */
    public Button getBtCreateEvent() {

        Button BtCreateEvent = new Button("Erstellen");
        BtCreateEvent.setOnAction(
                event -> {
                    if (event.getSource() == BtCreateEvent) {
                        newEvent.neuesEvent(Module, Events ,NameModul, StudyPlan,  SchoolTimeTable, StartTimeEvent,  EndTimeEvent);
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
                        newModulAndConfig.neuesModul(listbox,Module);
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
                        deleteModul.modullöschen( Module, NameModul,  Events,  StudyPlan,  SchoolTimeTable,  listbox);
                    }
                });
        return BtDeleteModul;
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



    private void getBtModulLöschenNachCheck(CheckBox CBModulLöschen, Button delete, ChoiceBox<?> chPickerModulName, Stage stage) {
        delete.setOnAction(action -> {
            boolean isCheck = CBModulLöschen.isSelected();

            if (isCheck == true) {

                Module.stream().filter(e -> e.toString().equals(chPickerModulName.getItems().get(chPickerModulName.getSelectionModel().getSelectedIndex() + 1).toString())).forEach(e -> {
                    for (String uuid : e.getUuid()) {
                        for (Event event : Events) {
                            if (event.getId().equals(uuid)) {
                                SchoolTimeTable.removeEntries(SchoolTimeTable.findEntries(event.getTitle().trim()));
                                StudyPlan.removeEntries(StudyPlan.findEntries(event.getTitle().trim()));
                            }
                        }
                    }
                });

                Module.remove(chPickerModulName.getSelectionModel().getSelectedIndex() + 1);
                listbox.getItems().remove(chPickerModulName.getSelectionModel().getSelectedIndex() + 1);
                listbox.refresh();
                stage.close();
            }

        });
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
            chPickerCalendar, Stage stage, ChoiceBox chRepetition, DatePicker datePickerRepetition) {
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

        newModulAndConfig.listener(TxtFModul, TxtFEcts, BtSafe);


        BtSafe.setOnAction(
                event -> {
                    if (event.getSource() == BtSafe) {
                        Modul modul = new Modul(TxtFModul.getText(),
                                Integer.parseInt(TxtFEcts.getText()));


                        Module.add(modul);

                        Button BtModul = new Button(modul.toString());
                        listbox.getItems().add(BtModul);

                        BtModul.setOnAction(actionEvent -> newModulAndConfig.editModul(modul, BtModul,listbox,Module));
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


}

