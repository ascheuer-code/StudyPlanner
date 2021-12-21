package Model;

import Helper.Helper;

import java.time.Duration;

/**
 * This class converts ECTS to minutes.
 *
 * @author leon weyand
 */
public class Ects {

    private int ects;
    private Duration duration;


    /**
     * Instantiates a new Ects.
     *
     * @param ects
     *         the ECTS that need to be converted to minutes.
     */
    public Ects(int ects) {
        Helper.checkMin(1, ects, "ECTS");
        this.ects = ects;
        this.duration = duration.ofHours(ects * 30);
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
        this.duration = duration.ofHours(ects * 30);
    }

    public Duration getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Ects: " +
                "ects: " + ects +
                ", duration: " + duration.toHours();
    }
}
