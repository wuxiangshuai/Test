package dataStructures.demo4;

/**
 * @ClassName: Calculator
 * @Author: WuXiangShuai
 * @Time: 15:33 2019/9/12.
 * @Description: 使用栈计算中缀表达式
 */
public class Calculator {
    public static void main(String[] args) {
        String expression = "700+2*6-2";
        CalculatorStack numStack = new CalculatorStack(10);
        CalculatorStack operStack = new CalculatorStack(10);
        int index = 0; // 用于扫描的索引
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' ';
        char[] chars = expression.toCharArray();
        while (index < chars.length) {
            ch = chars[index];
            // 如果是操作符
            if (operStack.isOper(ch)) {
                // 操作符栈空间不为空时
                if (!operStack.isEmpty()) {
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);
                        // res 入栈
                        numStack.push(res);
                        // oper 入栈
                        operStack.push(ch);
                    } else {
                        operStack.push(ch);
                    }
                } else {
                    operStack.push(ch);
                }
            }
            // 如果是数组
            else {
                int val = ch - 48;
                if (index == chars.length - 1) {
                    numStack.push(val);
                }
                else if (numStack.isEmpty()) {
                    numStack.push(val);
                }
                else {
                    if (!operStack.isOper(chars[index - 1])) {
                        val = numStack.pop() * 10 + val;
                        numStack.push(val);
                    } else {
                        numStack.push(val);
                    }
                }
            }
            index++;
        }
        // 扫描完毕，进行计算
        while (!operStack.isEmpty()) {
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            numStack.push(res);
        }
        System.out.println("计算结果为：" + numStack.pop());
    }
}


class CalculatorStack {
    private int maxSize;
    private int[] stack; // 使用数组模拟栈
    private int top = -1;

    public CalculatorStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    // 展示栈顶
    public int peek() {
        return stack[top];
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

    // 运算符优先级，值越大越高
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;
        }
    }

    // 判断是否是运算符
    public boolean isOper(int oper) {
        return oper == '+' || oper == '-' || oper == '*' || oper == '/';
    }

    // 计算方法
    public int cal(int num1, int num2, int oper) {
        int res = 0; // 存放计算结果
        switch (oper) {
            case '+':
                res = num2 + num1;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num2 * num1;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }
}