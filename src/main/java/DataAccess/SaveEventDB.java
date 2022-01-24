package DataAccess;


/*
 Autor Mahmoud Orabi und Lorenz Wollstein
*/


import Model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SaveEventDB {


    public void insert(Event event, EntityManager entityManager, EntityTransaction entityTransaction) {

        try {
            entityTransaction.begin();
            entityManager.persist(event);
            entityTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();


        }
    }

}

