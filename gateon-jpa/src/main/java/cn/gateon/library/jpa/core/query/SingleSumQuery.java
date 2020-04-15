package cn.gateon.library.jpa.core.query;

import cn.gateon.library.jpa.core.SingleSumQueryer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class SingleSumQuery<F> extends BaseQuery<F, Long> implements SingleSumQueryer<F> {

    public SingleSumQuery(EntityManager entityManager, Class<F> from) {
        super(entityManager, from, Long.class);

    }

    @Override
    public Long sum(String property) {
        query.select(cb.sum(root.get(property)));
        joinQuery(query);
        build(query);
        TypedQuery<Long> countQuery = entityManager.createQuery(query);
        return countQuery.getSingleResult();
    }
}
