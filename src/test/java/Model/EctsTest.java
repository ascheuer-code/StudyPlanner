package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the Ects class.
 *
 * @author leon weyand
 */
class EctsTest {

    /**
     * The Ects.
     */
    Ects ects;

    /**
     * Sets up an Ects class with argument 5 to test.
     */
    @BeforeEach
    void setUp() {
        this.ects = new Ects(5);
    }

    /**
     * Tests if method getEcts returns correct value.
     */
    @Test
    void getEcts() {
        assertEquals(ects.getEctsValue(), 5);
    }


    /**
     * Sets ects.
     */
    @Test
    void setEcts() {
        assertEquals(5, ects.getEctsValue());
        ects.setEcts(4);
        assertEquals(4, ects.getEctsValue());

        assertThrows(IllegalArgumentException.class, () -> {
            ects.setEcts(0);
        });
    }

}