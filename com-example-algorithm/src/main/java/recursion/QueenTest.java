package recursion;

public class QueenTest {

    int max = 8;
    int[] array = new int[max];

    public static void main(String[] args) {

    }

    private void check(int n) {
        if (n == max) { //n=8
            print();
            return;
        }
        //依次判断是否冲突
        for (int i = 0; i < max; i++) {
            array[n] = i;
            if (judge(n)) {
                check(n + 1);
            }
            //如果冲突，就继续执行array[n] = i


        }
    }

    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            // 表示判断是否是同一列，或斜线
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    //
    private void print() {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i] + "\t");
        }
        System.out.println();

    }
}
