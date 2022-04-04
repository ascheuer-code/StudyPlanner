package DataAccess;

/**
 * Autor Mahmoud Orabi und Lorenz Wollstein
 **/

import Model.Modul;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * data access class to insert a modul in the database
 */
public class SaveModulDB {

    /**
     * method to insert a mudul in the database
     * @param modul
     * @param entityManager
     * @param entityTransaction
     */
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

