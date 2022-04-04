package DataAccess;

/*
 Autor Mahmoud Orabi und Lorenz Wollstein
*/

import Model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * data access class to update an event
 */
public class EventUpdateDB {

    /**
     * method to update the event in the database
     * @param event
     * @param entityManager
     * @param entityTransaction
     */
    public void updateEvent(Event event, EntityManager entityManager, EntityTransaction entityTransaction) {

        try {
            entityTransaction.begin();
            Event oldEvent = entityManager.find(Event.class, event.getId());
            oldEvent.setCalendar(event.getCalendar());
            oldEvent.setEndTime(event.getEndTime());
            oldEvent.setStartTime(event.getStartTime());
            oldEvent.setStarDate(event.getStarDate());
            oldEvent.setEndDate(event.getEndDate());
            oldEvent.setTitle(event.getTitle());

            entityManager.persist(oldEvent);
            entityTransaction.commit();

        } catch (Exception e) {

            entityTransaction.rollback();
        }

    }


}
