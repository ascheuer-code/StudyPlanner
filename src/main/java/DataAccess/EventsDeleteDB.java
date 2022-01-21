package DataAccess;
/*
 Autor Mahmoud Orabi und Lorenz Wollstein
 */
import Model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.List;






public class EventsDeleteDB {
    EntityManagerFactory entityManagerFactory ;
    EntityManager entityManager;
    EntityTransaction entityTransaction;


    public void  EventDelete (Event event){
        entityManagerFactory = Persistence.createEntityManagerFactory("StudyPlanner");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            Event event1 = entityManager.find(Event.class,event.getId());
            entityManager.remove(event1);
            entityTransaction.commit();
            entityManager.close();
            entityManagerFactory.close();

        }catch (Exception e){
            e.printStackTrace();
            entityTransaction.rollback();

        }

    }
}
