package cn.don9cn.blog.service

import cn.don9cn.blog.support.mongo.core.PageResult
import java.io.Serializable

/**
 * 基础service接口
 */
interface BaseService<T:Serializable> {

    /**
     * 添加实体对象
     * @param entity
     * @return
     */
    fun baseInsert(entity: T): Int

    /**
     * 批量添加实体对象
     * @return
     */
    fun baseInsertBatch(list: List<T>): Int

    /**
     * 通过id更新实体
     * @param entity
     * @return
     */
    fun baseUpdate(entity: T): Int

    /**
     * 通过id删除实体
     * @param id
     * @return
     */
    fun baseDeleteById(id: String): Int

    /**
     * 批量删除实体对象
     * @return
     */
    fun baseDeleteBatch(codes: String): Int

    /**
     * 通过id查找实体
     * @param id
     * @return
     */
    fun baseFindById(id: String): T?

    /**
     * 查询所有数据
     * @return
     */
    fun baseFindAll(): List<T>

    /**
     * 查询指定条件的实体集合
     * @param entity
     * @return
     */
    fun baseFindListByParams(entity: T): List<T>

    /**
     * 带参数的分页查询
     * @param pageResult
     * @return
     */
    fun baseFindByPage(pageResult: PageResult<T>): PageResult<T>
}