package clock;

import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Controller {
    
    ActionListener listener;
    Timer timer;
    
    Model model;
    View view;
    
    
    public Controller(Model m, View v) {
        model = m;
        view = v;
        
        
        listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.update();
            }
        };
        
        /**
        * Code used to ask for importing files upon start-up, if yes is chosen, it calls the addAlarm function from View.
        */
        int alarmAddQuery = JOptionPane.showConfirmDialog(null,"Do you want to import any alarms?", "Alarm Add Query",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (alarmAddQuery == JOptionPane.YES_OPTION){
            View.addAlarm();
        }
        else if (alarmAddQuery == JOptionPane.NO_OPTION){
          System.out.println("Request denied.");
        }
        else {
           System.out.println("Neither selected.");
        }
        
        
        timer = new Timer(100, listener);
        timer.start();
    }

    /**
    * Old code used during attempts to open panes before shutdown, before the implementation of the exit button.
    */
    /*
    public static void main(String[] args)
  {
 
    Runtime.getRuntime().addShutdownHook(new Thread()
    {
      public void SavePrompt()
      {
      int alarmSaveQuery = JOptionPane.showConfirmDialog(null,"Do you want to save your alarms?", "Alarm Save Query",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if (alarmSaveQuery == JOptionPane.YES_OPTION){
          View.saveAlarm();
      }
      else if (alarmSaveQuery == JOptionPane.NO_OPTION){
          System.out.println("Alarm kept.");
       }
       else {
          System.out.println("Neither selected.");
       }
      }
    });
  }
*/
}
