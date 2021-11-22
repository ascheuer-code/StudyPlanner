package com.studyplanner.app;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import impl.com.calendarfx.view.NumericTextField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StudyPlanner extends Application {
int FENSTER_SCHON_OFFEN_ZÄHLER=0;
   List Module = new ArrayList();
   ListView listbox= new ListView();


    @Override
    public void start(Stage stage) throws Exception {

        CalendarView calendarView = new CalendarView(); // <1>

        Calendar birthdays = new Calendar("Birthdays"); // <2>
        Calendar holidays = new Calendar("Holidays");

        birthdays.setStyle(Style.STYLE1); // <3>
        holidays.setStyle(Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars"); // <4>
        myCalendarSource.getCalendars().addAll(birthdays, holidays);

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

        Button Event_Eintragen = new Button ("neuer Eintrag");
        Event_Eintragen.setOnAction(
                event -> {if(event.getSource()== Event_Eintragen){
                    if (FENSTER_SCHON_OFFEN_ZÄHLER<1){ neuesEvent();FENSTER_SCHON_OFFEN_ZÄHLER++;}
                    else{FensterSchonOffenExeptionWimdow();}}});

        Button Modul_Anlegen = new Button("Modul anlegen");
        Modul_Anlegen.setOnAction(
                event -> {if(event.getSource()== Modul_Anlegen){neuesModul();
                }});


        BorderPane layout = new BorderPane();
        VBox buttonbox = new VBox();
        buttonbox.getChildren().addAll( Event_Eintragen,Modul_Anlegen);




        layout.setTop(buttonbox);
        layout.setBottom(listbox);

        //linke Hälfte
        Pane bar = new Pane(layout);// ist die toolbar
        // rechte Hälfte
        Pane calender = new Pane(calendarView);// ist der calender
        calender.autosize();
        SplitPane split = new SplitPane(bar,calender);
        split.setDividerPosition(0,0.01);
        Scene sceneO = new Scene(split);
        stage.setScene(sceneO);
        stage.setWidth(1000);
        stage.setHeight(780);
        stage.centerOnScreen();
        stage.setTitle("Study Planer");
        stage.show();
    }

    // neues Modul anlegen und in den listen speichern
  // @max
    public void  neuesModul(){
        Stage stage= new Stage();
        Button Modul_Speichern = new Button("Speichern ");
        BorderPane layout = new BorderPane();
        VBox box = new VBox();
        Text modÜberschrift = new Text("Modulname :");
        Text ectsüberschrift = new Text("Ects Wert des Moduls:");
        TextField Modulname_Einlesen = new TextField();
        TextField Ects_Einlesen = new NumericTextField();

        box.getChildren().addAll(modÜberschrift,Modulname_Einlesen,ectsüberschrift,Ects_Einlesen,Modul_Speichern);
        layout.setCenter(box);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
        Modul_Speichern.setOnAction(
                event -> {if(event.getSource()== Modul_Speichern){
                    Modul modul = new Modul();
                    modul.ECTS = Integer. parseInt(Ects_Einlesen.getText());
                    modul.Modulname = Modulname_Einlesen.getText();
                    Module.add(modul);
                    listbox.getItems().add(modul);


                    stage.close();

                    }});

    }

        // anlegen eines neuen events im neuen fenster
        //@max
        public void neuesEvent(){
            Button button= new Button("Close");
            HBox box = new HBox();
            BorderPane layoutHilfe= new BorderPane();
            BorderPane layout = new BorderPane();
            Scene scene= new Scene(layout);
            Stage stage= new Stage();

            box.getChildren().addAll(button);
            layoutHilfe.setRight(box);
            layout.setTop(layoutHilfe);


            stage.setScene(scene);
            stage.setHeight(150);
            stage.setWidth(150);
            stage.show();

            button.setOnAction(event ->{if(event.getSource()== button){stage.close(); FENSTER_SCHON_OFFEN_ZÄHLER--;}});


        }




    // Offnet Fenster wen aufgerufen welches anzeigt , dass fenster bereits offen
    public void FensterSchonOffenExeptionWimdow(){
        Stage stage= new Stage();

        VBox layout = new VBox();

        Scene scene= new Scene(layout);

        Button close_window = new Button("Close Window");
        Text text= new Text("Es ist bereits ein Fenster dieser Art offen ");

        layout.getChildren().addAll( text,close_window );

        // Schliest das Fenster auf Knopfdruck
        close_window.setOnAction(event ->{if(event.getSource()== close_window)stage.close();});
        //Optische Anpassungen
        stage.setHeight(100);
        stage.setWidth(250);
        close_window.setMinWidth(230);
        close_window.setCenterShape(true);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    }
