package cn.gateon.library.jpa.core.criterion;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface Searcher<T> extends Criterion<T>{

    List<T> find();
}
