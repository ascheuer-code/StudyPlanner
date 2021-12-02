package com.studyplanner.code_ascheuer.github;

import Model.Helper;

/**
 * This class converts ECTS to minutes.
 *
 * @author leon weyand
 */
public class Ects {
    private int ects;
    private int minutes;
    private Helper helper;
    private static final String ECTS_POSITIVE = "Ects need to be positive!";

    /**
     * Instantiates a new Ects.
     *
     * @param ects the ECTS that need to be converted to minutes.
     */
    public Ects(int ects) {
        helper = new Helper();
        helper.checkMin(1, ects, ECTS_POSITIVE);
        this.ects = ects;
        this.minutes = ectsToMinutes(ects);
    }

    /**
     * Calculates the amount of minutes to learn for the ECTS.
     *
     * @param ects the ECTS that need to be converted to minutes.
     * @return the minutes to learn.
     */
    private int ectsToMinutes(int ects) {
        return ects*60*30;
    }

    /**
     * Gets ects.
     *
     * @return the ects
     */
    public int getEcts() {
        return ects;
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
        String s = "ECTS: " + ects + "; Minutes: " + minutes;
        return s;
    }
}
