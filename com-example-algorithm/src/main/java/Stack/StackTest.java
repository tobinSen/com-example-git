package Stack;

import java.util.Stack;

public class StackTest {

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.add("jack");
        stack.add("tom");
        stack.add("smith");

        while (!stack.empty()){
            System.out.println(stack.pop());
        }
    }
}
