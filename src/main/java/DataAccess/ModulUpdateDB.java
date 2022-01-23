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


           entityTransaction.begin();
            Modul  modul = entityManager.find(Modul.class, modul1.getId());
            modul.setUuid(modul1.getUuid());
            modul.setModulname(modul1.getModulname());
            modul.setEcts(modul1.gettEcts());

            entityManager.persist(modul);

            entityTransaction.commit();
            entityManager.close();
            entityManagerFactory.close();

        } catch (Exception e){
            e.printStackTrace();
            entityTransaction.rollback();
        }

    }


}



