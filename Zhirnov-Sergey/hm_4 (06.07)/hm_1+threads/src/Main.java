import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    Timetable timetable = new Timetable();
    int defaultTimeZone = 3;
    Pattern pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.((19|20)\\d\\d)\\-([01]?[0-9]|2[0-3])\\:[0-5][0-9]\\:[0-5][0-9]");
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
    boolean isSchedulingWork = false;

    public static void main(String[] args) throws ParseException, InterruptedException {
        System.out.println("Usage:");
        System.out.println("create(name, timezone, active)");
        System.out.println("modify(name, timezone, active)");
        System.out.println("addEvent(name, text, datetime)");
        System.out.println("addRandomTimeEvent(name, text, dateFrom, dateTo)");
        System.out.println("removeEvent(name, text)");
        System.out.println("cloneEvent(name, text, nameTo)");
        System.out.println("showInfo(name)");
        System.out.println("startScheduling");
        System.out.println("exitPreparation");
        Main main = new Main();
        main.scannerLoop();
    }

    private void scannerLoop() throws ParseException {
        Scanner reader = new Scanner(System.in);
        String line = reader.nextLine();
        while(!line.equals("exitPreparation")) {
            String[] params = line.split("\\(|,( )*|\\)");
            switch (params[0]) {
                case "create" :
                    if (params.length == 4) {
                        synchronized (timetable) {
                            if (timetable.findUserIndex(params[1]) == -1) {
                                if (Integer.parseInt(params[2]) > 14 || Integer.parseInt(params[2]) < -12) {
                                    System.out.println("Error: wrong time zone format. Usage UTC-format: -12 <= timzone <= 14");
                                    break;
                                }
                                timetable.addUser(params[1], Integer.parseInt(params[2]), Boolean.parseBoolean(params[3]));
                                System.out.println("User " + params[1] + " was added");
                            } else {
                                System.out.println("Error: user with this name already exist");
                            }
                        }
                    }
                    else {
                        System.out.println("Error: wrong arguments. Usage: create(name, timezone, active)");
                    }
                    break;
                case "modify" :
                    if (params.length == 4) {
                        synchronized (timetable) {
                            int userIndex = timetable.findUserIndex(params[1]);
                            if (userIndex != -1) {
                                if (Integer.parseInt(params[2]) > 14 || Integer.parseInt(params[2]) < -12) {
                                    System.out.println("Error: wrong time zone format. Usage: -12 <= timzone <= 14");
                                    break;
                                }
                                if (!timetable.getUser(userIndex).getEvents().isEmpty())
                                    for (Event event : timetable.getUser(userIndex).getEvents())
                                        event.getTime().setTime(event.getTime().getTime() + (timetable.getUser(userIndex).getTimeZone() - Integer.parseInt(params[2])) * 3600000);
                                timetable.getUser(userIndex).setTimeZone(Integer.parseInt(params[2]));
                                timetable.getUser(userIndex).setActive(Boolean.parseBoolean(params[3]));
                                System.out.println("User " + params[1] + " was modified");
                            } else {
                                System.out.println("Error: user with this name don't exist");
                            }
                        }
                    }
                    else {
                        System.out.println("Error: wrong arguments. Usage: modify(name, timezone, active)");
                    }
                    break;
                case "addEvent" :
                    if (params.length == 4) {
                        synchronized (timetable) {
                            int userIndex = timetable.findUserIndex(params[1]);
                            if (userIndex != -1) {
                                Matcher matcher = pattern.matcher(params[3]);
                                if (matcher.matches()) {
                                    Date eventTime = dateFormat.parse(params[3]);
                                    eventTime.setTime(eventTime.getTime() + (defaultTimeZone - timetable.getUser(userIndex).getTimeZone()) * 3600000);
                                    if (timetable.getUser(userIndex).isUnique(params[2])) {
                                        timetable.getUser(userIndex).addEvent(eventTime, params[2]);
                                        System.out.println("Event was added to user " + params[1]);
                                    } else
                                        System.out.println("Error: event with this text already exist");
                                } else {
                                    System.out.println("Error: wrong date format. Usage: dd.MM.yyyy-HH:mm:ss");
                                }
                            } else {
                                System.out.println("Error: user with this name don't exist");
                            }
                        }
                    }
                    else {
                        System.out.println("Error: wrong arguments. Usage: addEvent(name, text, datetime)");
                    }
                    break;
                case "addRandomTimeEvent" :
                    if (params.length == 5) {
                        synchronized (timetable) {
                            int userIndex = timetable.findUserIndex(params[1]);
                            if (userIndex != -1) {
                                Matcher matcher = pattern.matcher(params[3]);
                                if (matcher.matches()) {
                                    matcher = pattern.matcher(params[4]);
                                    if (matcher.matches()) {
                                        Date eventTime = dateFormat.parse(params[3]);
                                        Date eventTimeTo = dateFormat.parse(params[4]);
                                        long random = (eventTimeTo.getTime() - eventTime.getTime() + 1) * (long) Math.random();
                                        eventTime.setTime(eventTime.getTime() + random + (defaultTimeZone - timetable.getUser(userIndex).getTimeZone()) * 3600000);
                                        timetable.getUser(userIndex).addEvent(eventTime, params[2]);
                                        System.out.println("Random time event was added to user " + params[1]);
                                    } else {
                                        System.out.println("Error: wrong dateTo format. Usage: dd.MM.yyyy-HH:mm:ss");
                                    }
                                } else {
                                    System.out.println("Error: wrong dateFrom format. Usage: dd.MM.yyyy-HH:mm:ss");
                                }
                            } else {
                                System.out.println("Error: user with this name don't exist");
                            }
                        }
                    }
                    else {
                        System.out.println("Error: wrong arguments. Usage: addRandomTimeEvent(name, text, dateFrom, dateTo)");
                    }
                    break;
                case "removeEvent" :
                    if (params.length == 3) {
                        synchronized (timetable) {
                            int userIndex = timetable.findUserIndex(params[1]);
                            if (userIndex != -1) {
                                int eventInex = timetable.getUser(userIndex).findEvent(params[2]);
                                if (eventInex != -1) {
                                    timetable.getUser(userIndex).removeEvent(eventInex);
                                    System.out.println("Event was removed from " + params[1] + "'s events");
                                } else {
                                    System.out.println("Error: user don't have this event");
                                }
                            } else {
                                System.out.println("Error: user with this name don't exist");
                            }
                        }
                    }
                    else {
                        System.out.println("Error: wrong arguments. Usage: removeEvent(name, text)");
                    }
                    break;
                case "cloneEvent" :
                    if (params.length == 4) {
                        synchronized (timetable) {
                            int userIndexFrom = timetable.findUserIndex(params[1]);
                            int userIndexTo = timetable.findUserIndex(params[3]);
                            if (userIndexFrom != -1 || userIndexTo != -1) {
                                User userFrom = timetable.getUser(userIndexFrom);
                                User userTo = timetable.getUser(userIndexTo);
                                int eventIndex = userFrom.findEvent(params[2]);
                                if (eventIndex != -1) {
                                    if (userTo.isUnique(params[2])) {
                                        Event event = userFrom.getEvent(eventIndex);
                                        userTo.addEvent(event.getTime(), event.getDescription());
                                        System.out.println("Event was copped from user " + params[1] + " to user " + params[3]);
                                    } else {
                                        System.out.println("Error: user " + params[3] + " already have this event");
                                    }
                                } else {
                                    System.out.println("Error: user " + params[1] + " don't have this event");
                                }
                            } else {
                                if (userIndexFrom == -1)
                                    System.out.println("Error: user with name " + params[1] + " don't exist");
                                if (userIndexTo == -1)
                                    System.out.println("Error: user with name " + params[3] + " don't exist");
                            }
                        }
                    }
                    else {
                        System.out.println("Error: wrong arguments. Usage: cloneEvent(name, text, nameTo)");
                    }
                    break;
                case "showInfo" :
                    if (params.length == 2) {
                        synchronized (timetable) {
                            int userIndex = timetable.findUserIndex(params[1]);
                            if (userIndex != -1) {
                                timetable.getUser(userIndex).show();
                            } else {
                                System.out.println("Error: user with this name don't exist");
                            }
                        }
                    }
                    else {
                        System.out.println("Error: wrong arguments. Usage: showInfo(name)");
                    }
                    break;
                case "startScheduling" :
                    if (params.length == 1) {
                        if (!isSchedulingWork) {
                                isSchedulingWork = true;
                                Scheduling scheduling = new Scheduling(timetable);
                                scheduling.start();
                        }
                        else {
                            System.out.println("Scheduling already started work");
                        }
                    }
                    else {
                        System.out.println("Error: wrong arguments. Usage: startScheduling");
                    }
                    break;
            }
            line = reader.nextLine();
        }
    }
}
