package DataAccess;

/*
 Autor Mahmoud Orabi und Lorenz Wollstein
*/

import Model.Modul;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ModulDeleteDB {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager ;
    EntityTransaction entityTransaction;

    public void  ModulDelete(Modul modul){
        entityManagerFactory = Persistence.createEntityManagerFactory("StudyPlanner");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction =entityManager.getTransaction();
        try {

            entityTransaction.begin();
            Modul modul1 = entityManager.find(Modul.class, modul.getId());
            entityManager.remove(modul1);

            entityTransaction.commit();
            entityManager.close();
            entityManagerFactory.close();

        }catch (Exception e){
            entityTransaction.rollback();
        }
    }


}
