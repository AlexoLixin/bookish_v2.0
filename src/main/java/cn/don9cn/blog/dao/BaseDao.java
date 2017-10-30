package cn.don9cn.blog.dao;

import cn.don9cn.blog.model.BaseModel;
import cn.don9cn.blog.plugins.daohelper.core.DaoHelper;
import cn.don9cn.blog.autoconfigs.mongodb.MyMongoOperator;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * @Author: liuxindong
 * @Description: 基础dao接口,定义并默认实现了基础的增删改查操作
 * @Create: don9 2017/10/8
 * @Modify:
 */
public interface BaseDao<T extends BaseModel> {

    /**
     * 获取 mongo 操作器
     * @return
     */
    default MyMongoOperator getMyMongoOperator(){
        return DaoHelper.getMyMongoOperator();
    }

    /**
     * 获取参数类型
     * @return
     */
    default Class<T> getTypeClass(){
        Type type = this.getClass().getInterfaces()[0].getGenericInterfaces()[0];
        ParameterizedType type2 = (ParameterizedType) type;
        Type[] actualTypeArguments = type2.getActualTypeArguments();
        Type actualTypeArgument = actualTypeArguments[0];
        Class<T> clazz = (Class<T>) actualTypeArgument;
        return clazz;
    }


    /**
     * 添加实体对象
     * @param entity
     * @return
     */
    default OptionalInt baseInsert(T entity){
        return getMyMongoOperator().insert(entity);
    }

    /**
     * 批量添加实体对象
     * @return
     */
    default OptionalInt baseInsertBatch(final List<T> list){
        return getMyMongoOperator().insertBatch(list);
    }

    /**
     * 条件更新实体
     * @param entity
     * @return
     */
    default OptionalInt baseUpdate(T entity){
        return getMyMongoOperator().update(entity);
    }

    /**
     * 通过id删除实体
     * @param id
     * @return
     */
    default OptionalInt baseDeleteById(String id){
        return getMyMongoOperator().removeById(id,getTypeClass());
    }

    /**
     * 批量删除实体对象
     * @return
     */
    default OptionalInt baseDeleteBatch(List<String> list){
        return getMyMongoOperator().removeBatch(list,getTypeClass());
    }

    /**
     * 通过id查找实体
     * @param id
     * @return
     */
    default Optional<T> baseFindById(String id){
        return getMyMongoOperator().findById(id,getTypeClass());
    }

    /**
     * 查询所有数据
     * @return
     */
    default Optional<List<T>> baseFindAll(){
        return getMyMongoOperator().exFindAll(getTypeClass());
    }

    /**
     * 查询指定条件的实体集合
     * @param entity
     * @return
     */
    default Optional<List<T>> baseFindListByParams(T entity){
        return getMyMongoOperator().findListByParams(entity);
    }

    /**
     * 带参数的分页查询
     * @param pageResult
     * @return
     */
    default Optional<PageResult<T>> baseFindByPage(PageResult<T> pageResult){
        return getMyMongoOperator().findPage(pageResult);
    }

    /**
     * 根据主键集合查询实体
     * @param ids
     * @return
     */
    default Optional<List<T>> baseFindListInIds(List<String> ids){
        return getMyMongoOperator().findInIds(ids,getTypeClass());
    }

}
