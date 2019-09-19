package eight.java.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @Author: WuXiangShuai
 * @Time: 13:43 2019/4/13.
 * @Description:
 */
@SuppressWarnings("unused")
public class Test {

    public static void main(String[] args) {
        //Lambda
        //调用接口方法
        Ser ser = msg -> System.out.println(msg);
        ser.say("haha");
        //匿名内部类
        TreeSet<Integer> set = new TreeSet<Integer>((a, b) -> a > b ? a : b);
        for (int i = 0; i < 100; i++) set.add(i);
        //set.stream().forEach(i -> i = Integer.parseInt(Math.random()*100 + ""));
        //for (Integer i : set) System.out.println(i);
        set.stream().forEach(System.out::println);
        //List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());

        //测速
        //testSpeed();

        //函数式接口，例：Predicate<T>，匿名实现其 test() 方法
        eval(set, n -> true);
        eval(set, n -> n%2==0);

        //Stream流操作
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 4, 3, 2, 1, 5);
        stream.skip(3).forEach(System.out::println);


    }

    /**
     * Predicate <T> 接口是一个函数式接口，它接受一个输入参数 T，返回一个布尔值结果。
     * 该接口包含多种默认方法来将Predicate组合成其他复杂的逻辑（比如：与，或，非）
     * 该接口用于测试对象是 true 或 false
     * @param set
     * @param pre
     */
    public static void eval(TreeSet<Integer> set, Predicate<Integer> pre){
        set.stream().forEach(i -> System.out.println(pre.test(i)));
    }

    /**
     * 测试 list.foreach() 与 list.stream.foreach() 谁的遍历速度更快
     * 结论：stream 流的速度更快，近一倍
     */
    private static void testSpeed() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) list.add(i);
        long l1 = System.currentTimeMillis();
        list.forEach(System.out::print);
        long l2 = System.currentTimeMillis();
        list.stream().forEach(System.out::print);
        long l3 = System.currentTimeMillis();
        System.out.println("\n"+(l2 - l1));
        System.out.println(l3 - l2);
    }

    interface Ser{
        void say(String msg);
    }

}
