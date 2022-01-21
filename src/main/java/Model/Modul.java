package Model;

import Helper.Helper;

import javax.persistence.*;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.List;
/**
 * The type Model.
 *
 * @author Andreas Scheuer mahmoud orabi
 */
@Entity
public class Modul {

    @Id
    @GeneratedValue (strategy = GenerationType.TABLE)
    private int  id;
    @Column
    private String modulname;
    @Embedded
    private  Ects ects;
    @ElementCollection
    @Column
    private  List<String> uuid;


    /**
     * Instantiates a new Modul.
     *
     * @param modulname the modulname
     * @param ects      the ects
     */
    public Modul(String modulname , int ects ){
        Helper.checkIfNullOrEmpty(modulname, "Modulname");
        this.modulname = modulname;
        this.ects = new Ects(ects) ;
        this.id = getId();
        this.uuid = new ArrayList<>();



    }


    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public List<String> getUuid() {
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
     * @param modulname the titel
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

    public int gettEcts(){
        return ects.getEctsValue();

    }

    /**
     * Sets ects.
     *
     * @param ects the ects
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
        return (this.modulname + "\n" + this.ects + "  ");

    }
    /*
    * get
    * return String
     */
    public int getId(){
        return id;
    }
    /**
     * Sets id.
     */
    public void  setId(int id ) {
        this.id = id ;
    }

    public void setUuid(List<String> uuid) {
        this.uuid = uuid;
    }

    /**
     * Sets id.
     */
 public Modul(){ }

}
