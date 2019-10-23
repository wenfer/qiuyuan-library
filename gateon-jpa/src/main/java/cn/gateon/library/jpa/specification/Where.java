package cn.gateon.library.jpa.specification;


import cn.gateon.library.jpa.enums.Position;

import java.util.Date;

/**
 * <p>
 * <F> from的表
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface Where<F> {

    /**
     * 构建or查询
     * or().is("p","1").is("p","2")
     */
    Where<F> or();

    Where<F> is(String property, Object value);

    default Where<F> is(boolean condition, String property, Object value) {
        return condition ? is(property, value) : this;
    }

    /**
     * A列 大等于 B列
     */
    Where<F> gteAtoB(String propertyA, String propertyB);

    Where<F> in(String property, Object... value);

    Where<F> isNull(String property, boolean isNull);

    Where<F> like(String property, String value, Position position);

    default Where<F> like(boolean condition, String property, String value, Position position) {
        return condition ? like(property, value, position) : this;
    }

    Where<F> gte(String property, Number value);

    Where<F> gt(String property, Number value);

    Where<F> lte(String property, Number value);

    Where<F> lt(String property, Number value);

    /**
     * 起始时间查询
     */
    Where<F> start(String property, Date time);

    default Where<F> start(boolean condition, String property, Date date) {
        return condition ? start(property, date) : this;
    }


    /**
     * 结束时间查询
     */
    Where<F> end(String property, Date time);

    default Where<F> end(boolean condition, String property, Date date) {
        return condition ? end(property, date) : this;
    }


}
