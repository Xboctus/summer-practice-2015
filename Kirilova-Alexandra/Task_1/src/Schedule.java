import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by vv on 07.07.15.
 */
public class Schedule {

    List<User> users = new ArrayList<>();
    int defTimezone = 3;

    private User isDef(String name)
    {
        for (User temp : users) { if (temp.getName().equals(name)) return temp;}
        return null;
    }

    public static void main (String[] args) throws ParseException, InterruptedException
    {
        Schedule main = new Schedule();
        main.seqShedule();
    }

    private void seqShedule() throws ParseException, InterruptedException
    {
        System.out.print("SCHEDULE COMMANDER");
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        while (! line.equals("StartScheduling"))
        {
            String[] params = line.split("\\(|,( )*|\\)");
            switch (params [0])
            {
                case "Create":
                    User tempu0 = isDef(params[1]);
                    if (tempu0 == null)
                    {
                        User user = new User (params[1], Integer.parseInt(params[2]), Boolean.parseBoolean(params[3]));
                        users.add(user);
                    }
                    else { System.out.print("Name defined."); }
                    break;

                case "Modify":
                    User tempu1 = isDef(params[1]);
                    if ( tempu1 != null)
                    {
                        tempu1.setName(params[1]);
                        tempu1.setTimezone(Integer.parseInt(params[2]));
                        tempu1.setStatus(Boolean.parseBoolean(params[3]));
                    }
                    else { System.out.print("User does not exist"); }
                    break;

                case "AddEvent":
                    User tempu2 = isDef(params[1]);
                    if (tempu2 != null)
                    {
                        DateFormat dtf = new SimpleDateFormat("dd.MM.yyyy'-'HH:mm:ss");
                        Date resdate = dtf.parse(params[3]);
                        resdate.setTime(resdate.getTime() + (defTimezone - tempu2.getTimezone()) * 3600000);
                        Event event = new Event(params[1], resdate, params[2]);
                        tempu2.addEvent(event);
                    }
                    else { System.out.print("User does not exist"); }
                    break;

                case "RemoveEvent":
                    User tempu3 = isDef (params[1]);
                    if (tempu3 != null) { tempu3.removeEvent(params[2]); }
                    else { System.out.print("User does not exist"); }
                    break;

                case "AddRandomTimeEvent":
                    User tempu4 = isDef(params[1]);
                    if (tempu4 != null)
                    {
                        long dateLeft = Timestamp.valueOf(params[3]).getTime();
                        long dateRight = Timestamp.valueOf(params[4]).getTime();
                        long delta = dateLeft - dateRight + 1;
                        Event event = new Event(params[1], new Date(dateLeft + (long)(Math.random()*delta)), params[2]);
                        tempu4.addEvent(event);
                    }
                    else { System.out.print("User does not exist"); }
                    break;

                case "CloneEvent":
                    User tempu5 = isDef(params[1]);
                    User tempClone = isDef(params[3]);
                    if ((tempu5 != null) && (tempClone != null))
                    {
                        Event event = tempu5.findEvent(params[2]);
                        tempClone.addEvent(event);
                    }
                    else { System.out.print("User does not exist"); };
                    break;

                case "ShowInfo":
                    User tempu6 = isDef(params[1]);
                    if (tempu6 != null) { tempu6.showInfo(); }
                    else { System.out.print("User does not exist"); }
                    break;

            }
            line = input.nextLine();
        }
        input.close();

        List <Event> activeEvents = new ArrayList<>();
        long curTime = System.currentTimeMillis();
        for (User user:users)
        {
            if (user.isActive())
            {
                for (Event event:user.getEvents())
                {
                    if (event.getDate().getTime() >= curTime)
                    {
                        activeEvents.add(new Event(user.getName(), event.getDate(), event.getText()));
                    }
                }
            }
        }
        Collections.sort(activeEvents);

        for (int i = 0; i < activeEvents.size(); i++)
        {
            if (i == 0) { Thread.sleep(activeEvents.get(i).getTime() - System.currentTimeMillis()); }
            else { Thread.sleep(activeEvents.get(i).getTime() -activeEvents.get(i-1).getTime()); }
            activeEvents.get(i).print();
        }


    }


}
