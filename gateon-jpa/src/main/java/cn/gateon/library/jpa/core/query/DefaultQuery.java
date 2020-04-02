package cn.gateon.library.jpa.core.query;

import javax.persistence.EntityManager;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class DefaultQuery<F,R> extends BaseQuery<F, R> {

    public DefaultQuery(EntityManager entityManager,Class<F> fromClass, Class<R> resultClass) {
        super(entityManager, fromClass, resultClass);
    }


}
