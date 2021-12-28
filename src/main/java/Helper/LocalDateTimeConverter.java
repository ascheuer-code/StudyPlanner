package Helper;

import Model.Event;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The type Local date time converter.
 */
public class LocalDateTimeConverter {


    /**
     * Convert Entry to Event.
     *
     * @param entry
     *         the entry
     *
     * @return the event
     * @author Andreas Scheuer
     */
    public static Event convertEntrytoEvent(Entry entry) {

        Event event = new Event();

        event.setTitle(entry.getTitle());
        event.setStarDate(entry.getInterval().getStartDate().toString());
        event.setStartTime(entry.getInterval().getStartTime().toString());
        event.setEndDate((entry.getInterval().getEndDate().toString()));
        event.setEndTime((entry.getInterval().getEndTime().toString()));

        if(!entry.getId().equals(event.getId())){
            entry.setId(event.getId());
        }

        return event;
   }

    /**
     * Convert Event to Entry.
     *
     * @param event
     *         the event
     *
     * @return the entry
     * @author Andreas Scheuer
     */
    public static Entry convertEventToEntry(Event event){

        Entry entry = new Entry();

        entry.setId(event.getId());
        entry.setTitle(event.getTitle());
        entry.setInterval(convertEventIntervalToEntryInterval(event));

        return entry;
   }

    /**
     * Convert Date and Time string to Interval .
     *
     * @param event
     *         the event
     *
     * @return the interval
     * @author Andreas Scheuer
     */
    public static Interval convertEventIntervalToEntryInterval(Event event) {

        return new Interval(LocalDate.parse(event.getStarDate()), LocalTime.parse(event.getStartTime()), LocalDate.parse(event.getEndDate()), LocalTime.parse(event.getEndTime()));
    }

}
