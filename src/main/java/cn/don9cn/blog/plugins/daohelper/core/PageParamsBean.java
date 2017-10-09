package cn.don9cn.blog.plugins.daohelper.core;

/**
 * @Author: liuxindong
 * @Description: 分页查询参数实体
 * @Create: 2017/10/9 14:01
 * @Modify:
 */
public class PageParamsBean<T> {

    private final int page;
    private final int size;
    private final T t;


    public PageParamsBean(int page, int size,  T t) {
        this.page = page;
        this.size = size;
        this.t = t;
    }


    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public T getT() {
        return t;
    }

}
