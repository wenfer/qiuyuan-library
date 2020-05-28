package cn.gateon.library.jpa.searcher;

import cn.gateon.library.common.data.Page;
import cn.gateon.library.common.data.PageRequest;
import cn.gateon.library.jpa.specification.Where;

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
