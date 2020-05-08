package leetCode.array;

import java.util.Arrays;

/**
 * 删除排序数组中的重复项
 * <p>
 * 0,1,2,3,4,0 2 2 3 3 4
 * <p>
 * 思路：3点
 */
public class DelRepeatArray {

    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int num = removeDuplicates(nums);
        System.out.println(num);
        int[] repeatNums = new int[num];
        System.arraycopy(nums, 0, repeatNums, 0, num);
        System.out.println(Arrays.toString(repeatNums));
    }

    //排序 + 双指针 + 不同项迁移
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }

        }
        return i + 1;
    }
}
