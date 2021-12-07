package Model;

import Helper.Helper;

/**
 * This class converts ECTS to minutes.
 *
 * @author leon weyand
 */
public class Ects {

    private final int minutes;
    private int ects;


    /**
     * Instantiates a new Ects.
     *
     * @param ects
     *         the ECTS that need to be converted to minutes.
     */
    public Ects(int ects) {
        Helper.checkMin(1, ects, "ECTS");
        this.ects = ects;
        this.minutes = ectsToMinutes(ects);
    }

    /**
     * Calculates the amount of minutes to learn for the ECTS.
     *
     * @param ects
     *         the ECTS that need to be converted to minutes.
     *
     * @return the minutes to learn.
     */
    private int ectsToMinutes(int ects) {
        // please put in the Comments how they will be calculated with a Source
        return ects * 60 * 30;
    }

    /**
     * Gets ects.
     *
     * @return the ects
     */
    public int getEctsValue() {
        return ects;
    }

    /**
     * Sets ects.
     *
     * @param ects
     *         the ects
     */
    public void setEcts(int ects) {
        Helper.checkSpan(1, 20, ects, "ECTS");
        this.ects = ects;
    }

    /**
     * Gets minutes.
     *
     * @return the minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Gets a String.
     *
     * @return the ECTS and minutes as a String.
     */
    public String toString() {
        String s = "Ects: " + ects + "; Minutes: " + minutes;
        return s;
    }
}
