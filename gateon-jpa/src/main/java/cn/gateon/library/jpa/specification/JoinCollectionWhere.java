package cn.gateon.library.jpa.specification;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

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
