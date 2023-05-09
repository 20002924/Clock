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
    JPanel panel2; //rename
    JLabel label; // rename
    JFormattedTextField input; //rename
    JFormattedTextField currentTimeField;
    ActionListener listener;
    ActionListener timerselect;
    private Object selectedTime;
    
    
    
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
        
        JButton button = new JButton("Button 1 (PAGE_START)");
        pane.add(button, BorderLayout.PAGE_START);
        button.addActionListener(new ActionListener() {
            PriorityQueue<TimeNumber> q;
        Scanner stdin = new Scanner(System.in);
        public void actionPerformed(ActionEvent ae) {

        System.out.println("Welcome to the Priority Queue manager.");
                q = new ClockQueue<>(8);
                System.out.println("Using a sorted array.");
                

        System.out.println("Enter commands at the prompt.");
        System.out.println("A <time> <priority> adds a timenumber to the queue.");
        System.out.println("H displays the time of the timenumber at the head of the queue");
        System.out.println("R removes the timenumber at the head of the queue");
        System.out.println("E checks if the queue is empty");
        System.out.println("P prints the whole queue");
        System.out.println("Q quits from the system");

        System.out.print("> ");
        String input = stdin.nextLine();

        while (!input.toLowerCase().equals("q")) {
            if (input.toLowerCase().charAt(0) == 'a') {

                String time = input.substring(2, input.lastIndexOf(' '));
                TimeNumber timenumber = new TimeNumber(time);
                int priority = Integer.parseInt(input.substring(input.lastIndexOf(' ') + 1));
                System.out.println("Adding " + timenumber.getTime() + " with priority " + priority);
                try {
                    q.add(timenumber, priority);
                } catch (QueueOverflowException e) {
                    System.out.println("Add operation failed: " + e);
                }
            } else if (input.toLowerCase().charAt(0) == 'h') {

                try {
                    String time = q.head().getTime();
                    System.out.println("The timenumber at the head of the queue is " + time);
                } catch (QueueUnderflowException e) {
                    System.out.println("Can't get head of queue: " + e);
                }
            } else if (input.toLowerCase().charAt(0) == 'r') {

                try {
                    String time = q.head().getTime();
                    System.out.println("Removing " + time + " from the head of the queue");
                    q.remove();
                } catch (QueueUnderflowException e) {
                    System.out.println("Can't remove head of queue: " + e);
                }
            } else if (input.toLowerCase().charAt(0) == 'e') {

                if (q.isEmpty()) {
                    System.out.println("The queue is empty");
                } else {
                    System.out.println("The queue is NOT empty");
                }
            } else if (input.toLowerCase().charAt(0) == 'p') {


                System.out.println(q);
            }
            System.out.print("> ");
            input = stdin.nextLine();
        }
        System.out.println("Bye");
    }
        });
         
        panel.setPreferredSize(new Dimension(200, 200));
        pane.add(panel, BorderLayout.CENTER);
         
        button = new JButton("Add Alarm");
        pane.add(button, BorderLayout.LINE_START);
        button.addActionListener(new ActionListener() {
            
            
        Scanner stdin = new Scanner(System.in);

            public void actionPerformed(ActionEvent stg) {
                System.out.println("Welcome to the background code.");
                
                
                System.out.println("Using a sorted array.");
                selectedTime = input.getText();
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
                int priority = namedTime - nowTime;
                //int priority = nowTime - namedTime;
                if (priority < 0) {
                    priority = priority + 240000;
                    priority = Math.abs(priority);
                }
                System.out.println("Adding " + timenumber.getTime() + " with priority " + priority);
                try {
                q.add(timenumber, priority);
                alarmListModel.addElement(timenumber);
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
                    alarmListModel.removeElement(selectedEditAlarm);
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
                int priorityReplace = namedTime - nowTime;
                if (priorityReplace < 0) {
                    priorityReplace = priorityReplace + 240000;
                    priorityReplace = Math.abs(priorityReplace);
                }
                try {
                    q.add(timenumberReplace, priorityReplace);
                    alarmListModel.addElement(newAlarm);
                } catch (QueueOverflowException ex) {
                    System.out.println("That wasn't supposed to happen. Couldn't add new alarm.");
                }
                
            }
        });
        JButton clockResetButton = new JButton("Refresh Current Time");
        editAlarm.add(clockResetButton, BorderLayout.LINE_END);
        // Make it change short time and current time as well as refresh priorities.
        
        
        //reformat all this code to be different
        Format clockTime = DateFormat.getTimeInstance(DateFormat.MEDIUM);
    label = new JLabel("Short time:");
    input = new JFormattedTextField(clockTime);
    input.setValue(new Date());
    input.setColumns(20);
    currentTimeField = new JFormattedTextField(clockTime);
    currentTimeField.setValue(new Date());
    
    panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panel2.add(label);
    panel2.add(input);
    pane.add(panel2, BorderLayout.PAGE_END);
    
        
        // End of borderlayout code
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void update(Observable o, Object arg) {
        panel.repaint();
    }
}
