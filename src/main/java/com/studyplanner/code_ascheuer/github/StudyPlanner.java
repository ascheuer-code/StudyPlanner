package com.studyplanner.code_ascheuer.github;

import Model.Event;
import Model.Modul;
import Helper.LocalDateTimeConverter;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import impl.com.calendarfx.view.NumericTextField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static Helper.LocalDateTimeConverter.*;


public class StudyPlanner extends Application {
    int FENSTER_SCHON_OFFEN_ZÄHLER = 0;

    LocalTime Beginn_Zeit_Event;
    LocalTime End_Zeit_Event;
    String Mod_Name_Übergabe;
    List Module = new ArrayList();
    List Events = new ArrayList();
    ListView listbox = new ListView();
    Calendar StundenPlan = new Calendar("Stundenplan");
    Calendar LernPlan = new Calendar("Lernplan");

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

        CalendarView calendarView = new CalendarView(); // <1>



        StundenPlan.setStyle(Style.STYLE2);
        LernPlan.setStyle(Style.STYLE3);


        CalendarSource myCalendarSource = new CalendarSource("Planer"); // <4>

        myCalendarSource.getCalendars().addAll(LernPlan, StundenPlan);

        calendarView.getCalendarSources().addAll(myCalendarSource); // <5>

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


        /**
        *  Hier wird dem button  Event_Eintragen seine Funktion zugewiesen.
         *  Das heißt er ruft die Methode neuesEvent(Sting string) auf
        */
        Button Event_Eintragen = new Button("neuer Eintrag");
        Event_Eintragen.setOnAction(
                event -> {
                    if (event.getSource() == Event_Eintragen) {
                        neuesEvent("Platzhalter");
                    }
                    else{
                       // neuesEvent();
                    }
                });

        /**
         *  Hier wird dem button  Modul_Anlegen seine Funktion zugewiesen.
         *  Das heißt er ruft die Methode neuesModul() auf
         */
        Button Modul_Anlegen = new Button("Modul anlegen");
        Modul_Anlegen.setOnAction(
                event -> {
                    if (event.getSource() == Modul_Anlegen) {
                        neuesModul();
                    }
                });


        /**
         *  Anelgen und design der linken Seite des Splitpane.
         *  hir muss alles rein was in unsere seite vom Calender sein soll
         */

        BorderPane linkeSeiteLayout = new BorderPane();
        VBox buttonbox = new VBox();
        buttonbox.getChildren().addAll(Event_Eintragen, Modul_Anlegen);
        linkeSeiteLayout.setTop(buttonbox);
        linkeSeiteLayout.setBottom(listbox);
        Pane bar = new Pane(linkeSeiteLayout);// ist die toolbar

        /**
         *  Anlegen und Design des Splitpane.
         *  Erzeugt die beiden Hälften des Frontends.
         */
        SplitPane split = new SplitPane(bar, calendarView);
        split.setDividerPosition(0, 0.18);
        Scene sceneO = new Scene(split);
        stage.setScene(sceneO);
        stage.setWidth(1000);
        stage.setHeight(780);
        stage.centerOnScreen();
        stage.setTitle("Study Planer");
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
        Text modÜberschrift = new Text("Modulname :");
        Text ectsüberschrift = new Text("Ects Wert des Moduls:");
        // Eingabe Felder
        TextField Modulname_Einlesen = new TextField();
        TextField Ects_Einlesen = new NumericTextField();

        // Speichert die Eingabefleder in einem Modul Objekt und legt sie in der Liste Module ab.
        Button Modul_Speichern = new Button("Speichern ");
        Modul_Speichern.setOnAction(
                event -> {
                    if (event.getSource() == Modul_Speichern) {
                        Modul modul = new Modul(Modulname_Einlesen.getText(),
                                Integer.parseInt(Ects_Einlesen.getText()));
                        Module.add(modul);
                        Button button = new Button("" + modul);
                        listbox.getItems().add(button);
                        button.setOnAction(actionEvent -> {
                            neuesEvent(Modulname_Einlesen.getText());
                        });
                        stage.close();
                    }
                });

        box.getChildren().addAll(modÜberschrift, Modulname_Einlesen, ectsüberschrift, Ects_Einlesen, Modul_Speichern);
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
    public void neuesEvent(String string) {
        Stage stage = new Stage();
        // Texte die zur Steuerung angezeigt werden
        Text Modulname = new Text("Modulname");
        Text modulname = new Text();
        Text kalender = new Text("Kalender");
        Text anfangszeit = new Text("Anfangszeit");
        Text endzeit = new Text("Endzeit");
        Text beschreibung = new Text("Beschreibung");

        String Beschreibung;
        TextField beschreibungtext = new TextField();

        // Anfang der Variablen Picker

        // Man kann auch
        ChoiceBox modulNamePicker  = new ChoiceBox();
        for(Object x :Module){
            modulNamePicker.getItems().addAll(x);}
        modulNamePicker.setOnAction((event) -> {
            int selectedIndex = modulNamePicker.getSelectionModel().getSelectedIndex();
            Object  x = modulNamePicker.getSelectionModel().getSelectedItem();
            setModulNamefürÜbergabe((Modul) x);
        });

        // Max
        // Anfangsuhrzeit Picker für neues Event
        ChoiceBox zeitenanfang = new ChoiceBox();
        int stundeanfang = 8;
        int minuteanfang = 0;
        LocalTime x = LocalTime.of(stundeanfang, minuteanfang);
        for (int i = 0; i <= 24; i++) {
            zeitenanfang.getItems().addAll(x);
            x = x.plusMinutes(30);
        }
        zeitenanfang.setOnAction((event) -> {
            int selectedIndex = zeitenanfang.getSelectionModel().getSelectedIndex();
            LocalTime beginnzeit = (LocalTime) zeitenanfang.getSelectionModel().getSelectedItem();
            setBeginn_Zeit_Event(beginnzeit);
        });

        // Max
        // Enduhrzeit Picker für neues Event
        ChoiceBox zeitenende = new ChoiceBox();
        int stundeende = 8;
        int minuteende = 30;
        LocalTime y = LocalTime.of(stundeende, minuteende);
        for (int i = 0; i <= 24; i++) {
            zeitenende.getItems().addAll(y);
            y = y.plusMinutes(30);
        }
        zeitenende.setOnAction((event) -> {
            int selectedIndex = zeitenende.getSelectionModel().getSelectedIndex();
            LocalTime zeitende = (LocalTime) zeitenende.getSelectionModel().getSelectedItem();
            setEnd_Zeit_Event(zeitende);
        });

        // max
        // DatumPicker für neues event
        Text datum = new Text("Datum");
        LocalDate Datumausen;
        DatePicker datumpicker = new DatePicker();
        Button button1 = new Button("Datum wählen");
        button1.setOnAction(action -> {
            LocalDate Datum = datumpicker.getValue();

        });

        // Kalender auswählen
        //Marc
        ChoiceBox kalenderAuswahl = new ChoiceBox();
        kalenderAuswahl.getItems().addAll(LernPlan.getName());
        kalenderAuswahl.getItems().addAll(StundenPlan.getName());

        kalenderAuswahl.setOnAction((event) -> {
            String eintrag = String.valueOf(kalenderAuswahl.getSelectionModel().getSelectedItem());
        });
        // Ende der Variablen Picker


        VBox layout = new VBox();

        layout.getChildren().addAll(Modulname, modulNamePicker,kalender, kalenderAuswahl, anfangszeit, zeitenanfang, endzeit, zeitenende, datum,
                datumpicker, beschreibung, beschreibungtext);

        Button Event_Speichern = new Button("Event sichern :");
        // kein lamda weil hat mit lamda nicht funktioniert
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                // Entry wird erstellt hier

                Entry eventtest = new Entry();

                eventtest.setTitle(modulNamePicker.getSelectionModel().getSelectedItem().toString());
                eventtest.setInterval(datumpicker.getValue());
                eventtest.setInterval(Beginn_Zeit_Event, End_Zeit_Event);

                /**
                 * @Marc
                 * Event wird aus dem Entry erstellt mittels converter
                 */
                Event event = new Event();
                event = convertEntrytoEvent(eventtest);

                System.out.println(event.toString());

                Duration duration = Duration.between(LocalTime.parse(event.getStartTime()),LocalTime.parse(event.getEndTime()));
                Duration testduration = Duration.ofHours(30);

                testduration = testduration.minus(duration);

                System.out.println("Duration is:" + duration);
                System.out.println("Testduration = " + testduration);




                /**
                 * @Marc
                 * Entry wird aus dem Event erstellt mittels converter
                 */


                Entry test = new Entry();
                test = convertEventToEntry(event);

                System.out.println(test.toString());


                /**
                 * @Marc
                 * Convertierter Entry wird dem Kalender hinzugefügt
                 *
                 */

                // Wählen zwischen den Kalendern
                //@Marc
                if(kalenderAuswahl.getSelectionModel().getSelectedItem() == "Lernplan"){
                    LernPlan.addEntry(test);
                }
                else{
                    StundenPlan.addEntry((eventtest));
                }



                stage.close();
            }
        };
        // Aufruf des Listener
        Event_Speichern.setOnAction(event);


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(layout);
        borderPane.setBottom(Event_Speichern);
        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.setHeight(310);
        stage.setWidth(600);
        stage.show();
    }

    /**
     * @param x
     */
    public void setBeginn_Zeit_Event(LocalTime x) {
        Beginn_Zeit_Event = x;

    }

    /**
     * @param x
     */
    public void setEnd_Zeit_Event(LocalTime x) {
        End_Zeit_Event = x;
    }


    /**
     * @param x
     */
    public void setModulNamefürÜbergabe(Modul x) {
         Mod_Name_Übergabe = x.getModulname();
         System.out.println(Mod_Name_Übergabe);
    }

}
