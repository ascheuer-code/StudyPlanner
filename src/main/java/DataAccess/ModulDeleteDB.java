package DataAccess;

/*
 Autor Mahmoud Orabi und Lorenz Wollstein
*/

import Model.Modul;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ModulDeleteDB {


    public void ModulDelete(Modul modul, EntityManager entityManager, EntityTransaction entityTransaction) {

        try {

            entityTransaction.begin();
            Modul modul1 = entityManager.find(Modul.class, modul.getId());
            entityManager.remove(modul1);

            entityTransaction.commit();

        } catch (Exception e) {
            entityTransaction.rollback();
        }
    }


}
