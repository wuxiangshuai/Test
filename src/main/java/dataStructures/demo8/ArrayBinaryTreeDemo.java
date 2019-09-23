package dataStructures.demo8;

/**
 * @ClassName: ArrayBinaryTreeDemo
 * @Author: WuXiangShuai
 * @Time: 16:25 2019/9/19.
 * @Description: 顺序二叉树
 */
public class ArrayBinaryTreeDemo {

    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5, 6, 7};
        ArrayBinaryTree tree = new ArrayBinaryTree(arr);
        tree.pre(0);
    }

}
// 编写一个顺序二叉树
class ArrayBinaryTree {
    int arr[];
    int arrLength;

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
        this.arrLength = arr.length;
    }

    // 完成顺序存储二叉树的前序遍历
    public void pre(int index) {
        if (isNotEmpty() && index >= 0) {
            System.out.print(arr[index] + " ");
            int leftIndex = index * 2 + 1;
            if (leftIndex < arrLength) {
                pre(leftIndex);
            }
            int rightIndex = index * 2 + 2;
            if (rightIndex < arrLength) {
                pre(rightIndex);
            }
        }
    }

    public boolean isNotEmpty() {
        boolean b = arr == null || 0 == arr.length;
        if (b) {
            System.out.println("数组为空");
        }
        return !b;
    }
}
