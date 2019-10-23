package cn.gateon.library.common.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author qiuyuan
 * @created 2018-08-30 15:01
 * @Description:
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

    public Page(List<T> list, PageRequest request, long total) {
        this(list, request.getPage() + 1, request.getSize(), total);
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
