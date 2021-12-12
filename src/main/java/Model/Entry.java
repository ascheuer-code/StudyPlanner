package Model;

import Helper.Helper;

/**
 * die klasse entry nimmt alle Model
 * Autor Mahmoud Orabi
 *
 * @param <E>
 *            the type parameter
 */
public class Entry<E> {
    // Klassenattribute
    private final E model;

    /**
     * Instantiates a new Entry.
     *
     * @param einmodel
     *                 the einmodel
     */
    // hier wird der Eintrag von Modul.java erstellen und überprüft, ob er null oder
    // nicht
    public Entry(E einmodel) {
        Helper.checkObj(einmodel, "Entry");
        this.model = einmodel;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "Entry [model=" + model + "]";
    }

}
