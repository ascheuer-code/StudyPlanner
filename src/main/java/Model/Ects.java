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
     * @param ects the ECTS that need to be converted to minutes.
     */
    public Ects(int ects) {
        Helper.checkMin(1, ects, "ECTS");
        this.ects = ects;
        this.duration = Duration.ofHours(ects * 30);
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
     * @param ects the ects
     */
    public void setEcts(int ects) {
        Helper.checkSpan(1, 20, ects, "ECTS");
        if (this.duration == null) {
            this.duration = Duration.ofHours(ects * 30);
        }
        if (this.duration != null && this.ects > ects) {
            setDuration(this.duration.minus(Duration.ofHours((this.ects - ects) * 30)));
        }
        if (this.duration != null && this.ects < ects) {
            setDuration(this.duration.minus(Duration.ofHours((this.ects - ects) * 30)));
        }
        this.ects = ects;

    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Ects: " + ects + "\n" +
                "Soll: " + durationTrim(duration.toString());
    }

    /**
     * Duration trim string.
     *
     * @param string the string
     * @return the string
     */
    public String durationTrim(String string) {
        return string.substring(2);
    }

    /**
     * To string 2 string.
     *
     * @return the string
     */
    public String toString2() {
        return "" + ects;
    }
}
