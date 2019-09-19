package eight.java.Stream;

import eight.java.bean.Nav;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * @Author: WuXiangShuai
 * @Time: 20:00 2019/4/16.
 * @Description:
 */
@SuppressWarnings("unused")
public class StreamTest {

    static String jj = "My name is wuxiangshuai and I come from JiNan .";
    static List<String> list = Arrays.asList("java", "python", "C++","php","java");
    static int[] nums = {9, 123, 51, 1, 23, 53, 15, 13, 57};

    public static void main(String[] args) {
//        bet1();
//        bet2();
//        end1();
//        inertia();
//        createStream();

    }

    /**
     * 创建流
     */
	private static void createStream() {
        //创建流的两种方式
//        list.stream();
//        list.parallelStream();

        //数组转流
//        Arrays.stream(nums);

        //创建数字流
//        IntStream.of(nums);
//        IntStream range = IntStream.range(1, 100);// before 1, before 100，即 1 ~ 99
//        IntStream range = IntStream.rangeClosed(1, 100);// before 1, after 100，即 1 ~ 100
//        range.forEach(System.out::println);

        //Random实现无限流
//        IntStream limit = new Random().ints().limit(100);
//        limit.forEach(System.out::println);

        //自产流
        Random random = new Random();
        Stream<Integer> limit = Stream.generate(random::nextInt).limit(20);
        limit.forEach(System.out::println);
    }

    /**
     * 惰性求值，即若没有终止操作，中间操作不执行
     */
    private static void inertia() {
        Stream<String> stream = Stream.of(jj.split(" "));
//        stream.map(StreamTest::testInertia);
        stream.map(StreamTest::testInertia).forEach(i -> System.out.println(Character.toChars(i)));
    }
    public static Integer testInertia(String obj){
        System.out.println("中间操作："+obj);
        return obj.charAt(0)+1;
    }

    /**
     * 每个操作结束，Stream被消耗，无法继续使用
     */
    private static void end1() {
        Stream<String> stream = Stream.of(jj.split(" "));
        //两者都用于排序，主要区别在并行处理上，forEach是并行处理的，forEachOrder是按顺序处理的，显然前者速度更快。
//        jj.chars().parallel().forEach(i -> System.out.print((char)i));
//        jj.chars().parallel().forEachOrdered(i -> System.out.print((char)i));

        //collect，转化为集合
//        List<String> list = stream.collect(Collectors.toList());
        //自定义实现Collection的数据结构收集
//        List<String> list = stream.collect(Collectors.toCollection((Supplier<List<String>>) ArrayList::new));
//        List<String> list = stream.collect(Collectors.toCollection(LinkedList::new));
//        System.out.println(list);

        //toArray，转换为数组
//        Object[] arr = stream.toArray();
//        System.out.println(Arrays.toString(arr));

        //reduce，拼接流元素
//        Optional<String> reduce = stream.reduce((s1, s2) -> s1 + " | " + s2);
//        System.out.println(reduce.get());//若数据为空，会报异常
//        System.out.println(reduce.orElse("数据为空"));
        //初始化的reduce拼接流元素
//        String reduce = stream.reduce("", (s1, s2) -> s1 + " | " + s2);
//        System.out.println(reduce);
        Integer reduce1 = stream.map(i -> i.length()).reduce(0, (s1, s2) -> s1 + s2);
        System.out.println("所有流单词的长度为："+reduce1);

        //max，min，count，sum
//        Optional<Integer> max = stream.map(i -> i.length()).max((i1, i2) -> i1 - i2);
//        System.out.println(max.orElse(0));
//        Optional<Integer> min = stream.map(i -> i.length()).min((i1, i2) -> i1 - i2);
//        System.out.println(min.orElse(0));
//        long count = stream.map(i -> i.length()).count();
//        System.out.println(count);
//        int sum = IntStream.of(9, 123, 51, 1, 23, 53, 15, 13, 57).sum();
//        System.out.println(sum);

        //findFirst
//        Optional<String> first = stream.findFirst();
//        System.out.println(first.orElse("-"));

        //sorted，排序
//        List<String> collect = stream.sorted().collect(Collectors.toList());
//        List<String> collect = stream.sorted((i, k) -> i.charAt(0) - k.charAt(0)).collect(Collectors.toList());
//        System.out.println(collect);

        //对Stream的字符串拼接
//        String collect = stream.collect(Collectors.joining());
//        String collect = stream.collect(Collectors.joining(" | "));
//        String collect = stream.collect(Collectors.joining(" | ", "开始：| ", " |：结束"));
//        System.out.println(collect);

        //根据条件，分类成一个key为True和Flase的Map
//        List<String> list = Arrays.asList("java", "python", "C++","php","java");
//        Map<Boolean, List<String>> result = list.stream().collect(partitioningBy(s -> s.length() > 2));
        List<Nav> list = Arrays.asList(new Nav("wxs", 22), new Nav("haha", 23), new Nav("hehe", 30), new Nav("heihei", 32));
//        Map<Boolean, List<Nav>> result = list.stream().collect(Collectors.partitioningBy(i -> i.getAge() > 25));
//        result.get(true).forEach(System.out::println);
//        result.get(false).forEach(System.out::println);


    }

    @Test
    public void bet2() {
        Stream<String> stream = Stream.of(jj.split(" "));

        //map 对流中的每个元素进行操作
//        stream.map(i -> i.length()).forEach(System.out::print);

        //filter 根据条件过滤
//        stream.filter(i -> i.length() > 3).forEach(System.out::println);

        //flatMap A元素中含有的B元素为一个集合，可通过flatMap获取到该集合
        //将相同类型的元素中相同的属性拿出来，组成一个新的集合或数组
        List<Integer> collect = stream
//                .filter(i -> i.length() > 4)
                .flatMap(i -> i.chars().boxed())
//                .forEach(System.out::println);
                .collect(Collectors.toList());
//                .forEach(i -> System.out.print((char)i.intValue()));
        collect.forEach(System.out::print);

        //peek 用于debug，非终止操作，在元素被消费前调用
//        stream.peek(i -> System.out.print(i.toUpperCase())).forEach(System.out::println);
    }

    public static void bet1() {
        String str = "ab bc cd de ef fg gh";
        //遍历循环
//        Stream.of(str.split(" ")).forEach(System.out::print);

        //map 对流中的每个元素进行同样的操作
//        Stream.of(str.split(" ")).map(s -> s.length()).forEach(System.out::print);

        //filter 过滤
//        Stream.of(str.split(" ")).map(s -> s.length()).filter(i -> i > 0).forEach(System.out::print);

        //flatMap 流中对象 A 含 B 属性，且 B 属性是一个集合，利用其可获取到流中的 B 属性 并组成一个新的流
//        Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed()).forEach(System.out::println);

        //生成一个包含原Stream的所有元素的新Stream，新Stream每个元素 【被消费之前】 都会执行peek给定的消费函数
//        Stream.of(str.split(" ")).peek(c -> System.out.print(c.toUpperCase())).forEach(System.out::print);
//        Stream.of(2, 4).peek(x -> System.out.print(x - 1)).forEach(System.out::print);

        //filter 过滤
//        new Random().ints() // 产生流
//                .filter(i -> i > 0 && i < 100000) // 过滤流
//                .limit(10) // 限制流的长度
//                .sorted()
//                .forEach(System.out::println);

        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 4, 3, 2, 1, 5);
        //去重
//        stream.distinct().forEach(System.out::print);

        //排序
//        stream.sorted().forEach(System.out::print);

        //限流（截取前n）
//        stream.limit(5).forEach(System.out::print);

        //裁剪（截去前n）
        stream.skip(3).forEach(System.out::print);
    }


}
