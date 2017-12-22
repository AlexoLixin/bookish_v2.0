package cn.don9cn.blog.support.mongo

import cn.don9cn.blog.autoconfigs.shiro.util.MyShiroSessionUtil
import cn.don9cn.blog.exception.MyMongoOperatorException
import cn.don9cn.blog.model.BaseModel
import cn.don9cn.blog.support.daohelper.core.PageResult
import cn.don9cn.blog.util.DateUtil
import cn.don9cn.blog.util.EntityParserUtil
import com.mongodb.Mongo
import org.apache.commons.lang3.StringUtils
import org.apache.log4j.Logger
import org.springframework.data.domain.Example
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import java.lang.reflect.InvocationTargetException
import java.util.*
import java.util.function.Function

/**
 * @Author: liuxindong
 * @Description:  mongo操作模版
 * @Created: 2017/12/22 16:50
 * @Modified:
 */
class MyMongoOperator(mongo: Mongo, databaseName: String) {

    private val logger = Logger.getLogger(cn.don9cn.blog.autoconfigs.mongodb.MyMongoOperator::class.java)

    private val mongoTemplate = MongoTemplate(mongo, databaseName)


    /**
     * 根据entity实体类型获取collectionName
     * @param entity
     * @param <T>
     * @return
    </T> */
    private fun <T : BaseModel> getCollectionName(entity: T): String {
        return entity.javaClass.simpleName
    }

    /**
     * 解析entity实体,提取其不为空的字段和对应的值
     * @param entity
     * @param <T>
     * @return
    </T> */
    fun <T : BaseModel> parseEntity(entity: T): Map<String, Any> {
        val resultMap = HashMap<String, Any>()
        val entityClass = entity.javaClass
        EntityParserUtil.getAllFields(entity).forEach { field ->
            val fieldName = field.name
            val getMethodName = EntityParserUtil.parseField2Getter(fieldName)
            try {
                val getMethod = entityClass.getMethod(getMethodName, null)
                val value = getMethod.invoke(entity, null)
                if (value != null) {
                    resultMap.put(field.name, value)
                }
            } catch (e: NoSuchMethodException) {
                logger.info("MyMongoOperator.parseEntity 未能获取实体 " + entityClass.simpleName + " 中字段 " + fieldName + "的值,选择跳过该字段")
            } catch (e: InvocationTargetException) {
                logger.info("MyMongoOperator.parseEntity 未能获取实体 " + entityClass.simpleName + " 中字段 " + fieldName + "的值,选择跳过该字段")
            } catch (e: IllegalAccessException) {
                logger.info("MyMongoOperator.parseEntity 未能获取实体 " + entityClass.simpleName + " 中字段 " + fieldName + "的值,选择跳过该字段")
            }
        }
        return resultMap
    }

    /**
     * 创建默认查询条件(只通过 id 值查找)
     * @param entity
     * @return
     */
    fun <T : BaseModel> createDefaultQuery(entity: T): Query {
        return Query.query(Criteria.where("_id").`is`(entity.code))
    }

    /**
     * 创建查询条件(通过所有有值的字段查找)
     * @param entity
     * @return
     */
    fun <T : BaseModel> createQueryByAllFields(entity: T): Query {
        return Query.query(Criteria().alike(Example.of(entity)))
    }


    /**
     * 自定义查询条件
     * @param entity
     * @return
     */
    fun <T : BaseModel> createFreeQuery(entity: T, function: Function<Map<String, Any>, Query>): Query {
        return function.apply(parseEntity(entity))
    }

    /**
     * 创建默认更新条目
     * @param entity
     * @return
     */
    fun <T : BaseModel> createDefaultUpdate(entity: T): Update {
        val fieldMap = parseEntity(entity)
        val update = Update()
        fieldMap.keys.forEach { field -> update.set(field, fieldMap[field]) }
        return update
    }

    /**
     * 自定义更新条目
     * @param entity
     * @return
     */
    fun <T : BaseModel> createFreeUpdate(entity: T, function: Function<Map<String, Any>, Update>): Update {
        return function.apply(parseEntity(entity))
    }

    /**
     * 自定义更新条目
     * @param update
     * @return
     */
    fun createFreeUpdate(update: Update, function: Function<Update, Update>): Update {
        return function.apply(update)
    }

    /**
     * 基础-添加
     */
    fun <T : BaseModel> insert(entity: T): Int {
        try {
            entity.createBy = MyShiroSessionUtil.getUserCodeFromSession()
            if (entity.code != null && entity.code!!.trim() == "") entity.code = null
            entity.createTime = DateUtil.getCreateDateString()
            mongoTemplate.insert(entity, getCollectionName(entity))
            return 1
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.insert 插入失败")
        }
    }

    /**
     * 基础-批量添加
     */
    fun <T : BaseModel> insertBatch(list: List<T>): Int {
        try {
            list.forEach {
                if (it.code != null && it.code!!.trim() == "") it.code = null
                it.createBy = MyShiroSessionUtil.getUserCodeFromSession()
                it.createTime = DateUtil.getCreateDateString()
            }
            val collectionName = list[0].javaClass.simpleName
            mongoTemplate.insert(list, collectionName)
            return list.size
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.insertBatch 批量插入失败")
        }
    }

    /**
     * 基础-条件更新
     */
    fun <T : BaseModel> update(entity: T): Int {
        try {
            entity.modifyBy = MyShiroSessionUtil.getUserCodeFromSession()
            entity.modifyTime = DateUtil.getModifyDateString()
            return mongoTemplate.updateFirst(createDefaultQuery(entity),
                    createDefaultUpdate(entity), entity.javaClass, getCollectionName(entity)).n
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.update 更新失败")
        }
    }

    /**
     * 基础-自定义更新单体
     */
    fun <T : BaseModel> freeUpdateOne(query: Query, update: Update, clazz: Class<T>): Int {
        try {
            return mongoTemplate.updateFirst(query, update, clazz, clazz.simpleName).n
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.freeUpdateOne 更新失败")
        }

    }

    /**
     * 基础-自定义更新多个
     */
    fun <T : BaseModel> freeUpdateMulti(query: Query, update: Update, clazz: Class<T>): Int {
        try {
            return mongoTemplate.updateMulti(query, update, clazz, clazz.simpleName).n
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.freeUpdateMulti 更新失败")
        }

    }

    /**
     * 基础-通过id删除
     */
    fun <T : BaseModel> removeById(id: String, typeClass: Class<T>): Int {
        try {
            return mongoTemplate.remove(Query.query(Criteria.where("_id").`is`(id)), typeClass, typeClass.simpleName).n
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.removeById 删除失败")
        }

    }

    /**
     * 基础-批量删除
     */
    fun <T : BaseModel> removeBatch(list: List<String>, typeClass: Class<T>): Int {
        try {
            return mongoTemplate.findAllAndRemove(Query.query(Criteria.where("_id").`in`(list)), typeClass, typeClass.simpleName).size
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.removeBatch 批量删除失败")
        }

    }

    /**
     * 基础-删除所有
     */
    fun <T : BaseModel> removeAll(typeClass: Class<T>): Int {
        try {
            return mongoTemplate.remove(Query.query(Criteria.where("_class").`is`(typeClass.typeName)), typeClass.simpleName).n
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.removeAll 删除所有失败")
        }
    }

    /**
     * 自定义删除
     */
    fun <T : BaseModel> freeRemove(query: Query, clazz: Class<T>): Int {
        try {
            return mongoTemplate.remove(query, clazz, clazz.simpleName).n
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.freeRemove 自定义删除失败")
        }
    }

    /**
     * 删除并返回删除项
     */
    fun <T : BaseModel> freeFindAllAndRemove(query: Query, clazz: Class<T>): List<T> {
        try {
            return mongoTemplate.findAllAndRemove(query, clazz, clazz.simpleName)
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.freeRemove 自定义删除失败")
        }
    }

    /**
     * 基础-根据id查找
     */
    fun <T : BaseModel> findById(id: String, typeClass: Class<T>): T {
        try {
            return mongoTemplate.findById(id, typeClass, typeClass.simpleName)
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.findById 查询失败")
        }
    }

    /**
     * 基础-条件查找所有
     */
    fun <T : BaseModel> findListByParams(entity: T): List<T> {
        val query = createQueryByAllFields(entity)
        try {
            return mongoTemplate.find(query, entity.javaClass, getCollectionName(entity))
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.findListByParams 查询失败")
        }
    }

    /**
     * 基础-查找所有
     */
    fun <T : BaseModel> exFindAll(typeClass: Class<T>): List<T> {
        try {
            return mongoTemplate.findAll(typeClass, typeClass.simpleName)
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.exFindAll 查询失败")
        }

    }

    /**
     * 基础-根据id批量查找
     */
    fun <T : BaseModel> findInIds(ids: List<String>, typeClass: Class<T>): List<T> {
        try {
            return mongoTemplate.find(Query.query(Criteria.where("_id").`in`(ids)), typeClass, typeClass.simpleName)
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.findInIds 查询失败")
        }
    }

    /**
     * 基础-分页查询
     */
    fun <T : BaseModel> findPage(pageResult: PageResult<T>): PageResult<T> {
        val skip = pageResult.skip
        val entity = pageResult.entity
        val query = createQueryByAllFields(entity)
        if (StringUtils.isNotBlank(pageResult.startTime)) query.addCriteria(Criteria.where("createTime").gt(pageResult.startTime))
        if (StringUtils.isNotBlank(pageResult.endTime)) query.addCriteria(Criteria.where("createTime").lt(pageResult.endTime))
        try {
            val count = mongoTemplate.count(query, entity.javaClass, getCollectionName(entity))
            pageResult.totalCount = count
            var list: List<T> = ArrayList()
            if (skip < count) {
                list = mongoTemplate.find(
                        query.with(pageResult.pageRequest),
                        entity.javaClass as Class<T>, getCollectionName(entity)
                )
            }
            pageResult.rows = list
            return pageResult
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.findPage 查询失败")
        }

    }

    /**
     * 自定义分页查询
     */
    fun <T : BaseModel> freeFindPage(pageResult: PageResult<T>, query: Query): PageResult<T> {
        val skip = pageResult.skip
        val entity = pageResult.entity
        if (StringUtils.isNotBlank(pageResult.startTime)) query.addCriteria(Criteria.where("createTime").gt(pageResult.startTime))
        if (StringUtils.isNotBlank(pageResult.endTime)) query.addCriteria(Criteria.where("createTime").lt(pageResult.endTime))
        try {
            val count = mongoTemplate.count(query, entity.javaClass, getCollectionName(entity))
            pageResult.totalCount = count
            var list: List<T> = ArrayList()
            if (skip < count) {
                list = mongoTemplate.find(
                        query.with(pageResult.pageRequest),
                        entity.javaClass as Class<T>, getCollectionName(entity)
                )
            }
            pageResult.rows = list
            return pageResult
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.freeFindPage 查询失败")
        }

    }

    /**
     * 自定义查找单体
     */
    fun <T : BaseModel> freeFindOne(query: Query, typeClass: Class<T>): T {
        try {
            return mongoTemplate.findOne(query, typeClass, typeClass.simpleName)
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.freeFindOne 查询失败")
        }

    }

    /**
     * 自定义查找集合
     */
    fun <T : BaseModel> freeFindList(query: Query, typeClass: Class<T>): List<T> {
        try {
            return mongoTemplate.find(query, typeClass, typeClass.simpleName)
        } catch (e: Exception) {
            throw MyMongoOperatorException(e, "MyMongoOperator.freeFindList 查询失败")
        }
    }

}