package simple;

public class Entry {
    int key;
    int value;
    Entry next;

    public Entry(int key, int value, Entry next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }
}
