package cn.don9cn.blog.support.mongo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: liuxindong
 * @Description: dao辅助插件
 * @Create: don9 2017/10/8
 * @Modify:
 */
@Configuration
@AutoConfigureAfter(value = {DslOperator.class})
public class DaoHelper {

    private static DslOperator dslOperator;

    @Autowired
    public DaoHelper(DslOperator dslOperator) {
        DaoHelper.dslOperator = dslOperator;
    }

    public static DslOperator getDslOperator() {
        return dslOperator;
    }
}
