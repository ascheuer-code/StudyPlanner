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
     * Gets ects minutes test.
     */
    @Test
    void getEctsMinutesTest() {
        assertEquals(18000, modul.getEcts().getMinutes());
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
        assertEquals(1800, modul.getEcts().getMinutes());
    }

    @Test
    void ModultoStringTest() {
        assertEquals("Ects: 10; Minutes: 18000  Modulname: Test", modul.toString());
    }
}
