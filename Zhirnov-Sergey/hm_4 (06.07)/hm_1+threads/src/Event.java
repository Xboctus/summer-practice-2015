import java.util.Date;

public class Event implements Comparable<Event>{
    private Date eventTime;
    private String description;
    private Boolean need;

    Event(Date eventTime, String description) {
        this.eventTime = eventTime;
        this.description = description;
        need = true;
    }

    protected Date getTime() {
        return eventTime;
    }

    protected String getDescription() {
        return description;
    }

    protected boolean isNeed() {
        return need;
    }

    protected void notNeed() {
        need = false;
    }

    @Override
    public int compareTo(Event o) {
        if (this.eventTime == o.getTime()) {
            if (this.description.equals(o.getDescription()))
                return 0;
            else {
                if (this.description.compareTo(o.description) > 0)
                    return 1;
                else
                    return -1;
            }
        }
        else {
            if (this.eventTime.getTime() > o.getTime().getTime())
                return 1;
            else
                return -1;
        }
    }
}