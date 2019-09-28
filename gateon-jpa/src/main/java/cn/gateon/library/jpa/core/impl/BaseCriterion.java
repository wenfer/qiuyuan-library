package cn.gateon.library.jpa.core.impl;

import cn.gateon.library.jpa.core.Sql;
import cn.gateon.library.jpa.core.criterion.Criterion;

import javax.persistence.criteria.Root;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public abstract class BaseCriterion<T> implements Criterion<T> {

    protected Sql sql;

    public BaseCriterion(Sql sql) {
        this.sql = sql;
    }

    protected abstract Root<T> getRoot();

    @Override
    public Criterion<T> is(String property, Object value) {
        sql.where(criteriaBuilder -> criteriaBuilder.equal(getRoot().get(property), value));
        return this;
    }
}
