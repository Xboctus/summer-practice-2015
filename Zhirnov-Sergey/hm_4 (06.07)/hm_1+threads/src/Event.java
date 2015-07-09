import java.util.Date;

public class Event {
    private Date eventTime;
    private String description;
    private Boolean isWas;

    Event(Date eventTime, String description) {
        this.eventTime = eventTime;
        this.description = description;
        this.isWas = false;
    }

    protected Date getTime() {
        return eventTime;
    }

    protected String getDescription() {
        return description;
    }

    protected boolean isWas() {
        return isWas;
    }

    protected void was() {
        isWas = true;
    }
}