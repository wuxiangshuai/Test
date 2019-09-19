package dataStructures.demo6;

import java.util.Arrays;

/**
 * @ClassName: QuickSort
 * @Author: WuXiangShuai
 * @Time: 15:52 2019/9/17.
 * @Description: 快速排序
 */
public class QuickSort {

    public static void main(String[] args) {
//        int arr[] = {-9, 78, 0, 32, -567, 70};
//        int arr[] = {-9, -567, 0, 32, 78, 70};
        int arr[] = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80000);
        }
        System.out.println(System.currentTimeMillis());
        quickSort(arr, 0, arr.length - 1);
        System.out.println(System.currentTimeMillis());
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort(int[] arr, int left, int right) {
        int l = left; // 左下标
        int r = right;// 右下标
        int pivot = arr[(left + right) / 2]; // 获取中间值
        while (l < r) { // 当右边界大于左边界时，一直循环
            while (arr[l] < pivot) { // 从左向右遍历，找到第一个比中间值大的元素
                l++;
            }
            while (arr[r] > pivot) { // 从右向左遍历，找到第一个比中间值小的元素
                r--;
            }
            if (l >= r) break; // 若左边界大于右边界，退出循环
            int temp = arr[l]; // 交换两边的值，使其左边的值都小于中间值，右边的值都大于中间值
            arr[l] = arr[r];
            arr[r] = temp;
            if (arr[l] == pivot) l++; // 若左元素恰好等于中间值，使其继续向右移动一位
            if (arr[r] == pivot) r--; // 若右元素恰好等于中间值，使其继续向左移动一位
        }
        if (l == r) { // 若左右边界相等，使其错位，否则其会一直递归下去，造成栈溢出
            l++;
            r--;
        }
        if (left < r) quickSort(arr, left, r); // 左边元素继续排序
        if (right > l) quickSort(arr, l, right); // 右边元素继续排序
    }

}
