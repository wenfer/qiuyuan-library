package cn.gateon.library.jpa.core.impl;

import cn.gateon.library.jpa.core.Sql;
import cn.gateon.library.jpa.core.criterion.Searcher;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class DefaultSearcher<T> extends BaseCriterion<T> implements Searcher<T> {

    private CriteriaQuery<T> query;

    private EntityManager entityManager;

    private final Root<T> root;

    public DefaultSearcher(EntityManager entityManager, CriteriaBuilder cb, Class<T> clazz) {
        super(new Sql(cb));
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        this.query = criteriaBuilder.createQuery(clazz);
        this.root = query.from(clazz);
        this.entityManager = entityManager;
    }

    private TypedQuery<T> buildQuery() {
        sql.build(query);
        return entityManager.createQuery(this.query);
    }

    @Override
    public List<T> find() {
        return buildQuery().getResultList();
    }

    @Override
    protected Root<T> getRoot() {
        return root;
    }
}
