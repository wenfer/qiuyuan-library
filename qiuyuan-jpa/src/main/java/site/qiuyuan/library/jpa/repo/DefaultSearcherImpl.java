package site.qiuyuan.library.jpa.repo;

import site.qiuyuan.library.common.data.Page;
import site.qiuyuan.library.common.data.PageRequest;
import site.qiuyuan.library.jpa.searcher.Searcher;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.NonUniqueResultException;
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
@Slf4j
class DefaultSearcherImpl<R> extends AbstractSearcherImpl<R> implements Searcher<R> {

    private List<Order> orders = new ArrayList<>();

    private Class<R> beanClass;

    private final EntityManager entityManager;

    DefaultSearcherImpl(EntityManager entityManager, CriteriaBuilder criteriaBuilder, Class<R> beanClass) {
        super(criteriaBuilder);
        this.entityManager = entityManager;
        this.beanClass = beanClass;
    }

    @Override
    public List<R> find() {
        CriteriaQuery<R> query = cb.createQuery(beanClass);
        Root<R> root = query.from(beanClass);
        return createQuery(query, root).getResultList();
    }

    @Override
    public R findOne() {
        CriteriaQuery<R> query = cb.createQuery(beanClass);
        Root<R> root = query.from(beanClass);
        List<R> resultList = createQuery(query, root).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        if (resultList.size() > 1) {
            log.error("此条数据不唯一,请及时处理");
            throw new NonUniqueResultException(resultList.size());
        }
        return resultList.get(0);
    }

    @Override
    public long count() {
        CriteriaQuery<Long> count = cb.createQuery(Long.class);
        Root<?> root = count.from(beanClass);
        count.select(cb.count(root));
        List<Predicate> predicates = buildPredicate(root);
        if (!CollectionUtils.isEmpty(predicates)) {
            Predicate[] predicateArray = new Predicate[predicates.size()];
            count.where(predicates.toArray(predicateArray));
        }
        TypedQuery<Long> countQuery = entityManager.createQuery(count);
        return countQuery.getSingleResult();
    }


    @Override
    public boolean exists() {
        CriteriaQuery<Boolean> exists = cb.createQuery(Boolean.class);
        Root<R> root = exists.from(beanClass);
        exists.select(cb.gt(cb.count(root), 0));
        List<Predicate> predicates = buildPredicate(root);
        if (!CollectionUtils.isEmpty(predicates)) {
            Predicate[] predicateArray = new Predicate[predicates.size()];
            exists.where(predicates.toArray(predicateArray));
        }
        TypedQuery<Boolean> existsQuery = entityManager.createQuery(exists);
        return existsQuery.getSingleResult();
    }

    @Override
    public Page<R> page(PageRequest pageRequest) {
        String sort = pageRequest.getSort();
        CriteriaQuery<R> query = cb.createQuery(beanClass);
        Root<R> root = query.from(beanClass);
        if (!StringUtils.isEmpty(sort)) {
            orders.add(new OrderImpl(root.get(sort), pageRequest.getAsc()));
        }
        TypedQuery<R> typedQuery = createQuery(query, root);
        if (!CollectionUtils.isEmpty(orders)) {
            query.orderBy(orders);
        }
        typedQuery.setMaxResults(pageRequest.getSize());
        typedQuery.setFirstResult(pageRequest.getPage() * pageRequest.getSize());
        List<R> resultList = typedQuery.getResultList();
        return new Page<>(resultList, pageRequest, count());
    }


    private TypedQuery<R> createQuery(CriteriaQuery<R> query, From<?, ?> root) {
        List<Predicate> predicates = buildPredicate(root);
        if (!CollectionUtils.isEmpty(predicates)) {
            Predicate[] predicateArray = new Predicate[predicates.size()];
            query.where(predicates.toArray(predicateArray));
        }
        if (!CollectionUtils.isEmpty(orders)) {
            query.orderBy(orders);
        }
        return entityManager.createQuery(query);
    }


}
