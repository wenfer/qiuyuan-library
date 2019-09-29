package cn.gateon.library.jpa.core.query;

import javax.persistence.EntityManager;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class SingleSumQuery<F> extends BaseQuery<F, Long> {

    public SingleSumQuery(String property, EntityManager entityManager, Class<F> from, Class<Long> result) {
        super(entityManager, from, result);
        query.select(cb.sum(root.get(property)));
    }

}
