package cn.don9cn.blog.dao;

import cn.don9cn.blog.annotation.MapperNameSpace;
import cn.don9cn.blog.plugins.daohelper.core.DaoHelper;
import cn.don9cn.blog.plugins.daohelper.constant.SqlConstant;
import cn.don9cn.blog.plugins.daohelper.core.PageParamsBean;
import cn.don9cn.blog.plugins.daohelper.core.PageResult;
import cn.don9cn.blog.plugins.daohelper.util.FieldParserUtil;
import com.github.pagehelper.PageHelper;
import org.mybatis.spring.SqlSessionTemplate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: 基础dao接口,定义并默认实现了基础的增删改查操作
 * @Create: don9 2017/10/8
 * @Modify:
 */
public interface BaseDao<T extends Serializable> {

    default SqlSessionTemplate getSessionTemplate(){
        return DaoHelper.getSqlSessionTemplate();
    }

    default String getSqlName(String sqlName){
        String nameSpace = this.getDefaultSqlNameSpace();
        return nameSpace + SqlConstant.SEPERATOR + sqlName;
    }

    default String getDefaultSqlNameSpace(){
        Type type = this.getClass().getGenericInterfaces()[0];
        ParameterizedType type2 = (ParameterizedType) type;
        Type[] actualTypeArguments = type2.getActualTypeArguments();
        Type actualTypeArgument = actualTypeArguments[0];
        Class<T> clazz = (Class<T>) actualTypeArgument;
        MapperNameSpace annotation = clazz.getAnnotation(MapperNameSpace.class);
        String namespace = annotation.namespace();
        return namespace;
    }

    /**
     * 添加实体对象
     * @param entity
     * @return
     */
    default Optional<Integer> insert(T entity){
        return Optional.of(this.getSessionTemplate().insert(this.getSqlName(SqlConstant.INSERT),entity));
    }

    /**
     * 批量添加实体对象
     * @return
     */
    default Optional<Integer> insertBatch(final List<T> list){
        return Optional.of(this.getSessionTemplate().insert(this.getSqlName(SqlConstant.INSERT_BATCH),list));
    }

    /**
     * 通过id更新实体
     * @param entity
     * @return
     */
    default Optional<Integer> update(T entity){
        return Optional.of(this.getSessionTemplate().update(this.getSqlName(SqlConstant.UPDATE),entity));
    }

    /**
     * 通过id删除实体
     * @param id
     * @return
     */
    default Optional<Integer> deleteById(String id){
        return Optional.of(this.getSessionTemplate().delete(this.getSqlName(SqlConstant.DELETE),id));
    }

    /**
     * 批量删除实体对象
     * @return
     */
    default Optional<Integer> deleteBatch(List<String> list){
        return Optional.of(this.getSessionTemplate().update(this.getSqlName(SqlConstant.DELETE_BATCH),list));
    }

    /**
     * 通过id查找实体
     * @param id
     * @return
     */
    default Optional<T> findById(String id){
        return Optional.ofNullable(this.getSessionTemplate().selectOne(this.getSqlName(SqlConstant.FIND_BY_ID),id));
    }

    /**
     * 查询所有数据
     * @return
     */
    default Optional<List<T>> findAll(){
        return Optional.ofNullable(this.getSessionTemplate().selectList(this.getSqlName(SqlConstant.FIND_ALL)));
    }

    /**
     * 查询指定条件的实体集合
     * @param entity
     * @return
     */
    default Optional<List<T>> findListByParams(T entity){
        return Optional.ofNullable(this.getSessionTemplate().selectList(this.getSqlName(SqlConstant.FIND_LIST_BY_PARAMS),entity));
    }

    /**
     * 带参数的分页查询
     * @param pageParamsBean
     * @return
     */
    default Optional<PageResult<T>> findByPage(PageParamsBean<T> pageParamsBean){
        String sidx = pageParamsBean.getSidx();
        String sord = pageParamsBean.getSord();
        int page = pageParamsBean.getPage();
        int size = pageParamsBean.getSize();
        T clazz = pageParamsBean.getT();
        sidx = FieldParserUtil.parse2DbName(sidx);
        sidx = sidx + " " + sord;
        page =(("null".equals(String.valueOf(page)))||("0".equals(String.valueOf(page))))?SqlConstant.DEFAULT_CURRENT_PAGE:page;
        size =(("null".equals(String.valueOf(size)))||("0".equals(String.valueOf(size))))?SqlConstant.DEFAULT_CURRENT_PAGE:size;
        PageHelper.startPage(page, size, sidx);
        return PageResult.build(Optional.ofNullable(this.getSessionTemplate().selectList(this.getSqlName(SqlConstant.FIND_BY_PAGE), clazz)));
    }

}
