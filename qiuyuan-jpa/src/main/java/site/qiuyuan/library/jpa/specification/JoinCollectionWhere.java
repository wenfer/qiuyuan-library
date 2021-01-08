package site.qiuyuan.library.jpa.specification;

import java.util.Collection;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0.1
 */
public interface JoinCollectionWhere extends PredicateBuilder {

    JoinCollectionWhere eq(Object value);

    JoinCollectionWhere in(Collection<?> values);
}
