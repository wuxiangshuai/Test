package test;

import java.io.*;

/**
 * @ClassName: Test
 * @Author: WuXiangShuai
 * @Time: 18:42 2019/4/24.
 * @Description:
 */
public class Test {
    public static void main(String args[]) throws Exception {
        Object obj = new Object();
        Test test = new Test();
        String str = new String("haha");
        System.out.println(obj.getClass().getClassLoader());
        System.out.println(test.getClass().getClassLoader());
        System.out.println(test.getClass().getClassLoader().getParent());
        System.out.println(test.getClass().getClassLoader().getParent().getParent());
    }
}