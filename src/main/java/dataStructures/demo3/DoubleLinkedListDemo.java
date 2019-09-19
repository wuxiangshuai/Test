package dataStructures.demo3;

/**
 * @ClassName: DoubleLinkedListDemo
 * @Author: WuXiangShuai
 * @Time: 17:42 2019/9/11.
 * @Description:
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {
        DoubleNode heroNode1 = new DoubleNode(1, "及时雨", "宋江");
        DoubleNode heroNode2 = new DoubleNode(2, "玉麒麟", "卢俊义");
        DoubleNode heroNode3 = new DoubleNode(3, "智多星", "吴用");
        DoubleNode heroNode5 = new DoubleNode(5, "吴相帅", "1");
        DoubleNode heroNode6 = new DoubleNode(6, "吴相帅", "6");

        DoubleLinkedList list = new DoubleLinkedList();
        list.add(heroNode1);
        list.add(heroNode2);
        list.add(heroNode3);
        list.add(heroNode5);
        list.add(heroNode6);
        list.show();
        DoubleNode heroNode4 = new DoubleNode(4, "豹子头", "林冲");
        list.addByOrder(heroNode4);
        list.show();
        list.delete(5);
        list.show();
    }
    
}

// 定义 DoubleLinkedList
class DoubleLinkedList {
    // 初始化头结点
    public DoubleNode head = new DoubleNode();

    // 添加节点到单向链表
    public void add(DoubleNode node) {
        DoubleNode temp = this.head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node;
        node.pre = temp;
    }

    // 显示链表
    public void show() {
        if (this.head.next == null) {
            return;
        }
        DoubleNode temp = this.head.next;
        while (temp != null) {
            System.out.println(temp.no + " : " + temp.name + " : " + temp.nickname);
            temp = temp.next;
        }
        System.out.println();
    }

    // 按 no 插入
    public void addByOrder(DoubleNode node) {
        DoubleNode temp = this.head;
        while (temp.next != null && temp.next.no < node.no) {
            temp = temp.next;
        }
        if (temp.next.no == node.no) {
            System.out.println("节点已存在！");
            return;
        }
        DoubleNode next = temp.next;
        temp.next = node;   // 前一个节点的 next 为 node
        node.pre = temp;    // node 的 pre 为 temp
        node.next = next;   // node 的 next 为 next
        next.pre = node;    // 后一个节点的 pre 为 node
    }

    // 修改
    public void update(DoubleNode newNode) {
        if (head.next == null) {
            System.out.println("链表为空！");
            return;
        }
        DoubleNode temp = head.next;
        while (temp.next != null && temp.no < newNode.no) {
            temp = temp.next;
        }
        if (temp.no == newNode.no) {
            temp.name = newNode.name;
            temp.nickname = newNode.nickname;
        } else {
            System.out.println("未找到编号为【" + newNode.no + "】的节点");
        }
    }

    // 删除
    public void delete(int no) {
        if (this.head.next == null) {
            System.out.println("链表为空！");
            return;
        }
        DoubleNode temp = this.head;
        while (temp.next != null && temp.next.no < no) {
            temp = temp.next;
        }
        if (temp.next != null && temp.next.no == no) {
            temp.next = temp.next.next;
            temp.next.pre = temp;
        } else {
            System.out.println("未找到编号为【" + no + "】的节点");
        }
    }
}

// 定义 Node 节点
class DoubleNode {
    public int no;
    public String name;
    public String nickname;
    public DoubleNode next;
    public DoubleNode pre;

    public DoubleNode() {
    }

    public DoubleNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "DoubleNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
