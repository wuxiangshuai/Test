package dataStructures.demo9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: HuffmanTree
 * @Author: WuXiangShuai
 * @Time: 18:41 2019/9/23.
 * @Description:
 */
public class HuffmanTree {

    public static void main(String[] args) {
        int arr[] = {13, 7, 8, 3, 29, 6, 1};
        Node root = createHuffmanTree(arr);
        pre(root);
    }

    public static void pre(Node root) {
        if (null != root) {
            root.pre();
        } else {
            System.out.println("空树！");
        }
    }

    private static Node createHuffmanTree(int[] arr) {
        // 为操作方便，将arr数组转化为List<Node> list
        List<Node> nodes = new ArrayList<>();
        for (int i : arr) {
            nodes.add(new Node(i));
        }
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            // 取出根节点权值最小的两个节点
            Node lastest = nodes.get(0);
            Node lastest1 = nodes.get(1);
            Node parent = new Node(lastest.value + lastest1.value);
            parent.left = lastest;
            parent.right = lastest1;
            nodes.remove(lastest);
            nodes.remove(lastest1);
            nodes.add(parent);
        }
        return nodes.get(0);
    }

}

class Node implements Comparable<Node> {
    int value;//节点权
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    // 前序遍历
    public void pre() {
        System.out.println(this);
        if (null != left) {
            left.pre();
        }
        if (null != right) {
            right.pre();
        }
    }
}
