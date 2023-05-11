package clock;

/**
 * Imports needed for the View page, these could likely have been split across other classes.
 */
import java.awt.*;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.util.Scanner;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
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
    
    /**
    * Defined variables here are used mainly for inside classes access.
     */
    ClockPanel panel;
    JPanel timeInputPanel;
    JLabel timeLabel;
    JFormattedTextField timeInput;
    JFormattedTextField currentTimeField;
    JPanel editAlarm;
    static JList alarmList;
    static JScrollPane alarmListModel;
    ActionListener listener;
    ActionListener timerselect;
    private Object selectedTime;
    static String alarmListDeletion;
    static JButton silentRemovalButton = new JButton("You're not supposed to see this.");
    String toggle;
    String toggleEdit;
    String toggleFile;
    static JButton baton1Button = new JButton("You're not supposed to see this.");
    static JButton baton2Button = new JButton("You're not supposed to see this.");
    
    
    
    
    public View(Model model) {
        
        /**
        * Calls the priority queue to keep the same queue across the project. 
        */
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
        
        /**
        * The top panel, it displays the information about clock colours as well as containing the exit button.
        */
        JPanel panelPrimer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        /**
        * Previous method used to show current alarm, removed for the visual indicator. 
        */
        //Format clockTimePrimer = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        //JFormattedTextField currentAlarm = new JFormattedTextField(clockTimePrimer);
        //JLabel currentAlarmLabel = new JLabel("Current Alarm:");
        //currentAlarm.setColumns(20);
        JLabel currentAlarmLabel = new JLabel("Current Alarm Colours: Orange, Green, Magenta");
        /**
        * The exit button, this contains the query for saving alarms before shutdown.
        * Shutdown hooks were attempted with less than satisfactory results and so this method was used instead.
        */
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent xit) {
                /**
                 * Option pane used to choose the outcome, if yes is chosen the regular export function is activated.
                 */
            int alarmSaveQuery = JOptionPane.showConfirmDialog(null,"Do you want to save your alarms?", "Alarm Save Query",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (alarmSaveQuery == JOptionPane.YES_OPTION){
                View.saveAlarm();
            }
            else if (alarmSaveQuery == JOptionPane.NO_OPTION){
                System.out.println("Request denied.");
             }
             else {
                System.out.println("Neither selected.");
             }
            System.exit(0);
            }
        });
        panelPrimer.add(currentAlarmLabel, BorderLayout.CENTER);
        panelPrimer.add(exitButton, BorderLayout.EAST);
        //panelPrimer.add(currentAlarm, BorderLayout.CENTER);
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
         
        /**
        * This button is used to add times to the queue, it does this through the text input from the textInputPanel.
        */
        final JButton button = new JButton("Add Alarm");
        //pane.add(button, BorderLayout.LINE_START);
        button.addActionListener(new ActionListener() {
        //Scanner stdin = new Scanner(System.in);

            public void actionPerformed(ActionEvent stg) {
                //System.out.println("Welcome to the background code.");
                //System.out.println("Using a sorted array.");
                /**
                * The following code is used to get both the TimeNumber and int from the string retrieved from the input box.
                */
                selectedTime = timeInput.getText();
                String alarmTime = (String) selectedTime;
                //currentTimeField.setValue(new Date());
                /**
                * The following code appears often in this class, it is used to get an integer for priority as well as an item/string.
                * The string is retrieved from another source and then has the colons removed to turn it into an integer.
                * The string is often used to form the TimeNumber which is used for the priority queue items.
                */
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
                /**
                * Fragments of old code are visible here, the previous method deducted the current time from the alarm time.
                */   
                /*
                if (priority < 0) {
                    priority = priority + 240000;
                    priority = Math.abs(priority);
                }
                */
                /**
                * The add item code, this is largely the same process as the regular priority queue.
                */
                System.out.println("Adding " + timenumber.getTime() + " with priority " + priority);
                try {
                q.add(timenumber, priority);
                /**
                * The added item is also added to the scroll list.
                */
                alarmListModel.addElement(alarmTime);
                } catch (QueueOverflowException e) {
                System.out.println("Add operation failed: " + e);
                }
                /**
                * Method used before implementation of the input box, used through the console.
                */
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
                /**
                * The disabled code below was used to see the order of the queue, this was used to troubleshoot any problems.
                */
                /*
                try {
                    String time2 = q.head().getTime();
                    System.out.println("The timenumber at the head of the queue is " + time2);
                } catch (QueueUnderflowException e) {
                    System.out.println("Can't get head of queue: " + e);
                }
                System.out.println(q);
                */
                //String queueAttempt = q.toString();
                //System.out.println(queueAttempt);
            }
        });
        
        /**
        * The editAlarm panel is used for the scroll list and the buttons used to edit, remove, import and export alarms.
        */
        final JPanel editAlarm = new JPanel();
        editAlarm.setLayout(new BoxLayout(editAlarm, BoxLayout.PAGE_AXIS));
        final JButton editButton = new JButton("Edit Alarm");
        editAlarm.add(editButton);
        pane.add(editAlarm, BorderLayout.LINE_END);
        /**
        * The alarm list is used to show the current list of alarms, this code allows additions from other functions.
        */
        final JList alarmList = new JList(alarmListModel);
        JScrollPane alarmScrollList = new JScrollPane(alarmList);
        alarmScrollList.setPreferredSize(new Dimension(150, 100));
        //editAlarm.add(alarmList);
        editAlarm.add(alarmScrollList);
        /**
        * The edit button is used as to remove the original chosen alarm and add the new alarm specified.
        */
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent stg) {
                Object selectedEditAlarm = alarmList.getSelectedValue();
                //JOptionPane.showMessageDialog(null, "Alarm for : "+selectedEditAlarm);
                /**
                * The original alarm is chosen from the currently selected alarm from the scroll list.
                * This alarm is used to get both the string and priority for deletion.
                */
                String newAlarm = JOptionPane.showInputDialog(editAlarm, "Changing alarm for: "+selectedEditAlarm);
                System.out.println("New Alarm for: " + newAlarm);
                String removalTime = String.valueOf(selectedEditAlarm);
                String removalPenul = removalTime.replaceAll(":","");
                int removalFinal = Integer.valueOf(removalPenul);
                try {
                    /**
                    * The int is used to remove the alarm from the queue and the string is used to delete from the scroll list.
                    */
                    q.removeEdit(removalFinal);
                    System.out.println("Removed Alarm: "+selectedEditAlarm);
                    alarmListModel.removeElement(removalTime);
                } catch (QueueUnderflowException ex) {
                    System.out.println("That wasn't supposed to happen. Couldn't remove alarm.");
                }
                /**
                * The following section is used to add the new alarm through retrieving the new value from the option pane.
                */
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
                    /**
                    * The new replacement number and string get added to the queue and the scroll list. 
                    */
                    q.add(timenumberReplace, priorityReplace);
                    alarmListModel.addElement(newAlarm);
                } catch (QueueOverflowException ex) {
                    System.out.println("That wasn't supposed to happen. Couldn't add new alarm.");
                }
                
            }
        });
        editButton.setVisible(false);
        /**
        * silentRemovalButton is used to delete from the scroll list through the method called from Model.
        */
        silentRemovalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent delc) {
                //JOptionPane.showMessageDialog(null, "Alarm for : "+selectedEditAlarm);
                /**
                * alarmListDeletion which is passed from Model is used to delete from the scroll list and queue any matching times.
                */
                Object selectedDeletionAlarm = alarmListDeletion;
                JOptionPane.showMessageDialog(editAlarm, "Deleting alarm for: "+alarmListDeletion);
                String removalAlarm = String.valueOf(alarmListDeletion);
                String removalPenulAlarm = removalAlarm.replaceAll(":","");
                int removalFinalAlarm = Integer.valueOf(removalPenulAlarm);
                try {
                    /**
                    * The new conversions of alarmListDeletion to appropriate formats is used to delete from the queue and scroll list.
                    */
                    q.removeEdit(removalFinalAlarm);
                    System.out.println("Removed Alarm: "+selectedDeletionAlarm);
                    alarmListModel.removeElement(selectedDeletionAlarm);
                } catch (QueueUnderflowException ex) {
                    System.out.println("That wasn't supposed to happen. Couldn't remove alarm: "+removalFinalAlarm);
                }
                
            }
        });
        
        /**
        * clockDeleteButton is used to delete alarms specified in the scroll list.
        */
        final JButton clockDeleteButton = new JButton("Delete Alarm");
        editAlarm.add(clockDeleteButton, BoxLayout.X_AXIS);
        clockDeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent delc) {
                Object selectedEditAlarm = alarmList.getSelectedValue();
                //JOptionPane.showMessageDialog(null, "Alarm for : "+selectedEditAlarm);
                
                /**
                *  Similar to the edit method, the item is retrieved from the scroll list and turned into an integer for deletion.
                */
                JOptionPane.showMessageDialog(editAlarm, "Deleting alarm for: "+selectedEditAlarm);
                String removalTime = String.valueOf(selectedEditAlarm);
                String removalPenul = removalTime.replaceAll(":","");
                int removalFinal = Integer.valueOf(removalPenul);
                try {
                    /**
                    * The alarm is removed from the queue and scroll list where the integer and string match.
                    */
                    q.removeEdit(removalFinal);
                    System.out.println("Removed Alarm: "+selectedEditAlarm);
                    alarmListModel.removeElement(selectedEditAlarm);
                    System.out.println(q);
                } catch (QueueUnderflowException ex) {
                    System.out.println("That wasn't supposed to happen. Couldn't remove alarm.");
                }
                
            }
        });
        clockDeleteButton.setVisible(false);
        
        /**
        * Old method used to update the current alarm, this was also used when priority was calculated based on current time deducted by alarm time.
        */
        /*
        JButton upcomingAlarmButton = new JButton("Current Alarm");
        editAlarm.add(upcomingAlarmButton, BorderLayout.PAGE_END);
        upcomingAlarmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent delc) {
                DateFormat formatterView = new SimpleDateFormat("HH:mm:ss");
                String tiempoView = (String) formatterView.format(new Date());
                String rawTiempoView = tiempoView.replaceAll(":","");
                
                //JOptionPane.showMessageDialog(null, "Alarm for : "+selectedEditAlarm);
                
                JOptionPane.showMessageDialog(editAlarm, "Setting currrent alarm for: "+tiempoView);
                int tiempoFinal = Integer.valueOf(rawTiempoView);
                try {
                    q.headCurrentRetrieval(tiempoFinal);
                    System.out.println("Set current alarm to: "+tiempoView);
                    
                } catch (QueueUnderflowException ex) {
                    System.out.println("That wasn't supposed to happen. Couldn't change alarm.");
                }
                
            }
        });
        */
        
        /**
        * The import method, this part of the code was not as well implemented, with repeated statements used.
        * This method is used to locate, read and extract times from iCalendar files, these times are then added to the queue and scroll list.
        */
        final JButton importButton = new JButton("Import Alarm(s)");
        editAlarm.add(importButton, BorderLayout.PAGE_END);
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent imp) {
                /**
                *  The file chooser is used, which allows the user to select a file from their computer to load in the program.
                */
                JFileChooser alarmFileChooser = new JFileChooser();
                alarmFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int chosenFile = alarmFileChooser.showOpenDialog(editAlarm);
                if (chosenFile == JFileChooser.APPROVE_OPTION) {
                    /**
                    * If a file is chosen the path to the file is retrieved and set to a variable.
                    */
                    File chosenFileFound = alarmFileChooser.getSelectedFile();
                    System.out.println("Selected file: " + chosenFileFound.getAbsolutePath());
                    String chosenFilePath = chosenFileFound.getAbsolutePath();
                    try {
                        /**
                        * The program then attempts to read through the file.
                        */
                        FileInputStream alarmFileInputStream = new FileInputStream(chosenFilePath);
                        Scanner alarmScanner =new Scanner(alarmFileInputStream);
                        String alarmTextRetrieval = "";
                        while (alarmScanner.hasNextLine()) {
                            /**
                            * If the next line in the file has ORGANIZER present the line beyond that is retrieved.
                            * This is used to get the DTSTART line and retrieve the times from there.
                            */
                            if (alarmScanner.nextLine().startsWith("ORGANIZER")) {
                                alarmTextRetrieval = alarmTextRetrieval + alarmScanner.nextLine();
                            }
                        }
                        /**
                        * The following string removes the text from before each number.
                        */
                        //System.out.println(alarmTextRetrieval);
                        String alarmTextCleaned = alarmTextRetrieval.replaceAll("DTSTART:","");
                        //System.out.println(alarmTextCleaned);
                        
                        /**
                        * These methods are used to define the import variables outside of each character length statement.
                        */
                        int importCheck = 0;
                        String import1 = "ERROR1";
                        String import2 = "ERROR2";
                        String import3 = "ERROR3";
                        String import4 = "ERROR4";
                        String import5 = "ERROR5";
                        String import6 = "ERROR6";
                        String import7 = "ERROR7";
                        String import8 = "ERROR8";
                        String import1Retrieve = "ERROR1Retrieve";
                        String import2Retrieve = "ERROR2Retrieve";
                        String import3Retrieve = "ERROR3Retrieve";
                        String import4Retrieve = "ERROR4Retrieve";
                        String import5Retrieve = "ERROR5Retrieve";
                        String import6Retrieve = "ERROR6Retrieve";
                        String import7Retrieve = "ERROR7Retrieve";
                        String import8Retrieve = "ERROR8Retrieve";

                        /**
                        * Each one of these if statements checks for the overall length of retrieved DTSTART lines.
                        * Each line ends up as 16 characters and so for each 16 characters there is a statement to add another time.
                        * The sub string functions are used to remove the unnecessary date and letters from the time.
                        * The time is then returned as a string.
                        * The import check variable is increased for each if statement run.
                        */
                        //String loopAlarmSave = java.util.Arrays.toString(queueAttemptFinal.split("(?<=\\G......)"));
                        System.out.println(alarmTextCleaned);
                        if (alarmTextCleaned.length() >= 16) {
                        import1 = String.valueOf(alarmTextCleaned.substring(0, 16));
                        import1Retrieve = import1.substring(import1.indexOf("T") + 1);
                        import1Retrieve = import1Retrieve.substring(0, import1Retrieve.indexOf("Z"));
                        System.out.println("Successful splitting of "+import1Retrieve);
                        importCheck = importCheck + 1;
                        }
                        if (alarmTextCleaned.length() >= 32) {
                        import2 = String.valueOf(alarmTextCleaned.substring(16, 32));
                        import2Retrieve = import2.substring(import2.indexOf("T") + 1);
                        import2Retrieve = import2Retrieve.substring(0, import2Retrieve.indexOf("Z"));
                        System.out.println("Successful splitting of "+import2Retrieve);
                        importCheck = importCheck + 1;
                        }
                        if (alarmTextCleaned.length() >= 48) {
                        import3 = String.valueOf(alarmTextCleaned.substring(32, 48));
                        import3Retrieve = import3.substring(import3.indexOf("T") + 1);
                        import3Retrieve = import3Retrieve.substring(0, import3Retrieve.indexOf("Z"));
                        System.out.println("Successful splitting of "+import3Retrieve);
                        importCheck = importCheck + 1;
                        }
                        if (alarmTextCleaned.length() >= 64) {
                        import4 = String.valueOf(alarmTextCleaned.substring(48, 64));
                        import4Retrieve = import4.substring(import4.indexOf("T") + 1);
                        import4Retrieve = import4Retrieve.substring(0, import4Retrieve.indexOf("Z"));
                        System.out.println("Successful splitting of "+import4Retrieve);
                        importCheck = importCheck + 1;
                        }
                        if (alarmTextCleaned.length() >= 80) {
                        import5 = String.valueOf(alarmTextCleaned.substring(64, 80));
                        import5Retrieve = import5.substring(import5.indexOf("T") + 1);
                        import5Retrieve = import5Retrieve.substring(0, import5Retrieve.indexOf("Z"));
                        System.out.println("Successful splitting of "+import5Retrieve);
                        importCheck = importCheck + 1;
                        }
                        if (alarmTextCleaned.length() >= 96) {
                        import6 = String.valueOf(alarmTextCleaned.substring(80, 96));
                        import6Retrieve = import6.substring(import6.indexOf("T") + 1);
                        import6Retrieve = import6Retrieve.substring(0, import6Retrieve.indexOf("Z"));
                        System.out.println("Successful splitting of "+import6Retrieve);
                        importCheck = importCheck + 1;
                        }
                        if (alarmTextCleaned.length() >= 112) {
                        import7 = String.valueOf(alarmTextCleaned.substring(96, 112));
                        import7Retrieve = import7.substring(import7.indexOf("T") + 1);
                        import7Retrieve = import7Retrieve.substring(0, import7Retrieve.indexOf("Z"));
                        System.out.println("Successful splitting of "+import7Retrieve);
                        importCheck = importCheck + 1;
                        }
                        if (alarmTextCleaned.length() >= 128) {
                        import8 = String.valueOf(alarmTextCleaned.substring(112, 128));
                        import8Retrieve = import8.substring(import8.indexOf("T") + 1);
                        import8Retrieve = import8Retrieve.substring(0, import8Retrieve.indexOf("Z"));
                        System.out.println("Successful splitting of "+import8Retrieve);
                        importCheck = importCheck + 1;
                        }
                        else {
                            System.out.println("Why are you doing this? There are no alarms to import");
                        }
                        
                        /*
                        String alarmTextSplit = alarmTextCleaned;
                        alarmTextSplit = alarmTextSplit.substring(alarmTextSplit.indexOf("T") + 1);
                        alarmTextSplit = alarmTextSplit.substring(0, alarmTextSplit.indexOf("Z"));
                        System.out.println(alarmTextSplit);
                        */
                        
                        /**
                        * The following if statements check how many strings were retrieved from the file.
                        * For each one, the imported time is converted into an integer.
                        * For each one, the imported time is also added to a string with colons changing it to be used for time.
                        * Finally each retrieved string is added to both the queue and the scroll list.
                        */
                        if (importCheck >= 1) {
                            int import1Priority = Integer.valueOf(import1Retrieve);
                            String import1Time = import1Retrieve.replaceAll("..(?!$)", "$0:");
                            System.out.println(import1Time);
                            TimeNumber import1Timenumber = new TimeNumber(import1Time);
                            try {
                                q.add(import1Timenumber, import1Priority);
                                alarmListModel.addElement(import1Time);
                            } catch (QueueOverflowException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (importCheck >= 2) {
                            int import2Priority = Integer.valueOf(import2Retrieve);
                            String import2Time = import2Retrieve.replaceAll("..(?!$)", "$0:");
                            System.out.println(import2Time);
                            TimeNumber import2Timenumber = new TimeNumber(import2Time);
                            try {
                                q.add(import2Timenumber, import2Priority);
                                alarmListModel.addElement(import2Time);
                            } catch (QueueOverflowException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (importCheck >= 3) {
                            int import3Priority = Integer.valueOf(import3Retrieve);
                            String import3Time = import3Retrieve.replaceAll("..(?!$)", "$0:");
                            System.out.println(import3Time);
                            TimeNumber import3Timenumber = new TimeNumber(import3Time);
                            try {
                                q.add(import3Timenumber, import3Priority);
                                alarmListModel.addElement(import3Time);
                            } catch (QueueOverflowException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (importCheck >= 4) {
                            int import4Priority = Integer.valueOf(import4Retrieve);
                            String import4Time = import4Retrieve.replaceAll("..(?!$)", "$0:");
                            System.out.println(import4Time);
                            TimeNumber import4Timenumber = new TimeNumber(import4Time);
                            try {
                                q.add(import4Timenumber, import4Priority);
                                alarmListModel.addElement(import4Time);
                            } catch (QueueOverflowException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (importCheck >= 5) {
                            int import5Priority = Integer.valueOf(import5Retrieve);
                            String import5Time = import5Retrieve.replaceAll("..(?!$)", "$0:");
                            System.out.println(import5Time);
                            TimeNumber import5Timenumber = new TimeNumber(import5Time);
                            try {
                                q.add(import5Timenumber, import5Priority);
                                alarmListModel.addElement(import5Time);
                            } catch (QueueOverflowException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (importCheck >= 6) {
                            int import6Priority = Integer.valueOf(import6Retrieve);
                            String import6Time = import6Retrieve.replaceAll("..(?!$)", "$0:");
                            System.out.println(import6Time);
                            TimeNumber import6Timenumber = new TimeNumber(import6Time);
                            try {
                                q.add(import6Timenumber, import6Priority);
                                alarmListModel.addElement(import6Time);
                            } catch (QueueOverflowException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (importCheck >= 7) {
                            int import7Priority = Integer.valueOf(import7Retrieve);
                            String import7Time = import7Retrieve.replaceAll("..(?!$)", "$0:");
                            System.out.println(import7Time);
                            TimeNumber import7Timenumber = new TimeNumber(import7Time);
                            try {
                                q.add(import7Timenumber, import7Priority);
                                alarmListModel.addElement(import7Time);
                            } catch (QueueOverflowException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (importCheck >= 8) {
                            int import8Priority = Integer.valueOf(import8Retrieve);
                            String import8Time = import8Retrieve.replaceAll("..(?!$)", "$0:");
                            System.out.println(import8Time);
                            TimeNumber import8Timenumber = new TimeNumber(import8Time);
                            try {
                                q.add(import8Timenumber, import8Priority);
                                alarmListModel.addElement(import8Time);
                            } catch (QueueOverflowException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                
                
            }
        });
        importButton.setVisible(false);
        
        /**
        * The export method, this part of the code like the import method is messy in comparison with the other code.
        * This method is used to retrieve times from the queue and add export them to a new iCalendar file.
        */
        final JButton exportButton = new JButton("Export Alarm(s)");
        editAlarm.add(exportButton, BorderLayout.PAGE_END);
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent imp) {
                /**
                * The following section is used to find the current date and time and is used to give each file a unique name.
                */
                DateFormat fileNameFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                String fileNameFormatted = (String) fileNameFormat.format(new Date());
                String finalNameFormatted = fileNameFormatted.replaceAll(":","-");
                StringBuilder iCalendarBuilder = new StringBuilder();
                iCalendarBuilder.append(finalNameFormatted);
                iCalendarBuilder.append(".ics");
                
                try {
                /**
                * The following code retrieves the items of the current queue and strips the output of any special characters.
                * The items are then placed together with no spaces in order to split them apart.
                */
                File alarmFile = new File(iCalendarBuilder.toString());
                alarmFile.createNewFile();    
                FileWriter alarmFileWriter = new FileWriter(alarmFile.getAbsoluteFile());
                String queueAttempt = q.toStringSave();
                String queueAttemptCleaned = queueAttempt.replaceAll("\\[|\\]|\\{|\\}|\\(|\\)","");
                String queueAttemptCommaCleaned = queueAttemptCleaned.replaceAll(",","").replaceAll(":","");
                //System.out.println(queueAttemptCommaCleaned);
                String queueAttemptFinal = queueAttemptCommaCleaned.replaceAll(" ","");
                
                /**
                * Like the import variables these are used to allow the inside if statements to use them. 
                */
                int exportCheck = 0;
                String export1 = "ERROR1";
                String export2 = "ERROR2";
                String export3 = "ERROR3";
                String export4 = "ERROR4";
                String export5 = "ERROR5";
                String export6 = "ERROR6";
                String export7 = "ERROR7";
                String export8 = "ERROR8";
                
                /**
                * The following method, like the import method's equivalent finds how many characters are present in the combined string.
                * For each full number set (6) the string is split, with that set being passed to the export variable.
                * the export check counter is increased through each use of these methods.
                */
                //String loopAlarmSave = java.util.Arrays.toString(queueAttemptFinal.split("(?<=\\G......)"));
                //System.out.println(queueAttemptFinal);
                if (queueAttemptFinal.length() >= 6) {
                export1 = String.valueOf(queueAttemptFinal.substring(0, 6));
                System.out.println("Successful splitting of "+export1);
                exportCheck = exportCheck + 1;
                }
                if (queueAttemptFinal.length() >= 12) {
                export2 = String.valueOf(queueAttemptFinal.substring(6, 12));
                System.out.println("Successful splitting of "+export2);
                exportCheck = exportCheck + 1;
                }
                if (queueAttemptFinal.length() >= 18) {
                export3 = String.valueOf(queueAttemptFinal.substring(12, 18));
                System.out.println("Successful splitting of "+export3);
                exportCheck = exportCheck + 1;
                }
                if (queueAttemptFinal.length() >= 24) {
                export4 = String.valueOf(queueAttemptFinal.substring(18, 24));
                System.out.println("Successful splitting of "+export4);
                exportCheck = exportCheck + 1;
                }
                if (queueAttemptFinal.length() >= 30) {
                export5 = String.valueOf(queueAttemptFinal.substring(24, 30));
                System.out.println("Successful splitting of "+export5);
                exportCheck = exportCheck + 1;
                }
                if (queueAttemptFinal.length() >= 36) {
                export6 = String.valueOf(queueAttemptFinal.substring(30, 36));
                System.out.println("Successful splitting of "+export6);
                exportCheck = exportCheck + 1;
                }
                if (queueAttemptFinal.length() >= 42) {
                export7 = String.valueOf(queueAttemptFinal.substring(36, 42));
                System.out.println("Successful splitting of "+export7);
                exportCheck = exportCheck + 1;
                }
                if (queueAttemptFinal.length() >= 48) {
                export8 = String.valueOf(queueAttemptFinal.substring(42, 48));
                System.out.println("Successful splitting of "+export8);
                exportCheck = exportCheck + 1;
                }
                else {
                    System.out.println("Why are you doing this? There are no alarms to export");
                }
                System.out.println("Total exportCheck is: "+exportCheck);
                /**
                * This section of code is used to find the date used for DTSAMP, DTSTART and DTEND.
                * It does this through creating a new date format and turning it into a string for later use.
                */
                DateFormat stampDate = new SimpleDateFormat("yyyyMMdd");
                String stampDateFormatted = (String) stampDate.format(new Date());
                DateFormat stampDateSecond = new SimpleDateFormat("HHmmss");
                String stampDateFormattedSecond = (String) stampDateSecond.format(new Date());
                
                /**
                * This section starts the writing of the iCalendar file, the permanent additions appear before the if statements.
                */
                BufferedWriter alarmBufferedWriter = new BufferedWriter(alarmFileWriter);
                alarmBufferedWriter.write("BEGIN:VCALENDAR\n");
                alarmBufferedWriter.write("VERSION:2.0\n");
                alarmBufferedWriter.write("PRODID://UHI/20002924//EN\n");
                /**
                * The following if statements add an event for every alarm exported, the alarms are passed as a part of the DTSTART and DTEND lines.
                * The previously mentioned date format is used for the remaining dates.
                * The exportCheck variable is used to determine how many alarms are exported.
                */
                if (exportCheck >= 1) {
                alarmBufferedWriter.write("BEGIN:VEVENT\n");
                alarmBufferedWriter.write("UID:"+finalNameFormatted+1+" 20002924@example.com\n");
                alarmBufferedWriter.write("DTSTAMP:"+stampDateFormatted+"T"+stampDateFormattedSecond+"Z\n");
                alarmBufferedWriter.write("ORGANIZER;CN=20002924:MAILTO:20002924@example.com\n");
                alarmBufferedWriter.write("DTSTART:"+stampDateFormatted+"T"+export1+"Z\n");
                alarmBufferedWriter.write("DTEND:"+stampDateFormatted+"T"+export1+"Z\n");
                alarmBufferedWriter.write("END:VEVENT\n");
                }
                if (exportCheck >= 2) {
                alarmBufferedWriter.write("BEGIN:VEVENT\n");
                alarmBufferedWriter.write("UID:"+finalNameFormatted+2+" 20002924@example.com\n");
                alarmBufferedWriter.write("DTSTAMP:"+stampDateFormatted+"T"+stampDateFormattedSecond+"Z\n");
                alarmBufferedWriter.write("ORGANIZER;CN=20002924:MAILTO:20002924@example.com\n");
                alarmBufferedWriter.write("DTSTART:"+stampDateFormatted+"T"+export2+"Z\n");
                alarmBufferedWriter.write("DTEND:"+stampDateFormatted+"T"+export2+"Z\n");
                alarmBufferedWriter.write("END:VEVENT\n");
                }
                if (exportCheck >= 3) {
                alarmBufferedWriter.write("BEGIN:VEVENT\n");
                alarmBufferedWriter.write("UID:"+finalNameFormatted+3+" 20002924@example.com\n");
                alarmBufferedWriter.write("DTSTAMP:"+stampDateFormatted+"T"+stampDateFormattedSecond+"Z\n");
                alarmBufferedWriter.write("ORGANIZER;CN=20002924:MAILTO:20002924@example.com\n");
                alarmBufferedWriter.write("DTSTART:"+stampDateFormatted+"T"+export3+"Z\n");
                alarmBufferedWriter.write("DTEND:"+stampDateFormatted+"T"+export3+"Z\n");
                alarmBufferedWriter.write("END:VEVENT\n");
                }
                if (exportCheck >= 4) {
                alarmBufferedWriter.write("BEGIN:VEVENT\n");
                alarmBufferedWriter.write("UID:"+finalNameFormatted+4+" 20002924@example.com\n");
                alarmBufferedWriter.write("DTSTAMP:"+stampDateFormatted+"T"+stampDateFormattedSecond+"Z\n");
                alarmBufferedWriter.write("ORGANIZER;CN=20002924:MAILTO:20002924@example.com\n");
                alarmBufferedWriter.write("DTSTART:"+stampDateFormatted+"T"+export4+"Z\n");
                alarmBufferedWriter.write("DTEND:"+stampDateFormatted+"T"+export4+"Z\n");
                alarmBufferedWriter.write("END:VEVENT\n");
                }
                if (exportCheck >= 5) {
                alarmBufferedWriter.write("BEGIN:VEVENT\n");
                alarmBufferedWriter.write("UID:"+finalNameFormatted+5+" 20002924@example.com\n");
                alarmBufferedWriter.write("DTSTAMP:"+stampDateFormatted+"T"+stampDateFormattedSecond+"Z\n");
                alarmBufferedWriter.write("ORGANIZER;CN=20002924:MAILTO:20002924@example.com\n");
                alarmBufferedWriter.write("DTSTART:"+stampDateFormatted+"T"+export5+"Z\n");
                alarmBufferedWriter.write("DTEND:"+stampDateFormatted+"T"+export5+"Z\n");
                alarmBufferedWriter.write("END:VEVENT\n");
                }
                if (exportCheck >= 6) {
                alarmBufferedWriter.write("BEGIN:VEVENT\n");
                alarmBufferedWriter.write("UID:"+finalNameFormatted+6+" 20002924@example.com\n");
                alarmBufferedWriter.write("DTSTAMP:"+stampDateFormatted+"T"+stampDateFormattedSecond+"Z\n");
                alarmBufferedWriter.write("ORGANIZER;CN=20002924:MAILTO:20002924@example.com\n");
                alarmBufferedWriter.write("DTSTART:"+stampDateFormatted+"T"+export6+"Z\n");
                alarmBufferedWriter.write("DTEND:"+stampDateFormatted+"T"+export6+"Z\n");
                alarmBufferedWriter.write("END:VEVENT\n");
                }
                if (exportCheck >= 7) {
                alarmBufferedWriter.write("BEGIN:VEVENT\n");
                alarmBufferedWriter.write("UID:"+finalNameFormatted+7+" 20002924@example.com\n");
                alarmBufferedWriter.write("DTSTAMP:"+stampDateFormatted+"T"+stampDateFormattedSecond+"Z\n");
                alarmBufferedWriter.write("ORGANIZER;CN=20002924:MAILTO:20002924@example.com\n");
                alarmBufferedWriter.write("DTSTART:"+stampDateFormatted+"T"+export7+"Z\n");
                alarmBufferedWriter.write("DTEND:"+stampDateFormatted+"T"+export7+"Z\n");
                alarmBufferedWriter.write("END:VEVENT\n");
                }
                if (exportCheck >= 8) {
                alarmBufferedWriter.write("BEGIN:VEVENT\n");
                alarmBufferedWriter.write("UID:"+finalNameFormatted+8+" 20002924@example.com\n");
                alarmBufferedWriter.write("DTSTAMP:"+stampDateFormatted+"T"+stampDateFormattedSecond+"Z\n");
                alarmBufferedWriter.write("ORGANIZER;CN=20002924:MAILTO:20002924@example.com\n");
                alarmBufferedWriter.write("DTSTART:"+stampDateFormatted+"T"+export8+"Z\n");
                alarmBufferedWriter.write("DTEND:"+stampDateFormatted+"T"+export8+"Z\n");
                alarmBufferedWriter.write("END:VEVENT\n");
                }
                alarmBufferedWriter.write("END:VCALENDAR");

                /**
                * Finally the file is written and closed, with it being saved to the computer.
                */
                alarmBufferedWriter.close();
                
                }  catch (IOException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
        });
        exportButton.setVisible(false);
        
        /**
        * The first baton button is used to pass the export request from the earlier exit functions.
        * A simple doClick function calls the export button to go through the usual process.
        */
        baton1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent baton1) {
                exportButton.doClick();
            }});
        /**
        * The second baton button is used to pass the import request upon start-up from the method in Controller.
        * A simple doClick function is also used here to activate the usual process for the import button.
        */
        baton2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent baton2) {
                importButton.doClick();
            }});
        
        /**
        * The timeInputPanel is used to input times for the buttons to add to the queue.
        * The input box is used to retrieve the string value of the time input and use it for the add method.
        */
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
        timeInputPanel.add(button);
        pane.add(timeInputPanel, BorderLayout.PAGE_END);
        timeLabel.setVisible(false);
        timeInput.setVisible(false);
        button.setVisible(false);
    
        /**
        * The menu panel is used to close or open parts of the program.
        * These parts are namely the add alarm, edit alarm, delete alarm, import alarm and export alarm buttons.
        * Throughout the code several of these buttons have been set invisible through setVisible.
        * The following code reverses the states of setVisible.
        */
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.PAGE_AXIS));
        /**
        * The showAddButton is used to show or hide the add alarm section.
        */
        JButton showAddButton = new JButton("Show/Hide Creation");
        showAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent showa) {
                /**
                * The following if statements check the current state of the sections.
                * They start of as false in visibility terms, the final else if statement catches this and allows them to be set visible.
                * The first two loops then allow the section to be toggled at will.
                * The loop integer is used in order to stop an infinite loop of the section being toggled on or off.
                */
                int loop = 0;
                if (toggle == "off" && loop == 0) {
                    timeLabel.setVisible(true);
                    timeInput.setVisible(true);
                    button.setVisible(true);
                    toggle = "on";
                    loop = loop +1;
                    //System.out.println(toggle);
                }
                if (toggle == "on" && loop == 0) {
                    timeLabel.setVisible(false);
                    timeInput.setVisible(false);
                    button.setVisible(false);
                    toggle = "off";
                    loop = loop +1;
                    //System.out.println(toggle);
                }
                else if (loop == 0) {
                   timeLabel.setVisible(true);
                   timeInput.setVisible(true);
                   button.setVisible(true);
                   toggle = "on";
                   loop = loop +1;
                   //System.out.println(toggle);
                }
            }
            });
        /**
        * The showEditButton is used to show or hide the edit alarm and delete alarm buttons.
        * This changes the format of the scroll list as it has more or less space available.
        */
        JButton showEditButton = new JButton("Show/Hide Editing");
        showEditButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent showe) {
                /**
                * This section is the same as the others used for visibility aside from a change in variables.
                * They start of as false in visibility terms, the final else if statement catches this and allows them to be set visible.
                * The first two loops then allow the section to be toggled at will.
                * The loop integer is used in order to stop an infinite loop of the section being toggled on or off.
                */
                int loop = 0;
                if (toggleEdit == "off" && loop == 0) {
                    editButton.setVisible(true);
                    clockDeleteButton.setVisible(true);
                    toggleEdit = "on";
                    loop = loop +1;
                    //System.out.println(toggle);
                }
                if (toggleEdit == "on" && loop == 0) {
                    editButton.setVisible(false);
                    clockDeleteButton.setVisible(false);
                    toggleEdit = "off";
                    loop = loop +1;
                    //System.out.println(toggle);
                }
                else if (loop == 0) {
                   editButton.setVisible(true);
                   clockDeleteButton.setVisible(true);
                   toggleEdit = "on";
                   loop = loop +1;
                   //System.out.println(toggle);
                }
            }
            });
        /**
        * The showFileButton is used to show or hide the import and export alarm buttons.
        * This changes the format of the scroll list as well due to the addition or removal of space for buttons.
        */
        JButton showFileButton = new JButton("Show/Hide Files");
        showFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent showa) {
                /**
                * This section is the same as the others used for visibility aside from a change in variables.
                * They start of as false in visibility terms, the final else if statement catches this and allows them to be set visible.
                * The first two loops then allow the section to be toggled at will.
                * The loop integer is used in order to stop an infinite loop of the section being toggled on or off.
                */
                int loop = 0;
                if (toggleFile == "off" && loop == 0) {
                    importButton.setVisible(true);
                    exportButton.setVisible(true);
                    toggleFile = "on";
                    loop = loop +1;
                    //System.out.println(toggle);
                }
                if (toggleFile == "on" && loop == 0) {
                    importButton.setVisible(false);
                    exportButton.setVisible(false);
                    toggleFile = "off";
                    loop = loop +1;
                    //System.out.println(toggle);
                }
                else if (loop == 0) {
                   importButton.setVisible(true);
                   exportButton.setVisible(true);
                   toggleFile = "on";
                   loop = loop +1;
                   //System.out.println(toggle);
                }
            }
            });
        menuPanel.add(showAddButton, BorderLayout.WEST);
        
        menuPanel.add(showEditButton, BorderLayout.SOUTH);
        menuPanel.add(showFileButton, BorderLayout.SOUTH);
        
        pane.add(menuPanel, BorderLayout.WEST);
        
        
        // End of borderlayout code
        
        frame.pack();
        frame.setVisible(true);
    }
    /**
    * removeFromAlarm is used from Model in order to remove the items from the queue and the scroll list without additional variables.
    * This is primarily used in order to access the scroll list from Model as Model has access to queue deletion already.
    */
    public static void removeFromAlarm(Object removedFromList) {
    View.alarmListDeletion = String.valueOf(removedFromList);
        View.silentRemovalButton.doClick();
    }
    
    /**
    * saveAlarm is used by the exit button to prompt the user if they want to save alarms before leaving.
    * This method calls the baton button and clicks it therefore clicking the the export button through baton's function.
    */
    public static void saveAlarm() {
        View.baton1Button.doClick();
    }
    /**
    * saveAlarm is used by the Controller to prompt the user if they want import any alarms.
    * This method calls the baton button and clicks it therefore clicking the the import button through baton's function.
    */
    public static void addAlarm() {
        View.baton2Button.doClick();
    }
    
    public void update(Observable o, Object arg) {
        panel.repaint();
    }
}
