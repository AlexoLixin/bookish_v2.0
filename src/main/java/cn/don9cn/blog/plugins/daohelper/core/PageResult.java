package cn.don9cn.blog.plugins.daohelper.core;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: 分页查询结果
 * @Create: 2017/10/9 13:13
 * @Modify:
 */
public class PageResult<T> implements Serializable {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;
    /**
     * 当前页
     */
    private final Integer nowPage;
    /**
     * 每页记录数
     */
    private final Integer pageSize;
    /**
     * 开始下标
     */
    private final Integer startRow;
    /**
     * 结束下标
     */
    private final long endRow;
    /**
     * 总记录数
     */
    private final long totalCount;
    /**
     * 总页数
     */
    private final Integer total;
    /**
     * 排序字段
     */
    private String orderColumn;
    /**
     * 排序方式
     */
    private String orderTurn;
    /**
     * 查询结果集
     */
    private final List<T> rows;

    public PageResult(Integer nowPage, Integer pageSize,long totalCount,Integer total,List<T> rows) {
        this.nowPage = nowPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.total = total;
        this.startRow = nowPage > 0 ? (nowPage - 1) * pageSize : 0;
        this.endRow = nowPage * pageSize > totalCount ? totalCount : nowPage * pageSize;
        this.rows = rows;
    }

    public static <E> Optional<PageResult<E>> build(Optional<List<E>> optional){
        if(optional.isPresent()){
            Page<E> page = (Page) optional.get();
            PageResult<E> pageResult = new PageResult(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getPages(),page.getResult());
            return Optional.of(pageResult);
        }else{
            return Optional.empty();
        }
    }

    public Integer getNowPage() {
        return nowPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public long getEndRow() {
        return endRow;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public Integer getTotal() {
        return total;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderTurn() {
        return orderTurn;
    }

    public void setOrderTurn(String orderTurn) {
        this.orderTurn = orderTurn;
    }

    public List<T> getRows() {
        return rows;
    }
}
