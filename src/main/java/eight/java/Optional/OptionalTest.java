package eight.java.Optional;

import eight.java.bean.User;
import eight.java.bean.Address;
import org.junit.Test;

import java.util.Optional;


/**
 * @ClassName: OptionalTest
 * @Author: WuXiangShuai
 * @Time: 20:03 2019/4/24.
 * @Description:
 * Optional 类是一个可以为null的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。
 * Optional 是个容器：它可以保存类型T的值，或者仅仅保存null。Optional提供很多有用的方法，这样我们就不用显式进行空值检测。
 * Optional 类的引入很好的解决空指针异常。
 */
public class OptionalTest {

    /**
     * 多级解包
     * 注意：getter的返回值应该是 Optional<T>
     */
    @Test
    public void test09(){
        User user = new User("wxs", "anna@gmail.com", "1234");
        String aDefault = Optional.ofNullable(user)
                .flatMap(User::getAddress)
                .flatMap(Address::getCountryName)
                .orElseGet(() -> "default");
        System.out.println(aDefault);
    }

    /**
     * filter，过滤
     */
    @Test
    public void test08(){
        User user = new User("wxs", "anna@gmail.com", "1234");
        Optional<User> userOptional = Optional.ofNullable(user).filter(u -> u.getCountry() != null);
        System.out.println(userOptional.isPresent());
    }

    /**
     * map，flatMap
     */
    @Test
    public void test07(){
        User user = new User("wxs", "anna@gmail.com", "1234");
        String map = Optional.ofNullable(user).map(i -> i.getEmail()).orElse("haha");
        System.out.println(map);

        //返回类型为 Optional<T> 时，可以通过flatMap()进行解包
//        Optional<String> haha = Optional.ofNullable(user).map(i -> i.getName()).orElse(Optional.ofNullable("haha"));
        String haha = Optional.ofNullable(user).flatMap(i -> i.getName()).orElse("haha");
        System.out.println(haha);
    }

    /**
     * orElseThrow，定义Optional对象包含值为空时，抛出自定义的异常，而不是NullPointerException
     */
    @Test
    public void test06(){
        User user = null;
        try {
            Optional.ofNullable(user).orElseThrow(() -> new IllegalArgumentException());//抛出非法参数异常
        } catch (IllegalArgumentException e) {
            System.out.println("非法参数异常！");
        }
    }

    /**
     * orElse、orElseGet
     */
    @SuppressWarnings("unused")
	@Test
    public void test05(){
        User user = null;
        User user2 = new User("anna@gmail.com", "1234");
        //orElse(T other)返回值如果存在，否则返回 other 。
        User orElse = Optional.ofNullable(user).orElse(user2);
        System.out.println(orElse);
        //Optional.ofNullable() 仅针对最外层对象，对其内的属性并没有判断
//        System.out.println(testNullInput(user));

        System.out.println("-----------------------------------------");

        /**
         * orElse(),orElseGet()
         * 当Optional对象包含空值时，二者都创建了新的User对象以供返回
         * 但是当Optional对象包含的是非空对象时，orElse()方法依然创建了对象，orElseGet()方法未创建对象
         * 个人建议使用orElseGet()
         */
        //orElse()
        System.out.println("orElse()");
        User orElse1 = Optional.ofNullable(user).orElse(createNewUser());
        //orElseGet()
        System.out.println("orElseGet()");
        User orElseGet1 = Optional.ofNullable(user).orElseGet(() -> createNewUser());

        System.out.println("----------");

        //orElse()
        System.out.println("orElse()");
        User orElse2 = Optional.ofNullable(user2).orElse(createNewUser());
        //orElseGet()
        System.out.println("orElseGet()");
        User orElseGet2 = Optional.ofNullable(user2).orElseGet(() -> createNewUser());
//        System.out.println(orElseGet);
    }

    public User createNewUser(){
        System.out.println("createNewUser");
        return new User("anna@gmail.com", "1234");
    }

    public String testNullInput(User user){
        User user2 = new User("anna@gmail.com", "1234");
        return Optional.ofNullable(user.getEmail()).orElse(user2.getEmail());
    }

    @Test
    public void test04(){
//        String name = "wxs";
        String name = null;
        Optional<String> optional = Optional.ofNullable(name);
        //ifPresent(Consumer<? super T> consumer)
        // 如果存在值，则使用该值调用指定的消费者，否则不执行任何操作。
        optional.ifPresent(System.out::println);
        //isPresent() 返回 true如果存在值，否则为 false 。
        System.out.println(optional.isPresent()?"有值":"没有值");
        System.out.println(optional.get());
    }

    @Test
    public void test03(){
        User user = null;
        //ofNullable 返回一个 Optional指定值的Optional，如果非空，则返回一个空的 Optional
        //适用于不确定传入对象是否为空时使用
        Optional<User> userOptional = Optional.ofNullable(user);
        userOptional.get();
    }

    @Test
    public void test02(){
        User user = new User();
        User user02 = null;
        //of 返回具有 Optional的当前非空值的Optional。
        Optional<User> userOptional = Optional.of(user);
        //若传入的对象为空，则报 NullPointerException
        Optional<User> userOptional02 = Optional.of(user02);
        userOptional.get();
        userOptional02.get();
    }

    @Test
    public void test01(){
        //返回一个空的 Optional实例
        Optional<User> empty = Optional.empty();
        empty.get();
    }

}
