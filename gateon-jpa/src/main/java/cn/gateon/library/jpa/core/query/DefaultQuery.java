package cn.gateon.library.jpa.core.query;

import javax.persistence.EntityManager;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class DefaultQuery<T> extends BaseQuery<T, T> {

    public DefaultQuery(EntityManager entityManager, Class<T> resultClass) {
        super(entityManager, resultClass, resultClass);
    }


}
