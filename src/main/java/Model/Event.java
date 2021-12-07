package Model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The type Event.
 */
public class Event {
    /**
     * The Modulname.
     */
    public String modulname;
    /**
     * The Anfagszeit.
     */
    public LocalTime anfagszeit;
    /**
     * The Endzeit.
     */
    public LocalTime endzeit;
    /**
     * The Datum.
     */
    public LocalDate datum;
    /**
     * The Beschreibung.
     */
    public String beschreibung;

    /**
     * Instantiates a new Event.
     *
     * @param modulname
     *                     the modulname
     * @param anfangszeit
     *                     the anfangszeit
     * @param endzeit
     *                     the endzeit
     * @param datum
     *                     the datum
     * @param beschreibung
     *                     the beschreibung
     */
    public Event(String modulname, LocalTime anfangszeit, LocalTime endzeit, LocalDate datum, String beschreibung) {
        this.modulname = modulname;
        this.anfagszeit = anfangszeit;
        this.endzeit = endzeit;
        this.datum = datum;
        this.beschreibung = beschreibung;
    }

    /**
     * Get modulname string.
     *
     * @return the string
     */
    public String getModulname() {
        return modulname;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return modulname + "  " + anfagszeit + "  " + endzeit + "  " + datum + "  " + beschreibung;
    }

    /**
     * To string 2 string.
     *
     * @return the string
     */
    public String toString2() {
        return modulname;
    }
}