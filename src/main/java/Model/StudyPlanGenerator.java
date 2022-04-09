package Model;

import DataAccess.EventsDeleteDB;
import DataAccess.ModulUpdateDB;
import DataAccess.SaveEventDB;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.*;
import java.util.stream.Collectors;

import static Helper.LocalDateTimeConverter.convertEventToEntry;

/**
 * This class is for generating the custom studyplan
 *
 * @author Leon
 */
public class StudyPlanGenerator {
    List<Event> events;
    List<Modul> modules;
    Calendar studyPlan;
    HashMap<Modul, Integer> moduleDaysHashMap;
    HashMap<Modul, Integer> moduleDaysHashMap2;
    EntityManager entityManager;
    EntityTransaction entityTransaction;
    int counter = -1;

    /**
     * Initiates a new StudyPlanGenerator
     *
     * @param events            the events saved in the StudyPlan
     * @param modules           the modules saved in the StudyPlan
     * @param studyPlan         the Calendar showing the generated Studyplan
     * @param entityManager
     * @param entityTransaction
     */
    public StudyPlanGenerator(List<Event> events, List<Modul> modules, com.calendarfx.model.Calendar studyPlan,
                              EntityManager entityManager, EntityTransaction entityTransaction) {
        this.events = events;
        this.modules = modules;
        this.studyPlan = studyPlan;
        this.entityManager = entityManager;
        this.entityTransaction = entityTransaction;
        moduleDaysHashMap = new HashMap<>();
        moduleDaysHashMap2 = new HashMap<>();
    }

    /**
     * Starts the StudyPlanGenerator
     *
     */
    public void start() {
        modules.sort(Comparator.comparing(modul -> modul.gettEcts()));
        Collections.reverse(modules);
        calculateModuleDays();
        generateStudyPlan();
    }

    /**
     * calculates how many filler Events every Module gets assigned to, based on the
     * ECTS amount.
     * Saved in a HashMap with Module as key and fillerEvent Amount as value.
     *
     * @author Leon
     */
    private void calculateModuleDays() {
        int maxEcts = getMaxEcts();
        int currentDayAmount = 0;
        int fillerEventAmount = getFillerEvents().size();
        for (Modul module : modules) {
            double dayAmount = fillerEventAmount * (module.gettEcts() / (double) maxEcts);
            currentDayAmount += (int) dayAmount;
            moduleDaysHashMap.put(module, (int) dayAmount);
        }

        addAdditionalDays(fillerEventAmount - currentDayAmount);
    }

    /**
     * Assigns the amount of fillerEvents to Modules, that haven't been assigned
     * yet.
     *
     * @param days the amount of fillerEvents, that haven't been assigned
     * @author Leon
     */
    private void addAdditionalDays(int days) {
        int index = 0;
        while (days > 0) {

            if (index > modules.size() - 1) {
                index = 0;
            }

            moduleDaysHashMap.replace(modules.get(index), moduleDaysHashMap.get(modules.get(index)) + 1);
            days -= 1;
            index += 1;
        }
    }

    /**
     * Gets a List with all fillerEvents
     *
     * @return a List with fillerEvents
     * @author Leon
     */
    private List<Event> getFillerEvents() {
        List<Event> fillerEvents = new ArrayList<>();
        events.stream().filter(e -> e.getTitle().trim().equals("Filler")).forEach(e -> fillerEvents.add(e));
        sortFillerEvents(fillerEvents);
        return fillerEvents;
    }

    /**
     * Sorts Date and Time of event List.
     *
     * @param listToSort the fillerEvents that need to be sorted
     * @return a sorted List with fillerEvents
     * @author Leon
     */
    private List<Event> sortFillerEvents(List<Event> listToSort) {
        for(int i = 0; i < listToSort.size()-1; i++) {
            for(int j = 0; j < listToSort.size() - 1; j++) {
                String strI = listToSort.get(j+1).getStarDate() + ";" + listToSort.get(j+1).getStartTime();
                String strJ = listToSort.get(j).getStarDate() + ";" + listToSort.get(j).getStartTime();
                if(strI.compareTo(strJ) < 0) {
                    Event temp = listToSort.get(j);
                    listToSort.set(j, listToSort.get(j+1));
                    listToSort.set(j+1, temp);
                }
            }
        }
        return listToSort;
    }

    /**
     * Gets the ECTS sum of all Modules.
     *
     * @return the ECTS sum
     * @author Leon
     */
    private int getMaxEcts() {
        int ects = 0;
        for (Modul modul : modules) {
            ects += modul.getEcts().getEctsValue();
        }
        return ects;
    }

    /**
     * replaces all fillerEvents with random Module events.
     * unnecessary fillerEvents get deleted.
     *
     * @author Leon
     */
    private void generateStudyPlan() {
        List<Event> fillerEvents = getFillerEvents();
        int maxDays = fillerEvents.size();
        if(maxDays > 10) {
            fillHashMap2(maxDays, 10);
        } else {
            fillHashMap2(maxDays, maxDays);
        }
        for (Event event : fillerEvents) {
            checkModuleDurations();

            if (moduleDaysHashMap.size() == 0) {
                break;
            }

            if(counter < 9) {
                counter++;
            } else {
                counter = 0;
                maxDays -= 10;
                if(maxDays >= 10) {
                    fillHashMap2(maxDays, 10);
                } else {
                    moduleDaysHashMap2 = moduleDaysHashMap;
                }
            }

            Modul ranModule = getRandomModule();

            Event ownEvent = new Event();
            ownEvent.setTitle(ranModule.getModulname());
            ownEvent.setStartTime(event.getStartTime());
            ownEvent.setEndTime(event.getEndTime());
            ownEvent.setStarDate(event.getStarDate());
            ownEvent.setEndDate(event.getEndDate());
            ownEvent.setCalendar("Lernplan");

            modules.stream().filter(e -> e.getModulname().equals(ranModule.getModulname())).forEach(e -> {
                e.getUuid().add(ownEvent.getId());
                ModulUpdateDB modulUpdateDB = new ModulUpdateDB();
                modulUpdateDB.Update(e, entityManager, entityTransaction);
            });

            List<Event> removeEvents = events.stream().filter(e -> e.getId().equals(event.getId()))
                    .collect(Collectors.toList());
            EventsDeleteDB eventsDeleteDB = new EventsDeleteDB();
            eventsDeleteDB.EventDelete(removeEvents, entityManager, entityTransaction);
            events.removeAll(removeEvents);

            events.add(ownEvent);
            SaveEventDB saveEventDB = new SaveEventDB();
            saveEventDB.insert(ownEvent, entityManager, entityTransaction);
            Entry<?> entry = convertEventToEntry(ownEvent);
            studyPlan.addEntry(entry);
        }

        for (Entry entry : studyPlan.findEntries("Filler")) {
            studyPlan.removeEntry(entry);
        }
    }

    /**
     * Gets a random Module from the Hashmap and reduces the value by one.
     * Deletes the Module from the HashMap, when the value is 0.
     *
     * @return the random Module
     * @author Leon
     */
    private Modul getRandomModule() {
        Random random = new Random();
        int index = random.nextInt(moduleDaysHashMap2.size());
        List<Modul> tempModules = new ArrayList<>(moduleDaysHashMap2.keySet());
        Modul module = tempModules.get(index);
        moduleDaysHashMap2.replace(module, moduleDaysHashMap2.get(module) - 1);

        if (moduleDaysHashMap2.get(module) == 0) {
            moduleDaysHashMap2.remove(module);
        }
        return module;
    }

    /**
     * Fills the HashMap2 with modules and days.
     * HashMap2 is a smaller part of HashMap.
     * Deletes the Module from the HashMap, when the value is 0.
     *
     * @param maxDays the number of fillerEvents.
     * @param fillDays is a smaller number than maxDays.
     * @author Leon
     */
    private void fillHashMap2(int maxDays, int fillDays) {
        List<Modul> tempModules = new ArrayList<>(moduleDaysHashMap.keySet());
        int currDays = 0;
        for (Modul m : tempModules) {
            double days = (moduleDaysHashMap.get(m) / (double) maxDays) * fillDays;
            moduleDaysHashMap2.put(m, (int) days);
            currDays += (int) days;
        }

        for (Modul m : tempModules) {
            if(10 - currDays > 0) {
                moduleDaysHashMap2.replace(m, moduleDaysHashMap2.get(m) + 1);
                currDays++;
            }
            moduleDaysHashMap.replace(m, moduleDaysHashMap.get(m) - moduleDaysHashMap2.get(m));
            if(moduleDaysHashMap.get(m) == 0) {
                moduleDaysHashMap.remove(m);
            }
        }
    }

    /**
     * Checks if any Modules duration is <= 0 and deletes it from the HashMap
     *
     * @author Leon
     */
    private void checkModuleDurations() {
        for (Modul modul : modules) {
            if (modul.getEcts().getDuration().toMinutes() <= 0) {
                moduleDaysHashMap.remove(modul);
            }
        }
    }

}