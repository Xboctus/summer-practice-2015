import java.text.SimpleDateFormat;
import java.util.Date;

public class ActiveEvent implements Comparable<ActiveEvent> {
    private String name;
    private Date date;
    private String text;

    public ActiveEvent(String name, Date date, String text) {
        this.name = name;
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
        System.out.println(ft.format(date) + " " + name + " " + text + ";");
    }
}

