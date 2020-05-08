package queue;

import java.util.Scanner;

public class AnnularQueue {

    public static void main(String[] args) {
        CircleArray queue = new CircleArray(3);
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
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = sc.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = queue.getQueue();
                        System.out.println("取出的数据是：" + res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 'h':
                    try {
                        queue.headQueue();
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

    static class CircleArray {
        private int maxSize;
        private int front; //默认0 队列第一个元素
        private int rear;  //默认0 指向下一个元素
        private int arr[];

        public CircleArray(int maxSize) {
            this.maxSize = maxSize;
            arr = new int[maxSize];
        }

        public boolean isFull() {
            /**
             * 2
             * 1
             * 0
             *  maxSize =3
             *  front =0
             *  rear =2
             */
            return (rear + 1) % maxSize == front;
        }

        public boolean isEmpty() {
            return rear == front;
        }

        public void addQueue(int n) {
            if (isFull()) {
                System.out.println("队列满了");
                return;
            }
            //
            arr[rear] = n;
            //这里会出现越界问题（巧妙之处：是当指向最后一个下标的时的下一个下标是第一个下标）
            //程序逻辑 和 算法的不同之处：算法会根据（数字）规律找到不同的算术运算符的逻辑
            rear = (rear + 1) % maxSize;
        }

        public int getQueue() {
            if (isEmpty()) {
                throw new RuntimeException("队列为空");
            }
            int value = arr[front];
            // 算法的巧妙：指向最后一个元素的时候，下一个下标位置
            front = (front + 1) % maxSize;
            return value;
        }

        public void showQueue() {
            if (isEmpty()) {
                return;
            }
            for (int i = front; i < front + size(); i++) {
                System.out.println("元素：" + arr[i % maxSize]);
            }
        }

        //当前队列有效数字
        public int size() {
            //rear=2
            //front =1
            //maxSize=3
            return (rear + maxSize - front) % maxSize;
        }

        public int headQueue() {
            if (isEmpty()) {
                throw new RuntimeException("队列为空");
            }
            return arr[front];
        }

    }
}
