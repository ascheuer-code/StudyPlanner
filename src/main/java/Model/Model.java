package Model;

/**
 * The type Model.
 * @author Andreas Scheuer
 * mahmoud orabi
 */

    public class Model {
        //klassenattribute
        private int ects;
        private String titel;
        //private static  final String ECTS_FEHLER = " die Ects soll > 0 und < 20" ;
        //private static  final String TITEL_Felher = "Titel soll nicht leer sein ";
        // Konstruktor , der  (ects) als Integer  und (titel ) als String darstellt.
        public Model(int ects , String titel){
            Helper.checkSpan(0,20,ects,"ECTS");
            Helper.checkIfNullOrEmpty(titel,"Titel");
            this.ects= ects;
            this.titel= titel;
        }
        // hier wird den Titel gezeigt
        public String getTitel (){
            return titel;
        }
        //hier wird den Ects gezeigt
        public int getEcts(){
            return ects;
        }
        // hier wird den Ects ändern
        public void setEcts(int ects){

            Helper.checkSpan(0,20,ects,"ECTS");
            this.ects= ects;
        }
        //hier wird den Ects ändern
        public void setTitel(String titel ){
            Helper.checkIfNullOrEmpty(titel,"Titel");
            this.titel= titel;

        }

        public String toString (){
            return ("Ects: " + this.ects+ "  " + "Titel: " +this.titel);

        }
    }


