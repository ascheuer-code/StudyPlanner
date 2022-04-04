package Model;

import Helper.Helper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The class Model.
 *
 * @author Andreas Scheuer mahmoud orabi
 */
@Entity
public class Modul {

    @Id
    private String id;
    @Column
    private String modulname;
    @Embedded
    private Ects ects;

    @ElementCollection
    @Column
    private List<String> uuid;


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
        this.id = getId();
        this.uuid = new ArrayList<>();


    }

    /**
     * getId
     * return String
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     */
    public Modul() {
    }

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public List<String> getUuid() {
        return uuid;
    }

    public void setUuid(List<String> uuid) {
        this.uuid = uuid;
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
     * Sets ects.
     *
     * @param ects
     *         the ects
     */
    public void setEcts(int ects) {
        this.ects.setEcts(ects);
    }

    /**
     * get ects
     *
     * @return ects
     */
    public int gettEcts() {
        return ects.getEctsValue();

    }

    @Override
    public int hashCode() {
        return Objects.hash(ects, modulname);
    }

    /**
     * equals method to check if two Objects are equal
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modul modul = (Modul) o;
        return Objects.equals(ects, modul.ects) && Objects.equals(modulname, modul.modulname);
    }

    /**
     * toString
     * @return String
     */
    public String toString() {
        return (this.modulname);

    }

    /**
     * toString2
     * @return String
     */
    public String toString2() {
        return (this.modulname + "\n" + this.ects.toStringEcts() + "  ");
    }

    /**
     * Sets id.
     */
    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

}
