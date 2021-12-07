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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class StudyPlanner extends Application {
    int FENSTER_SCHON_OFFEN_ZÄHLER = 0;

    LocalTime Beginn_Zeit_Event;
    LocalTime End_Zeit_Event;
    List Module = new ArrayList();
    List Events = new ArrayList();
    ListView listbox = new ListView();
    Calendar calendar = new Calendar("Test");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        CalendarView calendarView = new CalendarView(); // <1>

        calendar.setStyle(Style.STYLE1);


        CalendarSource myCalendarSource = new CalendarSource("My Calendars"); // <4>

        myCalendarSource.getCalendars().addAll(calendar);


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

        List ladentest = new ArrayList();
        Modul eins = new Modul("eins", 1);
        Modul eins2 = new Modul("zwei", 2);
        Modul eins3 = new Modul("drei", 3);
        Modul eins4 = new Modul("vier", 4);
        ladentest.add(eins);
        ladentest.add(eins2);
        ladentest.add(eins3);
        ladentest.add(eins4);

        Button Event_Eintragen = new Button("neuer Eintrag");
        Event_Eintragen.setOnAction(
                event -> {
                    if (event.getSource() == Event_Eintragen) {
                        neuesEvent("Platzhalter");
                    }
                });

        Button Modul_Anlegen = new Button("Modul.java anlegen");
        Modul_Anlegen.setOnAction(
                event -> {
                    if (event.getSource() == Modul_Anlegen) {
                        neuesModul();
                    }
                });


        BorderPane layout = new BorderPane();
        VBox buttonbox = new VBox();
        buttonbox.getChildren().addAll(Event_Eintragen, Modul_Anlegen);
        layout.setTop(buttonbox);
        layout.setBottom(listbox);
        for (Object var : ladentest) {
            Button button = new Button("" + var);
            listbox.getItems().add(button);
            button.setOnAction(actionEvent -> {
                neuesEvent(button.getText());
            });

        }


        //linke Hälfte
        Pane bar = new Pane(layout);// ist die toolbar
        // rechte Hälfte
        GridPane calender = new GridPane();// ist der calender
        calender.getChildren().add(calendarView);

        calender.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        SplitPane split = new SplitPane(bar, calender);
        split.setDividerPosition(0, 0.18);
        Scene sceneO = new Scene(split);
        stage.setScene(sceneO);
        stage.setWidth(1000);
        stage.setHeight(780);
        stage.centerOnScreen();
        stage.setTitle("Study Planer");
        stage.show();
    }

    // neues Modul.java anlegen und in den listen speichern
    // @max
    public void neuesModul() {
        Stage stage = new Stage();
        Button Modul_Speichern = new Button("Speichern ");
        BorderPane layout = new BorderPane();
        VBox box = new VBox();
        Text modÜberschrift = new Text("Modulname :");
        Text ectsüberschrift = new Text("Ects Wert des Moduls:");
        TextField Modulname_Einlesen = new TextField();
        TextField Ects_Einlesen = new NumericTextField();

        box.getChildren().addAll(modÜberschrift, Modulname_Einlesen, ectsüberschrift, Ects_Einlesen, Modul_Speichern);
        layout.setCenter(box);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
        Modul_Speichern.setOnAction(
                event -> {
                    if (event.getSource() == Modul_Speichern) {
                        Modul modul = new Modul(Modulname_Einlesen.getText(), Integer.parseInt(Ects_Einlesen.getText()));
                        Module.add(modul);
                        Button button = new Button("" + modul);
                        listbox.getItems().add(button);
                        button.setOnAction(actionEvent -> {
                            neuesEvent(button.getText());
                        });
                        stage.close();
                    }
                });


    }


    // Offnet Fenster wen aufgerufen welches anzeigt , dass fenster bereits offen
//    public void FensterSchonOffenExeptionWimdow(){
//        Stage stage= new Stage();
//
//        VBox layout = new VBox();
//
//        Scene scene= new Scene(layout);
//
//        Button close_window = new Button("Close Window");
//        Text text= new Text("Es ist bereits ein Fenster dieser Art offen ");
//
//        layout.getChildren().addAll( text,close_window );
//
//        // Schliest das Fenster auf Knopfdruck
//        close_window.setOnAction(event ->{if(event.getSource()== close_window)stage.close();});
//        //Optische Anpassungen
//        stage.setHeight(100);
//        stage.setWidth(250);
//        close_window.setMinWidth(230);
//        close_window.setCenterShape(true);
//
//        stage.setScene(scene);
//        stage.show();
//    }

    // anlegen eines neuen events im neuen fenster
    //@max
    public void neuesEvent(String string) {
        Stage stage = new Stage();


        Text Modulname = new Text("Modulname");
        Text modulname = new Text(string);
        Text anfangszeit = new Text("Anfangszeit");
        Text endzeit = new Text("Endzeit");
        Text beschreibung = new Text("Beschreibung");
        String Beschreibung;
        TextField beschreibungtext = new TextField();

        // uhrzeit Picker für neues Event
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


        // DatumPicker für neues event

        Text datum = new Text("Datum");
        LocalDate Datumausen;
        DatePicker datumpicker = new DatePicker();
        Button button1 = new Button("Datum wählen");

        button1.setOnAction(action -> {
            LocalDate Datum = datumpicker.getValue();

        });


        VBox links = new VBox();
        VBox rechts = new VBox();
        Text platzhalter1 = new Text();
        Text platzhalter2 = new Text();
        Text platzhalter3 = new Text();
        Text platzhalter4 = new Text();
        links.getChildren().addAll(Modulname, modulname, anfangszeit, zeitenanfang, endzeit, zeitenende, datum, datumpicker, beschreibung, beschreibungtext);


        Button Event_Speichern = new Button("Event sichern :");
        // kein lamda weil hat mit lamda nicht funktioniert
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Event event1 = new Event(string, Beginn_Zeit_Event, End_Zeit_Event, datumpicker.getValue(), beschreibungtext.getText());
                Events.add(event1);
                Entry<String> eventtest = new Entry<>(string);
                calendar.addEntry(eventtest);

                eventtest.setInterval(datumpicker.getValue());
                eventtest.setInterval(Beginn_Zeit_Event, End_Zeit_Event);
                System.out.println(event1);
                stage.close();
            }
        };
        Event_Speichern.setOnAction(event);


        SplitPane splitpane = new SplitPane(links);
        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(splitpane);
        borderPane.setBottom(Event_Speichern);
        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.setHeight(300);
        stage.setWidth(600);
        stage.show();
    }


    public void setBeginn_Zeit_Event(LocalTime x) {
        Beginn_Zeit_Event = x;

    }

    public void setEnd_Zeit_Event(LocalTime x) {
        End_Zeit_Event = x;
    }

}
