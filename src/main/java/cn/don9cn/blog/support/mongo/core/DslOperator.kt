package cn.booklish.mongodsl.core

import cn.don9cn.blog.support.mongo.ext.eq
import cn.don9cn.blog.support.mongo.ext.query
import cn.don9cn.blog.support.mongo.exception.DslOperationException
import com.mongodb.MongoClient
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

class DslOperator(mongoClient: MongoClient,database:String){

    val mongoTemplate = MongoTemplate(mongoClient,database)

    operator fun <T:Any> invoke(operation:DslOperator.() -> T):T{
        return operation()
    }

    /**
     * 解析entity实体,将其属性及其对应的值映射到map中
     */
    fun <T:Any> parseEntityToMap(entity:T):Map<String,Any>{
        val map = hashMapOf<String,Any>()
        val kclass = entity.javaClass.kotlin
        kclass.memberProperties.forEach { property ->
            if(property.findAnnotation<Id>()==null){
                property.get(entity)?.let {
                    map[property.name] = it
                }
            }
        }
        return map
    }

    /**
     * 创建默认(根据id)的query实体
     */
    fun createDefaultQuery(id:String): Query {
        return Query.query(Criteria.where("_id").`is`(id))
    }

    /**
     * 创建query实体
     */
    fun <T:Any> createQueryByEntity(entity:T): Query {
        val map = parseEntityToMap(entity)
        val query = Query()
        for((key,value) in map){
            query.addCriteria(Criteria.where(key).`is`(value))
        }
        return query
    }

    /**
     * 创建默认(全非空字段更新)的update实体
     */
    fun <T:Any> createDefaultUpdate(entity:T): Update {
        val map = parseEntityToMap(entity)
        return Update().apply {
            for((key,value) in map){
                set(key,value)
            }
        }
    }

    /**
     * 添加操作: 插入一条数据
     */
    fun <T:Any> insertOne(entity:T): Int {
        try{
            mongoTemplate.insert(entity,entity.javaClass.simpleName)
            return 1
        }catch (e:Exception){
            throw DslOperationException("insertOne 插入失败", e)
        }
    }

    /**
     * 添加操作: 插入一条数据
     */
    fun <T:Any> insertOne(operation:() -> T): Int {
        try{
            val entity = operation()
            mongoTemplate.insert(entity,entity.javaClass.simpleName)
            return 1
        }catch (e:Exception){
            throw DslOperationException("insertOne 插入失败", e)
        }
    }

    /**
     * 添加操作: 插入多条数据
     */
    fun <T:Any> insertBatch(list:List<T>): Int {
        try{
            mongoTemplate.insert(list,list[0].javaClass.simpleName)
            return list.size
        }catch (e:Exception){
            throw DslOperationException("insertBatch 插入失败", e)
        }
    }

    /**
     * 添加操作: 插入多条数据
     */
    fun <T:Any> insertBatch(operation:() -> List<T>): Int {
        try{
            val list = operation()
            mongoTemplate.insert(list,list[0].javaClass.simpleName)
            return list.size
        }catch (e:Exception){
            throw DslOperationException("insertBatch 插入失败", e)
        }
    }

    /**
     * 更新操作: 根据id
     */
    fun <T:Any> updateById(id:String,entity:T): Int {
        try{
            return mongoTemplate.updateFirst(createDefaultQuery(id),createDefaultUpdate(entity),
                    entity.javaClass,entity.javaClass.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("updateById 更新失败", e)
        }
    }

    /**
     * 更新操作: 根据id
     */
    inline fun <reified T:Any> updateById(id:String,update: Update): Int {
        try{
            return mongoTemplate.updateFirst(createDefaultQuery(id),update,
                    T::class.java,T::class.java.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("updateById 更新失败", e)
        }
    }

    /**
     * 更新操作: 根据id
     */
    fun <T:Any> updateById(id:String,update: Update,clazz: Class<T>): Int {
        try{
            return mongoTemplate.updateFirst(createDefaultQuery(id),update,clazz,clazz.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("updateById 更新失败", e)
        }
    }

    /**
     * 更新操作: 根据自定义的查询条件,更新一条数据
     */
    inline fun <reified T:Any> updateOne(query: Query, update: Update): Int {
        try{
            return mongoTemplate.updateFirst(query,update, T::class.java,T::class.java.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("updateOne 更新失败", e)
        }
    }

    /**
     * 更新操作: 根据自定义的查询条件,更新一条数据
     */
    fun <T:Any> updateOne(query: Query, update: Update, clazz: Class<T>): Int {
        try{
            return mongoTemplate.updateFirst(query,update, clazz,clazz.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("updateOne 更新失败", e)
        }
    }

    /**
     * 更新操作: 根据自定义的查询条件,更新一条数据
     */
    fun <T:Any> updateOne(query: Query, entity: T): Int {
        try{
            return mongoTemplate.updateFirst(query,createDefaultUpdate(entity), entity.javaClass,entity.javaClass.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("updateOne 更新失败", e)
        }
    }

    /**
     * 更新操作: 根据自定义的查询条件,更新多条数据
     */
    inline fun <reified T:Any> updateMulti(query: Query, update: Update): Int {
        try{
            return mongoTemplate.updateMulti(query,update, T::class.java,T::class.java.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("updateMulti 更新失败", e)
        }
    }

    /**
     * 更新操作: 根据自定义的查询条件,更新多条数据
     */
    fun <T:Any> updateMulti(query: Query, update: Update, clazz: Class<T>): Int {
        try{
            return mongoTemplate.updateMulti(query,update,clazz,clazz.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("updateMulti 更新失败", e)
        }
    }

    /**
     * 更新操作: 根据自定义的查询条件,更新多条数据
     */
    fun <T:Any> updateMulti(query: Query, entity: T): Int {
        try{
            return mongoTemplate.updateMulti(query,createDefaultUpdate(entity), entity.javaClass,entity.javaClass.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("updateMulti 更新失败", e)
        }
    }

    /**
     * 删除操作: 根据id
     */
    inline fun <reified T:Any> removeById(id:String): Int {
        try{
            return mongoTemplate.remove(createDefaultQuery(id),T::class.java,T::class.java.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("removeById 删除失败", e)
        }
    }

    /**
     * 删除操作: 根据id
     */
    fun <T:Any> removeById(id:String,clazz: Class<T>): Int {
        try{
            return mongoTemplate.remove(createDefaultQuery(id),clazz,clazz.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("removeById 删除失败", e)
        }
    }

    /**
     * 删除操作: 根据自定义查询条件
     */
    inline fun <reified T:Any> remove(query: Query): Int {
        try{
            return mongoTemplate.remove(query,T::class.java,T::class.java.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("remove 删除失败", e)
        }
    }

    /**
     * 删除操作: 根据自定义查询条件
     */
    fun <T:Any> remove(query: Query,clazz: Class<T>): Int {
        try{
            return mongoTemplate.remove(query,clazz,clazz.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("remove 删除失败", e)
        }
    }

    /**
     * 删除操作: 根据class类型
     */
    fun <T:Any> removeByClazz(clazz: Class<T>): Int {
        try{
            return mongoTemplate.remove(Query.query(Criteria.where("_class").`is`(clazz.typeName)),clazz.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("removeByClazz 删除失败", e)
        }
    }

    /**
     * 删除操作: 根据实体
     */
    fun <T:Any> removeByEntity(entity: T): Int {
        try{
            return mongoTemplate.remove(createQueryByEntity(entity),entity.javaClass,entity.javaClass.simpleName).n
        }catch (e:Exception){
            throw DslOperationException("removeByEntity 删除失败", e)
        }
    }

    /**
     * 删除操作: 根据id删除并返回删除的实体
     */
    inline fun <reified T:Any> findAndRemoveById(id:String): T? {
        try{
            return mongoTemplate.findAndRemove(createDefaultQuery(id),T::class.java,T::class.java.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findAndRemoveById 删除失败", e)
        }
    }

    /**
     * 删除操作: 根据id删除并返回删除的实体
     */
    fun <T:Any> findAndRemoveById(id:String,clazz: Class<T>): T? {
        try{
            return mongoTemplate.findAndRemove(createDefaultQuery(id),clazz,clazz.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findAndRemoveById 删除失败", e)
        }
    }

    /**
     * 删除操作: 根据自定义查询条件删除并返回删除的实体
     */
    inline fun <reified T:Any> findAndRemove(query: Query): List<T> {
        try{
            return mongoTemplate.findAllAndRemove(query,T::class.java,T::class.java.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findAndRemove 删除失败", e)
        }
    }

    /**
     * 删除操作: 根据自定义查询条件删除并返回删除的实体
     */
    fun <T:Any> findAndRemove(query: Query,clazz: Class<T>): List<T> {
        try{
            return mongoTemplate.findAllAndRemove(query,clazz,clazz.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findAndRemove 删除失败", e)
        }
    }

    /**
     * 删除操作: 根据class类型删除并返回删除的实体
     */
    fun <T:Any> findAndRemoveByClazz(clazz: Class<T>): List<T> {
        try{
            return mongoTemplate.findAllAndRemove(Query.query(Criteria.where("_class").`is`(clazz.typeName)),clazz.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findAndRemoveByClazz 删除失败", e)
        }
    }

    /**
     * 删除操作: 根据实体条件删除并返回删除的实体
     */
    fun <T:Any> findAndRemoveByEntity(entity: T): List<T> {
        try{
            return mongoTemplate.findAllAndRemove(createQueryByEntity(entity),entity.javaClass,entity.javaClass.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findAndRemoveByEntity 删除失败", e)
        }
    }

    /**
     * 查找操作: 根据id
     */
    inline fun <reified T:Any> findById(id: String): T? {
        try{
            return mongoTemplate.findById(id,T::class.java,T::class.java.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findById 查找失败", e)
        }
    }

    /**
     * 查找操作: 根据id
     */
    fun <T:Any> findById(id: String,clazz:Class<T>): T? {
        try{
            return mongoTemplate.findById(id,clazz,clazz.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findById 查找失败", e)
        }
    }

    /**
     * 查找操作: 根据自定义查询条件
     */
    inline fun <reified T:Any> findOne(query: Query): T? {
        try{
            return mongoTemplate.findOne(query,T::class.java,T::class.java.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findOne 查找失败", e)
        }
    }

    /**
     * 查找操作: 根据自定义查询条件
     */
    fun <T:Any> findOne(query: Query,clazz:Class<T>): T? {
        try{
            return mongoTemplate.findOne(query,clazz,clazz.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findOne 查找失败", e)
        }
    }

    /**
     * 查找操作: 根据自定义查询条件
     */
    inline fun <reified T:Any> findList(query: Query): List<T> {
        try{
            return mongoTemplate.find(query,T::class.java,T::class.java.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findList 查找失败", e)
        }
    }

    /**
     * 查找操作: 根据自定义查询条件
     */
    fun <T:Any> findList(query: Query,clazz:Class<T>): List<T> {
        try{
            return mongoTemplate.find(query,clazz,clazz.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findList 查找失败", e)
        }
    }

    /**
     * 查找操作: 根据自定义查询条件
     */
    fun <T:Any> findListByClass(clazz:Class<T>): List<T> {
        try{
            return mongoTemplate.find(query("_class" eq clazz.typeName),clazz,clazz.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findList 查找失败", e)
        }
    }

    /**
     * 查找操作: 根据自定义查询条件
     */
    fun <T:Any> findListByEntity(entity:T): List<T> {
        try{
            return mongoTemplate.find(createQueryByEntity(entity),entity.javaClass,entity.javaClass.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findList 查找失败", e)
        }
    }

    /**
     * 查找操作: 根据自定义查询条件
     */
    inline fun <reified T:Any> findListInIds(ids: List<String>): List<T> {
        try{
            return mongoTemplate.find(Query.query(Criteria.where("_id").`in`(ids)),T::class.java,T::class.java.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findListInIds 查找失败", e)
        }
    }

    /**
     * 查找操作: 根据自定义查询条件
     */
    fun <T:Any> findListInIds(ids: List<String>,clazz:Class<T>): List<T> {
        try{
            return mongoTemplate.find(Query.query(Criteria.where("_id").`in`(ids)),clazz,clazz.simpleName)
        }catch (e:Exception){
            throw DslOperationException("findListInIds 查找失败", e)
        }
    }

    /**
     * 查找操作: 分页查询
     */
    fun <T:Any> findPage(page:PageResult<T>):PageResult<T>{
        val entity = page.entity?:throw DslOperationException("findPage 查找失败,PageResult中参数实体entity为空")
        val query = createQueryByEntity(entity)
        page.startTime?.let { query.addCriteria(Criteria.where("createTime").gt(it)) }
        page.endTime?.let { query.addCriteria(Criteria.where("createTime").lt(it)) }
        try {
            val count = mongoTemplate.count(query,entity.javaClass,entity.javaClass.simpleName)
            page.totalCount = count
            if(page.skip < count){
                page.rows = mongoTemplate.find(query.with(page.pageRequest),entity.javaClass,entity.javaClass.simpleName)
            }
            return page
        }catch (e:Exception){
            throw DslOperationException("findPage 查找失败", e)
        }
    }

    /**
     * 查找操作: 分页查询
     */
    inline fun <reified T:Any> findPage(query: Query,page:PageResult<T>): PageResult<T> {
        page.startTime?.let { query.addCriteria(Criteria.where("createTime").gt(it)) }
        page.endTime?.let { query.addCriteria(Criteria.where("createTime").lt(it)) }
        try {
            val count = mongoTemplate.count(query,T::class.java,T::class.java.simpleName)
            page.totalCount = count
            if(page.skip < count){
                page.rows = mongoTemplate.find(query.with(page.pageRequest),T::class.java,T::class.java.simpleName)
            }
            return page
        }catch (e:Exception){
            throw DslOperationException("findPage 查找失败", e)
        }
    }

    /**
     * 查找操作: 分页查询
     */
    fun <T:Any> findPage(query: Query,page:PageResult<T>,clazz:Class<T>): PageResult<T> {
        page.startTime?.let { query.addCriteria(Criteria.where("createTime").gt(it)) }
        page.endTime?.let { query.addCriteria(Criteria.where("createTime").lt(it)) }
        try {
            val count = mongoTemplate.count(query,clazz,clazz.simpleName)
            page.totalCount = count
            if(page.skip < count){
                page.rows = mongoTemplate.find(query.with(page.pageRequest),clazz,clazz.simpleName)
            }
            return page
        }catch (e:Exception){
            throw DslOperationException("findPage 查找失败", e)
        }
    }



}