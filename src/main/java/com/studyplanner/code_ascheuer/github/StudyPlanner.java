package com.studyplanner.code_ascheuer.github;

import Model.Event;
import Model.Modul;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
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

import static Helper.LocalDateTimeConverter.*;


public class StudyPlanner extends Application {
    int FENSTER_SCHON_OFFEN_ZÄHLER = 0;

    /**
     * Konstanten
     */
    LocalTime StartTimeEvent;
    LocalTime EndTimeEvent;
    String NameModul;
    List Module = new ArrayList();
    List Events = new ArrayList();
    ListView listbox = new ListView();
    Calendar SchoolTimeTable = new Calendar("Stundenplan");
    Calendar StudyPlan = new Calendar("Lernplan");

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        CalendarView calendarView = new CalendarView();

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


                        Button BtModul = new Button("Test  " + modul);
                        listbox.getItems().add(BtModul);

                        BtModul.setOnAction(actionEvent -> {
                            editModul(modul, TxtFModul.getText(), TxtFEcts.getText(), BtModul);
                        });
                        stage.close();
                    }
                });
        return BtSafe;
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
                if(newValue.trim().isEmpty()){
                    BtSafe.setDisable(true);
                }
                else{
                    TxtFEcts.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                            if(newValue.trim().isEmpty()){
                                BtSafe.setDisable(true);
                            }
                            else{
                                BtSafe.setDisable(false);
                            }
                        }
                    }
                    );

                }
            }
        });
    }

    /**
     * @Marc
     * Event bearbeiten
     */
    public void editModul( Modul editModul, String modulName, String ects, Button button){

        // Layout des aufgehenden Fensters
        Stage stage = new Stage();
        BorderPane layout = new BorderPane();
        VBox box = new VBox();


        // Texte die zur Steuerung angezeigt werden
        Text modulText = new Text("Modulname :");
        Text etcText = new Text("Ects Wert des Moduls:");
        // Eingabe Felder + VorhandenDaten
        TextField readModulName = new TextField(modulName);
        TextField readEcts = new TextField(ects);

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
                            Module.set(index,editModul);
                            //erstellt einen anderen Button

                            button.setText(editModul.toString());

                            listbox.getItems().set(index,button);



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
     * @ max/ marc
     * realisiert die Funktion des Buttons Event_Eintragen
     * Es wird ein neues Fenster erstellt, in dem die Eintragedaten abgefragt werden.
     *
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

        Button BtSafeEvent = getBTSafeEventButton(TxtFDescription,datePicker,ChPickerCalendar,stage);

        layout.getChildren().addAll(TxtModulName, ChPickerModulName,TxtCalendar, ChPickerCalendar, TxtStartTime, ChPickerStartTime, TxtEndTime, ChPickerEndTime,TxtDate,
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
    
    private Button getBTSafeEventButton(TextField txtFDescription, DatePicker datePicker, ChoiceBox chPickerCalendar, Stage stage) {
        Button button = new Button("Event sichern :");
        button.setOnAction(action -> {
            Event ownEvent = new Event();
            ownEvent.setTitle(NameModul  +" "+ txtFDescription.getText());
            ownEvent.setStartTime(StartTimeEvent.toString());
            ownEvent.setEndTime(EndTimeEvent.toString());
            ownEvent.setStarDate(datePicker.getValue().toString());
            ownEvent.setEndDate(datePicker.getValue().toString());

            Events.add(ownEvent);
            Entry entry = new Entry();
            entry = convertEventToEntry(ownEvent);

            System.out.println(ownEvent.toString() + "" + entry.toString());

            /**
             * @Marc
             * Speichert das Event in dem davor gesehenen Kalender
             */
            if(chPickerCalendar.getSelectionModel().getSelectedItem() == "Lernplan"){
                StudyPlan.addEntry(entry);
            }
            else if (chPickerCalendar.getSelectionModel().getSelectedItem() == "Stundenplan"){
                SchoolTimeTable.addEntry((entry));
            }

            stage.close();

        });
    return button;
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

    private ChoiceBox getChPickerModulName() {
        // Anfang der Feld anlegen Event
        /**
         * @Max @Marc
         * ChoiceBox
         * Bei jedem Event besteht die Möglichkeit jedes Modul auszuwählen
         */
        ChoiceBox ChPickerModulName  = new ChoiceBox();
        for(Object x :Module){
            ChPickerModulName.getItems().addAll(x);}

        ChPickerModulName.setOnAction((event) -> {
           Modul x = (Modul) ChPickerModulName.getSelectionModel().getSelectedItem();
            setModulNamefürÜbergabe(x);

        });
        return ChPickerModulName;
    }

    /**
     * @param x
     */
    public void setStartTimeEvent(LocalTime x) {
        StartTimeEvent = x;

    }

    /**
     * @param x
     */
    public void setEndTimeEvent(LocalTime x) {
        EndTimeEvent = x;
    }


    /**
     * @param x
     */
    public void setModulNamefürÜbergabe(Modul x) {
         NameModul = x.getModulname();

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

}
