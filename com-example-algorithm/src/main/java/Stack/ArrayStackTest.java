package Stack;

import java.util.Scanner;

public class ArrayStackTest {

    public static void main(String[] args) {

        ArrayStack stack = new ArrayStack(4);
        String key = "";
        boolean loop = true;
        Scanner sc = new Scanner(System.in);
        while (loop) {
            System.out.println("show:表示显示栈");
            System.out.println("exit:退出程序");
            System.out.println("push:表示添加一个元素到栈中");
            System.out.println("pop:出栈数据");

            key = sc.next();
            switch (key) {
                case "show":
                    stack.list();
                    break;
                case "exit":
                    System.exit(-1);
                    break;
                case "push":
                    int i = sc.nextInt();
                    stack.push(i);
                    break;
            }
        }

    }

    static class ArrayStack {
        private int maxSize; //栈的大小
        private int[] stack; //保存数据
        private int top = -1;

        public ArrayStack(int maxSize) {
            this.maxSize = maxSize;
            stack = new int[maxSize];
        }

        public boolean isFull() {
            return top == maxSize - 1;
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public void push(int value) {
            if (isFull()) {
                return;
            }
            top++;
            stack[top] = value;
        }

        public int pop() {
            if (isEmpty()) {
                throw new RuntimeException("栈空");
            }
            int value = stack[top];
            top--;
            return value;
        }

        public void list() {
            if (isEmpty()) {
                System.out.println("栈空...");
                return;
            }
            for (int i = top; i < stack.length; i--) {
                System.out.println(stack[i]);
            }
        }

    }
}
