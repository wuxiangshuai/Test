package com.example.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.demo.filter.CorsFilter;

import java.util.Arrays;

/**
 * @ClassName: FilterConfig
 * @Author: WuXiangShuai
 * @Time: 14:57 2019/6/6.
 * @Description:
 */
@Configuration
public class FilterConfig {
    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter());
//        registration.addUrlPatterns("/user");
        registration.setUrlPatterns(Arrays.asList("/user", "/haha"));
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("CorsFilter");
        return registration;
    }

    /**
     * 创建一个bean
     * @return
     */
    @Bean(name = "CorsFilter")
    public CorsFilter sessionFilter() {
        return new CorsFilter();
    }
}
