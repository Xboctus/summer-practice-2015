import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User {
    private List<Event> events;
    private int timezone;
    private boolean status;
    private String name;

    public User(String name, int timezone, boolean status) {
        this.timezone = timezone;
        this.status = status;
        this.name = name;
        this.events = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEvent(Event event) {
        if(checkUnique(event)) {
            this.events.add(event);
        }
        else {
            System.out.print("Error: text of event for each user should be unique!");
        }
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public boolean isActive() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private boolean checkUnique(Event event){
        for(Event temp:events){
            if(temp.getText().equals(event.getText())) {
                return false;
            }
        }
        return true;
    }

    public void removeEvent(String text){
        for (Iterator<Event> iter = events.listIterator(); iter.hasNext(); ) {
            Event a = iter.next();
            if (a.getText().equals(text)) {
                iter.remove();
                break;
            }
        }
    }

    public Event findEvent(String text){
        for (Event temp:events) {
            if(temp.getText().equals(text)) {
                return temp;
            }
        }
        return null;
    }

    public void showInfo(){
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
        char s = (timezone >= 0) ? '+' : '-';
        System.out.println("Name:" + name + " Timezone:GMT" + s + Math.abs(timezone) + " Active:" + status);
        for (int i = 0; i < events.size(); i++) {
            System.out.println("#" + (i+1) + " Text:" + events.get(i).getText() + " Date:" + ft.format(events.get(i).getDate()));
        }
    }
}
