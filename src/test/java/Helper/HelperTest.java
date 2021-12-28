package Helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Helper test.
 *
 * @author Andreas Scheuer
 */
class HelperTest {

    /**
     * Check span.
     *
     * @author Andreas Scheuer
     */
    @Test
    void checkSpan() {
        assertThrows(IllegalArgumentException.class, () -> {
            Helper.checkSpan(0, 1, 2, "Test");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Helper.checkSpan(0, 1, -1, "Test");
        });
    }

    /**
     * Check min.
     *
     * @author Andreas Scheuer
     */
    @Test
    void checkMin() {
        assertThrows(IllegalArgumentException.class, () -> {
            Helper.checkMin(0, -1, "Test");
        });

    }

    /**
     * Check max.
     *
     * @author Andreas Scheuer
     */
    @Test
    void checkMax() {
        assertThrows(IllegalArgumentException.class, () -> {
            Helper.checkMax(1, 2, "Test");
        });
    }

    /**
     * Check if null or empty.
     *
     * @author Andreas Scheuer
     */
    @Test
    void checkIfNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            Helper.checkIfNullOrEmpty("", "Test");
        });

        String testString = null;
        assertThrows(NullPointerException.class, () -> {
            Helper.checkIfNullOrEmpty(testString, "Test");
        });
    }

    /**
     * All whitespace to one.
     *
     * @author Andreas Scheuer
     */
    @Test
    void allWhitespaceToOne() {
        assertEquals("Affe hat drei Haare", Helper.allWhitespaceToOne("Affe     hat     drei     Haare"));
    }

    /**
     * Remove all whitespaces.
     *
     * @author Andreas Scheuer
     */
    @Test
    void removeAllWhitespaces() {
        assertEquals("AffehatdreiHaare", Helper.removeAllWhitespaces("Affe     hat     drei     Haare"));
    }

}