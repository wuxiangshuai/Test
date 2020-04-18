package com.example.demo;

import java.util.Arrays;

public class DataStructureTest {

    public static void main(String[] args) {
        int arr[] = {8, 6, 2, 10, 9, 5, 1};
        Quick_Sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void Quick_Sort(int arr[], int begin, int end){
        if(begin > end)
            return;
        int tmp = arr[begin];
        int i = begin;
        int j = end;
        while(i != j){
            while(arr[j] >= tmp && j > i)
                j--;
            while(arr[i] <= tmp && j > i)
                i++;
            if(j > i){
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }
        arr[begin] = arr[i];
        arr[i] = tmp;
        Quick_Sort(arr, begin, i-1);
        Quick_Sort(arr, i+1, end);
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left > right) return;
        // 复制left、right的值
        int l = left;
        int r = right;
        int mid = arr[(left + right) / 2];
        while (l < r) {
            while (arr[l] < mid) { // 找到第一个比mid大的值
                l++;
            }
            while (arr[r] > mid) { // 找到第一个比mid小的值
                r--;
            }
            int temp = arr[l]; // 交换
            arr[l] = arr[r];
            arr[r] = temp;
        }
        quickSort(arr, left, r); // 左边元素继续遍历
        quickSort(arr, l, right); // 右边元素继续遍历
    }

    /**
     * @Description: 归并排序
     * @Author: wxs
     * @Date: 2020/4/18 11:03
     * @Param: []
     * @returns: void
     */
    private static void mergeSort() {
        int arr[] = {8, 6, 2, 10, 9, 5, 1};
        int temp[] = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, temp);
        System.out.println(Arrays.toString(arr));
    }

    private static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, temp); // 左归并
            mergeSort(arr, mid + 1, right, temp); // 右归并
            merge(arr, left, mid, right, temp);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;
        int j = mid + 1;
        int t = 0;
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) temp[t++] = arr[i++];
            else temp[t++] = arr[j++];
        }
        while (i <= mid) temp[t++] = arr[i++];
        while (j <= right) temp[t++] = arr[j++];
        for (int k = 0; k < t; k++) {
            arr[left++] = temp[k];
        }
    }

    /***
     * @Description: 插入排序
     * @Author: wxs
     * @Date: 2020/4/18 10:21
     * @Param: []
     * @returns: void
     */
    private static void insertSort() {
        int arr[] = {8, 6, 2, 10, 9, 5, 1};
        int temp = 0;
        for (int i = 1; i < arr.length; i++) {
            int val = arr[i];
            int j = i - 1;
            for (; j >= 0 && arr[j] > val; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = val;
        }
//        for (int i = 0; i < arr.length - 1; i++) {
//            for (int j = i + 1; j < arr.length; j++) {
//                if (arr[i] > arr[j]) {
//                    temp = arr[i];
//                    arr[i] = arr[j];
//                    arr[j] = temp;
//                }
//            }
//        }
        System.out.println(Arrays.toString(arr));
    }

    /***
     * @Description: 冒泡排序
     * @Author: wxs
     * @Date: 2020/4/18 10:05
     * @Param: []
     * @returns: void
     */
    private static void bubbleSort() {
        int arr[] = {8, 6, 2, 10, 9, 5, 1};
        int temp = 0;
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            flag = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) break;
        }
        System.out.println(Arrays.toString(arr));
    }

}
