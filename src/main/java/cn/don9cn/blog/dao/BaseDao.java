package cn.don9cn.blog.dao;

import cn.don9cn.blog.annotation.MapperNameSpace;
import cn.don9cn.blog.plugins.DaoHelper.DaoHelper;
import cn.don9cn.blog.plugins.DaoHelper.SqlConstant;
import org.mybatis.spring.SqlSessionTemplate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: 基础dao接口,定义并默认实现了基础的增删改查操作
 * @Create: don9 2017/10/8
 * @Modify:
 */
public interface BaseDao<T extends Serializable> {

    default SqlSessionTemplate getSessionTemplate(){
        return DaoHelper.getSqlSessionTemplate();
    }

    default String getSqlName(String sqlName){
        String nameSpace = this.getDefaultSqlNameSpace();
        return nameSpace + SqlConstant.SEPERATOR + sqlName;
    }

    default String getDefaultSqlNameSpace(){
        Type type = this.getClass().getGenericInterfaces()[0];
        ParameterizedType type2 = (ParameterizedType) type;
        Type[] actualTypeArguments = type2.getActualTypeArguments();
        Type actualTypeArgument = actualTypeArguments[0];
        Class<T> clazz = (Class<T>) actualTypeArgument;
        MapperNameSpace annotation = clazz.getAnnotation(MapperNameSpace.class);
        String namespace = annotation.namespace();
        return namespace;
    }

    /**
     * 添加实体对象到数据库
     * @param entity
     * @return
     */
    default Optional<Integer> baseInsert(T entity){
        return null;
    }

}
