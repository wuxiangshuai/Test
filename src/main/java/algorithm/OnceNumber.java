package algorithm;

/**
 * 只出现一次的数
 * Q:给你一个整型数组,数组中有一个数只出现过一次,其他数都出现了两次,求这个只出现了一次的数。
 * ^ 两者相同为0，不同为1
 * ~ 位取反
 * | 二者含1则为1，不含则为0
 * & 二者都为1则1，都不为则0
 */
public class OnceNumber {
    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5, 1, 2, 3, 4};
        System.out.println(find(arr, 1, arr[0]));
    }
    public static int find(int[] arr,int i, int result){
        return arr.length <= i ? result : find(arr, i + 1, result ^ arr[i]);
    }
}
