package Model;

import Helper.Helper;

/**
 * The type Model.
 *
 * @author Andreas Scheuer mahmoud orabi
 */
public class Modul {

    private Ects ects;
    private String modulname;

    /**
     * Instantiates a new Modul.
     *
     * @param modulname
     *                  the modulname
     * @param ects
     *                  the ects
     */
    public Modul(String modulname, int ects) {
        Helper.checkIfNullOrEmpty(modulname, "Modulname");

        this.modulname = modulname;
        this.ects = new Ects(ects);
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
     * Set modulname.
     *
     * @param modulname
     *                  the titel
     */
    public void setModulname(String modulname) {
        Helper.checkIfNullOrEmpty(modulname, "Modulname");
        this.modulname = modulname;
    }

    /**
     * Get ects int.
     *
     * @return the int
     */
    public Ects getEcts() {
        return ects;
    }

    /**
     * @param ects
     */
    public void setEcts(int ects) {
        this.ects = new Ects(ects);
    }

    /**
     * @return String
     */
    public String toString() {
        return (this.ects + "  " + "Modulname: " + this.modulname);

    }
}
