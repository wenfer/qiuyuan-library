package cn.gateon.library.jpa.core.query;

import cn.gateon.library.jpa.core.CountQueryer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.3
 */
public class CountQuery<F> extends BaseQuery<F, Long> implements CountQueryer<F> {

    public CountQuery(EntityManager entityManager, Class<F> from) {
        super(entityManager, from,Long.class);

    }

    @Override
    public long count() {
        query.select(cb.count(root));
        //joinQuery(query);
        build(query);
        TypedQuery<Long> countQuery = entityManager.createQuery(query);
        return countQuery.getSingleResult();
    }
}
