package cn.don9cn.blog.action;

/**
 * @Author: liuxindong
 * @Description: 基础action,定义了基础的增删改查
 * @Create: 2017/10/9 15:23
 * @Modify:
 */
public abstract class BaseAction<T> {

    protected abstract Object doSave(T t);

    protected abstract Object doUpdate(T t);

    protected abstract Object doRemove(String codes);

    protected abstract Object doFindById(String code);

    protected abstract Object doFindAll();

    protected abstract Object doFindListByParams(T t);

    protected abstract Object doFindByPage(int page, int limit,T t);

}
