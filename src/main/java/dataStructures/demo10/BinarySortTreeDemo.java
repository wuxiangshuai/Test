package dataStructures.demo10;

import java.util.HashMap;

/**
 * @ClassName: BinarySortTreeDemo
 * @Author: WuXiangShuai
 * @Time: 15:41 2019/10/10.
 * @Description:
 */
public class BinarySortTreeDemo {

    public static void main(String[] args) {
        int arr[] = {6, 51, 125, 63, 123, 656, 24, 1, 51};
//        int arr[] = {7, 3, 10, 12, 5, 1, 9};
        BinarySortTree tree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            Node node = new Node(arr[i]);
            tree.add(node);
        }
        tree.del(125);
        tree.infixOrder();
    }

}
