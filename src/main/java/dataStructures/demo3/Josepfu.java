package dataStructures.demo3;

/**
 * @ClassName: Josepfu
 * @Author: WuXiangShuai
 * @Time: 11:54 2019/9/12.
 * @Description: 解决约瑟夫问题，使用单向环形链表。
 * 约瑟夫问题：n个人，从第m个人开始，依次报数，数到x，该人退出，其他人重新开始进行报数，直到所有人都退出。
 */
public class Josepfu {

    public static void main(String[] args) {
        CircleSingleLinkedList list = new CircleSingleLinkedList();
        list.addBoys(1);
        list.showBoy();
        list.out(4);
    }

}

class CircleSingleLinkedList {
    private Boy first = null;

    public void addBoys(int nums) {
        if (-1 >= nums) {
            System.out.println("输入值错误！");
            return;
        }
        Boy curBoy = new Boy(1);
        first = curBoy;
        curBoy.setNext(curBoy);
        for (int i = 2; i <= nums; i++) {
            Boy boy = new Boy(i);
            boy.setNext(first);
            curBoy.setNext(boy);
            curBoy = boy;
        }
    }

    public void showBoy() {
        if (null == first) {
            System.out.println("当前链表为空！");
            return;
        }
        Boy boy = this.first;
        do {
            System.out.println(boy.getNo());
            boy = boy.getNext();
        } while (boy.getNo() != first.getNo());
    }

    public void out(int num) {
        if (num <= 0) {
            System.out.println("输入值不正确！");
            return;
        }
        Boy boy = this.first; // boy 记录中间变量
        int i = 1;
        do {
            if (i == num - 1) {
                Boy next = boy.getNext(); // boy 的下一个节点进行出圈操作
                System.out.println(next.getNo() + " 出圈");
                boy.setNext(next.getNext()); // boy 的下一个节点指向 下下个节点
                i = 0; // 重启计数
            }
            i++;
            boy = boy.getNext();
        } while (boy.getNext().getNo() != boy.getNo()); // 当圈内仅剩一个时退出循环
        System.out.println(boy.getNo() + " 最后出圈");
    }
}

class Boy {
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public Boy setNo(int no) {
        this.no = no;
        return this;
    }

    public Boy getNext() {
        return next;
    }

    public Boy setNext(Boy next) {
        this.next = next;
        return this;
    }
}