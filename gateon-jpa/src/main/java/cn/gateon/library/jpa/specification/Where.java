package cn.gateon.library.jpa.specification;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface Where<F> {

    Where<F> is(String property, Object value);

    Where<F> gte(String property, Number value);

    Where<F> lte(String property, Number value);

}
