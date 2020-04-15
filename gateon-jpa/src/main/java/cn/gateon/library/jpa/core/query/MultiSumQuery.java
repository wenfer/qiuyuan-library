package cn.gateon.library.jpa.core.query;

import cn.gateon.library.jpa.core.SumQueryer;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class MultiSumQuery<F, R> extends BaseQuery<F, R> implements SumQueryer<F,R> {

    public MultiSumQuery(EntityManager entityManager, Class<F> from, Class<R> result) {
        super(entityManager, from, result);
    }

    private List<Expression<Long>> expressions = new ArrayList<>();

    @Override
    protected void beforeQuery() {
        Expression<?>[] ex = new Expression[expressions.size()];
        query.multiselect(expressions.toArray(ex));
    }

    @Override
    public SumQueryer<F,R> sum(String property) {
        Expression<Long> longExpression = cb.coalesce(cb.sumAsLong(root.get(property)), 0L);
        this.expressions.add(longExpression);
        return this;
    }


}
