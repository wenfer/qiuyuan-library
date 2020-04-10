package cn.gateon.library.jpa.core;

import java.util.Collection;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.1.4
 */
public interface CollectionQueryer<C> {

    CollectionQueryer<C> eq(C value);

    CollectionQueryer<C> in(Collection<C> values);


}
