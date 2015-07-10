package HW1;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements Comparable<Event> {
	private String username;
    private Date date;
    private String describ;

//    public Event(Date date, String describ) {
//        this.date = date;
//        this.describ = describ;
//    }
       
    public int compareTo(Event obj) {
        return date.compareTo(obj.date);
    }

    public long getTime() {
        return date.getTime();
    }

    public void print() {
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
        System.out.println(ft.format(now) + " " + username + " " + describ + ";");
    }

    public Event(String username, Date date, String describ) {
        this.username = username;
        this.date = date;
        this.describ = describ;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }
}