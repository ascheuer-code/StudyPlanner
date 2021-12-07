package View;

public class Modul {
    public String Modulname;
    public int ECTS;

    public Modul(String modulname, int ECTS) {
        this.Modulname = modulname;
        this.ECTS = ECTS;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return Modulname + "   ECTS=" + ECTS;
    }
}
