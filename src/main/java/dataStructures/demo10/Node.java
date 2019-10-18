package dataStructures.demo10;

/**
 * @ClassName: Node
 * @Author: WuXiangShuai
 * @Time: 10:14 2019/10/18.
 * @Description:
 */
public class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    // 右旋转 AVL
    public void rightRotate() {
        // 左节点不存在，无法右旋转
        if (left == null) return;
        // 实例化一个新节点，代替当前节点右移
        Node node = new Node(value);
        // 节点的右子树为当前节点的右子树
        node.right = right;
        // 节点的左子树为当前节点的左子树的右子树
        node.left = left.right;
        // node节点右移后，当前节点的值为左子节点的值
        value = left.value;
        // node节点右移后，当前节点的右子树为以 node 为根节点的树
        right = node;
        // node节点右移后，当前节点的左子树为原左子节点的左子树
        left = left.left;
    }

    // 左旋转 AVL
    // 原理：当前节点替换右子节点，而后消灭右子节点
    public void leftRotate() {
        // 右节点不存在，无法左旋转
        if (right == null) return;
        // 实例化一个新节点，代替当前节点左移
        Node node = new Node(value);
        // 节点的右子树为当前节点的右子树的左子树
        node.right = right.left;
        // 节点的左子树为当前节点的左子树
        node.left = left;
        // node节点左移后，当前节点的值为右子节点的值
        value = right.value;
        // node节点左移后，当前节点的右子树为原右子节点的右子树
        right = right.right;
        // node节点左移后，当前节点的左子树为以 node 为根节点的树
        left = node;
    }

    // 获取右节子树的高度 AVL
    public int rightHeight() {
        return right == null ? 0 : right.height();
    }

    // 获取左节子树的高度 AVL
    public int leftHeight() {
        return left == null ? 0 : left.height();
    }

    // 获取以当节点为根节点的树的高度 AVL
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    // 查询父节点
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

    // 查询节点
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

        // 左旋转或右旋转，生成平衡二叉树 AVL
        if (leftHeight() - rightHeight() > 1) {
            // 解决双旋树问题
            // 判断左子树的左右平衡关系
            // 若左子树的右子树高于左子树的左子树
            if (left != null && left.rightHeight() > left.leftHeight()) {
                // 先对左子树进行左旋转
                left.leftRotate();
                // 再进行右旋转
                rightRotate();
            } else
                rightRotate();
        } else if (rightHeight() - leftHeight() > 1) {
            // 若右子树的左子树高于右子树的右子树
            if (right != null && right.leftHeight() > right.rightHeight()) {
                // 先对右子树进行右旋转
                right.rightRotate();
                // 再进行左旋转
                leftRotate();
            } else
                leftRotate();
        }

    }

}
