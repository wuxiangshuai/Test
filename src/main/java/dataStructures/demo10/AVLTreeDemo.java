package dataStructures.demo10;

/**
 * @ClassName: AVLTreeDemo
 * @Author: WuXiangShuai
 * @Time: 18:51 2019/10/17.
 * @Description:
 */
public class AVLTreeDemo {

    public static void main(String[] args) {
//        int arr[] = {6, 51, 125, 63, 123, 656, 24, 1, 51};
//        int arr[] = {4, 3, 5, 6, 7, 8, 9}; // 左旋树
//        int arr[] = {10, 12, 8, 9, 7, 6, 5}; // 右旋树
        int arr[] = {10, 7, 6, 8, 9, 11}; // 双旋树
        BinarySortTree tree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            Node node = new Node(arr[i]);
            tree.add(node);
        }
//        tree.del(125);
        tree.infixOrder();

        System.out.println(tree.getRoot().height());
        System.out.println(tree.getRoot().left.height());
        System.out.println(tree.getRoot().right.height());
    }

}
