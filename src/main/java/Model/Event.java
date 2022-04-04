package Model;

import javafx.beans.property.SimpleIntegerProperty;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * The class Event.
 */
@Entity
public class Event {
    @Id
    private String id;
    @Column
    private String title;
    @Column
    private String calendar;
    @Column
    private String starDate;
    @Column
    private String startTime;
    @Column
    private String endDate;
    @Column
    private String endTime;
    @Transient
    private final  SimpleIntegerProperty duration = new SimpleIntegerProperty();


    /**
     * Instantiates a new Event.
     */
    public Event() {
        setId();
    }

    /**
     * Sets id.
     */
    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title
     *         the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getCalendar(){return calendar;}
    public void setCalendar(String calendar){this.calendar = calendar;}


    /**
     * Gets start date.
     *
     * @return the start date
     */
    public String getStarDate() {
        return starDate;
    }

    /**
     * Sets start date.
     *
     * @param starDate,
     *         the start date
     */
    public void setStarDate(String starDate) {
        this.starDate = starDate;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime
     *         the start time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate
     *         the end date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime
     *         the end time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
        duration.set((int) LocalTime.parse(this.startTime).until(LocalTime.parse(this.endTime), MINUTES));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(title, event.title) && Objects.equals(starDate, event.starDate) && Objects.equals(startTime, event.startTime) && Objects.equals(endDate, event.endDate) && Objects.equals(endTime, event.endTime);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", Kalender='" + calendar + '\'' +
                ", starDate='" + starDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endDate='" + endDate + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }


    /**
     * event constructor to instantiates an event
     * @param title
     * @param calendar
     * @param starDate
     * @param startTime
     * @param endDate
     * @param endTime
     */
    public Event(String title, String calendar, String starDate, String startTime, String endDate, String endTime) {
        this.id = getId();
        this.title = title;
        this.calendar = calendar;
        this.starDate = starDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }
}
