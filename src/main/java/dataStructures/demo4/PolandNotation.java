package dataStructures.demo4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @ClassName: PolandNotation
 * @Author: WuXiangShuai
 * @Time: 9:42 2019/9/16.
 * @Description: 逆波兰表达式计算器
 */
public class PolandNotation {

    public static void main(String[] args) {
        // 中缀表达式转后缀表达式
        // 1+((2+3)*4)-5 =>
        String expression = "1+((2+3)*4)-5";
        List<String> list = toInfixExpressionList(expression);
        System.out.println(list);
        // 中缀转后缀
        List<String> exp = parseSuffixExpression(list);
        System.out.println(exp);
        int res1 = calculate(exp);
        System.out.println(res1);
        // (3+4)*5-6 => 3 4 + 5 * 6 - => 29
        // 4*5-8+60+8/2 => 4 5 * 8 - 60 + 8 2 / +
        // 为方便，逆波兰表达式使用空格隔开
//        String suffixExpression = "3 4 + 5 * 6 -";
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        List<String> rpnList = getListByString(suffixExpression); // 将表达式改变为集合
        int res = calculate(rpnList);
        System.out.println(res);
    }

    private static List<String> parseSuffixExpression(List<String> ls) {
        // 符号栈
        Stack<String> s1 = new Stack<>();
        // 表达式栈，该栈在当前方法无pop操作，可用List代替
//        Stack<String> s2 = new Stack<>();
        // 后缀表达式集合
        ArrayList<String> s2 = new ArrayList<>();
        for (String str: ls) {
            if (str.matches("\\d+")) {
                s2.add(str);
            } else {
                if ("(".equals(str)) {
                    s1.push(str);
                } else if (")".equals(str)) {
                    // 将s1中的符号依次弹出并压入s2，直到(为止
                    while (!s1.peek().equals("(")) {
                        s2.add(s1.pop());
                    }
                    // 将s1中的(弹出，即消除小括号
                    s1.pop();
                } else {
                    // 当str的优先级小于等于栈顶运算符的优先级时，将s1栈顶的运算符弹出加入到s2，后再与s1中新的栈顶进行比对
                    while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(str)) {
                        s2.add(s1.pop());
                    }
                    // 将str压入s1
                    s1.push(str);
                }
            }
        }
        // 将s1中剩余的运算符压入s2
        while (s1.size() > 0) {
            s2.add(s1.pop());
        }
        return s2;
    }

    private static List<String> toInfixExpressionList(String expression) {
        if (expression.length() == 0) {
            return null;
        }
        char[] chars = expression.toCharArray();
        ArrayList<String> list = new ArrayList<>();
        list.add(String.valueOf(chars[0]));
        String num = "";
        for (int i = 1; i < chars.length; i++) {
            String c = String.valueOf(chars[i]);
            if (c.matches("\\d")) {
                num += c;
                list.add(num);
            } else {
                list.add(c);
                num = "";
            }
        }
        return list;
    }

    private static List<String> getListByString(String suffixExpression) {
        String[] split = suffixExpression.split(" ");
        return Arrays.asList(split);
    }

    public static int calculate(List<String> ls) {
        Stack stack = new Stack();
        for (String str : ls) {
            // 匹配多位数
            if (str.matches("\\d+")) {
                stack.push(str);
            }
            // 若当前str为符号，取出两个数，进行计算后，再入栈
            else {
                int num1 = Integer.parseInt(String.valueOf(stack.pop()));
                int num2 = Integer.parseInt(String.valueOf(stack.pop()));
                int res = 0;
                if ("+".equals(str)) {
                    res = num2 + num1;
                } else if ("-".equals(str)) {
                    res = num2 - num1;
                } else if ("*".equals(str)) {
                    res = num2 * num1;
                } else if ("/".equals(str)) {
                    res = num2 / num1;
                } else {
                    throw new RuntimeException("运算符有误！");
                }
                stack.push(res);
            }
        }
        return Integer.parseInt(String.valueOf(stack.pop()));
    }

}

class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int Mul = 2;
    private static int DIV = 2;

    public static int getValue(String operation) {
        int res = 0;
        switch (operation) {
            case "+":
                res = ADD;
                break;
            case "-":
                res = SUB;
                break;
            case "*":
                res = Mul;
                break;
            case "/":
                res = DIV;
                break;
            default:
                System.out.println("不存在该运算符");
                break;
        }
        return res;
    }
}