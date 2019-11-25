package dataStructures.demo12_algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: BiranrySearch
 * @Author: WuXiangShuai
 * @Time: 13:51 2019/11/25.
 * @Description: 非递归方式实现二分查找
 */
public class BinarySearch {

    public static void main(String[] args) {
        int arr[] = {7, 123, 4, 76, 1235, 745345, 1, 23, 36, 36};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        int i = binarySearch(arr, 1);
        System.out.println(i);
        ArrayList<Integer> indexs = new ArrayList<>();
        binarySearch(arr, 36, indexs);
        indexs.sort((v1, v2) -> v1 - v2);
        indexs.forEach(System.out::println);
    }

    /**
     * 二分查找
     * 非递归
     * @param arr 目标数组
     * @param target 目标值
     * @param target 索引存储集合
     */
    public static void binarySearch(int arr[], int target, List<Integer> indexs) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int midVal = arr[mid];
            if (target == midVal) {
                int index = mid;
                indexs.add(mid);
                // 向上查询
                while (target == arr[++index]) {
                    indexs.add(index);
                }
                // 向下查询
                while (target == arr[--mid]) {
                    indexs.add(mid);
                }
                break;
            } else if (target > midVal) {
                left = mid + 1;
            } else if (target < midVal) {
                right = mid - 1;
            }
        }
    }

    /**
     * 二分查找
     * 非递归
     * @param arr 目标数组
     * @param target 目标值
     * @return 返回对应下标，-1 说明不存在
     */
    public static int binarySearch(int arr[], int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int midVal = arr[mid];
            if (target == midVal) {
                return mid;
            } else if (target > midVal) {
                left = mid + 1;
            } else if (target < midVal) {
                right = mid - 1;
            }
        }
        return -1;
    }

}
