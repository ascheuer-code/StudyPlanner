package DataAccess;
/*
 Autor Mahmoud Orabi und Lorenz Wollstein
 */

import Model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;


public class EventsDeleteDB {


    public void EventDelete(Event event, EntityManager entityManager, EntityTransaction entityTransaction) {

        try {
            entityTransaction.begin();
            Event event1 = entityManager.find(Event.class, event.getId());
            entityManager.remove(event1);
            entityTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();

        }

    }

    public void EventDelete(List<Event> event, EntityManager entityManager, EntityTransaction entityTransaction) {

        try {
            entityTransaction.begin();
            for (Event item : event) {
                entityManager.remove(item);
            }
            entityTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();

        }

    }
}
