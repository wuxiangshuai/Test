package dataStructures.demo6;

import java.util.Arrays;

/**
 * @ClassName: ShellSort
 * @Author: WuXiangShuai
 * @Time: 11:55 2019/9/17.
 * @Description: 希尔排序
 */
public class ShellSort {

    public static void main(String[] args) {
//        int arr[] = {3, 9, -1, 10, -2, 99, 21, 5, -16, 37};
        int arr[] = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random()*80000);
        }
        System.out.println(System.currentTimeMillis());
        sheelSort(arr);
        System.out.println(System.currentTimeMillis());
    }

    private static void sheelSort(int[] arr) {
        int mid = arr.length / 2; // 第一次增量为集合长度的一半
        while (mid >= 1) { // 判断增量，每次减半，小于1时退出循环
            for (int i = mid; i < arr.length; i++) { // 元素分组，数量等于增量mid
                int val = arr[i];
                int index = i - mid;
                while (index >= 0 && arr[index] > val) {
                    arr[index + mid] = arr[index]; // 每次进入循环，对应元素向后移动一个节点
                    index -= mid; // 索引前移
                }
                if (index + mid != i) { // 当索引发生变化时
                    arr[index + mid] = val;
                }
            }
            mid /= 2; // 增量每次减半
        }
        System.out.println(Arrays.toString(arr));
    }

}
