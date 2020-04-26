package cn.gateon.library.jpa.repo;

import cn.gateon.library.common.data.Page;
import cn.gateon.library.common.data.PageRequest;
import cn.gateon.library.jpa.searcher.JoinQuery;
import cn.gateon.library.jpa.searcher.Searcher;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
class DefaultSearcherImpl<R> extends AbstractSearcherImpl<R> implements Searcher<R> {

    private List<Order> orders = new ArrayList<>();

    private final EntityManager entityManager;

    DefaultSearcherImpl(EntityManager entityManager, CriteriaBuilder criteriaBuilder, Class<R> beanClass) {
        super(criteriaBuilder, beanClass, criteriaBuilder.createQuery(beanClass), new ArrayList<>());
        this.entityManager = entityManager;
    }

    @Override
    public List<R> find() {
        return createQuery().getResultList();
    }

    @Override
    public R findOne() {
        return createQuery().getSingleResult();
    }

    @Override
    public long count() {
        CriteriaQuery<Long> count = cb.createQuery(Long.class);
        count.select(cb.count(root));
        if (!CollectionUtils.isEmpty(this.predicates)) {
            joinQuery(count);
            Predicate[] predicateArray = new Predicate[this.predicates.size()];
            count.where(this.predicates.toArray(predicateArray));
        }
        TypedQuery<Long> countQuery = entityManager.createQuery(count);
        return countQuery.getSingleResult();
    }

    @Override
    public boolean exists() {
        CriteriaQuery<Boolean> exists = cb.createQuery(Boolean.class);
        exists.select(cb.gt(cb.count(root), 0));
        if (!CollectionUtils.isEmpty(this.predicates)) {
            joinQuery(exists);
            Predicate[] predicateArray = new Predicate[this.predicates.size()];
            exists.where(this.predicates.toArray(predicateArray));
        }
        TypedQuery<Boolean> existsQuery = entityManager.createQuery(exists);
        return existsQuery.getSingleResult();
    }

    @Override
    public Page<R> page(PageRequest pageRequest) {
        String sort = pageRequest.getSort();
        if (!StringUtils.isEmpty(sort)) {
            orders.add(new OrderImpl(root.get(sort), pageRequest.getAsc()));
        }
        TypedQuery<R> typedQuery = createQuery();
        if (!CollectionUtils.isEmpty(orders)) {
            this.query.orderBy(orders);
        }
        typedQuery.setMaxResults(pageRequest.getSize());
        typedQuery.setFirstResult(pageRequest.getPage() * pageRequest.getSize());
        List<R> resultList = typedQuery.getResultList();
        return new Page<>(resultList, pageRequest, count());
    }


    protected void joinQuery(CriteriaQuery<?> criteriaQuery) {
        Root<?> from = criteriaQuery.from(root.getJavaType());
        if (!CollectionUtils.isEmpty(root.getJoins())) {
            query.distinct(true);
            for (Join<?, ?> join : root.getJoins()) {
                from.join(join.getAttribute().getName(), join.getJoinType());
            }
        }
    }


    private TypedQuery<R> createQuery() {
        if (!CollectionUtils.isEmpty(this.predicates)) {
            Predicate[] predicateArray = new Predicate[this.predicates.size()];
            query.where(this.predicates.toArray(predicateArray));
        }
        if (!CollectionUtils.isEmpty(orders)) {
            query.orderBy(orders);
        }
        return entityManager.createQuery(query);
    }

    @Override
    public JoinQuery join(String field) {
        Join<?, ?> join = root.join(field);
        return new JoinQueryImpl<>(cb, join, predicates);
    }
}
