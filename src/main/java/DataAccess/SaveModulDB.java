package DataAccess;

/**
 * Autor Mahmoud Orabi und Lorenz Wollstein
 **/

import Model.Modul;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SaveModulDB {

    public void insert(Modul modul, EntityManager entityManager, EntityTransaction entityTransaction) {

        try {
            entityTransaction.begin();
            entityManager.persist(modul);
            entityTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();

        }

    }


}

