package dataStructures.demo6;

/**
 * @ClassName: InsertSort
 * @Author: WuXiangShuai
 * @Time: 10:43 2019/9/17.
 * @Description: 插入排序
 */
public class InsertSort {

    public static void main(String[] args) {
//        int arr[] = {3, 9, -1, 10, -2, 100, -10};
        int arr[] = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random()*80000);
        }
        System.out.println(System.currentTimeMillis());
        insertSort(arr);
        System.out.println(System.currentTimeMillis());
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    private static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int val = arr[i];
            int j = i - 1;
            for (; j >= 0 && arr[j] > val; j--) {
                arr[j + 1] = arr[j];
            }
            if (j + 1 != i) {
                arr[j + 1] = val;
            }
        }
    }

}
