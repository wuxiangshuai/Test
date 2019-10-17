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
        HashMap<String, Object> map = new HashMap<>();
        map.put("123", "haha");
    }

}

class BinarySortTree {
    private Node root;

    public Node min(Node node) {
        if (node.left != null) {
            return min(node.left);
        }
        return node;
    }

    public void del(int val) {
        if (root == null) return;
        else {
            // 查询要删除的节点
            Node target = search(val);
            if (target == null) {
                System.out.println("未查询到该节点");
                return;
            }
            // 若当前二叉树仅有根节点
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }
            // 发现target的父节点
            Node parent = root.searchParent(val);

            // 若目标节点是叶子节点
            // 比较父节点左、右子节点值，找到与val相等的节点，将其置空
            if (target.left == null && target.right == null) {
                if (parent.left != null && parent.left.value == val) {
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == val) {
                    parent.right = null;
                }
                return;
            }
            // 若目标节点有两颗子树
            else if (target.left != null && target.right != null) {
                // 找到右子树最小节点
                Node min = min(target.right);
                // 删除该节点
                del(min.value);
                // 将目标节点替换成该节点
                target.value = min.value;
            }
            // 若目标节点有一颗子树
            else /*if (target.left == null && target.right != null || target.left != null && target.right == null) */{
                if (parent.left != null && parent.left.value == val) {
                    parent.left = target.left != null ? target.left : target.right;
                } else if (parent.right != null && parent.right.value == val) {
                    parent.right = target.left != null ? target.left : target.right;
                }
            }
        }
    }

    // 查询父节点方法
    public Node searchParent(int val) {
        if (root == null) return null;
        else return root.searchParent(val);
    }

    // 查询方法
    public Node search(int val) {
        if (root == null) return null;
        else return root.search(val);
    }

    public void add(Node node) {
        if (root == null) {
            root = node;
            return;
        }
        root.add(node);
    }

    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空，不能遍历！");
        }
    }
}

class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    public Node searchParent(int val) {
        // 当前节点就是父节点
        if (left != null && left.value == val || right != null && right.value == val) {
            return this;
        }
        // 向左递归查询
        else if (val < value && left != null) {
            return left.searchParent(val);
        }
        // 向右递归查询
        else if (val > value && right != null) {
            return right.searchParent(val);
        }
        // 查询失败
        System.out.println("查询失败，父节点不存在！");
        return null;
    }

    public Node search(int val) {
        // 当前节点就是该节点
        if (value == val) {
            return this;
        }
        // 向左递归查询
        else if (val < value && left != null) {
            return left.search(val);
        }
        // 向右递归查询
        else if (val > value && right != null) {
            return right.search(val);
        }
        // 未找到
        System.out.println("查询失败，节点不存在！");
        return null;
    }

    @Override
    public String toString() {
        Integer left = this.left == null ? null : this.left.value;
        Integer right = this.right == null ? null : this.right.value;
        return "Node{" +
                "value=" + value +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    // 中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    // 添加节点
    public void add(Node node) {
        if (null == node) {
            return;
        }
        // 该节点小于当前节点的值
        if (node.value < value) {
            if (null == left) {
                left = node;
            } else {
                left.add(node);
            }
        }
        // 该节点大于当前节点的值
        else/* if (node.value > value) */ {
            if (null == right) {
                right = node;
            } else {
                right.add(node);
            }
        }
    }

}
