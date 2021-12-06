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
        private static  final String ECTS_FEHLER = " die Ects soll > 0 und < 20" ;
        private static  final String TITEL_Felher = "Titel soll nicht leer sein ";
        // Konstruktor , der  (ects) als Integer  und (titel ) als String darstellt.
        public Model(int ects , String titel){
            check ((ects > 0 && ects  < 20) , ECTS_FEHLER);
            check(titel != null || titel.isEmpty() || titel.isBlank() ,TITEL_Felher);
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
        public void setEcts(int e){
            this.ects= e;
        }
        //hier wird den Ects ändern
        public void setTitel(String t ){
            this.titel= t;

        }
        // methode zur testen
        public void  check (boolean b , String n )  {
            if (!b){
                throw new IllegalArgumentException(n) ;
            }

        }
        public String toString (){
            return ("Ects: " + this.ects+ "  " + "Titel: " +this.titel);

        }
    }


