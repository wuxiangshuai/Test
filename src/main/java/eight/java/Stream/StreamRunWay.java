package eight.java.Stream;

import org.junit.Test;

import java.util.Random;
import java.util.stream.Stream;

/**
 * @ClassName: StreamRunWay
 * @Author: WuXiangShuai
 * @Time: 18:16 2019/4/24.
 * @Description:
 */
public class StreamRunWay {

    /**
     * 1 所有操作都是链式操作，一个元素只迭代一次
     * 2 每一个中间操作都返回一个新的流
     *      流里面有一个属性叫 sourceStage ,它都指向同一个地方就是这个流的Head
     *      Head -> nextStage -> nextStage -> ... -> null
     */
    @Test
    public void test01() {
        Random random = new Random();
        Stream<Integer> stream = Stream
                //返回无限顺序无序流，其中每个元素由提供的Supplier
                .generate(() -> random.nextInt())
                //短路操作
                .limit(50)
                //中间操作
                .peek(i -> System.out.println(i))
                .filter(i -> {
                    System.out.println("filter ::: " + i);
                    return i > 5000000;
                });
        System.out.println("未操作");
        long count = stream.count();
        System.out.println("操作完毕：：：" + count);
    }

    /**
     * 3、有状态操作会将无状态操作截断，单独处理
     */
    @Test
    public void test02() {
        Random random = new Random();
        Stream<Integer> stream = Stream.generate(() -> random.nextInt()) // 产生流
                // 短路操作
                .limit(5)
                // 中间操作(无状态)
                .peek(s -> System.out.println("peek: " + s))
                // 中间操作（无状态）
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s > 1000000;
                })
                // 中间操作（有状态）
                .sorted((i1, i2) -> {
                    System.out.println("排序：" + i1 + "," + i2);
                    return i1.compareTo(i2);
                })
                // 中间操作（无状态）
                .peek(s -> System.out.println("peek2: " + s));
        // 终止操作
        stream.count();
    }

    /**
     * 4、并行环境下，有状态操作不一定能并行操作
     * 5、parallel 和 sequetial 两者操作也是中间操作，但他们不改变流，只修改 Head 的并行标志
     */
    @Test
    public void test03(){
        Random random = new Random();
        Stream<Integer> stream = Stream.generate(() -> random.nextInt()) // 产生流
                // 短路操作
                .limit(500)
                .parallel()
                // 中间操作(无状态)
                .peek(s -> System.out.println(Thread.currentThread().getName()+" -- peek: " + s))
                // 中间操作（无状态）
                .filter(s -> {
                    System.out.println(Thread.currentThread().getName()+" -- filter: " + s);
                    return s > 1000000;
                })
                // 中间操作（有状态） 【并行环境下时不能进行并行操作】
                .sorted((i1, i2) -> {
                    System.out.println(Thread.currentThread().getName()+" -- 排序: " + i1 + "," + i2);
                    return i1.compareTo(i2);
                })
                // 中间操作（无状态）
                .peek(s -> System.out.println(Thread.currentThread().getName()+" -- peek"+Thread.currentThread().getName()+": " + s));
        // 终止操作
        stream.count();
    }

    /**
     * 如果所有操作都是无状态操作，那么都是链式调用的；
     * 但是如果在无状态操作之间添加了有状态操作，那么有状态操作会将链式操作截成两部分，那两部分分别进行链式操作
     */
    @Test
    public void test04(){
        Stream<Integer> integerStream = Stream.generate(() -> new Random().nextInt())
                .limit(10)
                .peek(s -> System.out.println("peek 1:" + s))
                .filter(s1 -> {
                    System.out.println("filter 1:" + s1);
                    return true;
                })
                .sorted((s1, s2) -> {
                    System.out.println("排序：" + s1 + "," + s2);
                    return s1.compareTo(s2);
                })
                .peek(s -> System.out.println("peek 2:" + s));
        integerStream.count();
    }

    /**
     * 并行操作对有状态的中间操作的影响
     */
    @Test
    public void test05(){
        Stream<Integer> integerStream = Stream.generate(() -> new Random().nextInt())
                .limit(500)
                .peek(s -> System.out.println("peek:" + s))
                .filter(s1 -> {
                    System.out.println(Thread.currentThread().getName()+"filter:" + s1);
                    return true;
                })
                .sorted((s1, s2) -> {
                    System.out.println(Thread.currentThread().getName()+"排序：" + s1 + "," + s2);
                    return s1.compareTo(s2);
                })
                .peek(s ->  System.out.println(Thread.currentThread().getName()+"peek02-:" + s))
                .parallel();

        integerStream.count();
    }
}
