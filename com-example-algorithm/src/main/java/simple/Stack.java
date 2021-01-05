package simple;

public class Stack {

    private int size;  //size用于记录最大index
    private int[] arr; //数组进行存储

    public Stack(int size) {
        if (0 > size) {
            size = 10;
        }
        arr = new int[size];
    }

    public void push(int ele) {
        arr[arr.length - 1] = ele;
        size++;
    }

    public int peek() {
        return arr[size - 1];
    }
}
