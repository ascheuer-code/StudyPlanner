package DataAccess;


/*
 Autor Mahmoud Orabi und Lorenz Wollstein
*/


import Model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

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

    public void insert(List<Event> event, EntityManager entityManager, EntityTransaction entityTransaction) {

        try {
            entityTransaction.begin();
            for (Event item : event) {
                entityManager.persist(item);
            }
            entityTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();


        }
    }

}

