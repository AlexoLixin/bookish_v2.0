package cn.don9cn.blog.service;


import cn.don9cn.blog.plugins.operaresult.core.OperaResult;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;

import java.io.Serializable;
import java.util.List;

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
    OperaResult baseInsert(T entity);

    /**
     * 批量添加实体对象
     * @return
     */
    OperaResult baseInsertBatch(List<T> list);

    /**
     * 通过id更新实体
     * @param entity
     * @return
     */
    OperaResult baseUpdate(T entity);

    /**
     * 通过id删除实体
     * @param id
     * @return
     */
    OperaResult baseDeleteById(String id);

    /**
     * 批量删除实体对象
     * @return
     */
    OperaResult baseDeleteBatch(String codes);

    /**
     * 通过id查找实体
     * @param id
     * @return
     */
    OperaResult baseFindById(String id);

    /**
     * 查询所有数据
     * @return
     */
    OperaResult baseFindAll();

    /**
     * 查询指定条件的实体集合
     * @param entity
     * @return
     */
    OperaResult baseFindListByParams(T entity);

    /**
     * 带参数的分页查询
     * @param pageResult
     * @return
     */
    OperaResult baseFindByPage(PageResult<T> pageResult);
}
