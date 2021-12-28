package Helper;

/**
 * The type Message.
 */
public class Message {

    /**
     * Fehlermeldung für Lib_Digits checkSpan()
     *
     * @param <T>       beliebige Zahl
     * @param <K>       beliebiger String
     * @param min       Minimalwert
     * @param max       Maximalwert
     * @param value     the value
     * @param fieldname Feldname des Attributes
     * @return String Fehlermeldung
     */
    public static <T, K> String errorWrongValueSpan(T min, T max, T value, K fieldname) {
        return String.format("Der eingegebene Wert bei %s ist: %s er unterschreitet %s oder überschreitet %s",
                fieldname.toString(), value.toString(), min.toString(), max.toString());
    }

    /**
     * Fehlermeldung für Lib_Digits checkMin()
     *
     * @param <T>       beliebige Zahl
     * @param <K>       beliebiger String
     * @param min       Minimalwert
     * @param value     the value
     * @param fieldname Feldname des Attributes
     * @return String Fehlermeldung
     */
    public static <T, K> String errorWrongValueMin(T min, T value, K fieldname) {
        return String.format("Der eingegebene Wert bei %s ist: %s er unterschreitet %s", fieldname.toString(),
                value.toString(), min.toString());
    }

    /**
     * Fehlermeldung für Lib_Digits checkMax()
     *
     * @param <T>       beliebige Zahl
     * @param <K>       beliebiger String
     * @param max       Maximalwert
     * @param value     the value
     * @param fieldname Feldname des Attributes
     * @return String Fehlermeldung
     */
    public static <T, K> String errorWrongValueMax(T max, T value, K fieldname) {
        return String.format("Der eingegebene Wert bei %s ist: %s überschreitet %s", fieldname.toString(),
                value.toString(), max.toString());
    }

    /**
     * Fehlermeldung für Lib_String checkIfNullOrEmpty()
     *
     * @param <K>       beliebiger String
     * @param fieldname Feldname des Attributes
     * @return String Fehlermeldung
     */
    public static <K> String errorEmptyString(K fieldname) {
        return String.format("Das Feld %s darf nicht leer sein", fieldname.toString());
    }

    /**
     * Error empty object string.
     *
     * @param <K>        the type parameter
     * @param objectname the objectname
     * @return String string
     */
    public static <K> String errorEmptyObject(K objectname) {
        return String.format("Das Object %s darf nicht leer sein", objectname.toString());
    }

    /**
     * Fehlermeldung für Lib_Arrays checkNullPointer()
     *
     * @param <T>   beliebige Zahl
     * @param index Index der überprüft wurde
     * @return String Fehlermeldung
     */
    public static <T> String errorNullPointerArray(T index) {
        return String.format("Der angegeben Index %s ist nicht belegt", index.toString());
    }

    /**
     * Fehlermeldung für Lib_File existent()
     *
     * @param fieldname the fieldname
     * @return the string
     */
    public static String errorFileNotFound(String fieldname) {
        return String.format("Die folgende Datei %s wurde nicht gefunden", fieldname);
    }
}