package juc;

import java.util.Random;
import java.util.concurrent.*;
import java.util.function.*;

/**
 * @ClassName: CompletableFutureDemo
 * @Author: WuXiangShuai
 * @Time: 14:01 2019/6/25.
 * @Description: CompletableFuture 对 jdk5 提出的 futrue 进行的补充
 * future 仅提供查看阶段任务是否完成和获取阶段任务结果的方法
 * CompletableFuture 提供更强大的方法
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws Exception {
//        runAsync();           // 无返回值的异步操作
//        supplyAsync();        // 有返回值的异步操作
//        whenComplete();       // 计算完成时的回调方法
//        thenApply();          // 当线程间存在依赖时，可以使用 thenApply 方法使两个线程串行化
//        handle();             // handle 方法是对执行完阶段的结果进行处理的方法
        thenAccept();         // 对阶段结果的处理
//        thenRun();            // 追加阶段

        //有返回值的使用 Supplier<>()，无返回值的使用 Consumer<Integer>、BiConsumer<>()
//        thenCombine();        // thenCombine 方法合并两个阶段成一个阶段，且合并两个阶段的结果，需单独执行，有返回值
//        thenAcceptBoth();     // 类似 thnCombine，可无需单独执行，只要依赖阶段有一个执行即触发，触发前执行所有依赖阶段，无返回值

//        applyToEither();      // 比较两个阶段谁执行速度较快，使用较快阶段的结果进行操作，有返回值
//        acceptEither();       // 比较两个阶段的执行速度，使用较快的阶段的结果进行操作，无返回值

//        runAfterEither();     // 任何一个阶段完成都会执行下一个阶段，下一阶段在执行时间最久的阶段之后执行
//        runAfterBoth();       // 两个阶段都执行完毕后执行

//        thenCompose();        // 流水线操作

//        all();
    }

    // allOf，所有阶段任务执行完毕后执行
    // anyof，任一阶段任务执行完毕后执行
    private static void all() throws Exception {
        ExecutorService pool = Executors.newCachedThreadPool();
        long l1 = System.currentTimeMillis();
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try{ TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println("future1");
            return 3;
        }, pool);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try{ TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println("future2");
            return 6;
        }, pool);
        System.out.println("中间操作1 - " + (System.currentTimeMillis() - l1));
        CompletableFuture.allOf(future1, future2).thenRunAsync(()-> System.out.println("allof Haha"));
        CompletableFuture.anyOf(future1, future2).thenRunAsync(()-> System.out.println("anyof Haha"));
        System.out.println("中间操作2 - " + (System.currentTimeMillis() - l1));
        System.out.println(future1.get());
        System.out.println("future1 get - " + (System.currentTimeMillis() - l1));
        System.out.println(future2.get());
        System.out.println("future2 get - " + (System.currentTimeMillis() - l1));
        long l2 = System.currentTimeMillis();
        System.out.println("haha - " + (l2 - l1));

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            try{ TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println("future3");
            return 12;
        }, pool);
        System.out.println(future3.get());
        System.out.println("over - " + (System.currentTimeMillis() - l1));
    }

    // 流水线操作，第一个阶段操作完成时，将其结果以参数形式传递给第二个阶段
    private static void thenCompose() throws Exception {
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = new Random().nextInt(3);
                System.out.println("t1 = " + t);
                return t;
            }
        }).thenCompose(new Function<Integer, CompletionStage<Integer>>() {
            @Override
            public CompletionStage<Integer> apply(Integer param/*提供的参数*/) {
                return CompletableFuture.supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int t = param *2;
                        System.out.println("t2 = " + t);
                        return t;
                    }
                });
            }
        });
        System.out.println("thenCompose result : "+f.get());
    }

    // 两个阶段都执行完毕后执行
    private static void runAfterBoth() throws Exception {
        long l1 = System.currentTimeMillis();
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = 1;
                try { TimeUnit.SECONDS.sleep(t); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("f1 = " + t);
                return t;
            }
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = 2;
                try { TimeUnit.SECONDS.sleep(t); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("f2 =  " + t);
                return t;
            }
        });
        try{ TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e){ e.printStackTrace(); }
        System.out.println(f2.get() + " - " + (System.currentTimeMillis() - l1));
        System.out.println(f1.get() + " - " + (System.currentTimeMillis() - l1));
        f1.runAfterBoth(f2, new Runnable() {
            @Override
            public void run() {
                System.out.println("上面两个任务都执行完成了。");
            }
        });
        System.out.println(" over " + (System.currentTimeMillis() - l1));
    }

    // 任何一个阶段完成都会执行下一个阶段，下一阶段在执行时间最久的阶段之后执行
    // 调用方正常执行，仅执行调用方，后执行下一阶段
    // 参数方正常执行，会启动调用方，在执行最久的阶段后执行下一阶段
    private static void runAfterEither() throws Exception {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = 1;
                try { TimeUnit.SECONDS.sleep(t); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("f1 =   " + t);
                return t;
            }
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = 3;
                try { TimeUnit.SECONDS.sleep(t); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("f2 =   " + t);
                return t;
            }
        });
//        System.out.println(f1.get());
        System.out.println(f2.get());
//        f1.runAfterEither(f2, new Runnable() {
        f1.runAfterEitherAsync(f2, new Runnable() {
            @Override
            public void run() {
                System.out.println("上面有一个已经完成了。");
            }
        });
    }

    // 比较两个阶段的执行速度，使用较快的阶段的结果进行操作，无返回值
    private static void acceptEither() throws Exception {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = 2;
                try { TimeUnit.SECONDS.sleep(t); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("f1 = " + t);
                return t;
            }
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = 3;
                try { TimeUnit.SECONDS.sleep(t); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("f2 =  " + t);
                return t;
            }
        });
        CompletableFuture<Void> future = f1.acceptEither(f2, new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                System.out.println(t);
            }
        });
        System.out.println(future.get());
    }

    // 比较两个阶段谁执行速度较快，使用较快阶段的结果进行操作，有返回值
    private static void applyToEither() throws Exception {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = 3;
                try { TimeUnit.SECONDS.sleep(t); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("f1 = " + t);
                return t;
            }
        });
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = 2;
                try { TimeUnit.SECONDS.sleep(t); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("f2 = " + t);
                return t;
            }
        });

//        CompletableFuture<Integer> result = f1.applyToEither(f2, new Function<Integer, Integer>() {
        CompletableFuture<Integer> result = f1.applyToEitherAsync(f2, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer t) {
                System.out.println(t);
                return t * 2;
            }
        });

        System.out.println(result.get());
        System.out.println(f1.isDone());
        System.out.println(f2.isDone());
    }

    // 返回一个新的CompletionStage，当这个和另一个给定的阶段都正常完成时，两个结果作为提供的操作的参数被执行。无返回值
    // 若另一个阶段正常完成，但这个调用阶段未正常执行，依旧执行该任务
    // 若另一个阶段未正常完成，不执行该阶段。若阶段都未执行，亦不执行该阶段。
    // 也可以直接执行新返回的阶段，执行该阶段会首先触发依赖阶段
    private static void thenAcceptBoth() throws Exception {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = 1;
                try { TimeUnit.SECONDS.sleep(t); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("f1 =  " + t);
                return t;
            }
        });
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = 1;
                try { TimeUnit.SECONDS.sleep(t); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("f2 = " + t);
                return t;
            }
        });
//        System.out.println(f1.isDone());
        System.out.println(f2.isDone());
//        System.out.println(f1.get());
        System.out.println(f2.get());
        CompletableFuture<Void> future = f1.thenAcceptBoth(f2, new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer t, Integer u) {
                System.out.println("f1 = " + t + "; f2 = " + u + ";");
            }
        });
//        future.get();
    }

    // thenCombine 方法合并两个阶段成一个阶段，且合并两个阶段的结果，有返回值
    // 合并出的是一个新的阶段，需单独执行
    private static void thenCombine() throws Exception {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "Hello";
            }
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "world !";
            }
        });
        ExecutorService pool = Executors.newWorkStealingPool(10);//适合耗时阶段
        //同步合并处理
//        CompletableFuture<String> result = future1.thenCombine(future2, new BiFunction<String, String, String>() {
        //异步合并处理
        CompletableFuture<String> result = future1.thenCombineAsync(future2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String t, String u) {
                return t + " " + u;
            }
        }, pool);
//        System.out.println(future1.get());
//        System.out.println(future2.get());
        System.out.println(result.get());
    }

    //追加阶段，待之前阶段执行完毕后执行，不关心之前阶段的执行结果，只要正常执行结束，即启动该阶段
    public static void thenRun() throws Exception{
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
//                int i = 10 / 0;
                return new Random().nextInt(10);
            }
        }).thenRunAsync(() -> {//追加异步阶段
            //.thenRun(() -> {//追加同步阶段
            System.out.println("thenRun ...");
        });
        future.get();
    }

    //对阶段结果的处理
    public static void thenAccept() throws Exception{
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(10);
            }
        }).thenAcceptAsync(integer -> {//异步处理
            //.thenAccept(integer -> {//同步处理
            System.out.println(integer);
        });
        System.out.println(future.get());;
    }

    //handle 方法是对执行完阶段的结果进行处理的方法
    //与 thenApply 不同的是，handle 可以处理异常，但 thenApply 只能执行正常的阶段，若之前阶段出现异常，则不继续执行
    public static void handle() throws Exception{
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
//                int i= 10/0;
                return new Random().nextInt(10);
            }
        }).handleAsync(new BiFunction<Integer, Throwable, Integer>() {//异步处理
            //.handle(new BiFunction<Integer, Throwable, Integer>() {//同步处理
            @Override
            public Integer apply(Integer param, Throwable throwable) {
                int result = -1;
                if(throwable==null){
                    result = param * 2;
                }else{
                    System.out.println(throwable.getMessage());
                }
                return result;
            }
        });
        System.out.println(future.get());
    }

    //当线程间存在依赖时，可以使用 thenApply 方法使两个线程串行化
    //若阶段出现异常，则不继续执行 thenApply 方法
    private static void thenApply() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                long result = new Random().nextInt(100);
                System.out.println("result1 = " + result);
                return result;
            }
        }).thenApply(new Function<Long/*上一个阶段的返回值类型*/, Long/*当前阶段的返回值类型*/>() {//依赖线程，同步执行
            //.thenApplyAsync(new Function<Long/*上一个阶段的返回值类型*/, Long/*当前阶段的返回值类型*/>() {//依赖线程，异步执行
            @Override
            public Long apply(Long t/*上一个阶段的返回值*/) {
                System.out.println("result1 = " + t);
                long result = t*5;
                System.out.println("result2 = " + result);
                return result;
            }
        });

        long result = future.get();
        System.out.println(result);
    }

    //计算完成时的回调方法
    public static void whenComplete() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { }
            if(new Random().nextInt()%2>=0) {
                int i = 12/0;
            }
            System.out.println("run end ...");
        });

        //顺序执行 whenComplete 阶段
//        future.whenComplete(new BiConsumer<Void, Throwable>() {//同步执行回调方法
        future.whenCompleteAsync(new BiConsumer<Void, Throwable>() {//异步执行回调方法
            @Override
            public void accept(Void t, Throwable action) {
                System.out.println("执行完成！");
            }
        });
        //把 whenComplete 阶段交给线程池进行执行
        future.whenCompleteAsync((t, action) -> System.out.println("异步执行完成！"));
        // 执行失败后的回调方法
        future.exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable t) {
                System.out.println("执行失败！"+t.getMessage());
                return null;
            }
        });

        TimeUnit.SECONDS.sleep(2);
        // 判断阶段任务是否异常完成
        System.out.println(future.isCompletedExceptionally());
        System.out.println(future.complete(null));
        System.out.println(future.completeExceptionally(new Throwable()));
        //完成后返回结果值，若异常完成，则返回异常
        System.out.println(future.join());
    }

    //无返回值的异步操作
    public static void runAsync() throws Exception {
        //默认使用 ForkJoinPool 作为线程池执行异步代码，指定线程池则用自定义的线程池执行异步代码
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { }
            System.out.println("run end ...");
        });

        future.get();
    }

    //有返回值的异步操作
    public static void supplyAsync() throws Exception {
        //默认使用 ForkJoinPool 作为线程池执行异步代码，指定线程池则用自定义的线程池执行异步代码
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { }
            System.out.println("run end ...");
            return System.currentTimeMillis();
        });

        ExecutorService threadPool = Executors.newCachedThreadPool();//短期异步阶段的线程池
        CompletableFuture<Long> future1 = CompletableFuture.supplyAsync(() -> {
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { }
            System.out.println("run1 end ...");
            return System.currentTimeMillis();
        }, threadPool);

        long time1 = future1.get();
        System.out.println("time1 = "+time1);

        long time = future.get();
        System.out.println("time = "+time);
//        try{ TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e){ e.printStackTrace(); }
//        System.out.println(future.isDone());
//        System.out.println(future1.isDone());
    }

}
