package cn.gateon.library.jpa.repo;

import cn.gateon.library.jpa.searcher.MultiSumSearcher;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
class MultiSumSearcherImpl<R> extends AbstractSearcherImpl<R> implements MultiSumSearcher<R> {

    private final EntityManager entityManager;

    private CriteriaQuery<R> query;

    private Root<?> root;

    MultiSumSearcherImpl(EntityManager entityManager, CriteriaBuilder criteriaBuilder, Class<?> from, Class<R> resultClass) {
        super(criteriaBuilder);
        this.entityManager = entityManager;
        this.query = criteriaBuilder.createQuery(resultClass);
        this.root = query.from(from);
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
