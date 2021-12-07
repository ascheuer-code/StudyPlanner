package Model;

/**
 * The type Modul.
 */
public class Modul {
    /**
     * The Modulname.
     */
    public String Modulname;
    /**
     * The Ects.
     */
    public int ECTS;

    /**
     * Instantiates a new Modul.
     *
     * @param modulname
     *         the modulname
     * @param ECTS
     *         the ects
     */
    public Modul(String modulname, int ECTS) {
        this.Modulname = modulname;
        this.ECTS = ECTS;
    }


    @Override
    public String toString() {
        return Modulname + "   ECTS=" + ECTS;
    }
}