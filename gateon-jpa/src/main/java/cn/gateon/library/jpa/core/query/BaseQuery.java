package cn.gateon.library.jpa.core.query;

import cn.gateon.library.jpa.core.Queryer;
import cn.gateon.library.jpa.core.SubQueryer;
import cn.gateon.library.jpa.specification.Where;
import cn.gateon.library.jpa.specification.impl.WhereImpl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class BaseQuery<F, R> implements Queryer<F, R> {

    private Class<F> from;

    protected Class<R> result;

    protected EntityManager entityManager;

    protected CriteriaBuilder cb;

    private Where<F> where;

    protected Root<F> root;

    protected CriteriaQuery<R> query;

    public BaseQuery(EntityManager entityManager, Class<F> from, Class<R> result) {
        this.from = from;
        this.entityManager = entityManager;
        this.result = result;
        this.cb = entityManager.getCriteriaBuilder();
        this.query = cb.createQuery(result);
        this.query.from(from);
        this.where = new WhereImpl<>(cb, query.from(from));
    }

    @Override
    public void setWhere(Where<F> where) {
        this.where = where;
    }

    @Override
    public Where<F> where() {
        return where;
    }

    @Override
    public R findOne() {
        where.build(query);
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<R> findAll() {
        where.build(query);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public <SF, SR> SubQueryer<SF, SR> subQuery(String property, Class<SF> from, Class<SR> result) {
        Subquery<SR> subquery = query.subquery(result);
        Root<SF> sfRoot = subquery.from(from);
        return new DefaultSubQuery<>(new WhereImpl<>(cb,sfRoot));
    }
}
