package ForthChar;

public final class SeqQueue<T> implements Queue<T> {

    private Object element[];
    private int front, rear;
    private static final int MIN_CAPACITY = 16;

    public SeqQueue(int length) {
        if (length < MIN_CAPACITY) {
            length = MIN_CAPACITY;
        }
        this.element = new Object[length];
        this.front = this.rear = 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean add() {
        return false;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public T poll() {
        return null;
    }
}
