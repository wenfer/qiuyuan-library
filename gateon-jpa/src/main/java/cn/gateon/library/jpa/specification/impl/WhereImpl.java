package cn.gateon.library.jpa.specification.impl;

import cn.gateon.library.jpa.enums.Position;
import cn.gateon.library.jpa.specification.Where;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Date;
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

    private boolean and = true;

    private void add(Predicate predicate) {
        predicates.add(and ? predicate : cb.or(predicate));
    }

    public WhereImpl(CriteriaBuilder cb, From<F, R> root, List<Predicate> predicates) {
        this.predicates = predicates;
        this.cb = cb;
        this.root = root;
    }

    public WhereImpl(CriteriaBuilder cb, From<F, R> root, List<Predicate> predicates, boolean and) {
        this.predicates = predicates;
        this.cb = cb;
        this.and = and;
        this.root = root;
    }


    @Override
    public Where or() {
        return new WhereImpl<>(cb, root, predicates, false);
    }

    @Override
    public Where is(String property, Object value) {
        add(cb.equal(root.get(property), value));
        return this;
    }

    @Override
    public Where gteAtoB(String propertyA, String propertyB) {
        add(cb.ge(root.get(propertyA), root.get(propertyB)));
        return this;
    }

    @Override
    public Where in(String property, Object... value) {
        add(root.get(property).in(value));
        return this;
    }

    @Override
    public Where isNull(String property, boolean isNull) {
        add(isNull ? cb.isNull(root.get(property)) : cb.isNotNull(root.get(property)));
        return this;
    }

    @Override
    public Where like(String property, String value, Position position) {
        StringBuilder pattern = new StringBuilder();
        if (position.getMark() > 0) {
            pattern.append('%');
        }
        pattern.append(value);
        if (position.getMark() < 0) {
            pattern.append('%');
        }
        add(cb.like(root.get(property), pattern.toString()));
        return this;
    }

    @Override
    public Where gte(String property, Number value) {
        add(cb.ge(root.get(property), value));
        return this;
    }

    @Override
    public Where gt(String property, Number value) {
        add(cb.gt(root.get(property), value));
        return this;
    }

    @Override
    public Where lte(String property, Number value) {
        add(cb.le(root.get(property), value));
        return this;
    }

    @Override
    public Where lt(String property, Number value) {
        add(cb.lt(root.get(property), value));
        return this;
    }

    @Override
    public Where start(String property, Date date) {
        add(cb.greaterThan(root.get(property), date));
        return this;
    }

    @Override
    public Where end(String property, Date date) {
        add(cb.lessThan(root.get(property), date));
        return this;
    }

}
