package cn.gateon.library.jpa.specification;

import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface Where<F> {

    Where<F> eq(String property, Object value);

    Where<F> neq(String property, Object value);

    Where<F> in(String property, Object...value);

    Where<F> nin(String property, Object...value);

    Where<F> gte(String property, Number value);

    Where<F> gt(String property, Number value);

    Where<F> findInSet(String property, String value);

    Where<F> lte(String property, Number value);

    Where<F> lt(String property, Number value);

    Where<F> start(String property, Date start);

    Where<F> end(String property, Date end);

    Where<F> isNull(String property, boolean isNull);

    Where<F> like(String property, String value, int position);

}
