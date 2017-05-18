package com.yandong.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by yandong on 2017/5/18.
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.yandong.dao")
public class MybatisConfig {

    @Autowired
    private Environment env;

    /**
     31      * 创建数据源
     32      * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     33      */
    @Bean
    public DataSource getDataSource() throws Exception{

        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("db.driverClassName"));
        props.put("url", env.getProperty("db.url"));
        props.put("username", env.getProperty("db.username"));
        props.put("password", env.getProperty("db.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }


    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);//指定数据源(这个必须有，否则报错)
        fb.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/config.xml"));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mappers/*.xml"));//指定xml文件位置

        return fb.getObject();
    }

}
