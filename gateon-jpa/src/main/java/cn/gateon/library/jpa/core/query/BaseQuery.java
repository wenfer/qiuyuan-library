package cn.gateon.library.jpa.core.query;

import cn.gateon.library.common.data.Page;
import cn.gateon.library.common.data.PageRequest;
import cn.gateon.library.jpa.core.CollectionQueryer;
import cn.gateon.library.jpa.core.Queryer;
import cn.gateon.library.jpa.core.SubQueryer;
import cn.gateon.library.jpa.core.jpa.ConvertFunction;
import cn.gateon.library.jpa.specification.Where;
import cn.gateon.library.jpa.specification.impl.WhereImpl;
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
 * @since 1.0
 */
public class BaseQuery<F, R> implements Queryer<R> {

    Class<R> result;

    private EntityManager entityManager;

    CriteriaBuilder cb;

    private Where where;

    Root<F> root;

    CriteriaQuery<R> query;

    private List<Order> orders = new ArrayList<>();

    private List<Predicate> predicates = new ArrayList<>();

    BaseQuery(EntityManager entityManager, Class<F> from, Class<R> result) {
        this.entityManager = entityManager;
        this.result = result;
        this.cb = entityManager.getCriteriaBuilder();
        this.query = cb.createQuery(result);
        Root<F> root = this.query.from(from);
        this.where = new WhereImpl<>(cb, root, predicates);
    }

    private TypedQuery<R> createQuery() {
        Predicate[] predicateArray = new Predicate[this.predicates.size()];
        Predicate[] predicates = this.predicates.toArray(predicateArray);
        query.where(predicates);
        if (CollectionUtils.isEmpty(orders)) {
            query.orderBy(orders);
        }
        return entityManager.createQuery(query);
    }

    @Override
    public void setWhere(Where where) {
        this.where = where;
    }

    @Override
    public Where where() {
        return where;
    }

    @Override
    public Where join(String property) {
        Join<R, ?> join = root.join(property);
        return new WhereImpl<>(cb, join, predicates);
    }

    @Override
    public <Z> CollectionQueryer<Z> joinCollection(String property) {
        CollectionJoin<F, Z> objectObjectCollectionJoin = root.joinCollection(property);

        return new CollectionQuery<>(objectObjectCollectionJoin,cb);
    }

    @Override
    public Page<R> page(PageRequest pageRequest) {
        String sort = pageRequest.getSort();
        if (StringUtils.isEmpty(sort)) {
            orders.add(new OrderImpl(root.get(sort), pageRequest.getAsc()));
        }
        TypedQuery<R> typedQuery = createQuery();
        typedQuery.setMaxResults(pageRequest.getSize());
        typedQuery.setFirstResult(pageRequest.getPage() * pageRequest.getSize());
        List<R> resultList = typedQuery.getResultList();
        return new Page<>(resultList, pageRequest, count());
    }

    @Override
    public long count() {
        CriteriaQuery<Long> count = cb.createQuery(Long.class);
        build(count);
        join(count);
        TypedQuery<Long> countQuery = entityManager.createQuery(count);
        return countQuery.getSingleResult();
    }

    @Override
    public Queryer<R> orderBy(String property, boolean asc) {
        orders.add(new OrderImpl(root.get(property), asc));
        return this;
    }

    @Override
    public Queryer<R> orderBy(String property, boolean asc, String convertCharset) {
        ConvertFunction convertFunction = new ConvertFunction(cb, root.get(property).as(String.class), convertCharset);
        orders.add(new OrderImpl(convertFunction, asc));
        return this;
    }

    @Override
    public R findOne() {
        return createQuery().getSingleResult();
    }

    @Override
    public List<R> findAll() {
        return createQuery().getResultList();
    }

    @Override
    public <SF, SR> SubQueryer<SR> subQuery(String property, Class<SF> from, Class<SR> result) {
        Subquery<SR> subquery = query.subquery(result);
        Root<SF> sfRoot = subquery.from(from);
        return new DefaultSubQuery<>(new WhereImpl<>(cb, sfRoot, predicates), subquery);
    }


    private void join(CriteriaQuery criteriaQuery) {
        Root from = criteriaQuery.from(root.getJavaType());
        if (!CollectionUtils.isEmpty(root.getJoins())) {
            for (Join<F, ?> join : root.getJoins()) {
                from.join(join.getAttribute().getName(), join.getJoinType());
            }
        }
    }

    private AbstractQuery build(AbstractQuery query) {
        Predicate[] predicateArray = new Predicate[this.predicates.size()];
        Predicate[] predicates = this.predicates.toArray(predicateArray);
        query.where(predicates);
        return query;
    }
}
