package cn.don9cn.blog.plugins.daohelper.core;

import cn.don9cn.blog.action.bussiness.articleclassify.ArticleClassifyAction;
import cn.don9cn.blog.autoconfigs.shiro.util.MyShiroSessionUtil;
import cn.don9cn.blog.exception.MyMongoOperatorException;
import cn.don9cn.blog.model.BaseModel;
import cn.don9cn.blog.model.system.rbac.SysUser;
import cn.don9cn.blog.util.DateUtil;
import cn.don9cn.blog.util.EntityParserUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Author: liuxindong
 * @Description: mongo操作类,对 MongoTemplate 进行了封装和拓展
 * @Create: 2017/10/13 10:15
 * @Modify:
 */
@SuppressWarnings("Duplicates")
@Configuration
@AutoConfigureAfter(value = {MongoTemplate.class})
public class MyMongoOperator {

    private MongoTemplate mongoTemplate;

    private static Logger logger = Logger.getLogger(ArticleClassifyAction.class);

    @Autowired
    MyMongoOperator(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    /**
     * 根据entity实体类型获取collectionName
     * @param entity
     * @param <T>
     * @return
     */
    private <T extends BaseModel> String getCollectionName(T entity){
        return entity.getClass().getSimpleName();
    }

    /**
     * 解析entity实体,提取其不为空的字段和对应的值
     * @param entity
     * @param <T>
     * @return
     */
    public <T extends BaseModel> Map<Field,Object> parseEntity(T entity) {
        Map<Field,Object> resultMap = new HashMap<>();
        Class<? extends Serializable> entityClass = entity.getClass();
        EntityParserUtil.getAllFields(entity).forEach(field -> {
            String fieldName = field.getName();
            String getMethodName = EntityParserUtil.parseField2Getter(fieldName);
            try {
                Method getMethod = entityClass.getMethod(getMethodName,null);
                Object value = getMethod.invoke(entity, null);
                if(value!=null){
                    resultMap.put(field,value);
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                logger.info("MyMongoOperator.parseEntity 未能获取实体 "+entityClass.getSimpleName()+" 中字段 " + fieldName + "的值,选择跳过该字段");
            }
        });
        return resultMap;
    }

    /**
     * 创建默认查询条件(只通过 id 值查找)
     * @param entity
     * @return
     */
    public <T extends BaseModel> Query createDefaultQuery(T entity){
        return Query.query(Criteria.where("_id").is(entity.getCode()));
    }

    /**
     * 创建查询条件(通过所有有值的字段查找)
     * @param entity
     * @return
     */
    public <T extends BaseModel> Query createQueryByAllFields(T entity){
        return Query.query(new Criteria().alike(Example.of(entity)));
    }


    /**
     * 自定义查询条件
     * @param entity
     * @return
     */
    public <T extends BaseModel> Query createFreeQuery(T entity, Function<Map<Field,Object>,Query> function){
        return function.apply(parseEntity(entity));
    }

    /**
     * 创建默认更新条目
     * @param entity
     * @return
     */
    public <T extends BaseModel> Update createDefaultUpdate(T entity){
        Map<Field,Object> fieldMap = parseEntity(entity);
        Update update = new Update();
        fieldMap.keySet().forEach(field -> update.set(field.getName(),fieldMap.get(field)));
        return update;
    }

    /**
     * 自定义更新条目
     * @param entity
     * @return
     */
    public <T extends BaseModel> Update createFreeUpdate(T entity, Function<Map<Field,Object>,Update> function){
        return function.apply(parseEntity(entity));
    }

    /**
     * 自定义更新条目
     * @param update
     * @return
     */
    public Update createFreeUpdate(Update update, Function<Update,Update> function){
        return function.apply(update);
    }

    /**
     * 基础-添加
     * @param entity
     * @param <T>
     * @return
     */
    public <T extends BaseModel> OptionalInt baseInsert(T entity) {
        try{
            entity.setCreateBy(MyShiroSessionUtil.getUserCodeFromSession());
            if(entity.getCode()!=null && entity.getCode().trim().equals("")) entity.setCode(null);
            entity.setCreateTime(DateUtil.getCreateDateString());
            mongoTemplate.insert(entity,getCollectionName(entity));
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.insert 插入失败");
        }
        int x = 1;
        return OptionalInt.of(x);
    }

    /**
     * 基础-批量添加
     * @param list
     * @param <T>
     * @return
     */
    public <T extends BaseModel> OptionalInt baseInsertBatch(List<T> list) {
        list.forEach(entity -> {
            if(entity.getCode()!=null && entity.getCode().trim().equals("")) entity.setCode(null);
            entity.setCreateBy(MyShiroSessionUtil.getUserCodeFromSession());
            entity.setCreateTime(DateUtil.getCreateDateString());
        });
        String collectionName = list.get(0).getClass().getSimpleName();
        try{
            mongoTemplate.insert(list,collectionName);
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.insertBatch 批量插入失败");
        }
        return OptionalInt.of(list.size());
    }

    /**
     * 基础-条件更新
     * @param entity
     * @param <T>
     * @return
     */
    public <T extends BaseModel> OptionalInt baseUpdate(T entity) {
        int x;
        try{
            entity.setModifyBy(MyShiroSessionUtil.getUserCodeFromSession());
            entity.setModifyTime(DateUtil.getModifyDateString());
            x = mongoTemplate.updateFirst(createDefaultQuery(entity),
                    createDefaultUpdate(entity),entity.getClass(),getCollectionName(entity)).getN();
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.update 更新失败");
        }
        return OptionalInt.of(x);
    }

    /**
     * 基础-自定义更新单体
     * @return
     */
    public <T extends BaseModel> OptionalInt freeUpdateOne(Query query,Update update,Class<T> clazz) {
        int x;
        try{
            x = mongoTemplate.updateFirst(query,update,clazz,clazz.getSimpleName()).getN();
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.freeUpdateOne 更新失败");
        }
        return OptionalInt.of(x);
    }

    /**
     * 基础-自定义更新多个
     * @return
     */
    public <T extends BaseModel> OptionalInt freeUpdateMulti(Query query,Update update,Class<T> clazz) {
        int x;
        try{
            x = mongoTemplate.updateMulti(query,update,clazz,clazz.getSimpleName()).getN();
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.freeUpdateMulti 更新失败");
        }
        return OptionalInt.of(x);
    }

    /**
     * 基础-通过id删除
     * @param id
     * @param typeClass
     * @return
     */
    public <T extends BaseModel> OptionalInt baseDeleteById(String id, Class<T> typeClass) {
        int x;
        try{
            x = mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)),typeClass,typeClass.getSimpleName()).getN();
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.baseDeleteById 删除失败");
        }
        return OptionalInt.of(x);
    }

    /**
     * 基础-批量删除
     * @param list
     * @param typeClass
     * @param <T>
     * @return
     */
    public <T extends BaseModel> OptionalInt baseDeleteBatch(List<String> list, Class<T> typeClass) {
        int x;
        try{
            x = mongoTemplate.findAllAndRemove(Query.query(Criteria.where("_id").in(list)),typeClass,typeClass.getSimpleName()).size();
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.baseDeleteBatch 批量删除失败");
        }
        return OptionalInt.of(x);
    }

    /**
     * 基础-删除所有
     * @param typeClass
     * @param <T>
     * @return
     */
    public <T extends BaseModel> OptionalInt baseDeleteAll(Class<T> typeClass) {
        int x;
        try{
            x = mongoTemplate.remove(Query.query(Criteria.where("_class").is(typeClass.getTypeName())),typeClass.getSimpleName()).getN();
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.baseDeleteAll 删除所有失败");
        }
        return OptionalInt.of(x);
    }

    /**
     * 自定义删除
     * @param query
     * @param clazz
     * @return
     */
    public <T extends BaseModel> OptionalInt freeDelete(Query query,Class<T> clazz) {
        int x;
        try{
            x = mongoTemplate.remove(query,clazz,clazz.getSimpleName()).getN();
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.freeDelete 自定义删除失败");
        }
        return OptionalInt.of(x);
    }

    /**
     * 基础-根据id查找
     * @param id
     * @param typeClass
     * @param <T>
     * @return
     */
    public <T extends BaseModel> Optional<T> baseFindById(String id, Class<T> typeClass) {
        try{
            return Optional.ofNullable(mongoTemplate.findById(id,typeClass,typeClass.getSimpleName()));
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.baseFindById 查询失败");
        }
    }

    /**
     * 基础-条件查找所有
     * @param entity
     * @param <T>
     * @return
     */
    public <T extends BaseModel> Optional<List<T>> baseFindListByParams(T entity) {
        Query query = createQueryByAllFields(entity);
        try{
            return Optional.ofNullable(mongoTemplate.find(query, (Class<T>) entity.getClass(),getCollectionName(entity)));
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.findListByParams 查询失败");
        }
    }

    /**
     * 基础-查找所有
     * @param <T>
     * @param typeClass
     * @return
     */
    public <T extends BaseModel> Optional<List<T>> baseFindAll(Class<T> typeClass) {
        try{
            return Optional.ofNullable(mongoTemplate.findAll(typeClass,typeClass.getSimpleName()));
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.findAll 查询失败");
        }
    }

    /**
     * 基础-根据id批量查找
     * @param <T>
     * @param typeClass
     * @return
     */
    public <T extends BaseModel> Optional<List<T>> baseFindInIds(List<String> ids,Class<T> typeClass) {
        try{
            return Optional.ofNullable(mongoTemplate.find(Query.query(Criteria.where("_id").in(ids)),typeClass,typeClass.getSimpleName()));
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.baseFindInIds 查询失败");
        }
    }

    /**
     * 基础-分页查询
     * @param pageResult
     * @param <T>
     * @return
     */
    public <T extends BaseModel> Optional<PageResult<T>> baseFindByPage(PageResult<T> pageResult) {
        Integer skip = pageResult.getSkip();
        T entity = pageResult.getEntity();
        Query query = createQueryByAllFields(entity);
        if(StringUtils.isNotBlank(pageResult.getStartTime())) query.addCriteria(Criteria.where("createTime").gt(pageResult.getStartTime()));
        if(StringUtils.isNotBlank(pageResult.getEndTime())) query.addCriteria(Criteria.where("createTime").lt(pageResult.getEndTime()));
        try{
            long count = mongoTemplate.count(query, entity.getClass(), getCollectionName(entity));
            pageResult.setTotalCount(count);
            List<T> list = new ArrayList<>();
            if(skip<count){
                list = mongoTemplate.find(
                        query.with(pageResult.getPageRequest()),
                        (Class<T>) entity.getClass(), getCollectionName(entity)
                );
            }
            pageResult.setRows(list);
            return Optional.of(pageResult);
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.findPage 查询失败");
        }

    }

    /**
     * 自定义分页查询
     * @param pageResult
     * @param query
     * @param <T>
     * @return
     */
    public <T extends BaseModel> Optional<PageResult<T>> freeFindByPage(PageResult<T> pageResult,Query query) {
        Integer skip = pageResult.getSkip();
        T entity = pageResult.getEntity();
        try{
            long count = mongoTemplate.count(query, entity.getClass(), getCollectionName(entity));
            pageResult.setTotalCount(count);
            List<T> list = new ArrayList<>();
            if(skip<count){
                list = mongoTemplate.find(
                        query.with(pageResult.getPageRequest()),
                        (Class<T>) entity.getClass(), getCollectionName(entity)
                );
            }
            pageResult.setRows(list);
            return Optional.of(pageResult);
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.freeFindByPage 查询失败");
        }

    }

    /**
     * 自定义查找单体
     * @param query
     * @param typeClass
     * @param <T>
     * @return
     */
    public <T extends BaseModel> Optional<T> freeFindOne(Query query,Class<T> typeClass){
        try{
            return Optional.ofNullable(mongoTemplate.findOne(query,typeClass,typeClass.getSimpleName()));
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.freeFindOne 查询失败");
        }
    }

    /**
     * 自定义查找集合
     * @param query
     * @param typeClass
     * @param <T>
     * @return
     */
    public <T extends BaseModel> Optional<List<T>> freeFindList(Query query,Class<T> typeClass){
        try{
            return Optional.ofNullable(mongoTemplate.find(query,typeClass,typeClass.getSimpleName()));
        }catch (Exception e){
            throw new MyMongoOperatorException(e,"MyMongoOperator.freeFindList 查询失败");
        }
    }


}
