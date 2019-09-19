package dataStructures.demo8;

/**
 * @ClassName: ThreaderBinaryTreeDemo
 * @Author: WuXiangShuai
 * @Time: 16:59 2019/9/19.
 * @Description:
 */
public class ThreaderBinaryTreeDemo {
    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");
        HeroNode node6 = new HeroNode(6, "李逵");
        // 处理关系
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        ThreaderBinaryTree tree = new ThreaderBinaryTree();
        tree.setRoot(root);
        tree.threaderNodes();

        System.out.println(node4.getRight());
        System.out.println(node6.getLeft());
    }
}

class ThreaderBinaryTree {
    // 根节点
    HeroNode root;

    // 为了实现线索化，需要创建指向当前节点的前驱节点的指针
    // 在进行递归线索化时，pre总是保留前一个节点
    HeroNode pre = null;

    public ThreaderBinaryTree setRoot(HeroNode root) {
        this.root = root;
        return this;
    }

    // 重载threaderNodes
    public void threaderNodes() {
        threaderNodes(root);
    }

    /**
     * 编写对二叉树进行线索化的方法
     * @param node 当前需要进行线索化的节点
     */
    public void threaderNodes(HeroNode node) {
        if (null == node) return;
        // 1.先线索化左子树
        threaderNodes(node.getLeft());
        // 2.线索化当前节点
        // 处理当前节点的前驱节点
        if (node.getLeft() == null) {
            // 当前节点左指针指向null，故将其重定向到前驱节点
            node.setLeft(pre);
            // 修改当前节点左指针的类型，1=》指向前驱节点
            node.setLeftType(1);
        }
        // 处理后继节点
        if (pre != null && pre.getRight() == null) {
            // 前驱节点的右指针指向当前节点
            pre.setRight(node);
            pre.setRightType(1);
        }
        // 每处理一个节点后，让当前节点是下一个节点的前驱节点
        pre = node;

        // 3.线索化右子树
        threaderNodes(node.getRight());
    }

    // 前序遍历
    public void pre() {
        if (isNotEmpty()) {
            root.pre();
        }
    }

    // 中序遍历
    public void infix() {
        if (isNotEmpty()) {
            root.infix();
        }
    }

    // 后序遍历
    public void post() {
        if (isNotEmpty()) {
            root.post();
        }
    }

    // 判空
    public boolean isNotEmpty() {
        boolean b = null == root;
        if (b) {
            System.out.println("二叉树为空");
        }
        return !b;
    }

    // 前序查询
    public HeroNode preSearch (int no) {
        HeroNode node = null;
        if (isNotEmpty()) {
            node = root.preSearch(no);
        }
        return node;
    }

    // 中序查询
    public HeroNode infixSearch (int no) {
        HeroNode node = null;
        if (isNotEmpty()) {
            node = root.infixSearch(no);
        }
        return node;
    }

    // 后序查询
    public HeroNode postSearch (int no) {
        HeroNode node = null;
        if (isNotEmpty()) {
            node = root.postSearch(no);
        }
        return node;
    }

    public boolean delCildTree(int no) {
        boolean b = false;
        if (isNotEmpty()) {
            if (no == root.no) {
                root = null;
                b = true;
            } else {
                b = root.delCildTree(no);
            }
        }
        return b;
    }
}
