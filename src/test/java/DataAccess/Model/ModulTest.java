package DataAccess.Model;

import Model.Modul;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        assertThrows(IllegalArgumentException.class,() -> {
            modul.setModulname("");
        });
        assertThrows(NullPointerException.class,() -> {
            modul.setModulname(null);
        });
    }

    /**
     * Sets ects test.
     * Condition:
     * Ects and Duration must change properly
     */
    @Test
    void setEctsTest() {
        modul.setEcts(1);
        assertEquals(1, modul.getEcts().getEctsValue());
        assertEquals("PT30H", modul.getEcts().getDuration().toString());

        modul.setEcts(2);
        assertEquals(2,modul.getEcts().getEctsValue());
        assertEquals("PT60H", modul.getEcts().getDuration().toString());
    }
}
