package site.qiuyuan.library.jpa.searcher;

import site.qiuyuan.library.common.data.Page;
import site.qiuyuan.library.common.data.PageRequest;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
public interface Searcher<R> extends Conditional {

    List<R> find();

    R findOne();

    long count();


    boolean exists();

    Page<R> page(PageRequest request);
}
