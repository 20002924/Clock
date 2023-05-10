package clock;

/**
 * Definition of the Priority Queue abstract data type.
 *
 * A collection of items, each with an integer priority. The item with the
 * highest priority is the one returned by head() and is the one removed by
 * remove().
 *
 * This is a generic (i.e. parameterised) type. T can be any class.
 *
 * Implementation method is not specified. Implementations may set a maximum
 * allowed number of items, or not.
 * 
 * @param <T> The type of items stored in the queue.
 */
public interface PriorityQueue<T> {

    /**
     * Add the given item to the queue with the given priority. Throw an
     * exception if it's already full to capacity.
     *
     * @param item
     * @param priority
     * @throws QueueOverflowException
     */
    public void add(T item, int priority) throws QueueOverflowException;

    /**
     * The highest priority item stored. Throw an exception if it's empty.
     *
     * @return The item with the highest priority
     * @throws QueueUnderflowException
     */
    public T head() throws QueueUnderflowException;
    
    /**
     * The item with matching time.
     *
     * @param headTime
     * @return The item with the highest priority
     * @throws QueueUnderflowException
     */
    public T headEquals(int headTime) throws QueueUnderflowException;
    
    /**
     * The item's priority with matching time.
     *
     * @param samePriority
     * @return The item with the highest priority
     * @throws QueueUnderflowException
     */
    public int headRetrieval(int samePriority) throws QueueUnderflowException;
    
    /**
     * Find the current alarm from priority.
     *
     * @param searchHead
     * @return The item with the highest priority
     * @throws QueueUnderflowException
     */
    public int headCurrentRetrieval(int searchHead) throws QueueUnderflowException;
    
    /**
     * Find the current alarm from priority.
     *
     * @param currentPriority
     * @return The item with the highest priority
     * @throws QueueUnderflowException
     */
    public int headAlarmRetrieval(int currentPriority) throws QueueUnderflowException;
    
    /**
     * The priority of the chosen priority item. Throw an exception if it's empty.
     *
     * @return The item with the highest priority
     * @throws QueueUnderflowException
     */
    public int headPriority() throws QueueUnderflowException;

    /**
     * Remove the highest priority item from the queue. Throw an exception if
     * it's empty.
     *
     * @throws QueueUnderflowException
     */
    public void remove() throws QueueUnderflowException;

    /**
     * Remove the named item from editing.
     *
     * @param editRemoval
     * @throws QueueUnderflowException
     */
    public void removeEdit(int editRemoval) throws QueueUnderflowException;
    
    /**
     * Is the queue empty?
     *
     * @return True if there are no items stored, otherwise False
     */
    
    
    public boolean isEmpty();

    @Override
    /**
     * A string representation of the entire queue.
     *
     * This should be formatted as a list, in square brackets.
     *
     * Each item should be shown as an ordered pair in parentheses together with
     * its priority.
     *
     * The items may be listed in any order. In particular there is no
     * requirement that the item returned by head() should be listed first.
     *
     * For example: [(Fred, 10), (Mabel, 15), (Jane, 5)]
     */
    public String toString();
    
    
    /**
     * A string representation used to save the queue to file.
     *
     * This should be formatted as a list, in square brackets.
     *
     * Each item should be shown as an ordered pair in parentheses together with
     * its priority.
     *
     * The items may be listed in any order. In particular there is no
     * requirement that the item returned by head() should be listed first.
     *
     * For example: [(Fred, 10), (Mabel, 15), (Jane, 5)]
     */
    public String toStringSave();
}
