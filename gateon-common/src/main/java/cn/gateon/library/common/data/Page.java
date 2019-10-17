package cn.gateon.library.common.data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author qiuyuan
 * 分页
 */
@Setter
@Getter
public class Page<T> implements Serializable {

    /**
     * 总条数
     */
    private long total;
    /**
     * 当前页数
     */
    private int currentPage;
    /**
     * 总页数
     */
    private long pages;
    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 是否有上一页
     */
    private boolean hasPrevious;
    /**
     * 是否有下一页
     */
    private boolean hasNext;
    /**
     * 数据
     */
    private List<T> records;

    public Page(List<T> list, PageRequest paging, long total) {
        this(list, paging.getPage() + 1, paging.getSize(), total);
    }

    /**
     * 没有构造函数，缓存会反解析失败
     */
    public Page() {
    }

    public Page(List<T> list, int page, int size, long total) {
        this.records = list;
        this.currentPage = page;
        this.pageSize = size;
        this.total = total;
        this.pages = size == 0 ? 1 : (long) Math.ceil((double) total / size);
        this.hasNext = this.pages > (this.currentPage+1);
        this.hasPrevious = this.currentPage > 1;
    }


    public <U> Page<U> convert(Function<? super T, ? extends U> function) {
        if (records == null) {
            return new Page<>(new ArrayList<>(), currentPage, pageSize, total);
        }
        return new Page<>(records.stream().map(function).collect(Collectors.toList()), currentPage, pageSize, total);
    }

}
