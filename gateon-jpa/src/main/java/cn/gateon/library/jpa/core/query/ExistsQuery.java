package cn.gateon.library.jpa.core.query;

import cn.gateon.library.jpa.core.ExistsQueryer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class ExistsQuery<F> extends BaseQuery<F, Boolean> implements ExistsQueryer<F> {
    public ExistsQuery(EntityManager entityManager, Class<F> from) {
        super(entityManager, from, Boolean.class);
    }

    @Override
    public boolean exists() {
        query.select(cb.gt(cb.count(root), 0));
        //joinQuery(query);
        build(query);
        TypedQuery<Boolean> countQuery = entityManager.createQuery(query);
        return countQuery.getSingleResult();
    }
}
