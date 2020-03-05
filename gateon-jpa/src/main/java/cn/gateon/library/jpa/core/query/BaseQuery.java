package cn.gateon.library.jpa.core.query;

import cn.gateon.library.common.data.Page;
import cn.gateon.library.common.data.PageRequest;
import cn.gateon.library.jpa.core.CollectionQueryer;
import cn.gateon.library.jpa.core.Queryer;
import cn.gateon.library.jpa.core.SubQueryer;
import cn.gateon.library.jpa.core.jpa.ConvertFunction;
import cn.gateon.library.jpa.specification.Having;
import cn.gateon.library.jpa.specification.Or;
import cn.gateon.library.jpa.specification.Where;
import cn.gateon.library.jpa.specification.impl.OrWhereImpl;
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
public abstract class BaseQuery<F, R> implements Queryer<F, R> {

    Class<R> result;

    private final EntityManager entityManager;

    CriteriaBuilder cb;

    final Root<F> root;

    CriteriaQuery<R> query;

    private List<Order> orders = new ArrayList<>();

    private List<Predicate> where = new ArrayList<>();

    private List<Predicate> having;

    BaseQuery(EntityManager entityManager, Class<F> from, Class<R> result) {
        this.entityManager = entityManager;
        this.result = result;
        this.cb = entityManager.getCriteriaBuilder();
        this.query = cb.createQuery(result);
        this.root = this.query.from(from);
    }

    /**
     * 如需要额外构造或自定义构造，覆写此方法
     */
    protected void beforeQuery() {
    }

    private TypedQuery<R> createQuery() {
        beforeQuery();
        if (!CollectionUtils.isEmpty(having)) {
            Predicate[] predicateArray = new Predicate[this.having.size()];
            query.having(this.having.toArray(predicateArray));
        }
        if (!CollectionUtils.isEmpty(where)) {
            Predicate[] predicateArray = new Predicate[this.where.size()];
            query.where(this.where.toArray(predicateArray));
        }
        if (!CollectionUtils.isEmpty(orders)) {
            query.orderBy(orders);
        }
        return entityManager.createQuery(query);
    }


    @Override
    public Where<F> where() {
        return new WhereImpl<>(cb, root, where);
    }

    @Override
    public Or<F> or() {
        return new OrWhereImpl<>(cb, root, where);
    }


    @Override
    public Having<F> having() {
        this.having = new ArrayList<>();
        return new WhereImpl<>(cb, root, having);
    }

    @Override
    public Where<F> join(String property) {
        Join<F, R> join = root.join(property);
        return new WhereImpl<>(cb, join, where);
    }

    @Override
    public <Z> CollectionQueryer<Z> joinCollection(String property) {
        CollectionJoin<F, Z> objectObjectCollectionJoin = root.joinCollection(property);
        return new CollectionQuery<>(objectObjectCollectionJoin, cb);
    }

    @Override
    public Page<R> page(PageRequest pageRequest) {
        String sort = pageRequest.getSort();
        if (!StringUtils.isEmpty(sort)) {
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
        count.select(cb.count(root)).distinct(true);
        join(count);
        build(count);
        TypedQuery<Long> countQuery = entityManager.createQuery(count);
        return countQuery.getSingleResult();
    }

    @Override
    public Queryer<F,R> orderBy(String property, boolean asc) {
        orders.add(new OrderImpl(root.get(property), asc));
        return this;
    }

    @Override
    public Queryer<F,R> orderBy(String property, boolean asc, String convertCharset) {
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
        return new DefaultSubQuery<>(new WhereImpl<>(cb, sfRoot, where), subquery);
    }


    private void join(CriteriaQuery<?> criteriaQuery) {
        Root<?> from = criteriaQuery.from(root.getJavaType());
        if (!CollectionUtils.isEmpty(root.getJoins())) {
            for (Join<F, ?> join : root.getJoins()) {
                from.join(join.getAttribute().getName(), join.getJoinType());
            }
        }
    }

    private AbstractQuery<?> build(AbstractQuery<?> query) {
        Predicate[] predicateArray = new Predicate[this.where.size()];
        Predicate[] predicates = this.where.toArray(predicateArray);
        query.where(predicates);
        return query;
    }
}
