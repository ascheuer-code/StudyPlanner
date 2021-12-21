package Model;


import java.util.Objects;
import java.util.UUID;

/**
 * The type Event.
 */
public class Event {
    private String id;
    private String title;
    private String starDate;
    private String startTime;
    private String endDate;
    private String endTime;


    /**
     * Instantiates a new Event.
     */
    public Event(){
        setId();
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
     * Sets id.
     */
    public void setId() {
        this.id = UUID.randomUUID().toString();
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
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets star date.
     *
     * @return the star date
     */
    public String getStarDate() {
        return starDate;
    }

    /**
     * Sets star date.
     *
     * @param starDate the star date
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
     * @param startTime the start time
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
     * @param endDate the end date
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
     * @param endTime the end time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", starDate='" + starDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endDate='" + endDate + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(title, event.title) && Objects.equals(starDate, event.starDate) && Objects.equals(startTime, event.startTime) && Objects.equals(endDate, event.endDate) && Objects.equals(endTime, event.endTime);
    }

    /**
     * To string 2 string.
     *
     * @return String string
     */
    public String toString2() {
        return title;
    }
}
