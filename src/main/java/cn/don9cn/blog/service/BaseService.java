package cn.don9cn.blog.service;


import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 基础service接口
 * @Create: 2017/10/23 10:21
 * @Modify:
 */
public interface BaseService<T extends Serializable> {

    /**
     * 添加实体对象
     * @param entity
     * @return
     */
    Object baseInsert(T entity);

    /**
     * 批量添加实体对象
     * @return
     */
    Object baseInsertBatch(List<T> list);

    /**
     * 通过id更新实体
     * @param entity
     * @return
     */
    Object baseUpdate(T entity);

    /**
     * 通过id删除实体
     * @param id
     * @return
     */
    Object baseDeleteById(String id);

    /**
     * 批量删除实体对象
     * @return
     */
    Object baseDeleteBatch(String codes);

    /**
     * 通过id查找实体
     * @param id
     * @return
     */
    Object baseFindById(String id);

    /**
     * 查询所有数据
     * @return
     */
    Object baseFindAll();

    /**
     * 查询指定条件的实体集合
     * @param entity
     * @return
     */
    Object baseFindListByParams(T entity);

    /**
     * 带参数的分页查询
     * @param pageResult
     * @return
     */
    Object baseFindByPage(PageResult<T> pageResult);
}
