import java.text.SimpleDateFormat;
import java.util.Date;

public class ActiveEvent implements Comparable<ActiveEvent> {
    private String name;
    private Date date;
    private String description;

    ActiveEvent(String name, Date time, String text) {
        this.name = name;
        this.date = time;
        this.description = text;
    }

    public int compareTo(ActiveEvent event) {
        return date.compareTo(event.date);
    }

    protected long getTime() {
        return date.getTime();
    }

    protected void show() {
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
        System.out.println(ft.format(date) + " " + name + "\n" + description + ".");
    }
}