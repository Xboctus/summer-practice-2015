package HW1;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class State {
	 List<User> users = new ArrayList<>();
    int defaultTimezone = 3;
    int mult = 3600000;

    public static void main(String[] args) throws ParseException, InterruptedException {
        State main = new State();
        main.nextState();
    }

    private User isUser(String name) {
        for(User temp:users) {
            if (temp.getName().equals(name)) return temp;
        }
        return null;
    }
    
    private void nextState() throws ParseException, InterruptedException {
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        while(!line.equals("StartScheduling")){
        	String[] params = line.split("\\(|,( )*|\\)");
            switch (params[0]){
                case "create":
                        User temp = isUser(params[1]);
                        if (temp == null) {
                            User user = new User(params[1], Integer.parseInt(params[2]), Boolean.parseBoolean(params[3]));
                            users.add(user);
                        }
                        else {
                        	System.out.print("Name already used");
                        }
                
                    break;
                case "modify":
                        User temp1 = isUser(params[1]);
                        if (!(temp1 == null)) {
                            temp1.setName(params[1]);
                            temp1.setTimezone(Integer.parseInt(params[2]));
                            temp1.setStatus(Boolean.parseBoolean(params[3]));
                        }
                        else {
                        	System.out.print("No such user");
                        }
                    break;
                case "addEvent":
                        User temp2 = isUser(params[1]);
                        if (!(temp2 == null)) {
                                DateFormat df = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
                                Date result = df.parse(params[3]);
                                result.setTime(result.getTime() + (defaultTimezone - temp2.getTimezone())*mult);
                                Event event = new Event(params[1], result, params[2]);
                                temp2.addEvent(event);
                        }
                        else {
                        	System.out.print("No such user");
                        }
                    break;
                case "addRandomTimeEvent":
                        User temp3 = isUser(params[1]);
                        if (!(temp3 == null)) {
                                long dateFrom = Timestamp.valueOf(params[3]).getTime();
                                long dateTo = Timestamp.valueOf(params[4]).getTime();
                                long diff = dateFrom - dateTo + 1;

                                Event event = new Event(params[1],new Date(dateFrom + (long)(Math.random()*diff)), params[2]);
                                temp3.addEvent(event);
                        }
                        else {
                        	System.out.print("No such user");
                        }
                    break;
                case "removeEvent":
                        User temp4 = isUser(params[1]);
                        if (!(temp4 == null)) {
                            temp4.removeEvent(params[2]);
                        }
                        else {
                        	System.out.print("No such user");
                        }
                    break;
                case "cloneEvent":
                        User temp5 = isUser(params[1]);
                        User tempTo = isUser(params[3]);
                        if (!(temp5 == null) && !(tempTo == null)) {
                            Event event = temp5.findEvent(params[2]);
                            tempTo.addEvent(event);
                        }
                        else {
                        	System.out.print("No such user");
                        }
                    break;
                case "showInfo":
                        User temp6 = isUser(params[1]);
                        if (!(temp6 == null)) {
                            temp6.showInfo();
                        }
                        else {
                        	System.out.print("No such user");
                        }
                    break;
            }
            line = input.nextLine();
        }
        
        input.close();
        
        List<Event> schedEvents = new ArrayList<>();
        long curTime = System.currentTimeMillis();
        for(User user:users) {
            if (user.isActive()) {
                for (Event event:user.getEvents()) {
                    if (event.getDate().getTime() >= curTime) {
                    	 schedEvents.add(new Event(user.getName(), event.getDate(), event.getDescrib()));
                    }
                }
            }
        }
        Collections.sort(schedEvents);
     
        
        for (int i = 0; i < schedEvents.size(); i++) {
            if (i == 0) {
                Thread.sleep(schedEvents.get(i).getTime() - System.currentTimeMillis());
            }
            else {
                Thread.sleep(schedEvents.get(i).getTime() -  schedEvents.get(i - 1).getTime());
            }
            schedEvents.get(i).print();
        }
    
                     
    }
}