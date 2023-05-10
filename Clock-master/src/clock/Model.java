package clock;

import java.awt.FlowLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import java.util.GregorianCalendar;

public class Model extends Observable {
    
    int hour = 0;
    int minute = 0;
    int second = 0;
    int alarmHour = 0;
    int alarmMinute = 0;
    int alarmSecond = 0;
    
    int oldSecond = 0;
    
    public Model() {
        update();
    }
    
    public void update() {
        Calendar date = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String Tiempo = (String) formatter.format(new Date());
        hour = date.get(Calendar.HOUR);
        minute = date.get(Calendar.MINUTE);
        oldSecond = second;
        second = date.get(Calendar.SECOND);
        if (oldSecond != second) {
            setChanged();
            notifyObservers();
        }
        //System.out.println(hour+" "+minute+" "+second);
        //System.out.println(Tiempo);
        final PriorityQueue<TimeNumber> q;
        q = ClockQueue.ClockQueueInstance;
        String rawTiempo = Tiempo.replaceAll(":","");
        int convertedTiempo = Integer.valueOf(rawTiempo);
        int newAlarmPriority = 0;
        try {
            newAlarmPriority = q.headCurrentRetrieval(convertedTiempo);
        } catch (QueueUnderflowException ex) {
            //System.out.println("Queue Currently empty.");
        }
                //System.out.println("Next in the queue is: " + newAlarmPriority);
                String newAlarmName = String.valueOf(newAlarmPriority);
                if (newAlarmName.length() == 6) {
                int trio1 = Integer.parseInt(newAlarmName.substring(0, 2));
                int trio2 = Integer.parseInt(newAlarmName.substring(2, 4));
                int trio3 = Integer.parseInt(newAlarmName.substring(4, 6));
                //System.out.println("Testing: "+trio1+" "+trio2+" "+trio3);
                Calendar alarmDate = Calendar.getInstance();
                alarmDate.set(Calendar.SECOND, trio3);
                alarmDate.set(Calendar.MINUTE, trio2);
                alarmDate.set(Calendar.HOUR, trio1);
                alarmHour = alarmDate.get(Calendar.HOUR);
                alarmMinute = alarmDate.get(Calendar.MINUTE);
                alarmSecond = alarmDate.get(Calendar.SECOND);
                }
                if(newAlarmName.length() == 4) {
                int trio2 = Integer.parseInt(newAlarmName.substring(2, 4));
                int trio3 = Integer.parseInt(newAlarmName.substring(4, 6));
                System.out.println("Testing: "+" "+trio2+" "+trio3);
                }
                
        try {
            //String timeHead = q.head().getTime();
            int priorityHead = q.headPriority();
            //String rawTiempo = Tiempo.replaceAll(":","");
            //String rawTime = timeHead.replaceAll(":","");
            //int convertedTiempo = Integer.valueOf(rawTiempo);
            //int convertedTime = Integer.valueOf(rawTime);
            TimeNumber timenumberCompare = new TimeNumber(Tiempo);
            
            TimeNumber headTime = q.headEquals(convertedTiempo);
            int checkSamePriority = q.headRetrieval(convertedTiempo);
            if (convertedTiempo == checkSamePriority) {
                //JOptionPane.showMessageDialog(null, "The time is now! Alarm for : "+headTime+"!");
                int alarmQueryResult = JOptionPane.showConfirmDialog(null,"The time is now! Alarm for : "+headTime+"!"+" Do you want to remove this alarm?", "Alarm Query",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (alarmQueryResult == JOptionPane.YES_OPTION){
                    String chosenDeletedAlarm = String.valueOf(headTime);
                    System.out.println("Removing " + chosenDeletedAlarm + " from the head of the queue");
                    System.out.println(q);
                    //int nextAlarm = q.headAlarmRetrieval(convertedTiempo);
                    //System.out.println("The next in queue is: " + nextAlarm);
                    View.removeFromAlarm(headTime);
                }
                else if (alarmQueryResult == JOptionPane.NO_OPTION){
                    System.out.println("Alarm kept.");
                }
                else {
                    System.out.println("Neither selected.");
                }
                //String fixInput = newAlarmName.replaceAll("..(?!$)", "$0:");
                //System.out.println("Testing: "+trio1+" "+trio2+" "+trio3);
                
                /*
                String removedAlarm = q.head().getTime();
                    System.out.println("Removing " + removedAlarm + " from the head of the queue");
                    q.remove();
                    //q.removeEdit(convertedTiempo);
                    TimeNumber timenumberExpand = new TimeNumber(timeHead);
                    int priorityExpand = priorityHead + 240000;
                    //priorityExpand = Math.abs(priorityExpand);
                    q.add(timenumberExpand, priorityExpand);
                    System.out.println("Adding " + timenumberExpand.getTime() + " with priority " + priorityExpand);
                */
            
            }
            /*
            else if (convertedTiempo > convertedTime) {
                JOptionPane.showMessageDialog(null, "Alarm expired for : "+timeHead+"!");
                String removedAlarm = q.head().getTime();
                    System.out.println("Removing " + removedAlarm + " from the head of the queue");
                    q.remove();
                    TimeNumber timenumberExpand = new TimeNumber(timeHead);
                    int priorityExpand = priorityHead + 240000;
                    //priorityExpand = Math.abs(priorityExpand);
                    q.add(timenumberExpand, priorityExpand);
            
            }
            */
            
            //System.out.println(convertedTime);
            //System.out.println(convertedTiempo);
            
        } catch (QueueUnderflowException ex) {
            //System.out.println("None");
        }
        /*
        catch (QueueOverflowException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        /*
            final PriorityQueue<TimeNumber> q;
            q = ClockQueue.ClockQueueInstance;
            System.out.println(q);
        */
    }
    
    
    
    
}