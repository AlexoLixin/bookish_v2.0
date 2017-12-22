package cn.don9cn.blog.action;

import java.util.List;

/**
 * @Author: liuxindong
 * @Description: 基础action,定义了基础的增删改查
 * @Create: 2017/10/9 15:23
 * @Modify:
 */
public abstract class BaseAction<T> {

    protected abstract Object baseInsert(T t);

    protected abstract Object baseInsertBatch(List<T> list);

    protected abstract Object baseUpdate(T t);

    protected abstract Object baseRemove(String code);

    protected abstract Object baseRemoveBatch(String codes);

    protected abstract Object baseFindById(String code);

    protected abstract Object baseFindAll();

    protected abstract Object baseFindListByParams(T t);

    protected abstract Object baseFindByPage(int page, int limit,String startTime,String endTime, String orderBy, T t);

}
