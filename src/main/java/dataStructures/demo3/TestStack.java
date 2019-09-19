package dataStructures.demo3;

import java.util.Stack;

/**
 * @ClassName: TestStack
 * @Author: WuXiangShuai
 * @Time: 16:20 2019/9/11.
 * @Description:
 */
public class TestStack {

    public static void main(String[] args) {
        Stack stack = new Stack();
        // 入栈
        stack.add("haha");
        stack.add("123");
        stack.add("[];'/");
        // 出栈
        while (stack.size() > 0) {
            System.out.println(stack.pop()); // 取出
        }
    }

}
