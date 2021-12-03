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
     *
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
        assertEquals(ects.getEcts(), 5);
    }

    /**
     * Tests if method getMinutes returns correct value.
     */
    @Test
    void getMinutes() {
        assertEquals(ects.getMinutes(), 9000);
    }

    /**
     * Test if method toString returns correct String.
     */
    @Test
    void testToString() {
        assertEquals(ects.toString(), "ECTS: 5; Minutes: 9000");
    }

    /**
     * Tests if EctsException is thrown if negative Integer is given.
     *
     */
    @Test
    void testEctsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Ects ects2 = new Ects(-1);
        });
        String expectedMessage = "Ects need to be positive!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}