package cn.don9cn.blog.plugins.daohelper.core;


import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
     * 跳过
     */
    private Integer skip;
    /**
     * 总记录数
     */
    private long totalCount;
    /**
     * 总页数
     */
    private Integer total;
    /**
     * 排序字段
     */
    private String orderField;
    /**
     * 排序方式
     */
    private Sort.Direction orderTurn;
    /**
     * 参数实体
     */
    private final T entity;
    /**
     * 查询结果集
     */
    private List<T> rows;

    private PageRequest pageRequest;

    public PageResult(Integer nowPage, Integer pageSize, String orderBy, T entity) {
        this.nowPage = nowPage;
        this.pageSize = pageSize;
        this.entity = entity;
        this.skip = (nowPage-1) * pageSize;
        parseOrderBy(orderBy);
        this.pageRequest = new PageRequest(this.getNowPage()-1,this.getPageSize(),this.getOrderTurn(),this.getOrderField());
    }

    private void parseOrderBy(String orderBy) {
        if(StringUtils.isNotBlank(orderBy)){
            String[] split = orderBy.split(" ");
            this.orderField = split[0];
            if(split[1].equalsIgnoreCase("asc")){
                this.orderTurn = Sort.Direction.ASC;
            }else if(split[1].equalsIgnoreCase("desc")){
                this.orderTurn = Sort.Direction.DESC;
            }else{
                this.orderTurn = Sort.Direction.DESC;
            }
        }else{
            this.orderField = "createTime";
            this.orderTurn = Sort.Direction.DESC;
        }
    }


    public Integer getNowPage() {
        return nowPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public T getEntity() {
        return entity;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Sort.Direction getOrderTurn() {
        return orderTurn;
    }

    public void setOrderTurn(Sort.Direction orderTurn) {
        this.orderTurn = orderTurn;
    }

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }
}
