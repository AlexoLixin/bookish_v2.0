package cn.don9cn.blog.plugins.DaoHelper;

import cn.don9cn.blog.annotation.MapperNameSpace;
import cn.don9cn.blog.configs.mybatis.SessionFactoryConfig;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: dao辅助插件,用于动态生成sql语句,实现mapper.xml映射文件的配置最小化
 * @Create: don9 2017/10/8
 * @Modify:
 */
@Configuration
@AutoConfigureAfter(value = {SessionFactoryConfig.class})
public class DaoHelper {

    private static SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public DaoHelper(SqlSessionTemplate sqlSessionTemplate) {
        DaoHelper.sqlSessionTemplate = sqlSessionTemplate;
    }

    public static Optional<Integer> baseInsert(Object entity){
        System.out.println(getSqlName(entity,SqlConstant.BASE_UPDATE));
        return Optional.ofNullable(1);
    }

    public static SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    private static String getMapperNamespace(Object entity){
        MapperNameSpace annotation = entity.getClass().getAnnotation(MapperNameSpace.class);
        String namespace = annotation.namespace();
        return namespace;
    }

    private static String getSqlName(Object entity,String sqlName){
        return getMapperNamespace(entity) + SqlConstant.SEPERATOR + sqlName;
    }
}
