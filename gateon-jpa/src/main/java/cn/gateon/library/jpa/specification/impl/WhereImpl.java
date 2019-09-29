package cn.gateon.library.jpa.specification.impl;

import cn.gateon.library.jpa.specification.Where;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class WhereImpl<F> implements Where<F> {

    private final List<Predicate> predicates = new ArrayList<>();

    private final CriteriaBuilder cb;

    private final Root<F> root;

    public WhereImpl(CriteriaBuilder cb, Root<F> root) {
        this.cb = cb;
        this.root = root;
    }

    @Override
    public Where<F> is(String property, Object value) {
        predicates.add(cb.equal(root.get(property), value));
        return this;
    }

    @Override
    public void build(AbstractQuery query) {
        Predicate[] predicateArray = new Predicate[this.predicates.size()];
        Predicate[] predicates = this.predicates.toArray(predicateArray);
        query.where(predicates);
    }
}
