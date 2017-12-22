package cn.don9cn.blog.support.daohelper.core;

import cn.don9cn.blog.autoconfigs.mongodb.MyMongoOperator;
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
@AutoConfigureAfter(value = {MyMongoOperator.class})
public class DaoHelper {

    private static MyMongoOperator myMongoOperator;

    @Autowired
    public DaoHelper(MyMongoOperator myMongoOperator) {
        DaoHelper.myMongoOperator = myMongoOperator;
    }

    public static MyMongoOperator getMyMongoOperator() {
        return myMongoOperator;
    }
}
