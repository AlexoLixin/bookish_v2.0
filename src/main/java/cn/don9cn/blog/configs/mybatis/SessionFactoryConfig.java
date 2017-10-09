package cn.don9cn.blog.configs.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
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
public class SessionFactoryConfig implements TransactionManagementConfigurer {

    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 实体别名
     */
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasPackage;

    /**
     * mybatis配置文件
     */
    @Value("${mybatis.config-location}")
    private String configLocation;

    /**
     * mapper映射文件
     */
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    /**
     *@Author: liuxindong
     *@Description: 创建 SqlSessionFactory
     *@Create: 2017/10/8 19:48
     *@Modify:
     **/
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(configLocation));
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources(mapperLocations);
        System.out.println(resources.length);
        sqlSessionFactoryBean.setMapperLocations(resources);
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);
        return sqlSessionFactoryBean;
    }

    /**
     *@Author: liuxindong
     *@Description: 创建 SqlSessionTemplate
     *@Create: 2017/10/8 19:49
     *@Modify:
     **/
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

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
