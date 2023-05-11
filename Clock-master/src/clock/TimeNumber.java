package clock;

/**
 * Edited class for use in the priority queue.
 * Used for storing the times with colons in place.
 */
public class TimeNumber {

    protected String time;

    public TimeNumber(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return getTime();
    }
}
