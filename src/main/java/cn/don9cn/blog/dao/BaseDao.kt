package cn.don9cn.blog.dao

import cn.don9cn.blog.support.mongo.ext.inThe
import cn.don9cn.blog.support.mongo.ext.query
import cn.booklish.mongodsl.core.DslOperator
import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.model.BaseModel
import cn.don9cn.blog.support.mongo.core.DaoHelper
import java.lang.reflect.ParameterizedType


interface BaseDao<in T:BaseModel> {

    val dslOperator:DslOperator
        get() = DaoHelper.getDslOperator()

    /**
     * 获取参数类型
     * @return
     */
    fun getTypeClass(): Class<*> {
        val type = this.javaClass.interfaces[0].genericInterfaces[0]
        val type2 = type as ParameterizedType
        val actualTypeArguments = type2.actualTypeArguments
        val actualTypeArgument = actualTypeArguments[0]
        return actualTypeArgument as Class<T>
    }


    /**
     * 添加实体对象
     * @param entity
     * @return
     */
    fun baseInsert(entity: T): Int {
        return DaoHelper.getDslOperator().insertOne(entity)
    }

    /**
     * 批量添加实体对象
     * @return
     */
    fun baseInsertBatch(list: List<T>): Int {
        return DaoHelper.getDslOperator().insertBatch(list)
    }

    /**
     * 条件更新实体
     * @param entity
     * @return
     */
    fun baseUpdate(entity: T): Int {
        return DaoHelper.getDslOperator().updateById(entity.code!!,entity)
    }

    /**
     * 通过id删除实体
     * @param id
     * @return
     */
    fun baseDeleteById(id: String): Int {
        return DaoHelper.getDslOperator().removeById(id,getTypeClass())
    }

    /**
     * 批量删除实体对象
     * @return
     */
    fun baseDeleteBatch(list: List<String>): Int {
        return DaoHelper.getDslOperator().remove(query("_id" inThe list), getTypeClass())
    }

    /**
     * 通过id查找实体
     * @param id
     * @return
     */
    fun <T> baseFindById(id: String): T {
        return DaoHelper.getDslOperator().findById(id, getTypeClass()) as T
    }

    /**
     * 查询所有数据
     * @return
     */
    fun <T> baseFindAll(): List<T> {
        return DaoHelper.getDslOperator().findListByClass(getTypeClass()) as List<T>
    }

    /**
     * 查询指定条件的实体集合
     * @param entity
     * @return
     */
    fun <T : Any> baseFindListByParams(entity: T): List<T> {
        return DaoHelper.getDslOperator().findListByEntity(entity)
    }

    /**
     * 带参数的分页查询
     * @param pageResult
     * @return
     */
    fun <T : Any> baseFindByPage(pageResult: PageResult<T>): PageResult<T> {
        return DaoHelper.getDslOperator().findPage(pageResult)
    }

    /**
     * 根据主键集合查询实体
     * @param ids
     * @return
     */
    fun <T> baseFindListInIds(ids: List<String>): List<T> {
        return DaoHelper.getDslOperator().findList(query("_id" inThe ids),getTypeClass()) as List<T>
    }

}