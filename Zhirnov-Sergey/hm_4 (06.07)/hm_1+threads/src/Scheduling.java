import java.text.SimpleDateFormat;

public class Scheduling extends Thread {
    Timetable timetable;
    User nearUser;
    Event nearEvent;
    long nearEventTime;

    Scheduling (Timetable timetable) {
        this.timetable = timetable;
        this.nearUser = null;
        this.nearEvent = null;
        this.nearEventTime = Long.MAX_VALUE;
    }

    private void nullAll() {
        this.nearUser = null;
        this.nearEvent = null;
        this.nearEventTime = Long.MAX_VALUE;
    }

    private void show(User user, Event event) {
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
        System.out.println(ft.format(event.getTime()) + " " + user.getName() + "\n" + event.getDescription() + ".");
    }

    private void findNextEvent() {
        long curTime = System.currentTimeMillis();
        for (User user : timetable.getUsers())
            if (user.isActive() && user.getFirstEvent() != null) {
                if (user.getFirstEvent().getTime().getTime() > curTime) {
                    if (nearEventTime > user.getFirstEvent().getTime().getTime()) {
                        nearUser = user;
                        nearEvent = user.getFirstEvent();
                        nearEventTime = user.getFirstEvent().getTime().getTime();
                    }
                }
                else {
                    for (Event event : user.getEvents()) {
                        if (event.getTime().getTime() <= System.currentTimeMillis()) {
                            show(user, event);
                            event.notNeed();
                        }
                        else {
                            if (nearEventTime > event.getTime().getTime()) {
                                nearUser = user;
                                nearEvent = event;
                                nearEventTime = event.getTime().getTime();
                            }
                            break;
                        }
                    }
                }
                user.clear();
            }
    }

    protected void updateTable(Event event) {
        if (event == null || event.getTime().getTime() <= nearEventTime)
            findNextEvent();
    }

    public void run() {
        synchronized (timetable) {
            long curTime = System.currentTimeMillis();
            for (User user:timetable.getUsers()) {
                if (user.isActive()) {
                    for (Event event : user.getEvents()) {
                        if (event.getTime().getTime() < curTime) {
                            event.notNeed();
                        } else {
                            if (nearEventTime > event.getTime().getTime()) {
                                nearUser = user;
                                nearEvent = event;
                                nearEventTime = event.getTime().getTime();
                            }
                            break;
                        }
                    }
                    user.clear();
                }
            }
        }

        while (nearEvent != null) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
            if (nearEventTime <= System.currentTimeMillis()) {
                synchronized (timetable) {
                    show(nearUser, nearEvent);
                    nearUser.delEvent(nearEvent);
                    nullAll();
                    findNextEvent();
                }
            }
        }
    }
}
