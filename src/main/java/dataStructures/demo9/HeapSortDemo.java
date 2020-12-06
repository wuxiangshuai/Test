package dataStructures.demo9;

import java.util.Arrays;

/**
 * @ClassName: HeapSortDemo
 * @Author: WuXiangShuai
 * @Time: 9:21 2019/9/23.
 * @Description: 堆排序
 */
public class HeapSortDemo {

    public static void main(String[] args) {
//        int arr[] = {4, 6, 8, 5, 9};
        int arr[] = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random()*80000);
        }
        System.out.println(System.currentTimeMillis());
        heapSort(arr);
        System.out.println(System.currentTimeMillis());
    }

    // 堆排序方法
    private static void heapSort(int[] arr) {
        // 从下至上，从左向右排序
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }
//        System.out.println(Arrays.toString(arr));

        int temp;
        for (int i = arr.length - 1; i > 0; i--) {
            temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            adjustHeap(arr, 0, i);
        }

        System.out.println(Arrays.toString(arr));
    }

    /**
     * 将数组调整成顶堆，大顶堆或小顶堆依据需求而定，大顶堆对应由大致小，小顶堆对应由小及大
     * 功能：将以 i 对应的非叶子节点的数调整成对应顶堆
     * @param arr 待调整的数组
     * @param i 非叶子节点在数组中的索引
     * @param length 对多少个元素进行调整
     */
    private static void adjustHeap(int[] arr, int i, int length) {
        int temp = arr[i];
        // j 为左子结点的下标
        // 注意：因为数组以 0 为起始坐标，需要 + 1，若空出 0 ，=2i 即可
        for (int j = 2 * i + 1; j < length; j= j * 2 + 1) {
            // 判断左右子结点大小，选中较大的结点
            if (j + 1 < length && arr[j] < arr[j + 1]) {
                j ++;
            }
            // 若子节点大于根节点
            if (arr[j] > temp) {
                arr[i] = arr[j]; // 交换值
                // 沿着交换的方向继续向下判断，直到该子树满足顶堆要求
                i = j;
            } else {
                break;
            }
        }
        arr[i] = temp;
    }

}
