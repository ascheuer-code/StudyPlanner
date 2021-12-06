package Model;

import java.lang.*;
import java.util.ArrayList;

public class Entrylist {
    //Klassenattribute
    private ArrayList <Entry<Model>> entrylist;
    // hier wird die Einträgeliste angelegt
    public Entrylist(){
        entrylist = new ArrayList<Entry<Model>>();

    }
    // man fügt einen Eintrag hinzu
    public void EntryAdd(Entry<Model>     ent ) {
        if (ent == null) {
            throw new IllegalArgumentException("der Eintrag soll nicht leer sein danke :)");
        }
        entrylist.add(ent);
    }
    // man kann den Eintrag löeschen
    public void EntryLoesch(Entry <Model>    ent ) {
        if (ent == null) {
            throw new IllegalArgumentException("der Eintrag soll nicht leer sein danke :)");
        }
        entrylist.remove(ent);
    }
    // man kann den Eintag ändern
    public void EntryAendere(Entry<Model>     ent1 , Entry <Model> ent2 ) {
        if (ent2 == null) {
            throw new IllegalArgumentException("der Eintrag soll nicht leer sein danke :)");
        }


        entrylist.add(entrylist.indexOf(ent1),ent2);

    }
    // den Eintrag wird gezeigt
    public Entry<Model> EntryGET(int index){
        return entrylist.get(index);
    }

    @Override
// dadurch kann alle Einträge gezeigt werden .
    public String toString() {
        return "EntryList [entrylist=" + entrylist + "]";
    }  }


