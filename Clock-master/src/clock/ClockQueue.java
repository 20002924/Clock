package clock;


public class ClockQueue<T> implements PriorityQueue<T> {
    /**
     * Where the data is actually stored.
     */
    private final Object[] storage;

    /**
     * The size of the storage array.
     */
    private final int capacity;

    /**
     * The index of the last item stored.
     *
     * This is equal to the item count minus one.
     */
    private int tailIndex;

    /**
     * Create a new empty queue of the given size.
     *
     * @param size
     */
    public ClockQueue(int size) { 
        storage = new Object[size];
        capacity = size;
        tailIndex = -1;
    }
    public static ClockQueue ClockQueueInstance = new ClockQueue(8);

    @Override
    public T head() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            return ((PriorityItem<T>) storage[0]).getItem();
        }
    }
    
    @Override
    public T headEquals(int headTime) throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            int i = tailIndex;
            while ( i > 0 && ((PriorityItem<T>) storage[i]).getPriority() != headTime ) {
                i = i - 1;
            }
            
            return ((PriorityItem<T>) storage[i]).getItem();
        }
    }
    
    @Override
    public int headRetrieval(int samePriority) throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            int i = tailIndex;
            while ( i > 0 && ((PriorityItem<T>) storage[i]).getPriority() != samePriority ) {
                i = i - 1;
            }
            
            return ((PriorityItem<T>) storage[i]).getPriority();
        }
    }
    
    @Override
    public int headCurrentRetrieval(int searchHead) throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            int i = tailIndex;
            int n = tailIndex;
            while ( i > 0 && ((PriorityItem<T>) storage[i]).getPriority() > searchHead ) {
                i = i - 1;
                if (((PriorityItem<T>) storage[i]).getPriority() > searchHead ) {
                n = i;
            }
            }
            
            return ((PriorityItem<T>) storage[n]).getPriority();
        }
    }
    
    @Override
    public int headAlarmRetrieval(int currentPriority) throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            int i = tailIndex;
            int r = 0;
            while ( i > 0 && ((PriorityItem<T>) storage[i]).getPriority() != currentPriority ) {
                i = i - 1;
                r = r + 1;
            }
            if (r > 0) {
                i = i + 1;
            }
            
            return ((PriorityItem<T>) storage[i]).getPriority();
            
        }
    }
    
    @Override
    public int headPriority() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            return ((PriorityItem<T>) storage[0]).getPriority();
        }
    }

    @Override
    public void add(T item, int priority) throws QueueOverflowException {
        tailIndex = tailIndex + 1;
        if (tailIndex >= capacity) {
            /* No resizing implemented, but that would be a good enhancement. */
            tailIndex = tailIndex - 1;
            throw new QueueOverflowException();
        } else {
            /* Scan backwards looking for insertion point */
            int i = tailIndex;
            while (i > 0 && ((PriorityItem<T>) storage[i - 1]).getPriority() > priority) {
                storage[i] = storage[i - 1];
                i = i - 1;
            }
            storage[i] = new PriorityItem<>(item, priority);
        }
    }

    @Override
    public void remove() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            for (int i = 0; i < tailIndex; i++) {
                storage[i] = storage[i + 1];
            }
            tailIndex = tailIndex - 1;
        }
    }
    
    public void removeEdit(int editRemoval) throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            
            int tail = 0;
            int i = tailIndex;
            
            
            
            /*
            while (i > 0) {
                if (loopTime == editRemoval) {
                    tail = i;
                }
                i = i - 1;
            }
            */
            
            /*
            for (int i = 0; i < tailIndex; i++) {
                int loopTime = ((PriorityItem<T>) storage[i]).getPriority();
                if (loopTime == editRemoval) {
                    tail = i;
                }
                storage[i] = storage[i + 1];
            }
            */
            
            while (i > 0) {
                int loopTime = ((PriorityItem<T>) storage[i]).getPriority();
                if (loopTime == editRemoval) {
                    tail = i;
                }
                i = i - 1;
            }
            
            storage[tail] = storage[tailIndex];
            tailIndex = tailIndex - 1;
        }
    }

    @Override
    public boolean isEmpty() {
        return tailIndex < 0;
    }

    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i <= tailIndex; i++) {
            if (i > 0) {
                result = result + ", ";
            }
            result = result + storage[i];
        }
        result = result + "]";
        return result;
    }
    
    @Override
    public String toStringSave() {
        String result = "[";
        for (int i = 0; i <= tailIndex; i++) {
            if (i > 0) {
                result = result + ", ";
            }
            result = result + ((PriorityItem<T>) storage[i]).getItem();
        }
        result = result + "]";
        return result;
    }
}