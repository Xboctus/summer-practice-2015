import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Timetable {
    private List<User> usersTable;
    private ListIterator<User> iter;

    Timetable() {
        this.usersTable = new LinkedList<User>();
    }

    protected void addUser(String name, int timeZone, boolean active) {
        User user = new User(name, timeZone, active);
        if (usersTable.isEmpty()) {
            usersTable.add(user);
            return;
        }
        iter = usersTable.listIterator();
        int index = 0;
        while (iter.hasNext() && (usersTable.get(index).getName().compareTo(name) <= 0 || usersTable.get(index).getTimeZone() != timeZone)) {
            if (usersTable.get(index).getName().compareTo(name) == 0 & usersTable.get(index).getTimeZone() == timeZone) {
                System.out.println("This user already exist");
                return;
            }
            iter.next();
            index++;
        }
        usersTable.add(index, user);
    }

    protected User getUser(int index) {
        return usersTable.get(index);
    }

    protected List<User> getUsers() {
        return usersTable;
    }

    protected int findUserIndex(String name) {
        iter = usersTable.listIterator();
        if (usersTable.isEmpty())
            return -1;
        int index = 0;
        while (iter.hasNext()) {
            if (usersTable.get(index).getName().compareTo(name) == 0)
                return index;
            iter.next();
            index++;
        }
        return -1;
    }
}