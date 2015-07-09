import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.*;

public class Coordinator {
    List<User> users = new ArrayList<>();
    int defaultTimezone = 3;

    public static void main(String[] args) throws ParseException, InterruptedException {
        out.println("create(name, timezone, active)");
        out.println("modify(name, timezone, active)");
        out.println("addEvent(name, text, datetime)");
        out.println("addRandomTimeEvent(name, text, dateFrom, dateTo)");
        out.println("removeEvent(name, text)");
        out.println("cloneEvent(name, text, nameTo)");
        out.println("showInfo(name)");
        Coordinator main = new Coordinator();
        main.scannerLoop();
        main.coordinator();
    }


    private void scannerLoop() throws ParseException {
        Scanner reader = new Scanner(in);
        String line = reader.nextLine();
        while(!line.equals("startTimetable")){
            String[] params = line.split("\\(|,( )*|\\)");
            switch (params[0]){
                case "create":
                    if (params.length == 4) {
                        User temp = find(params[1]);
                        if (temp == null) {
                            User user = new User(params[1], Integer.parseInt(params[2]), Boolean.parseBoolean(params[3]));
                            users.add(user);
                        }
                        else {
                            out.print("Error: user with this name already exist");
                        }
                    }
                    else {
                        out.print("Error: wrong arguments");
                    }
                    break;
                case "modify":
                    if (params.length == 4) {
                        User temp = find(params[1]);
                        if (!(temp == null)) {
                            temp.setName(params[1]);
                            temp.setTimezone(Integer.parseInt(params[2]));
                            temp.setStatus(Boolean.parseBoolean(params[3]));
                        }
                        else {
                            out.print("Error: user with this name not found");
                        }
                    }
                    else {
                        out.print("Error: wrong arguments");
                    }
                    break;
                case "addEvent":
                    if (params.length == 4) {
                        User temp = find(params[1]);
                        if (!(temp == null)) {
                            Pattern pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.((19|20)\\d\\d)\\-([01]?[0-9]|2[0-3])\\:[0-5][0-9]\\:[0-5][0-9]");
                            Matcher matcher = pattern.matcher(params[3]);
                            if (matcher.matches()){
                                DateFormat df = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
                                Date result = df.parse(params[3]);
                                result.setTime(result.getTime() + (defaultTimezone - temp.getTimezone())*3600*1000);
                                Event event = new Event(result, params[2]);
                                temp.addEvent(event);
                            }
                            else {
                                out.print("Error: wrong dateformat");
                            }
                        }
                        else {
                            out.print("Error: user with this name not found");
                        }
                    }
                    else {
                        out.print("Error: wrong arguments");
                    }
                    break;
                case "addRandomTimeEvent":
                    if (params.length == 5) {
                        User temp = find(params[1]);
                        if (!(temp == null)) {
                            Pattern pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.((19|20)\\d\\d)\\-([01]?[0-9]|2[0-3])\\:[0-5][0-9]\\:[0-5][0-9]");
                            Matcher matcherFrom = pattern.matcher(params[3]);
                            Matcher matcherTo = pattern.matcher(params[4]);
                            if (matcherFrom.matches() && matcherTo.matches()){
                                long dateFrom = Timestamp.valueOf(params[3]).getTime();
                                long dateTo = Timestamp.valueOf(params[4]).getTime();
                                long diff = dateFrom - dateTo + 1;

                                Event event = new Event(new Date(dateFrom + (long)(Math.random()*diff)), params[2]);
                                temp.addEvent(event);
                            }
                            else {
                                out.print("Error: wrong dateformat");
                            }
                        }
                        else {
                            out.print("Error: user with this name not found");
                        }
                    }
                    else {
                        out.print("Error: wrong arguments");
                    }
                    break;
                case "removeEvent":
                    if (params.length == 3) {
                        User temp = find(params[1]);
                        if (!(temp == null)) {
                            temp.removeEvent(params[2]);
                        }
                        else {
                            out.print("Error: user with this name not found");
                        }
                    }
                    else {
                        out.print("Error: wrong arguments");
                    }
                    break;
                case "cloneEvent":
                    if (params.length == 4) {
                        User temp = find(params[1]);
                        User tempTo = find(params[3]);
                        if (!(temp == null) && !(tempTo == null)) {
                            Event event = temp.findEvent(params[2]);
                            tempTo.addEvent(event);
                        }
                        else {
                            out.print("Error: user with this name not found");
                        }
                    }
                    else {
                        out.print("Error: wrong arguments");
                    }
                    break;
                case "showInfo":
                    if (params.length == 2) {
                        User temp = find(params[1]);
                        if (!(temp == null)) {
                            temp.showInfo();
                        }
                        else {
                            out.print("Error: user with this name not found");
                        }
                    }
                    else {
                        out.print("Error: wrong arguments");
                    }
                    break;
            }
            line = reader.nextLine();
        }
    }

    private void coordinator() throws InterruptedException {
        List<ActiveEvent> activeEvents = new ArrayList<>();
        long curTime = System.currentTimeMillis();
        for(User user:users) {
            if (user.isActive()) {
                for (Event event:user.getEvents()) {
                    if (event.getDate().getTime() >= curTime) {
                        activeEvents.add(new ActiveEvent(user.getName(), event.getDate(), event.getText()));
                    }
                }
            }
        }
        Collections.sort(activeEvents);

        for (int i = 0; i < activeEvents.size(); i++) {
            if (i == 0) {
                Thread.sleep(activeEvents.get(i).getTime() - System.currentTimeMillis());
            }
            else {
                Thread.sleep(activeEvents.get(i).getTime() - activeEvents.get(i - 1).getTime());
            }
            activeEvents.get(i).print();
        }


    }

    private User find(String name) {
        for(User temp:users) {
            if (temp.getName().equals(name)) return temp;
        }
        return null;
    }
}
