package clock;

import java.awt.*;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.util.Scanner;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.Format;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View implements Observer {
    
    ClockPanel panel;
    JPanel timeInputPanel;
    JLabel timeLabel;
    JFormattedTextField timeInput;
    JFormattedTextField currentTimeField;
    static JList alarmList;
    static JScrollPane alarmListModel;
    ActionListener listener;
    ActionListener timerselect;
    private Object selectedTime;
    static String alarmListDeletion;
    static JButton silentRemovalButton = new JButton("You're not supposed to see this.");
    
    
    
    
    public View(Model model) {
        
        final PriorityQueue<TimeNumber> q;
        q = ClockQueue.ClockQueueInstance;
        
        final DefaultListModel alarmListModel = new DefaultListModel();
        
        JFrame frame = new JFrame();
        panel = new ClockPanel(model);
        //frame.setContentPane(panel);
        frame.setTitle("Java Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Start of border layout code
        
        // I've just put a single button in each of the border positions:
        // PAGE_START (i.e. top), PAGE_END (bottom), LINE_START (left) and
        // LINE_END (right). You can omit any of these, or replace the button
        // with something else like a label or a menu bar. Or maybe you can
        // figure out how to pack more than one thing into one of those
        // positions. This is the very simplest border layout possible, just
        // to help you get started.
        
        Container pane = frame.getContentPane();
        
        //JButton button = new JButton("Button 1 (PAGE_START)");
        JPanel panelPrimer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Format clockTimePrimer = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        JFormattedTextField currentAlarm = new JFormattedTextField(clockTimePrimer);
        JLabel currentAlarmLabel = new JLabel("Current Alarm:");
        currentAlarm.setColumns(20);
        panelPrimer.add(currentAlarmLabel, BorderLayout.CENTER);
        panelPrimer.add(currentAlarm, BorderLayout.CENTER);
        pane.add(panelPrimer, BorderLayout.PAGE_START);
        
        /*
        button.addActionListener(new ActionListener() {
            PriorityQueue<TimeNumber> q;
        Scanner stdin = new Scanner(System.in);
        public void actionPerformed(ActionEvent ae) {
            
        }});
        */
         
        panel.setPreferredSize(new Dimension(200, 200));
        pane.add(panel, BorderLayout.CENTER);
         
        JButton button = new JButton("Add Alarm");
        pane.add(button, BorderLayout.LINE_START);
        button.addActionListener(new ActionListener() {
        Scanner stdin = new Scanner(System.in);

            public void actionPerformed(ActionEvent stg) {
                System.out.println("Welcome to the background code.");
                
                
                System.out.println("Using a sorted array.");
                selectedTime = timeInput.getText();
                String alarmTime = (String) selectedTime;
                //currentTimeField.setValue(new Date());
                String selectedTimeCurrent = currentTimeField.getText();
                String presentTime = (String) selectedTimeCurrent;
                String currentTime = presentTime.replaceAll(":","");
                int nowTime = Integer.valueOf(currentTime);
                JOptionPane.showMessageDialog(null, "Alarm for : "+selectedTime);
                TimeNumber timenumber = new TimeNumber(alarmTime);
                String baseTime = alarmTime.replaceAll(":","");
                int namedTime = Integer.valueOf(baseTime);
                //int priority = namedTime - nowTime;
                //int priority = nowTime - namedTime;
                int priority = namedTime;
                /*
                if (priority < 0) {
                    priority = priority + 240000;
                    priority = Math.abs(priority);
                }
                */
                System.out.println("Adding " + timenumber.getTime() + " with priority " + priority);
                try {
                q.add(timenumber, priority);
                alarmListModel.addElement(alarmTime);
                } catch (QueueOverflowException e) {
                System.out.println("Add operation failed: " + e);
                }
                
                /*
                String input2 = stdin.nextLine();
                String time = input2.substring(2, input2.lastIndexOf(' '));
                TimeNumber timenumber = new TimeNumber(time);
                int priority = Integer.parseInt(input2.substring(input2.lastIndexOf(' ') + 1));
                System.out.println("Adding " + timenumber.getTime() + " with priority " + priority);
                try {
                    q.add(timenumber, priority);
                } catch (QueueOverflowException e) {
                    System.out.println("Add operation failed: " + e);
                }
                */
                try {
                    String time2 = q.head().getTime();
                    System.out.println("The timenumber at the head of the queue is " + time2);
                } catch (QueueUnderflowException e) {
                    System.out.println("Can't get head of queue: " + e);
                }
                System.out.println(q);
            }
        });
        
        final JPanel editAlarm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editButton = new JButton("Edit Alarm");
        editAlarm.add(editButton);
        pane.add(editAlarm, BorderLayout.LINE_END);
        final JList alarmList = new JList(alarmListModel);
        JScrollPane alarmScrollList = new JScrollPane(alarmList);
        alarmScrollList.setPreferredSize(new Dimension(150, 100));
        //editAlarm.add(alarmList);
        editAlarm.add(alarmScrollList);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent stg) {
                Object selectedEditAlarm = alarmList.getSelectedValue();
                //JOptionPane.showMessageDialog(null, "Alarm for : "+selectedEditAlarm);
                
                String newAlarm = JOptionPane.showInputDialog(editAlarm, "Changing alarm for: "+selectedEditAlarm);
                System.out.println("New Alarm for: " + newAlarm);
                String removalTime = String.valueOf(selectedEditAlarm);
                String removalPenul = removalTime.replaceAll(":","");
                int removalFinal = Integer.valueOf(removalPenul);
                try {
                    q.removeEdit(removalFinal);
                    System.out.println("Removed Alarm: "+selectedEditAlarm);
                    alarmListModel.removeElement(removalTime);
                } catch (QueueUnderflowException ex) {
                    System.out.println("That wasn't supposed to happen. Couldn't remove alarm.");
                }
                TimeNumber timenumberReplace = new TimeNumber(newAlarm);
                //currentTimeField.setValue(new Date());
                String selectedTimeCurrent = currentTimeField.getText();
                String presentTime = (String) selectedTimeCurrent;
                String currentTime = presentTime.replaceAll(":","");
                String baseTime = newAlarm.replaceAll(":","");
                int nowTime = Integer.valueOf(currentTime);
                int namedTime = Integer.valueOf(baseTime);
                //int priorityReplace = namedTime - nowTime;
                /*
                if (priorityReplace < 0) {
                    priorityReplace = priorityReplace + 240000;
                    priorityReplace = Math.abs(priorityReplace);
                }
                */
                int priorityReplace = namedTime;
                try {
                    q.add(timenumberReplace, priorityReplace);
                    alarmListModel.addElement(newAlarm);
                } catch (QueueOverflowException ex) {
                    System.out.println("That wasn't supposed to happen. Couldn't add new alarm.");
                }
                
            }
        });
        
        silentRemovalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent delc) {
                //JOptionPane.showMessageDialog(null, "Alarm for : "+selectedEditAlarm);
                Object selectedDeletionAlarm = alarmListDeletion;
                JOptionPane.showMessageDialog(editAlarm, "Deleting alarm for: "+alarmListDeletion);
                String removalAlarm = String.valueOf(alarmListDeletion);
                String removalPenulAlarm = removalAlarm.replaceAll(":","");
                int removalFinalAlarm = Integer.valueOf(removalPenulAlarm);
                try {
                    q.removeEdit(removalFinalAlarm);
                    System.out.println("Removed Alarm: "+selectedDeletionAlarm);
                    alarmListModel.removeElement(selectedDeletionAlarm);
                } catch (QueueUnderflowException ex) {
                    System.out.println("That wasn't supposed to happen. Couldn't remove alarm: "+removalFinalAlarm);
                }
                
            }
        });
        
        
        JButton clockDeleteButton = new JButton("Delete Alarm");
        editAlarm.add(clockDeleteButton, BorderLayout.LINE_END);
        clockDeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent delc) {
                Object selectedEditAlarm = alarmList.getSelectedValue();
                //JOptionPane.showMessageDialog(null, "Alarm for : "+selectedEditAlarm);
                
                JOptionPane.showMessageDialog(editAlarm, "Deleting alarm for: "+selectedEditAlarm);
                String removalTime = String.valueOf(selectedEditAlarm);
                String removalPenul = removalTime.replaceAll(":","");
                int removalFinal = Integer.valueOf(removalPenul);
                try {
                    q.removeEdit(removalFinal);
                    System.out.println("Removed Alarm: "+selectedEditAlarm);
                    alarmListModel.removeElement(selectedEditAlarm);
                } catch (QueueUnderflowException ex) {
                    System.out.println("That wasn't supposed to happen. Couldn't remove alarm.");
                }
                
            }
        });
        
        JButton upcomingAlarmButton = new JButton("Current Alarm");
        editAlarm.add(upcomingAlarmButton, BorderLayout.PAGE_END);
        
        Format clockTime = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        timeLabel = new JLabel("Short time:");
        timeInput = new JFormattedTextField(clockTime);
        timeInput.setValue(new Date());
        timeInput.setColumns(20);
        currentTimeField = new JFormattedTextField(clockTime);
        currentTimeField.setValue(new Date());
    
        timeInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timeInputPanel.add(timeLabel);
        timeInputPanel.add(timeInput);
        pane.add(timeInputPanel, BorderLayout.PAGE_END);
    
        
        // End of borderlayout code
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void removeFromAlarm(Object removedFromList) {
    View.alarmListDeletion = String.valueOf(removedFromList);
        View.silentRemovalButton.doClick();
    }
    
    public void update(Observable o, Object arg) {
        panel.repaint();
    }
}
