package Model;

import Helper.Helper;

import java.util.ArrayList;

/**
 * The type Entrylist.
 */
public class EventList {
    // Klassenattribute

    private final ArrayList<Event> eventlist;

    /**
     * Instantiates a new Entrylist.
     */
// hier wird die Einträgenliste angelegt
    public EventList() {

        this.eventlist = new ArrayList<>();

    }

    /**
     * Entry add.
     *
     * @param ent the ent
     */
// man fügt einen Eintrag hinzu
    public void addEvent(Event ent) {
        Helper.checkObj(ent, "Entry");
        eventlist.add(ent);
    }

    /**
     * Entry loesch.
     *
     * @param ent the ent
     */
// man kann den Eintrag löeschen
    public void removeEvent(Event ent) {
        Helper.checkObj(ent, "Entry");
        eventlist.remove(ent);
    }

    /**
     * Entry aendere.
     *
     * @param ent1 the ent 1
     * @param ent2 the ent 2
     */
// man kann den Eintag ändern
    public void replaceEvent(Event ent1, Event ent2) {
        Helper.checkObj(ent1, "Entry");
        Helper.checkObj(ent2, "Entry");

        eventlist.add(eventlist.indexOf(ent1), ent2);

    }

    /**
     * Entry get entry.
     *
     * @param index the index
     * @return the entry
     */
    public Event getEvent(int index) {

        Helper.checkMax(eventlist.size() + 1, index, "INDEX");
        Helper.checkMin(0, index, "INDEX");

        return eventlist.get(index);
    }

    /**
     * @return String
     */
    @Override
    // dadurch kann alle Einträge gezeigt werden .
    public String toString() {
        return "EntryList [entrylist=" + eventlist + "]";
    }
}
