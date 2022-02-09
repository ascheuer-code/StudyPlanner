package DataAccess;



/*
 Autor Mahmoud Orabi und Lorenz Wollstein
*/

import Model.Modul;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ModulUpdateDB {


    public void Update(Modul modul1, EntityManager entityManager, EntityTransaction entityTransaction) {


        try {
            entityTransaction.begin();
            Modul modul = entityManager.find(Modul.class, modul1.getId());
            modul.setUuid(modul1.getUuid());
            modul.setModulname(modul1.getModulname());
            modul.setEcts(modul1.gettEcts());

            entityManager.persist(modul);

            entityTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }

    }


}



