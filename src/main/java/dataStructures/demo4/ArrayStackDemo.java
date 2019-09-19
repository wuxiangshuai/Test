package dataStructures.demo4;

import java.util.Scanner;

/**
 * @ClassName: ArrayStackDemo
 * @Author: WuXiangShuai
 * @Time: 14:58 2019/9/12.
 * @Description:
 */
public class ArrayStackDemo {

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(4);
        String key = "";
        boolean loop = true; // 是否退出菜单
        Scanner sc = new Scanner(System.in);
        while (loop) {
            System.out.println("show展示，exit退出，push入栈，pop出栈");
            key = sc.next();
            switch (key) {
                case "show":
                    stack.show();
                    break;
                case "push":
                    System.out.println("请输入:");
                    int val = sc.nextInt();
                    stack.push(val);
                    break;
                case "pop":
                    int pop = stack.pop();
                    System.out.println("出栈数据为:" + pop);
                    break;
                case "exit":
                    sc.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出...");
    }

}

class ArrayStack {
    private int maxSize;
    private int[] stack; // 使用数组模拟栈
    private int top = -1;

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    // 判断是否栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    // 判断是否栈空
    public boolean isEmpty() {
        return top == -1;
    }

    // 入栈 push
    public void push(int val) {
        if (isFull()) {
            System.out.println("栈满！");
            return;
        }
        top++;
        stack[top] = val;
    }

    // 出栈 pop
    public int pop() {
        if (isEmpty()) {
            System.out.println("栈空！");
            return -1;
        }
        return stack[top--];
    }

    // 显示栈
    public void show() {
        if (isEmpty()) {
            System.out.println("栈空！");
            return;
        }
        int num = top;
        while (num > -1) {
            System.out.println(stack[num--]);
        }
    }
}
