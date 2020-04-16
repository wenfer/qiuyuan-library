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
import javax.persistence.NoResultException;
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

    protected final EntityManager entityManager;

    CriteriaBuilder cb;

    Root<F> root;

    CriteriaQuery<R> query;

    private List<Order> orders = new ArrayList<>();

    private List<Predicate> where = new ArrayList<>();

    private List<Predicate> having;

    BaseQuery(EntityManager entityManager, Class<F> from, Class<R> result) {
        this.entityManager = entityManager;
        this.result = result;
        this.cb = entityManager.getCriteriaBuilder();
        this.query = cb.createQuery(result);
        this.root = query.from(from);
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
        query.distinct(true);
        return new WhereImpl<>(cb, join, where);
    }

    @Override
    public <Z> CollectionQueryer<Z> joinCollection(String property) {
        Join<F, Z> objectObjectCollectionJoin = root.join(property, JoinType.LEFT);
        query.distinct(true);
        return new CollectionQuery<>(objectObjectCollectionJoin, cb, where);
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
        CriteriaQuery<Long> count = cb.createQuery(Long.class);
        count.select(cb.count(root));
        joinQuery(count);
        build(count);
        TypedQuery<Long> countQuery = entityManager.createQuery(count);
        return new Page<>(resultList, pageRequest, countQuery.getSingleResult());
    }

    /**
     * 这里有个坑，就是在拼接predicates的时候  root 使用的是model本身  所以别名是提前构建好的
     * 但是这里在createQuery的时候  会重新创建一个别名 导致之前的predicates 全部对应不上
     * 所以如果是基于本身的查询 在进行count的时候不会有这个问题  但是直接createQuery的时候就会报错
     *
     * @return
     */


    @Override
    public Queryer<F, R> orderBy(String property, boolean asc) {
        orders.add(new OrderImpl(root.get(property), asc));
        return this;
    }

    @Override
    public Queryer<F, R> orderBy(String property, boolean asc, String convertCharset) {
        ConvertFunction convertFunction = new ConvertFunction(cb, root.get(property).as(String.class), convertCharset);
        orders.add(new OrderImpl(convertFunction, asc));
        return this;
    }

    @Override
    public R findOne() {
        try {
            return createQuery().getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean exists() {
        return false;
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


    protected void joinQuery(CriteriaQuery<?> criteriaQuery) {
        Root<?> from = criteriaQuery.from(root.getJavaType());
        if (!CollectionUtils.isEmpty(root.getJoins())) {
            query.distinct(true);
            for (Join<F, ?> join : root.getJoins()) {
                from.join(join.getAttribute().getName(), join.getJoinType());
            }
        }
    }

    protected AbstractQuery<?> build(AbstractQuery<?> query) {
        Predicate[] predicateArray = new Predicate[this.where.size()];
        Predicate[] predicates = this.where.toArray(predicateArray);
        query.where(predicates);
        return query;
    }
}
