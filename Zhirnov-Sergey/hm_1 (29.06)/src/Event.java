import java.util.Date;

public class Event {
    private Date eventTime;
    private String description;

    Event(Date eventTime, String description) {
        this.eventTime = eventTime;
        this.description = description;
    }

    protected Date getTime() {
        return eventTime;
    }

    protected String getDescription() {
        return description;
    }
}