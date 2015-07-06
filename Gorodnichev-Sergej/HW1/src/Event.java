import java.util.Date;

/**
 * Created by sergej on 30.06.15.
 */

public class Event {
    private Date date;
    private String text;

    public Event(Date date, String text) {
        this.date = date;
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
