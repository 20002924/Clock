package clock;

/**
 * Minimal "person" class.
 *
 * At the moment a Person object just holds their time, but in a more realistic
 * system, there would obviously be more.
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
