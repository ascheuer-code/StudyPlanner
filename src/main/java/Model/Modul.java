package Model;

import Helper.Helper;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The type Model.
 *
 * @author Andreas Scheuer mahmoud orabi
 */
public class Modul {

    private final Ects ects;
    private final ArrayList<String> uuid;
    private String modulname;

    /**
     * Instantiates a new Modul.
     *
     * @param modulname
     *         the modulname
     * @param ects
     *         the ects
     */
    public Modul(String modulname, int ects) {
        Helper.checkIfNullOrEmpty(modulname, "Modulname");

        this.modulname = modulname;
        this.ects = new Ects(ects);
        this.uuid = new ArrayList<>();
    }

    public ArrayList<String> getUuid() {
        return uuid;
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
     *         the titel
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
        this.ects.setEcts(ects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ects, modulname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modul modul = (Modul) o;
        return Objects.equals(ects, modul.ects) && Objects.equals(modulname, modul.modulname);
    }

    /**
     * @return String
     */
    public String toString() {
        return (this.modulname + " " + this.ects + "  ");

    }
}
