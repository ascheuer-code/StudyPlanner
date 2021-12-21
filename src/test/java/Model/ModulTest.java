package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Modul test.
 *
 * @author Andreas Scheuer
 */
public class ModulTest {

    /**
     * The Modul.
     */
    Modul modul;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        modul = new Modul("Test", 10);
    }

    /**
     * Gets ects test.
     */
    @Test
    void getEctsTest() {
        assertEquals(10, modul.getEcts().getEctsValue());
    }

    /**
     * Gets modulname test.
     */
    @Test
    void getModulnameTest() {
        assertEquals("Test", modul.getModulname());
    }

    /**
     * Sets modulname test.
     */
    @Test
    void setModulnameTest() {
        modul.setModulname("Affe");
        assertEquals("Affe", modul.getModulname());
    }

    /**
     * Sets ects test.
     */
    @Test
    void setEctsTest() {
        modul.setEcts(1);
        assertEquals(1, modul.getEcts().getEctsValue());
        assertEquals(30, modul.getEcts().getDuration());
    }

}
