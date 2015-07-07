import java.text.SimpleDateFormat;
import java.util.*;

public class User {
    private String name;
    private int timeZone;
    private boolean status;
    private List<Event> eventList;
    private ListIterator<Event> iter;

    User(String userName, int userTimeZone, boolean active) {
        name = userName;
        timeZone = userTimeZone;
        status = active;
        eventList = new LinkedList<Event>();
    }

    protected String getName() {
        return name;
    }

    protected int getTimeZone() {
        return timeZone;
    }

    protected boolean isActive() {
        return status;
    }

    protected void setTimeZone (int timeZone) {
        this.timeZone = timeZone;
    }

    protected void setActive (boolean active) {
        this.status = active;
    }

    protected void userActiveOn() {
        status = true;
    }

    protected void userActiveOff() {
        status = false;
    }

    protected void addEvent(Date time, String description) {
        Event event = new Event(time, description);
        if (eventList.isEmpty()) {
            eventList.add(event);
            return;
        }
        iter = eventList.listIterator();
        int index = 0;
        while (iter.hasNext() && (eventList.get(index).getTime().compareTo(time) <= 0)) {
            if (eventList.get(index).getTime().compareTo(time) == 0) {
                while (eventList.get(index).getDescription().compareTo(description) >= 0) {
                    if (eventList.get(index).getDescription().compareTo(description) == 0) {
                        System.out.println("This event already exist");
                        return;
                    }
                    index++;
                }
                break;
            }
            iter.next();
            index++;
        }
        if (!iter.hasNext())
        {
            eventList.add(event);
            return;
        }
        eventList.add(index, event);
    }

    protected int findEvent(String description) {
        if (eventList.isEmpty())
            return -1;
        iter = eventList.listIterator();
        int index = 0;
        while (iter.hasNext()) {
            if (eventList.get(index).getDescription().compareTo(description) == 0)
                return index;
            iter.next();
            index++;
        }
        return -1;
    }

    protected boolean isUnique(String text) {
        if (eventList.isEmpty())
            return true;
        iter = eventList.listIterator();
        int index = 0;
        while (iter.hasNext()) {
            if (eventList.get(index).getDescription().compareTo(text) == 0)
                return false;
            iter.next();
            index++;
        }
        return true;
    }

    protected Event getEvent(int index) {
        return eventList.get(index);
    }

    protected List<Event> getEvents() {
        return eventList;
    }

    protected void removeEvent(int index) {
        eventList.remove(index);
    }

    protected void show() {
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
        if (timeZone >= 0)
            System.out.println("Name: " + name + "  Time Zone: UTC" + "+" + timeZone + "  Status: " + status);
        else
            System.out.println("Name: " + name + "  Time Zone: UTC" + timeZone + "  Status: " + status);
        for (int i = 0; i < eventList.size(); i++) {
            System.out.println("Event " + Integer.toString(i + 1) + "  Date: " + ft.format(eventList.get(i).getTime()) + "\n"
                    + eventList.get(i).getDescription() + ".");
        }
    }
}
