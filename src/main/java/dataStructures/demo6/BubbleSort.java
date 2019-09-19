package dataStructures.demo6;

/**
 * @ClassName: BubbleSort
 * @Author: WuXiangShuai
 * @Time: 9:59 2019/9/17.
 * @Description: 冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
//        int arr[] = {3, 9, -1, 10, -2};
        int arr[] = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random()*80000);
        }
        System.out.println(System.currentTimeMillis());
        sort(arr);
        System.out.println(System.currentTimeMillis());
    }

    private static void sort(int[] arr) {
        boolean flag;
        for (int i = 0; i < arr.length - 1; i++) {
            flag = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) break;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
