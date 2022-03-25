package DataAccess.Model;

import Model.Ects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

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
     * Test for Ects
     * Conditions:
     * Ects must between 1 - 100
     * Duration must change when Ects value changes -> 1 Ects = 30 Duration in Hours
     */
    @Test
    void setEcts() {
        assertEquals(5, ects.getEctsValue());
        assertEquals(Duration.of(150, ChronoUnit.HOURS),ects.getDuration());

        ects.setEcts(4);

        assertEquals(Duration.of(120,ChronoUnit.HOURS),ects.getDuration());
        assertEquals(4, ects.getEctsValue());

        assertThrows(IllegalArgumentException.class, () -> {
            ects.setEcts(0);
        });
        assertThrows(IllegalArgumentException.class,() -> {
            ects.setEcts(101);
        });
    }

}