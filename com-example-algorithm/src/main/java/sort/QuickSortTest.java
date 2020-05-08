package sort;

import java.util.Arrays;

public class QuickSortTest {

    public static void main(String[] args) {
        int[] arr = {-9, 78, 0, 0, -434, 0};
        quickSort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int left, int right) {
        int l = left;
        int r = right;
        int temp = 0;
        //pivot 中轴值
        int pivot = arr[(left + right) / 2];
        while (l < r) {

            while (arr[l] < pivot) {
                l += 1;
            }

            while (arr[r] > pivot) {
                r -= 1;
            }

            if (l >= r) {
                break;
            }
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
}
