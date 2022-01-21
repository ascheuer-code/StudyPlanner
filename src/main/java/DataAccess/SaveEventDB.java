package DataAccess;


/*
 Autor Mahmoud Orabi und Lorenz Wollstein
*/


import Model.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class SaveEventDB {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;



    public void insert(Event event) {
        entityManagerFactory = Persistence.createEntityManagerFactory("StudyPlanner");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        try {


            transaction.begin();
            entityManager.persist(event) ;
            transaction.commit();
            entityManager.close();
            entityManagerFactory.close();

        } catch (Exception e){
            e.printStackTrace();
            transaction.rollback();


        }}

}

