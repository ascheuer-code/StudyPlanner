package Model;


    /** die klasse entry nimmt alle Model
     * Autor Mahmoud Orabi
     * **/
    public class Entry<E> {
        //Klassenattribute
        private  E  model ;

        // hier wird der Eintrag von Modul erstellen und überprüft, ob er null oder nicht
        public Entry  ( E einmodel){
           Helper.checkObj(einmodel);
           this.model = einmodel;
        }
        @Override
        public String toString() {
            return "Entry [model=" + model + "]";
        }

    }

