package com.cy.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * User: 张文杰
 * Date: 2020/3/8
 * Time: 20:01
 * Description: 配置类
 * Configuration: 标识,让springboot认识这是一个配置类
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()) //添加过滤器
                .addPathPatterns("/admin/**") //过滤范围选择
                .excludePathPatterns("/admin")  //过滤排除
                .excludePathPatterns("/admin/login");
    }
}
