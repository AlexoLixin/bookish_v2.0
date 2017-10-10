package cn.don9cn.blog.autoconfigs.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 *@Author: liuxindong
 *@Description: 配置mybatis的SessionFactory
 *@Create: 2017/10/8 19:47
 *@Modify:
 **/
@Configuration
@EnableTransactionManagement
public class TransactionManagerConfig implements TransactionManagementConfigurer {

    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     *@Author: liuxindong
     *@Description: 创建事务管理器
     *@Create: 2017/10/8 19:49
     *@Modify:
     **/
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
