package cn.gateon.library.jpa.specification;

import java.util.Date;

/**
 * <p>
 * 条件构造器
 * 拼接where 后面的sql
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface Where<F> {

    /**
     * sql:   =
     */
    Where<F> eq(String property, Object value);

    /**
     * sql:   !=
     */
    Where<F> neq(String property, Object value);

    /**
     * sql:   in()
     */
    Where<F> in(String property, Object... value);

    /**
     * sql:   not in()
     */
    Where<F> nin(String property, Object... value);

    /**
     * sql:   >=
     */
    Where<F> gte(String property, Number value);

    /**
     * sql:   >
     */
    Where<F> gt(String property, Number value);

    /**
     * sql:   find_in_set()
     */
    Where<F> findInSet(String property, String value);

    /**
     * sql:   <=
     */
    Where<F> lte(String property, Number value);

    /**
     * sql:   <
     */
    Where<F> lt(String property, Number value);

    /**
     * type:  date or time
     * sql:   >=
     */
    Where<F> start(String property, Date start);

    /**
     * type:  date or time
     * sql:   <=
     */
    Where<F> end(String property, Date end);

    /**
     * sql:  (isNull?'':'not')  is null
     */
    Where<F> isNull(String property, boolean isNull);

    /**
     * @param position -1 左模糊   0 全模糊  1 右模糊
     */
    Where<F> like(String property, String value, int position);

}
