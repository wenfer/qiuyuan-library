package cn.gateon.library.jpa.specification.impl;

import cn.gateon.library.jpa.specification.Where;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class WhereImpl<F, R> implements Where {

    private final List<Predicate> predicates;

    private final CriteriaBuilder cb;

    private final From<F, R> root;

    public WhereImpl(CriteriaBuilder cb, From<F, R> root, List<Predicate> predicates) {
        this.predicates = predicates;
        this.cb = cb;
        this.root = root;
    }

    @Override
    public Where is(String property, Object value) {
        predicates.add(cb.equal(root.get(property), value));
        return this;
    }

    @Override
    public Where gte(String property, Number value) {
        predicates.add(cb.ge(root.get(property), value));
        return this;
    }

    @Override
    public Where lte(String property, Number value) {
        predicates.add(cb.le(root.get(property), value));
        return this;
    }

}
