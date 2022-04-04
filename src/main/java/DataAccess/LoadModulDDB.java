package DataAccess;
/*
 Autor Mahmoud Orabi und Lorenz Wollstein
*/


import Model.Modul;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * data access class to load a modul
 */
public class LoadModulDDB {

    List modulList = new ArrayList<>();

    /**
     * method to load the modul from the database
     * @param entityManager
     * @param entityTransaction
     * @return
     */
    public List<Modul> zeigemodul(EntityManager entityManager, EntityTransaction entityTransaction) {

        try {
            entityTransaction.begin();

            TypedQuery<Modul> q =
                    entityManager.createQuery("select t from Modul t", Modul.class);

            List<Modul> listOfSimpleEntities = q.getResultList();
            modulList.addAll(listOfSimpleEntities);


            //Query query = entityManager.createQuery("select id from Modul");
            // List<Integer> id = query.getResultList();

            // Query query1 = entityManager.createQuery("select modulname from Modul");
            // List<String> modulname = query1.getResultList();

            // Query query2 = entityManager.createQuery("select ects from Modul");
            // List ects = query2.getResultList();

            //for(int i = 0; i< modulname.size(); i++){
            //Modul modul = new Modul();
            //modul.setId(id.get(i));
            //modul.setModulname(modulname.get(i));
            //modul.setEcts((int) ects.get(i));


            //    modulList.add(modul);
            // }

            modulList.forEach(e -> System.out.println(e));

            entityTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }

        return modulList;
    }

}
