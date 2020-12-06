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
        // 从第二个元素开始
        for (int i = 1; i < arr.length; i++) {
            int val = arr[i];
            // 获取当前元素前一个元素的坐标
            int j = i - 1;
            // 前面的元素大于当前元素，找到当前元素插入的位置，并将该位置以及后面的元素（到当前元素之前）后移一位
            for (; j >= 0 && arr[j] > val; j--) {
                arr[j + 1] = arr[j];
            }
            // 判断当前元素是否需要插入
            if (j + 1 != i) {
                arr[j + 1] = val;
            }
        }
    }

}
