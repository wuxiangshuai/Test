package dataStructures.demo7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: InterpolationSearch
 * @Author: WuXiangShuai
 * @Time: 15:24 2019/9/18.
 * @Description: 插值查找
 * 相较二分查找，修改了mid的算法，让查询值参与到计算中，从而加快接近查询值的速度。
 */
public class InterpolationSearch {

    public static void main(String[] args) {
        int val = 1;
        int arr[] = {1, 1, 24, 56, 81, 101, 101, 101, 101, 512, 1024};
        System.out.println(binarySearch(arr, 0, arr.length - 1, val));

        val = 1024;
        List<Integer> valIndexs = new ArrayList<>();
        binarySearch(arr, 0, arr.length - 1, val, valIndexs);
        Object[] valIndexsArray = valIndexs.toArray();
        Arrays.sort(valIndexsArray);
        System.out.println(Arrays.toString(valIndexsArray));
    }

    /**
     * 插值法查找一个元素的索引
     * @param arr
     * @param left
     * @param right
     * @param val
     * @return
     */
    public static int binarySearch(int arr[], int left, int right, int val) {
        if (left > right || val < arr[0] || val > arr[arr.length - 1]) {
            return -1;
        }
        int mid = left + (right - left) * (val - arr[left]) / (arr[right] - arr[left]);
        int innValue = arr[mid];
        if (val == innValue) {
            return mid;
        } else if (val < innValue) {
            return binarySearch(arr, left, mid - 1, val);
        } else if (val > innValue) {
            return binarySearch(arr, mid + 1, right, val);
        }
        return -1;
    }

    /**
     * 插值法查询元素集内所有与查询值相等值的索引
     * @param arr
     * @param left
     * @param right
     * @param val
     * @return
     */
    public static void binarySearch(int arr[], int left, int right, int val, List<Integer> valIndexs) {
        if (left > right || val < arr[0] || val > arr[arr.length - 1]) {
            return;
        }
        // 自适应
        int mid = left + (right - left) * (val - arr[left]) / (arr[right] - arr[left]);
        if (mid > arr.length) {
            return;
        }
        int innValue = arr[mid];
        if (val == innValue) {
            valIndexs.add(mid);
            binarySearch(arr, left, mid - 1, val, valIndexs);
            binarySearch(arr, mid + 1, right, val, valIndexs);
        } else if (val < innValue) {
            binarySearch(arr, left, mid - 1, val, valIndexs);
        } else if (val > innValue) {
            binarySearch(arr, mid + 1, right, val, valIndexs);
        }
    }

}
