package com.studyplanner.app;

public class Modul {
  public   String Modulname;
    public int ECTS;

    @Override
    public String toString() {
        return "Modul{" +
                "Modulname='" + Modulname + '\'' +
                ", ECTS=" + ECTS +
                '}';
    }
}
