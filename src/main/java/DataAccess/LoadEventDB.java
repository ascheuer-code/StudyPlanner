package DataAccess;

import Model.Event;

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
            Query query = entityManager.createQuery("select title from Event ");
            List<String> strings = query.getResultList();
            Query query1 = entityManager.createQuery("select starDate from Event ");
            List<String> strings1 = query1.getResultList();
            Query query2 = entityManager.createQuery("select startTime from Event ");
            List<String> strings2 = query2.getResultList();
            Query query3 = entityManager.createQuery("select endTime from Event ");
            List<String> strings3 = query3.getResultList();
            Query query4 = entityManager.createQuery("select endDate from Event ");

            List<String> strings4 = query4.getResultList();
            Query query5 = entityManager.createQuery("select id from Event ");
            List<String> strings5 = query5.getResultList();
            events = new ArrayList<>();
            Event event = new Event();
            for (int i = 0; i < strings.size(); i++) {

                event.setTitle(strings.get(i));
                event.setStarDate(strings1.get(i));
                event.setStartTime(strings2.get(i));
                event.setEndTime(strings3.get(i));
                event.setEndDate(strings4.get(i));
                events.add(event);
            }


            transaction.commit();
            entityManager.close();
            entityManagerFactory.close();

        } catch (Exception e) {
            e.printStackTrace();

            transaction.rollback();

        }


        return events;
    }}

