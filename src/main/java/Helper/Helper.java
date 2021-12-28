package Helper;

import java.text.DecimalFormat;

/**
 * The type Helper.
 *
 * @author Andreas Scheuer
 */
public class Helper {

    /**
     * Prüft ob, der Wert kleiner gleich min und größer gleich Max ist.
     *
     * @param <T>       beliebige Zahl
     * @param <K>       beliebiger String
     * @param min       Mindest Wert
     * @param max       Maximal Wert
     * @param value     Prüfwert
     * @param fieldname the fieldname
     */
    public static <T extends Number, K> void checkSpan(T min, T max, T value, K fieldname) {
        if (value.doubleValue() >= min.doubleValue() && value.doubleValue() <= max.doubleValue()) {
            return;
        }
        throw new IllegalArgumentException(Message.errorWrongValueSpan(min, max, value, fieldname));
    }

    /**
     * Check obj.
     *
     * @param <T>        the type parameter
     * @param object     the object
     * @param objectname the objectname
     */
    public static <T> void checkObj(T object, String objectname) {
        if (object.equals(null)) {
            throw new IllegalArgumentException(Message.errorEmptyObject(objectname));
        }


    }

    /**
     * Prüft ob der Wert größer gleich min ist
     *
     * @param <T>       beliebige Zahl
     * @param <K>       beliebiger String
     * @param min       Mindest Wert
     * @param value     Prüfwert
     * @param fieldname the fieldname
     */
    public static <T extends Number, K> void checkMin(T min, T value, K fieldname) {
        if (value.doubleValue() >= min.doubleValue()) {
            return;
        }
        throw new IllegalArgumentException(Message.errorWrongValueMin(min, value, fieldname));
    }

    /**
     * Prüft ob der Wert kleiner gleich das max ist.
     *
     * @param <T>       beliebige Zahl
     * @param <K>       beliebiger String
     * @param max       the max
     * @param value     Prüfwert
     * @param fieldname the fieldname
     */
    public static <T extends Number, K> void checkMax(T max, T value, K fieldname) {
        if (value.doubleValue() <= max.doubleValue()) {
            return;
        }
        throw new IllegalArgumentException(Message.errorWrongValueMax(max, value, fieldname));
    }

    /**
     * Formatiert und wandelt den Integer nach einem bestimmten Format in einen
     * String
     *
     * @param <T>    beliebige Zahl
     * @param <K>    beliebiger String
     * @param value  Integer-Wert
     * @param format Die Stringformatierung
     * @return Formatierter String
     */
    public static <T extends Number, K> String numberFormatter(T value, K format) {

        DecimalFormat df = new DecimalFormat(format.toString());

        return df.format(value.doubleValue());
    }

    /**
     * Überprüft ob der übergebene String leer oder NULL ist
     *
     * @param string    Der zu prüfende String
     * @param fieldname the fieldname
     */
    public static void checkIfNullOrEmpty(String string, String fieldname) {
        if (!string.isEmpty() && !string.isBlank()) {
            return;
        }
        throw new IllegalArgumentException(Message.errorEmptyString(fieldname));
    }

    /**
     * Methode zum entfernen nicht benötigter Leerstellen
     *
     * @param string the string
     * @return the string
     */
    public static String allWhitespaceToOne(String string) {
        string = string.stripTrailing();
        string = string.stripLeading();
        string = string.replaceAll("\\s+", " ");
        return string;
    }

    /**
     * Methode zum entfernen aller Leerstellen
     *
     * @param string the string
     * @return the string
     */
    public static String removeAllWhitespaces(String string) {
        string = string.replaceAll("\\s+", "");
        return string;
    }

    /**
     * Methode zum entfernen nicht benötigter Leerstellen sowie Zeichen die bei der
     * ausgabe eine ArrayList entstehen
     *
     * @param string the string
     * @return the string
     */
    public static String trimArrayList(String string) {
        string = string.replaceAll(", ", "");
        string = string.replace("[", "");
        string = string.replace("]", "");
        return string;
    }

}
