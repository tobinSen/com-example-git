package sort;

/**
 * 插入排序
 */
public class InsertSort {

    public static void main(String[] args) {

    }

    public static void insertSort(int[] arr) {
        int insertVal = arr[1];
        int insertIndex = 0;
        while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
            arr[insertIndex + 1] = arr[insertIndex];
            insertIndex--;
        }
        //退出
        arr[insertIndex + 1] = insertVal;

    }
}
