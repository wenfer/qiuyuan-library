package site.qiuyuan.library.jpa.searcher;

import java.util.Collection;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
public interface JoinCollectionQuery<T> {

    JoinCollectionQuery<T> is(T value);

    JoinCollectionQuery<T> in(Collection<T> values);

}
