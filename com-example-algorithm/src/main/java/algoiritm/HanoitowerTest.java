package algoiritm;

public class HanoitowerTest {

    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == nums[i++]) {

            }
        }

    }

    //分治算法
    public static void hanoiTower(int num, char a, char b, char c) {
        if (num == 1) {
            System.out.println("第一个盘从" + a + "->" + c);
        } else {
            hanoiTower(num - 1, a, c, b);
            System.out.println("第" + num + "个盘从" + a + "->" + c);
            hanoiTower(num - 1, b, a, c);
        }
    }
}
