package cn.gateon.library.jpa.repo;

import cn.gateon.library.jpa.searcher.MultiSumSearcher;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import java.util.ArrayList;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
class MultiSumSearcherImpl<R> extends AbstractSearcherImpl<R> implements MultiSumSearcher<R> {

    private final EntityManager entityManager;

    MultiSumSearcherImpl(EntityManager entityManager, CriteriaBuilder criteriaBuilder, Class<?> from, Class<R> resultClass) {
        super(criteriaBuilder, from, criteriaBuilder.createQuery(resultClass), new ArrayList<>());
        this.entityManager = entityManager;
    }

    @Override
    public R multiSum(String... fields) {
        if (fields.length == 0) {
            throw new IllegalArgumentException("求和的字段不能为空");
        }
        Expression<?>[] ex = new Expression[fields.length];
        for (int i = 0; i < fields.length; i++) {
            ex[i] = cb.coalesce(cb.sumAsLong(root.get(fields[i])), 0L);
        }
        query.multiselect(ex);
        TypedQuery<R> query = entityManager.createQuery(this.query);
        return query.getSingleResult();
    }
}
