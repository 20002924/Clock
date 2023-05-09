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
        try {
            String timeHead = q.head().getTime();
            int priorityHead = q.headPriority();
            String rawTiempo = Tiempo.replaceAll(":","");
            String rawTime = timeHead.replaceAll(":","");
            int convertedTiempo = Integer.valueOf(rawTiempo);
            int convertedTime = Integer.valueOf(rawTime);
            if (convertedTiempo == convertedTime) {
                JOptionPane.showMessageDialog(null, "The time is now! Alarm for : "+timeHead+"!");
                String removedAlarm = q.head().getTime();
                    System.out.println("Removing " + removedAlarm + " from the head of the queue");
                    q.remove();
                    //q.removeEdit(convertedTiempo);
                    TimeNumber timenumberExpand = new TimeNumber(timeHead);
                    int priorityExpand = priorityHead + 240000;
                    //priorityExpand = Math.abs(priorityExpand);
                    q.add(timenumberExpand, priorityExpand);
                    System.out.println("Adding " + timenumberExpand.getTime() + " with priority " + priorityExpand);
            
            }
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
            
            //System.out.println(convertedTime);
            //System.out.println(convertedTiempo);
            
        } catch (QueueUnderflowException ex) {
            //System.out.println("None");
        } catch (QueueOverflowException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
            final PriorityQueue<TimeNumber> q;
            q = ClockQueue.ClockQueueInstance;
            System.out.println(q);
        */
    }
    
    
    
}