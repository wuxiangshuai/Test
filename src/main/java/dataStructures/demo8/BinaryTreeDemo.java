package dataStructures.demo8;

/**
 * @ClassName: BinaryTreeDemo
 * @Author: WuXiangShuai
 * @Time: 14:19 2019/9/19.
 * @Description: 二叉树
 */
public class BinaryTreeDemo {

    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.setRoot(root);
        System.out.println("前序遍历");
        binaryTree.pre(); // 12354
        System.out.println("中序遍历");
        binaryTree.infix(); // 21534
        System.out.println("后序遍历");
        binaryTree.post(); // 25431
        System.out.println("前序查找");
        System.out.println(binaryTree.preSearch(6));
        System.out.println("中序查找");
        System.out.println(binaryTree.infixSearch(6));
        System.out.println("后序查找");
        System.out.println(binaryTree.postSearch(6));
        System.out.println("测试删除");
        System.out.println(binaryTree.delCildTree(3));
        binaryTree.pre();
    }

}

// 定义二叉树
class BinaryTree {
    HeroNode root;

    public BinaryTree setRoot(HeroNode root) {
        this.root = root;
        return this;
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

// 定义节点
class HeroNode {
    int no;
    String name;
    HeroNode left;
    HeroNode right;

    // leftType = 0，表示左指针指向左子树；leftType = 1，表示指向前驱节点
    int leftType;
    // rightType = 0，表示右指针指向右子树；rightType = 1，表示指向后继节点
    int rightType;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public HeroNode setNo(int no) {
        this.no = no;
        return this;
    }

    public String getName() {
        return name;
    }

    public HeroNode setName(String name) {
        this.name = name;
        return this;
    }

    public HeroNode getLeft() {
        return left;
    }

    public HeroNode setLeft(HeroNode left) {
        this.left = left;
        return this;
    }

    public HeroNode getRight() {
        return right;
    }

    public HeroNode setRight(HeroNode right) {
        this.right = right;
        return this;
    }

    public int getLeftType() {
        return leftType;
    }

    public HeroNode setLeftType(int leftType) {
        this.leftType = leftType;
        return this;
    }

    public int getRightType() {
        return rightType;
    }

    public HeroNode setRightType(int rightType) {
        this.rightType = rightType;
        return this;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    // 前序遍历
    public void pre() {
        // 1.先输出父节点
        System.out.println(this);
        // 2.向左子树前序遍历
        if (null != this.left) {
            left.pre();
        }
        // 3.向右子树前序遍历
        if (null != this.right) {
            right.pre();
        }
    }

    // 中序遍历
    public void infix() {
        // 1.向左子树中序遍历
        if (null != left) {
            left.infix();
        }
        // 2.输出父节点
        System.out.println(this);
        // 3.向右子树中序遍历
        if (null != right) {
            right.infix();
        }
    }

    // 后序遍历
    public void post() {
        // 1.向左子树后序遍历
        if (null != left) {
            left.post();
        }
        // 2.向右子树后序遍历
        if (null != right) {
            right.post();
        }
        // 3.输出父节点
        System.out.println(this);
    }

    // 前序查找
    public HeroNode preSearch (int no) {
        if (this.no == no) {
            return this;
        }
        if (null != left) {
            HeroNode leftNode = left.preSearch(no);
            if (null != leftNode) {
                return leftNode;
            }
        }
        if (null != right) {
            HeroNode rightNode = right.preSearch(no);
            if (null != rightNode) {
                return rightNode;
            }
        }
        return null;
    }

    // 中序查找
    public HeroNode infixSearch (int no) {
        if (null != left) {
            HeroNode leftNode = left.infixSearch(no);
            if (null != leftNode) {
                return leftNode;
            }
        }
        if (this.no == no) {
            return this;
        }
        if (null != right) {
            HeroNode rightNode = right.infixSearch(no);
            if (null != rightNode) {
                return rightNode;
            }
        }
        return null;
    }

    // 后序查找
    public HeroNode postSearch (int no) {
        if (null != left) {
            HeroNode leftNode = left.postSearch(no);
            if (null != leftNode) {
                return leftNode;
            }
        }
        if (null != right) {
            HeroNode rightNode = right.postSearch(no);
            if (null != rightNode) {
                return rightNode;
            }
        }
        if (this.no == no) {
            return this;
        }
        return null;
    }

    public boolean delCildTree(int no) {
        boolean b = false;
        if (null != left) {
            if (no == left.no) {
                left = null;
                b = true;
            } else {
                b = left.delCildTree(no);
            }
        }
        if (!b && null != right) {
            if (no == right.no) {
                right = null;
                b = true;
            } else {
                b = right.delCildTree(no);
            }
        }
        return b;
    }

}
