package dataStructures.demo7;

import java.util.Arrays;

/**
 * @ClassName: FibonacciSearch
 * @Author: WuXiangShuai
 * @Time: 16:04 2019/9/18.
 * @Description: 斐波那契查找法
 */
public class FibonacciSearch {

    static int maxSize = 20;

    public static void main(String[] args) {
        int arr[] = {1, 8, 10, 89, 1000, 1234};
        System.out.println(fibonacciSearch(arr, 1234));
    }

    public static int[] fib() {
        int f[] = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    public static int fibonacciSearch (int arr[], int val) {
        int left = 0;
        int right = arr.length - 1;
        int k = 0; // 斐波那契数列索引
        int mid;
        int[] f = fib();
        while (right > f[k++] - 1) {}
        // arr长度不一定等于 f[k]，填充
        int[] temp = Arrays.copyOf(arr, f[k]);
        // 将填充部分都置为arr[high]
        for (int i = right; i < temp.length; i++) {
            temp[i] = arr[right];
        }

        while (left <= right) {
            // 0 - - 0.618 - 1
            // f[k] = f[k - 1] + f[k - 2]，且f[k - 1] > f[k - 2]
            // 故left + f[k - 1] 为本次黄金分割点 = 0.618
            mid = left + f[k - 1] - 1;
            // 找到查询值，返回索引下标
            if (val < temp[mid]) {
                // 此处查询值小于选择的值，下次查询右边界左移
                right = mid - 1;
                // 0 - - 0.618，故下一次黄金分割点在 0.618（f[k - 1]）内寻找
                // f[k - 1] = f[k - 2] + f[k - 3]，f[k - 2] > f[k - 3]
                // 即 left + f[k - 2] 为下一次黄金分割点
                // 此处 -1，下次循环再 f[k - 1]，黄金分割点即变为 left + f[k - 2]
                k--;
            } else if (val > temp[mid]) {
                // 此处查询值大于选择的值，下次查询左边界右移
                left = mid + 1;
                // 0.618 - 1，故下一次黄金分割点在 0.382（f[k - 2]）内寻找
                // f[k - 2] = f[k - 3] + f[k - 4]，f[k - 3] > f[k - 4]
                // 即 left + f[k - 3] 为下一次黄金分割点
                // 此处 -2，下次循环再 f[k - 1]，黄金分割点即变为 left + f[k - 3]
                k -= 2;
            } else {
                if (mid <= right) {
                    return mid;
                } else {
                    return right;
                }
            }
        }
        return -1;
    }

}
