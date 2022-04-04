package DataAccess;

import Model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * data access class to load an event from the data base
 */
public class LoadEventDB {

    /**
     * method to show the event
     * @param entityManager
     * @param entityTransaction
     * @return
     */
    public List<Event> zeigeEvent(EntityManager entityManager, EntityTransaction entityTransaction) {

        List<Event> events = new ArrayList<>();
        try {
            entityTransaction.begin();


            TypedQuery<Event> q =
                    entityManager.createQuery("select t from Event t", Event.class);

            List<Event> listOfSimpleEntities = q.getResultList();
            events.addAll(listOfSimpleEntities);


            entityTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();

            entityTransaction.rollback();


        }


        return events;
    }
}

