package Model;

/**
 * This class converts ECTS to minutes.
 *
 * @author leon weyand
 */
public class Ects {
    private static final String ECTS_POSITIVE = "Ects need to be positive!"; // <- this is not needed because the Error Message will be generated in Message Class with the Helper Method
    private int ects;
    private int minutes;
    private Helper helper; // not needed see next comment below

    /**
     * Instantiates a new Ects.
     *
     * @param ects
     *         the ECTS that need to be converted to minutes.
     */
    public Ects(int ects) {
        // @Leon The Helper class is Static so no Object will be needed just use Helper.checkMin(min,Value,Fieldname) thats all :D
        helper = new Helper(); // <- unused
        //helper.checkMin(1, ects, ECTS_POSITIVE);
        Helper.checkMin(1, ects, "ECTS"); // <- like this
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
