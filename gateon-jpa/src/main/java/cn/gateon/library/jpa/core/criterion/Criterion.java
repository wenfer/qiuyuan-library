package cn.gateon.library.jpa.core.criterion;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface Criterion<T> {

    Criterion<T> is(String property, Object value);

}
