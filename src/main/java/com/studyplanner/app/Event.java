package com.studyplanner.app;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    public String modulname;
    public LocalTime anfagszeit;
    public  LocalTime endzeit;
    public LocalDate datum;
   public  String beschreibung;

   public Event( String modulname,LocalTime anfangszeit,LocalTime endzeit,LocalDate datum, String beschreibung){
    this.modulname= modulname;
    this.anfagszeit= anfangszeit;
    this.endzeit= endzeit;
    this.datum= datum;
    this.beschreibung= beschreibung;}

    @Override
    public String toString() {
        return  modulname +"  "+ anfagszeit + "  "+ endzeit+ "  " +datum + "  "+beschreibung;
    }
}
