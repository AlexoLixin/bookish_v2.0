package cn.don9cn.blog.service;


import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author Don9
 * @create 2017-04-27-9:11
 **/
public interface BaseService<T extends Serializable> {

    /**
     * 添加实体对象
     * @param entity
     * @return
     */
    Optional<Integer> insert(T entity);

    /**
     * 批量添加实体对象
     * @return
     */
    Optional<Integer> insertBatch(List<T> list);

    /**
     * 通过id更新实体
     * @param entity
     * @return
     */
    Optional<Integer> update(T entity);

    /**
     * 通过id删除实体
     * @param id
     * @return
     */
    Optional<Integer> deleteById(String id);

    /**
     * 批量删除实体对象
     * @return
     */
    Optional<Integer> deleteBatch(List<String> list);

    /**
     * 通过id查找实体
     * @param id
     * @return
     */
    Optional<T> findById(String id);

    /**
     * 查询所有数据
     * @return
     */
    Optional<List<T>> findAll();

    /**
     * 查询指定条件的实体集合
     * @param entity
     * @return
     */
    Optional<List<T>> findListByParams(T entity);

    /**
     * 带参数的分页查询
     * @param pageParamsBean
     * @return
     */
    Optional<PageResult<T>> findByPage(PageParamsBean<T> pageParamsBean);
}
