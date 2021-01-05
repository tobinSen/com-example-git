package sort;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;


/**
 * 快排思路：
 * 1.先定一个中间的数，左右两边进行比较，让左边的数全部小于中间数，右边的数都是大于中间数
 * 2.左边的数再次取中间数递归重复第一步的操作，右边的数也重复第一步的操作
 */
public class QuickSortTest {

    public static void main(String[] args) {
        int[] arr = {-9, 78, 1, 0, -434, 3, 2};
        quickSort1(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
    }

    /**
     * @param arr   原数组
     * @param left  左索引
     * @param right 右索引
     */
    private static void quickSort(@NotNull int[] arr, int left, int right) {
        int l = left;
        int r = right;
        int temp = 0;
        //pivot 中轴值
        int pivot = arr[(left + right) / 2];
        while (l < r) {

            //找到left中第一个大于middle的数
            while (arr[l] < pivot) {
                l += 1; //这里l是变化的
            }

            //找到right中第一个小于middle的数
            while (arr[r] > pivot) {
                r -= 1;
            }

            if (l >= r) {
                break;
            }
            //left和right进行替换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            //如果相同
            if (arr[l] == pivot) {
                r -= 1; //这里是优化点，右边的是可以不用再次检查了
            }
            //
            if (arr[r] == pivot) {
                l += 1;
            }

        }
        //排序

        if (l == r) {
            l += 1;
            r -= 1;
        }
        if (left < r) {
            quickSort(arr, left, r);
        }

        if (right > l) {
            quickSort(arr, l, right);
        }


    }

    /*
     * 快速排序
     *
     * 参数说明:
     *     a -- 待排序的数组
     *     l -- 数组的左边界(例如，从起始位置开始排序，则l=0)
     *     r -- 数组的右边界(例如，排序截至到数组末尾，则r=a.length-1)
     */
    public static void quickSort1(int[] a, int l, int r) {

        if (l < r) {
            int i, j, x;

            i = l;   //临时存储l
            j = r;   //临时存储r
            x = a[i]; //x存储的是第一个元素
            while (i < j) {
                // 右边的值大于左边第一个数字
                while (i < j && a[j] > x)
                    j--; // 从右向左找第一个小于x的数
                if (i < j)
                    a[i++] = a[j];
                while (i < j && a[i] < x)
                    i++; // 从左向右找第一个大于x的数
                if (i < j)
                    a[j--] = a[i];
            }
            a[i] = x; //这个时候放入中间
            quickSort1(a, l, i - 1); /* 递归调用 */
            quickSort1(a, i + 1, r); /* 递归调用 */
        }
    }
}
