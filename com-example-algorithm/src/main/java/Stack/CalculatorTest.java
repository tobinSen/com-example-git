package Stack;

public class CalculatorTest {

    public static void main(String[] args) {

        String express = "3+2*6-2";
        ArrayStack numStack = new ArrayStack(10);
        ArrayStack operStack = new ArrayStack(10);

        int index = 0;
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' ';//将每次扫描得到char保存
        String keepNum = "";//拼接多位数
        while (true) {
            ch = express.substring(index, index + 1).charAt(0);
            if (operStack.isOper(ch)) {
                if (!operStack.isEmpty()) {
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);
                        numStack.push(res);
                        operStack.push(ch);
                    } else {
                        operStack.push(ch);
                    }
                } else {
                    operStack.push(ch);
                }
            } else {
                //numStack.push(ch - 48);
                keepNum += ch;

                if (index == express.length() - 1) {
                    numStack.push(Integer.valueOf(keepNum));
                } else {
                    //判断如果下一个数字继续，不是就截取
                    if (operStack.isOper(express.substring(index + 1, index + 2).charAt(0))) {
                        numStack.push(Integer.valueOf(keepNum));
                        keepNum = "";
                    }
                }
            }

            index++;
            if (index >= express.length()) {
                break;
            }
        }

        while (true) {
            if (operStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            numStack.push(res);
        }
        System.out.println(numStack.pop());

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

        //返回栈顶
        public int peek() {
            return stack[top];
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

        //字符可以当做运算符，字符和整型可以转换的
        public int priority(int oper) {
            if (oper == '*' || oper == '/') {
                return 1;
            } else if (oper == '+' || oper == '-') {
                return 0;
            } else {
                return -1;
            }
        }

        public boolean isOper(char val) {
            return val == '+' || val == '-' || val == '*' || val == '/';
        }

        public int cal(int num1, int num2, int oper) {
            int res = 0;
            switch (oper) {
                case '+':
                    res = num1 + num2;
                    break;
                case '-':
                    res = num2 - num1;
                    break;
                case '*':
                    res = num1 * num2;
                    break;
                case '/':
                    res = num2 / num1;
                    break;
                default:
                    break;
            }
            return res;
        }
    }
}