package DataAccess;


import Model.Event;
import Model.Modul;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class VerwaltungBD {
    EntityManagerFactory entityManagerFactory ;
    EntityManager entityManager ;
    EntityTransaction  entityTransaction;
    public VerwaltungBD(){

        entityManagerFactory = Persistence.createEntityManagerFactory("StudyPlanner");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

    }
    public void SaveModulDB(Modul modul){
          entityTransaction.commit();
          entityManager.persist(modul);
          entityTransaction.commit();
    }
    public Modul find(int id ){ return  entityManager.find(Modul.class,id);}
    public Event find1(String id ){ return  entityManager.find(Event.class,id);}
    public void ModulUpdateDB(Modul modul){
         Modul modul1 = find(modul.getId());
         entityTransaction.begin();
         modul1.setEcts(modul.gettEcts());
         modul1.setModulname(modul.getModulname());
         entityTransaction.commit();

    }
   public void ModulDeleteDB(Modul modul){
        entityTransaction.begin();
        entityManager.remove(modul);
        entityTransaction.commit();
   }
  public void SaveEventDB(Event event){
      entityTransaction.commit();
      entityManager.persist(event);
      entityTransaction.commit();

  }
  public void EventUpateDB( Event event  ){
        Event event1 = find1(event.getId());
        entityTransaction.begin();
         event1.setTitle(event.getTitle());
         event1.setStarDate(event.getStarDate());
         event1.setEndDate(event.getEndDate());
         event1.setStartTime(event.getStartTime());
         event1.setEndTime(event.getEndTime());
         event1.setId();
         entityManager.persist(event1);
         entityTransaction.begin();

  }
  public void  DeletEventDB(Event event ){
      entityTransaction.commit();
      entityManager.remove(event);
      entityTransaction.commit();

  }
  public List<Event> LoadEvent(){
      List<Event> events = new ArrayList<>();
      entityTransaction.begin();
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

     entityTransaction.commit();

     return   events;
  }
  public  List<Modul> LoadmodulDB(){
      List<Modul> moduls = new ArrayList<Modul>();
       entityTransaction.begin();
       Query query = entityManager.createQuery("select  ects from Modul ");
      List<String> stringList = query.getResultList();
      Query query1 = entityManager.createQuery("select  ects from Modul ");
      List<String> stringList1 = query.getResultList();


      for(Modul modul : moduls){
          modul.setModulname(modul.getModulname());
          modul.setEcts(modul.gettEcts());


      }
     return moduls;
  }
  public void close(){
        entityManager.close();
        entityManager.close();
  }
}
