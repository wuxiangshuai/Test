package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.util.Scanner;

/**
 * @ClassName: SpringConfig
 * @Author: WuXiangShuai
 * @Time: 17:10 2019/9/9.
 * @Description:
 */
@Configuration
@ComponentScan(basePackages= {"com.example.demo"})
@PropertySource("classpath:db.properties")
public class SpringConfig {

    @Value("${db_url}")
    private String url;
    @Value("${db_username}")
    private String username;
    @Value("${db_password}")
    private String password;
    @Value("${db_driver}")
    private String driver;
    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(driverManagerDataSource());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String strs[] = new String[3];
        System.out.println("Enter the first city: ");
        strs[0] = sc.nextLine();
        System.out.println("Enter the second city: ");
        strs[1] = sc.nextLine();
        System.out.println("Enter the third city: ");
        strs[2] = sc.nextLine();
        // 冒泡排序
        boolean flag;
        for (int i = 0; i < strs.length - 1; i++) {
            flag = false;
            for (int j = 0; j < strs.length - 1 - i; j++) {
                if (check(strs[j], strs[j + 1])) {
                    String temp = strs[j];
                    strs[j] = strs[j + 1];
                    strs[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) break;
        }
        for (String str : strs) {
            System.out.println(str);
        }
    }
    // >
    private static boolean check(String str1, String str2) {
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        boolean b = false;
        // 比较字符串长度，确定循环次数
        int min = Math.min(str1.length(), str2.length());
        // 依次对位对比
        for (int i = 0; i < min; i++) {
            if (chars1[i] > chars2[i]) {
                b = true;
                break;
            } else if (chars1[i] < chars2[i]) {
                break;
            }
            // 若至min位依然相同，长者为大的值
            if (i == min - 1) {
                if (str1.length() > str2.length()) {
                    b = true;
                }
            }
        }
        return b;
    }

//    public static void main(String[] args) {
//        while (true) {
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Enter an ASCII code: ");
//            int i = sc.nextInt();
//            if (0 < i || 127 > i) {
//                System.out.println("请输入0~127间的一个数！");
//            }
//            Character chars = new Character((char) i);
//            System.out.println("The character for ASCII code " + i + " is " + chars);
//        }
//    }

}
