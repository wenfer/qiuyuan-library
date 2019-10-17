package cn.gateon.library.jpa.core.query;

import cn.gateon.library.common.data.Page;
import cn.gateon.library.common.data.PageRequest;
import cn.gateon.library.jpa.core.Queryer;
import cn.gateon.library.jpa.core.SubQueryer;
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

    private List<Predicate> predicates = new ArrayList<>();

    BaseQuery(EntityManager entityManager, Class<F> from, Class<R> result) {
        this.entityManager = entityManager;
        this.result = result;
        this.cb = entityManager.getCriteriaBuilder();
        this.query = cb.createQuery(result);
        Root<F> root = this.query.from(from);
        this.where = new WhereImpl<>(cb, root, predicates);
    }

    private TypedQuery<R> query() {
        Predicate[] predicateArray = new Predicate[this.predicates.size()];
        Predicate[] predicates = this.predicates.toArray(predicateArray);
        query.where(predicates);
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
        Join<R,?> join = root.join(property);
        return new WhereImpl<>(cb, join, predicates);
    }



    @Override
    public Page<R> page(PageRequest pageRequest) {
        String sort = pageRequest.getSort();
        if(StringUtils.isEmpty(sort)){
            query.orderBy(new OrderImpl(root.get(sort),pageRequest.getAsc()));
        }
        query().setMaxResults(pageRequest.getSize());
        query().setFirstResult(pageRequest.getPage() * pageRequest.getSize());
        List<R> resultList = query().getResultList();
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
    public R findOne() {
        return query().getSingleResult();
    }

    @Override
    public List<R> findAll() {
        return query().getResultList();
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
