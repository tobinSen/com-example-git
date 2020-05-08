package queue;

import java.util.Scanner;

public class ArrayListQueueTest {

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);
        char key = ' ';
        Scanner sc = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列取出数据");
            System.out.println("h(head):查看队列头的数据");
            key = sc.next().charAt(0);
            switch (key) {
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = sc.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.println("取出的数据是：" + res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 'h':
                    try {
                        arrayQueue.headQueue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 'e':
                    sc.close();
                    loop = false;
                    break;
                default:
                    break;

            }
        }
        System.out.println("程序退出");

    }


    static class ArrayQueue {
        private int maxSize; //表示数组的最大容量
        private int front;  //队列头
        private int rear; //队列尾
        private int[] arr; //该数据用于存储数据，模拟队列

        public ArrayQueue(int maxSize) {
            this.maxSize = maxSize;
            arr = new int[this.maxSize];
            front = -1; //指向队列头部，分析出front是指向队列头的前一个位置
            rear = -1; //指向队尾，指向队列尾的数据(即就是队列最后一个数据)
        }

        public boolean isFull() {
            return rear == maxSize - 1;
        }

        public boolean isEmpty() {
            return rear == front;
        }

        public void addQueue(int n) {
            if (isFull()) {
                System.out.println("队列满了");
                return;
            }
            rear++; //rear后移动
            arr[rear] = n;
        }

        public int getQueue() {
            if (isEmpty()) {
                throw new RuntimeException("队列为空");
            }
            front++;
            return arr[front];
        }

        public void showQueue() {
            if (isEmpty()) {
                return;
            }
            for (int i = 0; i < arr.length; i++) {
                System.out.println("元素："+ arr[i]);
            }
        }

        public int headQueue() {
            if (isEmpty()) {
                throw new RuntimeException("队列为空");
            }
            return arr[front + 1];
        }
    }
}
