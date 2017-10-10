package cn.don9cn.blog.autoconfigs.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 *@Author: liuxindong
 *@Description: 注入dataSource数据源
 *@Create: 2017/10/8 19:46
 *@Modify:
 **/
@Configuration
public class DataSourceConfig {

    /**
     *@Author: liuxindong
     *@Description: 通过application.yml配置文件中的数据源配置信息,创建DruidDataSource(阿里开源工具,可配置可视化管理界面),并放入spring容器中
     *@Create: 2017/10/8 19:46
     *@Modify:
     **/
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }

}
