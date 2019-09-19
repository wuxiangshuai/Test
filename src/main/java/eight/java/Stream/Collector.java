package eight.java.Stream;

import eight.java.bean.Gender;
import eight.java.bean.Grade;
import eight.java.bean.Student;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: Collector
 * @Author: WuXiangShuai
 * @Time: 13:17 2019/4/20.
 * @Description:
 */
public class Collector {

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("fury", 1, 1, Gender.MALE, Grade.ONE),
                new Student("fury01", 2, 1, Gender.MALE, Grade.THREE),
                new Student("fury02", 3, 1, Gender.FEMALE, Grade.ONE),
                new Student("fury03", 4, 2, Gender.MALE, Grade.FOUR),
                new Student("fury04", 5, 2, Gender.FEMALE, Grade.ONE),
                new Student("fury05", 6, 2, Gender.MALE, Grade.ONE),
                new Student("fury06", 7, 3, Gender.FEMALE, Grade.TWO),
                new Student("fury07", 8, 3, Gender.MALE, Grade.ONE),
                new Student("fury08", 9, 4, Gender.FEMALE, Grade.THREE),
                new Student("fury09", 10, 4, Gender.MALE, Grade.ONE)
        );
        Stream<Student> stream = students.stream();
        demo(stream);
        textStream(stream);
    }

    private static void textStream(Stream<Student> stream) {
        //1、获取所有学生的年龄集合
//        List<Integer> collect = stream.map(Student::getAge).collect(Collectors.toList());
//        ArrayList<Integer> arrayList = stream.map(Student::getAge).collect(Collectors.toCollection(ArrayList::new));

        //2、统计年龄详细信息
//        IntSummaryStatistics ageCollect = stream.collect(Collectors.summarizingInt(Student::getAge));
//        System.out.println(ageCollect);//IntSummaryStatistics{count=10, sum=55, min=1, average=5.500000, max=10}

        //3、根据性别分区
//        Map<Boolean, List<Student>> genderCollect = stream.collect(Collectors.partitioningBy(i -> i.getGender() == Gender.FEMALE));
//        System.out.println(genderCollect.get(true));
//        System.out.println(genderCollect.get(false));

        //4、根据班级分组
//        Map<Grade, List<Student>> grade1 = stream.collect(Collectors.groupingBy(Student::getGrade));
//        Map<Grade, List<Student>> grade2 = stream.collect(Collectors.groupingBy(Student::getGrade, toList()));
//        TreeMap<Grade, List<Student>> grade3 = stream.collect(groupingBy(Student::getGrade, TreeMap::new, toList()));
//        System.out.println(grade2.get(Grade.ONE));

        //5、分组的下级操作
//        Map<Grade, Long> collect = stream.collect(groupingBy(Student::getGrade, Collectors.counting()));
//        System.out.println(collect.get(Grade.ONE));
        //集合Map<Arg1, Arg2>：Arg1 第一分类的类型，Arg2 第二分类的类型
//        Map<Integer, Double> collect = stream.collect(groupingBy(Student::getHaha, Collectors.averagingInt(Student::getAge)));
//        Set<Integer> integers = collect.keySet();
//        for (Integer i : integers ) {
//            System.out.println(collect.get(i));
//        }

//        final Integer collect = Arrays.asList(1, 2, 3, 4, 5)
//                .stream()
//                .collect(Collectors.reducing(0,(i, j)->i+j));
//        System.out.println(collect);
        /**
         * 第一个参数表示归约的初始值。我们需要累加，因此初始值为0
         * 第二个参数表示需要进行归约操作的字段。这里我们对Person对象的age字段进行累加。
         * 第三个参数表示归约的过程。这个参数接收一个Lambda表达式，而且这个Lambda表达式一定拥有两个参数，
         *      分别表示当前相邻的两个元素。由于我们需要累加，因此我们只需将相邻的两个元素加起来即可。
         */
        Integer collect = stream.collect(Collectors.reducing(0, Student::getAge, (i, j) -> i + j));
        System.out.println(collect);
    }

    public static void demo(Stream<Student> stream){
        //多使用方法引用来代替lambda表达式，这样可以少生成lambda$()函数
//        List<Integer> list = stream.map(Student::getAge).collect(Collectors.toList());

        //流信息汇总
//        IntSummaryStatistics summaryStatistics = stream.collect(Collectors.summarizingInt(Student::getAge));
//        System.out.println(summaryStatistics);

        //分块函数：Collectors.partitioningBy()，key：true：false
//        Map<Boolean, List<Student>> collect = stream.collect(Collectors.partitioningBy(i -> i.getGender() == Gender.MALE));
//        System.out.println(collect);
//        collect.get(true).forEach(i -> System.out.println(i.getGender()));
//        collect.get(false).forEach(i -> System.out.println(i.getGender()));

        //分组函数:Collectors.groupingBy()，key：Grade
//        Map<Grade, List<Student>> gradeGroup = stream.collect(Collectors.groupingBy(Student::getGrade));
//        gradeGroup.get(Grade.ONE).forEach(i -> System.out.println(i.getName() + " : " + i.getGrade()));

        //分组下级操作：按组统计个数
//        Map<Grade, Long> collect = stream.collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
//        System.out.println(collect.get(Grade.ONE));
    }

}
