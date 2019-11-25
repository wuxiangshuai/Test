package dataStructures.demo7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @ClassName: BinarySearch
 * @Author: WuXiangShuai
 * @Time: 14:30 2019/9/18.
 * @Description: 二分查找
 */
public class BinarySearch {

    public static void main(String[] args) {
        int arr[] = {1, 1, 24, 56, 81, 101, 101, 101, 101, 512, 1024};
        int val = 512;
        int left = 0;
        int right = arr.length - 1;
        int mid = -1;
        while (left < right) {
            mid = (left + right) / 2;
            if (val == arr[mid]) {
                break;
            } else if (val < arr[mid]) {
                right = mid - 1;
            } else if (val > arr[mid]) {
                left = mid + 1;
            }
        }
        System.out.println(mid);

        System.out.println(binarySearch(arr, 0, arr.length - 1, 512));

        List<Integer> valIndexs = new ArrayList<>();
        binarySearch(arr, 0, arr.length - 1, 101, valIndexs);
        Object[] valIndexsArray = valIndexs.toArray();
        Arrays.sort(valIndexsArray);
        System.out.println(Arrays.toString(valIndexsArray));
    }

    /**
     * 二分法查找一个元素的索引
     * 递归方式
     * @param arr
     * @param left
     * @param right
     * @param val
     * @return
     */
    public static int binarySearch(int arr[], int left, int right, int val) {
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
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
     * 二分法查询元素集内所有与查询值相等值的索引
     * 递归方式
     * @param arr
     * @param left
     * @param right
     * @param val
     * @param valIndexs
     */
    public static void binarySearch(int arr[], int left, int right, int val, List<Integer> valIndexs) {
        if (left > right) {
            return;
        }
        int mid = (left + right) / 2;
        int innValue = arr[mid];
        if (val == innValue) {
            valIndexs.add(mid);
            int temp = mid;
//            binarySearch(arr, left, mid - 1, val, valIndexs);
//            binarySearch(arr, mid + 1, right, val, valIndexs);
            while (val == arr[--temp]) {
                valIndexs.add(temp);
            }
            temp = mid;
            while (val == arr[++temp]) {
                valIndexs.add(temp);
            }
        } else if (val < innValue) {
            binarySearch(arr, left, mid - 1, val, valIndexs);
        } else if (val > innValue) {
            binarySearch(arr, mid + 1, right, val, valIndexs);
        }
    }

}
