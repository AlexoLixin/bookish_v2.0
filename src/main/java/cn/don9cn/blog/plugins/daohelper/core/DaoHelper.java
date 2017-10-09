package cn.don9cn.blog.plugins.daohelper.core;

import cn.don9cn.blog.configs.mybatis.TransactionManagerConfig;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: liuxindong
 * @Description: dao辅助插件,用于动态生成sql语句,实现mapper.xml映射文件的配置最小化
 * @Create: don9 2017/10/8
 * @Modify:
 */
@Configuration
@AutoConfigureAfter(value = {TransactionManagerConfig.class})
public class DaoHelper {

    private static SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public DaoHelper(SqlSessionTemplate sqlSessionTemplate) {
        DaoHelper.sqlSessionTemplate = sqlSessionTemplate;
    }

    public static SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }




}
