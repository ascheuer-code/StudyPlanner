package Model;


    /** die klasse entry nimmt alle Model
     * autor mahmoud orabi
     * **/
    public class Entry<E> {
        //klassenattribute
        private E  modul ;

        // hier wird das Eintrag von Modul erstellen und überprüft ob er null oder nicht
        public Entry  ( E einmodul){
            if (einmodul == null ) {throw new IllegalArgumentException();}
            modul = einmodul;
        }
        @Override
        public String toString() {
            return "Entry [modul=" + modul + "]";
        };

    }

