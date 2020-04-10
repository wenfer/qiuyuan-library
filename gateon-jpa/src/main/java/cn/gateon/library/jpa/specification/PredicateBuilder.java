package cn.gateon.library.jpa.specification;

import javax.persistence.criteria.Subquery;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2
 */
public interface PredicateBuilder<F> {

    /**
     * sql:   =
     */
    PredicateBuilder<F> eq(String property, Object value);

    /**
     * sql:   !=
     */
    PredicateBuilder<F> neq(String property, Object value);

    /**
     * sql:   in()
     */
    PredicateBuilder<F> in(String property, Collection<?> value);

    /**
     * sql:   not in()
     */
    PredicateBuilder<F> nin(String property, Collection<?> value);

    /**
     * sql:   >=
     */
    PredicateBuilder<F> gte(String property, Number value);

    /**
     * sql:   >
     */
    PredicateBuilder<F> gt(String property, Number value);

    /**
     * sql:   find_in_set()
     */
    PredicateBuilder<F> findInSet(String property, String value);

    /**
     * sql:   <=
     */
    PredicateBuilder<F> lte(String property, Number value);

    /**
     * sql:   <
     */
    PredicateBuilder<F> lt(String property, Number value);

    /**
     * type:  date or time
     * sql:   >=
     */
    PredicateBuilder<F> start(String property, Date start);

    /**
     * type:  date or time
     * sql:   <=
     */
    PredicateBuilder<F> end(String property, Date end);

    /**
     * sql:  (isNull?'':'not')  is null
     */
    PredicateBuilder<F> isNull(String property, boolean isNull);

    /**
     * <code>
     * SubQueryer<Product> subqueryer = query.subquery("id",F.class,R.class);
     * subqueryer.where().eq("","")
     * <p>
     * baseQueryer.where().any("id",subquery.build());
     * </code>
     */
    <S> PredicateBuilder<F> any(String property, Subquery<S> subQuery);

    /**
     * @param position -1 左模糊   0 全模糊  1 右模糊
     */
    PredicateBuilder<F> like(String property, String value, int position);

}
