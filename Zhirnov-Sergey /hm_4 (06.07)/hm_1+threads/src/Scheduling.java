import java.text.SimpleDateFormat;

public class Scheduling extends Thread {
    Timetable timetable = new Timetable();

    Scheduling (Timetable timetable) {
        this.timetable = timetable;
    }

    private void show(User user, Event event) {
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
        System.out.println(ft.format(event.getTime()) + " " + user.getName() + "\n" + event.getDescription() + ".");
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            }
            catch (Exception e) {}

            synchronized (timetable) {
                for (User user:timetable.getUsers())
                    if (user.isActive())
                        for (Event event:user.getEvents())
                            if (event.getTime().getTime() <= System.currentTimeMillis() && !event.isWas()) {
                                event.was();
                                show(user, event);
                            }
            }
        }
    }
}
