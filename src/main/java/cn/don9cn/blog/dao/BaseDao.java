package cn.don9cn.blog.dao;

import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: 基础dao接口,定义并默认实现了基础的增删改查操作
 * @Create: don9 2017/10/8
 * @Modify:
 */
public interface BaseDao<T extends Serializable> {

    /**
     * 添加实体对象
     * @param entity
     * @return
     */
    default Optional<Integer> insert(T entity){
        return Optional.empty();
    }

    /**
     * 批量添加实体对象
     * @return
     */
    default Optional<Integer> insertBatch(final List<T> list){
        return Optional.empty();
    }

    /**
     * 通过id更新实体
     * @param entity
     * @return
     */
    default Optional<Integer> update(T entity){
        return Optional.empty();
    }

    /**
     * 通过id删除实体
     * @param id
     * @return
     */
    default Optional<Integer> deleteById(String id){
        return Optional.empty();
    }

    /**
     * 批量删除实体对象
     * @return
     */
    default Optional<Integer> deleteBatch(List<String> list){
        return Optional.empty();
    }

    /**
     * 通过id查找实体
     * @param id
     * @return
     */
    default Optional<T> findById(String id){
        return Optional.empty();
    }

    /**
     * 查询所有数据
     * @return
     */
    default Optional<List<T>> findAll(){
        return Optional.empty();
    }

    /**
     * 查询指定条件的实体集合
     * @param entity
     * @return
     */
    default Optional<List<T>> findListByParams(T entity){
        return Optional.empty();
    }

    /**
     * 带参数的分页查询
     * @param pageParamsBean
     * @return
     */
    default Optional<PageResult<T>> findByPage(PageParamsBean<T> pageParamsBean){
        return Optional.empty();
    }

}
