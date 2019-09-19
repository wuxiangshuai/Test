package dataStructures.demo7;

/**
 * @ClassName: SeqSearch
 * @Author: WuXiangShuai
 * @Time: 14:23 2019/9/18.
 * @Description: 线性查找
 */
public class SeqSearch {

    public static void main(String[] args) {
        int arr[] = {1, 9, 11, -1, 34, 89};
        int val = -1;
        int index = seqSearch(arr, val);
        if (-1 != index) {
            System.out.println("index = " + index);
        } else {
            System.out.println("没找到...");
        }
    }

    private static int seqSearch(int[] arr, int val) {
        for (int i = 0; i < arr.length; i++) {
            if (val == arr[i])
                return i;
        }
        return -1;
    }

}
