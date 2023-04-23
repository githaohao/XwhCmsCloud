/**
 *
 */
package com.xwh.core.dao;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author xwh
 * 数据分页显示中，当前页的信息,默认每页15条
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> {
    //	public static final int PAGE_INDEX = 1;
    //	public static final int PAGE_SIZE = 20;
    private static final long serialVersionUID = 1L;

    // 查询参数
    private Object params;
    // 总的数据数
    private long total;
    // 当前页
    private long index;
    // 每页的大小
    private long size;
    // 当前页数据
    private List<T> list;

    @JsonIgnore
    protected List<T> records;
    @JsonIgnore
    protected long current;
    @JsonIgnore
    protected List<OrderItem> orders;
    @JsonIgnore
    protected boolean optimizeCountSql;
    @JsonIgnore
    protected boolean searchCount;
    @JsonIgnore
    protected boolean optimizeJoinOfCountSql;
    @JsonIgnore
    protected String countId;
    @JsonIgnore
    protected Long maxLimit;
    @JsonIgnore
    protected Long pages;

    public Page() {
    }

    // 支持外部可以设置查询的页大小
    private static final ThreadLocal<Integer> PageSize = new ThreadLocal<>();

    public Page(Long index, Long size) {
        this.index = index;
        super.setCurrent(index);
        super.setSize(size);
    }

    public Object getParams() {
        return params;
    }

    public long getIndex() {
        return super.getCurrent();
    }

    public List<T> getList() {
        return super.getRecords();
    }


    public void setIndex(long index) {
        super.setCurrent(index);
        this.index = index;
    }


}
