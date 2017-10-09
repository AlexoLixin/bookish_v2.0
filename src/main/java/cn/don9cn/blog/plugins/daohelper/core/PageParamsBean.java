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
    private final String sidx;
    private final String sord;
    private final T t;


    public PageParamsBean(int page, int size, String sidx, String sord, T t) {
        this.page = page;
        this.size = size;
        this.sidx = sidx;
        this.sord = sord;
        this.t = t;
    }


    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getSidx() {
        return sidx;
    }

    public String getSord() {
        return sord;
    }

    public T getT() {
        return t;
    }
}
