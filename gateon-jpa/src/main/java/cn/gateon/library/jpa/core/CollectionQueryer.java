package cn.gateon.library.jpa.core;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.1.4
 */
public interface CollectionQueryer<C> {

    CollectionQueryer<C> eq(C value);

    CollectionQueryer<C> in(Object... value);


}
