import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vv on 06.07.15.
 */
public class Event implements Comparable<Event>
{
    private String username;
    private Date date;
    private String text;

    public Event(String username, Date date, String text)
    {
        this.username = username;
        this.date = date;
        this.text = text;
    }

    public String getText(){ return text; }
    public void setText(String text) { this.text = text; }
    public Date getDate(){ return date;}
    public void setDate(Date date) { this.date = date; }
    public long getTime() { return date.getTime(); }

    public int compareTo(Event obj) { return date.compareTo(obj.date); }

    public void print()
    {
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
        System.out.println(ft.format(date) + " " + username + " " + text + ";");
    }
}
