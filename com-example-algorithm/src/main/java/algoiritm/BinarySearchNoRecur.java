package algoiritm;

/**
 * 二分查找（非递归）
 */
public class BinarySearchNoRecur {

    public static void main(String[] args) {

    }

    /**
     *
     * @param arr 原始数据
     * @param target 需要查找的数字
     * @return
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) { //这里进行缩进
            int mid = (left + right) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
