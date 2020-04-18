package dataStructures.demo6;

import java.util.Arrays;

/**
 * @ClassName: MergeSort
 * @Author: WuXiangShuai
 * @Time: 17:58 2019/9/17.
 * @Description: 归并排序
 */
public class MergeSort {

    public static void main(String[] args) {
//        int arr[] = {-9, -567, 0, 32, 78, 70};
        int arr[] = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80000);
        }
        int temp[] = new int[arr.length];
        System.out.println(System.currentTimeMillis());
        mergeSort(arr, 0, arr.length - 1, temp);
        System.out.println(System.currentTimeMillis());
        System.out.println(Arrays.toString(arr));
    }

    /*
    {8, 6, 2, 10, 9, 5, 1}
    {8, 6, 2, 10} {9, 5, 1}
    {8, 6} {2, 10} {9, 5} {1}
    {6, 8, 2, 10} {5, 9, 1}
    {6, 8, 2, 10, 1, 5, 9}
    {1, 2, 5, 6, 8, 9, 10}
     */
    public static void mergeSort(int arr[], int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            // 向左分解
            mergeSort(arr, left, mid, temp);
            // 向右分解
            mergeSort(arr, mid + 1, right, temp);
            // 合并
            merge(arr, left, mid, right, temp);
        }
    }

    /**
     * 并
     * @param arr 原始数组
     * @param left 左边界
     * @param mid 中间索引
     * @param right 右边界
     * @param temp 临时数组
     */
    private static void merge(int[] arr, int left, int mid, int right, int temp[]) {
        int i = left; // 初始化左边有序序列的初始索引
        int j = mid + 1; // 初始化右边有序序列的初始化索引
        int t = 0; // temp 数组当前索引
        // 1.将左右两序列按照规则放到temp
        while (i <= mid && j <= right) {
            // 左边元素小于右边元素，则将左边元素置入temp
            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            } else { // 右边元素小于左边元素，则将右边元素置入temp
                temp[t++] = arr[j++];
            }
        }
        // 2.将剩余的元素放入temp
        while (i <= mid) {
            temp[t++] = arr[i++];
        }
        while (j <= right) {
            temp[t++] = arr[j++];
        }
        // 3.将temp数组拷贝到arr
        t = 0; // 重置temp索引
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft++] = temp[t++];
        }
    }

}
