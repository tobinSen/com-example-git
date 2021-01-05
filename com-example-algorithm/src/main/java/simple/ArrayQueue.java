package simple;

public class ArrayQueue {
    private final Object[] items;

    private int head; //head 和tail 是不允许出队列的

    private int tail;

    public ArrayQueue(int capacity) {
        items = new Object[capacity];
    }


    /**
     * 队列空：head == tail
     * 队列满：
     */
    public boolean push(Object item) {
        if (head == (tail + 1) % items.length) {
            return false; //满了
        }
        items[tail] = item;
        tail = (tail + 1) % items.length; //保证是一个环形的
        return true;
    }

    public Object poll() {
        if (head == tail) {
            return null; //队列是空
        }
        Object item = items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        return item;
    }
}
