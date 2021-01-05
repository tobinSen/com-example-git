package simple;

public class Link {

    private int size = 0;
    private Node first;
    private Node last;

    public void addLast(int data) {
        if (size == 0) {
            fillStart(data);
        }else {
            Node node = new Node();
            node.setData(data);
            last.setNext(node);
            last = node;
        }
    }

    private void fillStart(int data) {
        first = new Node();
        first.setData(data);
        last = first; //last和first同一个
    }
}
