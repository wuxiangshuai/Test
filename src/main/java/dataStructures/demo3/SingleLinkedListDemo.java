package dataStructures.demo3;

import java.util.Stack;

/**
 * @ClassName: SingleLinkedListDemo
 * @Author: WuXiangShuai
 * @Time: 14:06 2019/9/11.
 * @Description: 单选链表
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        HeroNode heroNode1 = new HeroNode(1, "及时雨", "宋江");
        HeroNode heroNode2 = new HeroNode(2, "玉麒麟", "卢俊义");
        HeroNode heroNode3 = new HeroNode(3, "智多星", "吴用");
        HeroNode heroNode5 = new HeroNode(5, "吴相帅", "1");
        HeroNode heroNode6 = new HeroNode(6, "吴相帅", "6");

        SingleLinkedList list = new SingleLinkedList();
        list.add(heroNode1);
        list.add(heroNode2);
        list.add(heroNode3);
        list.add(heroNode5);
        list.add(heroNode6);
//        list.show();
//        System.out.println();
//        HeroNode heroNode4 = new HeroNode(4, "豹子头", "林冲");
//        list.addByOrder(heroNode4);
//        list.show();
//        list.addByOrder(heroNode4);
//        list.update(new HeroNode(5, "吴相帅嘿嘿", "666"));
//        list.show();
//        list.delete(5);
//        list.show();
//        list.delete(5);
//        reverseList(list);
        reversePrint(list);
    }

    // 腾讯面试题：反转链表
    public static void reverseList(SingleLinkedList list) {
        HeroNode temp = list.head.next; // 接收链表第一个节点
        HeroNode node = null;           // 中间变量，代替反转后的前一个变量，即原链表的后有个变量
        HeroNode innTemp = null;        // 中间变量，代替反转后的下一个变量，即原链表的前一个变量
        // temp1 temp2 temp3 temp4......
        // node = temp1, innTemp = null  -> temp1.next = null
        // node = temp2, innTemp = temp1 -> temp2.next = temp1
        // node = temp3, innTemp = temp2 -> temp3.next = temp2
        // node = temp4, innTemp = temp3 -> temp4.next = temp3
        while (temp != null) {
            node = temp;        // temp1, temp2, temp3, temp4...
            temp = temp.next;
            node.next = innTemp;// null,  temp1, temp2, temp3...
                                // temp1.next = null,  temp2.next = temp1, temp3.next = temp2, temp4.next = temp3...
            innTemp = node;     // temp1, temp2, temp3, temp4...
        }
        list.head.next = node;
        list.show();
    }

    // 百度面试题：逆序打印单向链表
    public static void reversePrint(SingleLinkedList list) {
        HeroNode head = list.head; // 接收链表第一个节点
        if (head.next == null) {
            System.out.println("链表为空！");
            return;
        }
        Stack stack = new Stack();
        HeroNode temp = head.next;
        while (temp != null) {
            stack.add(temp);
            temp = temp.next;
        }
        while (stack.size() > 0) {
            HeroNode pop = (HeroNode) stack.pop();
            System.out.println(pop.no);
        }
    }

}

// 定义 SingleLinkedList
class SingleLinkedList {
    // 初始化头结点
    public HeroNode head = new HeroNode();

    // 添加节点到单向链表
    public void add(HeroNode node) {
        HeroNode temp = this.head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node;
    }

    // 显示链表
    public void show() {
        if (this.head.next == null) {
            return;
        }
        HeroNode temp = this.head.next;
        while (temp != null) {
            System.out.println(temp.no + " : " + temp.name + " : " + temp.nickname);
            temp = temp.next;
        }
    }

    // 按 no 插入
    public void addByOrder(HeroNode node) {
        HeroNode temp = this.head;
        while (temp.next != null && temp.next.no < node.no) {
            temp = temp.next;
        }
        if (temp.next.no == node.no) {
            System.out.println("节点已存在！");
            return;
        }
        HeroNode next = temp.next;
        temp.next = node;
        node.next = next;
    }

    // 修改
    public void update(HeroNode newNode) {
        if (head.next == null) {
            System.out.println("链表为空！");
            return;
        }
        HeroNode temp = head.next;
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
        HeroNode temp = this.head;
        while (temp.next != null && temp.next.no < no) {
            temp = temp.next;
        }
        if (temp.next != null && temp.next.no == no) {
            temp.next = temp.next.next;
        } else {
            System.out.println("未找到编号为【" + no + "】的节点");
        }
    }
}

// 定义 Node 节点
class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next;

    public HeroNode() {
    }

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "SingleNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", next='" + next + '\'' +
                '}';
    }
}
