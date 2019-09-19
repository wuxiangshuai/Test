package dataStructures.demo6;

import java.util.Arrays;

/**
 * @ClassName: RadixSort
 * @Author: WuXiangShuai
 * @Time: 10:11 2019/9/18.
 * @Description: 基数排序
 */
public class RadixSort {

    public static void main(String[] args) {
//        int arr[] = {53, 3, 542, 748, 14, 214};
        int arr[] = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80000);
        }
        System.out.println(System.currentTimeMillis());
        radixSort(arr);
        System.out.println(System.currentTimeMillis());
        System.out.println(Arrays.toString(arr));
    }

    public static void radixSort(int arr[]) {
        if (arr.length == 0) {
            return;
        }
        // 定义二维数组表示10个桶
        int bucket[][] = new int[10][arr.length];
        // 定义一维数组，记录各个桶中的数据个数
        int elementIndex[] = new int[10];
        // 定义数组内最大值
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
        }
        // 获取最大值长度
        int maxLength = String.valueOf(max).length();
        // 根据最大值长度确定循环次数
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            for (int j = 0; j < arr.length; j++) { // 按各个位数放入对应桶中
                int index = i == 0 ? arr[j] % 10 : arr[j] / n % 10;
                bucket[index][elementIndex[index]] = arr[j];
                elementIndex[index]++;
            }
            int arrIndex = 0; // 重置arr索引
            // 按桶从小到大，桶内从小到大取值，覆盖arr原有元素
            for (int j = 0; j < elementIndex.length; j++) {
                for (int k = 0; k < elementIndex[j]; k++) {
                    arr[arrIndex++] = bucket[j][k];
                }
                // 重置各桶内值为0
                elementIndex[j] = 0;
            }
        }
    }

}
