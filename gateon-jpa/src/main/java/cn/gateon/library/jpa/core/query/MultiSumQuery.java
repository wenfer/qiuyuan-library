package cn.gateon.library.jpa.core.query;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import java.lang.reflect.Field;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class MultiSumQuery<F, R> extends BaseQuery<F, R> {

    public MultiSumQuery(EntityManager entityManager, Class<F> from, Class<R> result) {
        super(entityManager, from, result);
        init();
    }

    private void init() {
        Field[] fields = result.getFields();
        Expression<?>[] expressions = new Expression[fields.length];
        for (int i = 0; i < fields.length; i++) {
            expressions[i] = cb.sumAsLong(root.get(fields[i].getName()));
        }
        query.multiselect(expressions);
    }

}
