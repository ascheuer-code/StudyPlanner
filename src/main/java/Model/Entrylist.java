package Model;

import java.lang.*;
import java.util.ArrayList;

public class Entrylist {
    //Klassenattribute

    private final ArrayList <Entry<Model>> entrylist;
    // hier wird die Einträgenliste angelegt
    public Entrylist(){

        entrylist = new ArrayList<Entry<Model>>();

    }
    // man fügt einen Eintrag hinzu
    public void EntryAdd(Entry<Model>     ent ) {
        Helper.checkObj(ent);
        entrylist.add(ent);
    }
    // man kann den Eintrag löeschen
    public void EntryLoesch(Entry <Model>    ent ) {
        Helper.checkObj(ent);
        entrylist.remove(ent);
    }
    // man kann den Eintag ändern
    public void EntryAendere(Entry<Model>     ent1 , Entry <Model> ent2 ) {
        Helper.checkObj(ent1);
        Helper.checkObj(ent2);


        entrylist.add(entrylist.indexOf(ent1),ent2);

    }
    // den Eintrag wird gezeigt
    public Entry<Model> EntryGET(int index){

        Helper.checkMax(entrylist.size()+1,index,"INDEX");
        Helper.checkMin(0,index,"INDEX");

        return entrylist.get(index);
    }

    @Override
// dadurch kann alle Einträge gezeigt werden .
    public String toString() {
        return "EntryList [entrylist=" + entrylist + "]";
    }  }


