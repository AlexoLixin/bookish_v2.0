package cn.don9cn.blog.action;

import cn.don9cn.blog.plugins.operaresult.core.OperaResult;

import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 基础action,定义了基础的增删改查
 * @Create: 2017/10/9 15:23
 * @Modify:
 */
public abstract class BaseAction<T> {

    protected abstract OperaResult baseInsert(T t);

    protected abstract OperaResult baseInsertBatch(List<T> list);

    protected abstract OperaResult baseUpdate(T t);

    protected abstract OperaResult baseRemove(String code);

    protected abstract OperaResult baseRemoveBatch(String codes);

    protected abstract OperaResult baseFindById(String code);

    protected abstract OperaResult baseFindAll();

    protected abstract OperaResult baseFindListByParams(T t);

    protected abstract OperaResult baseFindByPage(int page, int limit,String startTime,String endTime, String orderBy, T t);

}
