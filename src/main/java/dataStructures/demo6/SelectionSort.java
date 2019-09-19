package dataStructures.demo6;

import static org.apache.coyote.http11.Constants.a;

/**
 * @ClassName: SelectionSort
 * @Author: WuXiangShuai
 * @Time: 10:26 2019/9/17.
 * @Description: 选择排序
 */
public class SelectionSort {

    public static void main(String[] args) {
        int arr[] = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random()*80000);
        }
        int minIndex;
        System.out.println(System.currentTimeMillis());
        for (int i = 0; i < arr.length - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
        System.out.println(System.currentTimeMillis());
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

}
