package DataAccess;



/*
 Autor Mahmoud Orabi und Lorenz Wollstein
*/

import Model.Modul;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
public class ModulUpdateDB {

    EntityManagerFactory entityManagerFactory ;
    EntityManager entityManager;
    EntityTransaction entityTransaction;
    public void Update(Modul modul1){
        entityManagerFactory = Persistence.createEntityManagerFactory("StudyPlanner");
        entityManager  = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        try {
            Modul  modul = entityManager.find(Modul.class, modul1.getId());

            entityTransaction.begin();
            modul.setEcts(modul1.gettEcts());
            modul.setModulname(modul1.getModulname());
            entityTransaction.commit();
            entityManager.close();
            entityManagerFactory.close();

        } catch (Exception e){
            e.printStackTrace();
            entityTransaction.rollback();
        }

    }


}



