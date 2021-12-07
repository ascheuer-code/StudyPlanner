package Model;

/**
 * The type Model.
 *
 * @author Andreas Scheuer mahmoud orabi
 */
public class Model {
    //klassenattribute
    private int ects;
    private String titel;

    /**
     * Instantiates a new Model.
     *
     * @param ects
     *         the ects
     * @param titel
     *         the titel
     */


    public Model(int ects, String titel) {
        Helper.checkSpan(0, 20, ects, "ECTS");
        Helper.checkIfNullOrEmpty(titel, "Titel");
        this.ects = ects;
        this.titel = titel;
    }

    /**
     * Get titel string.
     *
     * @return the string
     */

    public String getTitel() {
        return titel;
    }

    /**
     * Set titel.
     *
     * @param titel
     *         the titel
     */

    public void setTitel(String titel) {
        Helper.checkIfNullOrEmpty(titel, "Titel");
        this.titel = titel;

    }

    /**
     * Get ects int.
     *
     * @return the int
     */

    public int getEcts() {
        return ects;
    }

    /**
     * Set ects.
     *
     * @param ects
     *         the ects
     */

    public void setEcts(int ects) {

        Helper.checkSpan(0, 20, ects, "ECTS");
        this.ects = ects;
    }

    public String toString() {
        return ("Ects: " + this.ects + "  " + "Titel: " + this.titel);

    }
}


