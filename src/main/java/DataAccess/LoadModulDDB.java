package DataAccess;
/*
 Autor Mahmoud Orabi und Lorenz Wollstein
*/

import Model.Ects;
import Model.Modul;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
public class LoadModulDDB {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;
    List modulList = new ArrayList<>();

    public List<Modul> zeigemodul (){


        entityManagerFactory = Persistence.createEntityManagerFactory("StudyPlanner");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();


        try {
            transaction.begin();

            Query query = entityManager.createQuery("select id from Modul");
            List<Integer> id = query.getResultList();

            Query query1 = entityManager.createQuery("select modulname from Modul");
            List<String> modulname = query1.getResultList();

            Query query2 = entityManager.createQuery("select ects from Modul");
            List<Integer> ects = query2.getResultList();

            for(int i = 0; i< modulname.size(); i++){
                Modul modul = new Modul();
                modul.setId(id.get(i));
                modul.setModulname(modulname.get(i));
                modul.setEcts(ects.get(i));


                modulList.add(modul);
            }

            modulList.stream().forEach(e -> System.out.println(e));

            transaction.commit();

            entityManager.close();
            entityManagerFactory.close();






        }catch (Exception e ){
            e.printStackTrace();
            transaction.rollback();
        }

        return modulList;
    }

}
