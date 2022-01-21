package DataAccess;

/*
 Autor Mahmoud Orabi und Lorenz Wollstein
*/

import Model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EventUpdateDB {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager ;
    EntityTransaction entityTransaction;

    public void updateEvent(Event event ){
        entityManagerFactory = Persistence.createEntityManagerFactory("StudyPlanner");
        entityManager  = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            Event event1 = entityManager.find(Event.class, event.getId());



            event1.setTitle(event.getTitle());
            event1.setStarDate(event.getStarDate());
            event1.setStartTime(event.getStartTime());
            event1.setEndTime(event.getEndTime());
            event1.setEndDate(event.getEndDate());
            entityTransaction.commit();
            entityManager.close();
            entityManagerFactory.close();

        }catch (Exception e){

            entityTransaction.rollback();
        }

    }


}
