package DataAccess;

import Model.Event;
import Model.Modul;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

public class LoadEventDB {


    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;




    public List<Event> zeigeEvent() {
        entityManagerFactory = Persistence.createEntityManagerFactory("StudyPlanner");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        List<Event> events = new ArrayList<>();
        try {
            transaction.begin();


            TypedQuery<Event> q =
                    entityManager.createQuery("select t from Event t", Event.class);

            List<Event> listOfSimpleEntities = q.getResultList();
            events.addAll(listOfSimpleEntities);



            transaction.commit();
            entityManager.close();
            entityManagerFactory.close();

        } catch (Exception e) {
            e.printStackTrace();

            transaction.rollback();


        }


        return events;
    }}

