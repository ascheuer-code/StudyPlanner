package Model;

import java.lang.*;
import java.util.ArrayList;

/**
 * The type Entrylist.
 */
public class Entrylist {
    //Klassenattribute

    private  ArrayList <Entry<Model>> entrylist;

    /**
     * Instantiates a new Entrylist.
     */
// hier wird die Einträgenliste angelegt
    public Entrylist(){

        entrylist = new ArrayList<Entry<Model>>();

    }

    /**
     * Entry add.
     *
     * @param ent
     *         the ent
     */
// man fügt einen Eintrag hinzu
    public void EntryAdd(Entry<Model>     ent ) {
        Helper.checkObj(ent);
        entrylist.add(ent);
    }

    /**
     * Entry loesch.
     *
     * @param ent
     *         the ent
     */
// man kann den Eintrag löeschen
    public void EntryLoesch(Entry <Model>    ent ) {
        Helper.checkObj(ent);
        entrylist.remove(ent);
    }

    /**
     * Entry aendere.
     *
     * @param ent1
     *         the ent 1
     * @param ent2
     *         the ent 2
     */
// man kann den Eintag ändern
    public void EntryAendere(Entry<Model>     ent1 , Entry <Model> ent2 ) {
        Helper.checkObj(ent1);
        Helper.checkObj(ent2);


        entrylist.add(entrylist.indexOf(ent1),ent2);

    }

    /**
     * Entry get entry.
     *
     * @param index
     *         the index
     *
     * @return the entry
     */
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


