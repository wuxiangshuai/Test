package dataStructures.demo10;

/**
 * @ClassName: BinarySortTree
 * @Author: WuXiangShuai
 * @Time: 10:14 2019/10/18.
 * @Description:
 */
public class BinarySortTree {
    private Node root;

    // 获取最小值
    public Node min(Node node) {
        if (node.left != null) {
            return min(node.left);
        }
        return node;
    }

    // 删除节点
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
                    if (parent != null)// 若父节点不存在，即目标元素为根节点
                        parent.left = target.left != null ? target.left : target.right;
                    else root = target.left;
                } else if (parent.right != null && parent.right.value == val) {
                    if (parent != null)
                        parent.right = target.left != null ? target.left : target.right;
                    else root = target.right;
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

    public Node getRoot() {
        return root;
    }
}
