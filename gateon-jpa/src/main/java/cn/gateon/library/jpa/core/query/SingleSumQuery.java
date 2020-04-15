package cn.gateon.library.jpa.core.query;

import cn.gateon.library.jpa.core.SumQueryer;

import javax.persistence.EntityManager;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class SingleSumQuery<F> extends BaseQuery<F, Long> implements SumQueryer<F> {

    public SingleSumQuery(EntityManager entityManager, Class<F> from) {
        super(entityManager, from, Long.class);

    }

    @Override
    public SumQueryer<F> sum(String property) {
        query.select(cb.sum(root.get(property)));
        return this;
    }
}
