package cn.gateon.library.jpa.specification;

import javax.persistence.criteria.Subquery;
import java.util.Collection;
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
public interface Where extends PredicateBuilder {

    OperatorEnum operator();

    static Where and() {
        return new WhereImpl(OperatorEnum.AND);
    }

    static Where or() {
        return new WhereImpl(OperatorEnum.OR);
    }


    /**
     * sql:   =
     */
    Where eq(String property, Object value);

    /**
     * sql:   !=
     */
    Where neq(String property, Object value);

    /**
     * sql:   in()
     */
    Where in(String property, Collection<?> value);

    /**
     * sql:   not in()
     */
    Where nin(String property, Collection<?> value);

    /**
     * sql:   >=
     */
    Where gte(String property, Number value);

    /**
     * sql:   >
     */
    Where gt(String property, Number value);

    /**
     * sql:   find_in_set()
     */
    Where findInSet(String property, String value);

    /**
     * sql:   <=
     */
    Where lte(String property, Number value);

    /**
     * sql:   <
     */
    Where lt(String property, Number value);

    /**
     * type:  date or time
     * sql:   >=
     */
    Where start(String property, Date start);

    /**
     * type:  date or time
     * sql:   <=
     */
    Where end(String property, Date end);

    /**
     * sql:  (isNull?'':'not')  is null
     */
    Where isNull(String property, boolean isNull);

    /**
     * <code>
     * SubQueryer<Product> subqueryer = query.subquery("id",F.class,R.class);
     * subqueryer.where().eq("","")
     * <p>
     * baseQueryer.where().any("id",subquery.build());
     * </code>
     */
    <S> Where any(String property, Subquery<S> subQuery);

    /**
     * @param position -1 左模糊   0 全模糊  1 右模糊
     */
    Where like(String property, String value, int position);

}
