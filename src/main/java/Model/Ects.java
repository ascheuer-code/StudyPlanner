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
     * @param ects
     *         the ects
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Ects: " + ects + "\n" +
                "Soll: " + durationTrim(duration.toString());
    }

    public String durationTrim(String string) {
        String result = string.substring(2);
        return result;
    }

    public String toString2() {
        return "" + ects;
    }
}
