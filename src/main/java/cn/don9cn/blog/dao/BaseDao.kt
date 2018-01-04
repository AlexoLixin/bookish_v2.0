package cn.don9cn.blog.dao

import cn.don9cn.blog.support.mongo.ext.inThe
import cn.don9cn.blog.support.mongo.ext.query
import cn.booklish.mongodsl.core.DslOperator
import cn.booklish.mongodsl.core.PageResult
import cn.don9cn.blog.autoconfigure.shiro.util.ShiroSessionUtil
import cn.don9cn.blog.model.BaseModel
import cn.don9cn.blog.support.mongo.core.DaoHelper
import cn.don9cn.blog.util.getNowDate
import java.lang.reflect.ParameterizedType


interface BaseDao<T:BaseModel> {

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
        entity.createTime = getNowDate()
        entity.createBy = ShiroSessionUtil.getUserCode()
        return dslOperator.insertOne(entity)
    }

    /**
     * 批量添加实体对象
     * @return
     */
    fun baseInsertBatch(list: List<T>): Int {
        list.forEach {
            it.createTime = getNowDate()
            it.createBy = ShiroSessionUtil.getUserCode()
        }
        return dslOperator.insertBatch(list)
    }

    /**
     * 条件更新实体
     * @param entity
     * @return
     */
    fun baseUpdate(entity: T): Int {
        entity.modifyTime = getNowDate()
        entity.modifyBy = ShiroSessionUtil.getUserCode()
        return dslOperator.updateById(entity.code!!,entity)
    }

    /**
     * 通过id删除实体
     * @param id
     * @return
     */
    fun baseDeleteById(id: String): Int {
        return dslOperator.removeById(id,getTypeClass())
    }

    /**
     * 批量删除实体对象
     * @return
     */
    fun baseDeleteBatch(list: List<String>): Int {
        return dslOperator.remove(query("_id" inThe list), getTypeClass())
    }

    /**
     * 通过id查找实体
     * @param id
     * @return
     */
    fun baseFindById(id: String): T? {
        return dslOperator.findById(id, getTypeClass()) as? T
    }

    /**
     * 查询所有数据
     * @return
     */
    fun baseFindAll(): List<T> {
        return dslOperator.findListByClass(getTypeClass()) as List<T>
    }

    /**
     * 查询指定条件的实体集合
     * @param entity
     * @return
     */
    fun baseFindListByParams(entity: T): List<T> {
        return dslOperator.findListByEntity(entity)
    }

    /**
     * 带参数的分页查询
     * @param pageResult
     * @return
     */
    fun baseFindByPage(pageResult: PageResult<T>): PageResult<T> {
        return dslOperator.findPage(pageResult)
    }

    /**
     * 根据主键集合查询实体
     * @param ids
     * @return
     */
    fun baseFindListInIds(ids: List<String>): List<T> {
        return dslOperator.findList(query("_id" inThe ids),getTypeClass()) as List<T>
    }

}