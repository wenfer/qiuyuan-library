package cn.gateon.library.jpa.specification;

import cn.gateon.library.jpa.enums.OperatorEnum;
import cn.gateon.library.jpa.specification.impl.WhereImpl;

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

    default OperatorEnum operator() {
        return OperatorEnum.AND;
    }

    default Where and() {
        return new WhereImpl();
    }

    /**
     * sql:   =
     */
    PredicateBuilder eq(String property, Object value);

    /**
     * sql:   !=
     */
    PredicateBuilder neq(String property, Object value);

    /**
     * sql:   in()
     */
    PredicateBuilder in(String property, Collection<?> value);

    /**
     * sql:   not in()
     */
    PredicateBuilder nin(String property, Collection<?> value);

    /**
     * sql:   >=
     */
    PredicateBuilder gte(String property, Number value);

    /**
     * sql:   >
     */
    PredicateBuilder gt(String property, Number value);

    /**
     * sql:   find_in_set()
     */
    PredicateBuilder findInSet(String property, String value);

    /**
     * sql:   <=
     */
    PredicateBuilder lte(String property, Number value);

    /**
     * sql:   <
     */
    PredicateBuilder lt(String property, Number value);

    /**
     * type:  date or time
     * sql:   >=
     */
    PredicateBuilder start(String property, Date start);

    /**
     * type:  date or time
     * sql:   <=
     */
    PredicateBuilder end(String property, Date end);

    /**
     * sql:  (isNull?'':'not')  is null
     */
    PredicateBuilder isNull(String property, boolean isNull);

    /**
     * <code>
     * SubQueryer<Product> subqueryer = query.subquery("id",F.class,R.class);
     * subqueryer.where().eq("","")
     * <p>
     * baseQueryer.where().any("id",subquery.build());
     * </code>
     */
    <S> PredicateBuilder any(String property, Subquery<S> subQuery);

    /**
     * @param position -1 左模糊   0 全模糊  1 右模糊
     */
    PredicateBuilder like(String property, String value, int position);

}
