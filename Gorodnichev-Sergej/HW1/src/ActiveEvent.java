import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by sergej on 30.06.15.
 */
public class ActiveEvent implements Comparable<ActiveEvent> {
    private String username;
    private Date date;
    private String text;

    public ActiveEvent(String username, Date date, String text) {
        this.username = username;
        this.date = date;
        this.text = text;
    }

    public int compareTo(ActiveEvent obj) {
        return date.compareTo(obj.date);
    }

    public long getTime() {
        return date.getTime();
    }

    public void print() {
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
        System.out.println(ft.format(date) + " " + username + " " + text + ";");
    }
}

