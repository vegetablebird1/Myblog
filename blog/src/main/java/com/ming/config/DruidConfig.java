package com.ming.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //后台监控，固定写法.相当于web.xml，因为springboot内置servlet容器，没有web.xml，代替方法：ServletRegistrationBean
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        //后台需要登录，账号密码设置
        HashMap<String,String> initParameters = new HashMap<>();

        // //initParameters的key是固定的，不能改
        // initParameters.put("loginUsername","admin");
        // initParameters.put("loginPassword","123456");

        //允许谁能访问
        initParameters.put("allow","");
        //禁止谁能访问
        // initParameters.put("xxxx","ip地址");

        bean.setInitParameters(initParameters);//设置初始化参数
        return bean;
    }

    // //filter
    // @Bean
    // public FilterRegistrationBean filterRegistrationBean(){
    //     FilterRegistrationBean bean = new FilterRegistrationBean();
    //     bean.setFilter(new WebStatFilter());
    //
    //     //设置过滤什么
    //     Map<String,String> initParameters = new HashMap<>();
    //     //*.js,*.css,/druid/*不进行过滤
    //     initParameters.put("exclusions","*.js,*.css,/druid/*");
    //
    //     bean.setInitParameters(initParameters);
    //     return bean;
    // }
}
