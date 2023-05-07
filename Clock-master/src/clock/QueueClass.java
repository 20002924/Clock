
package clock;

public class QueueClass {
    PriorityQueue<TimeNumber> q;
    
    public QueueClass(View view) {
            q = new ClockQueue<>(8);
    }
    
}
