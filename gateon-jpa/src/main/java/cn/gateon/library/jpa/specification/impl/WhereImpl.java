package cn.gateon.library.jpa.specification.impl;

import cn.gateon.library.jpa.core.SubQueryer;
import cn.gateon.library.jpa.core.jpa.FindInSetFunction;
import cn.gateon.library.jpa.specification.Having;
import cn.gateon.library.jpa.specification.PredicateBuilder;
import cn.gateon.library.jpa.specification.Where;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class WhereImpl<F, R> implements Where<F>, Having<F> {

    protected final List<Predicate> predicates;

    private final CriteriaBuilderImpl cb;

    private final From<F, R> root;

    public WhereImpl(CriteriaBuilder cb, From<F, R> root, List<Predicate> predicates) {
        this.predicates = predicates;
        this.cb = (CriteriaBuilderImpl) cb;
        this.root = root;
    }

    @Override
    public Where<F> eq(String property, Object value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.equal(root.get(property), value));
        return this;
    }

    @Override
    public Where<F> neq(String property, Object value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.notEqual(root.get(property), value));
        return this;
    }

    @Override
    public Where<F> in(String property, Collection<?> value) {
        if (value == null) {
            return this;
        }
        predicates.add(root.get(property).in(value));
        return this;
    }

    @Override
    public Where<F> nin(String property, Collection<?> value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.not(root.get(property)).in(value));
        return this;
    }

    @Override
    public Where<F> gte(String property, Number value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.ge(root.get(property), value));
        return this;
    }

    @Override
    public Where<F> gt(String property, Number value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.gt(root.get(property), value));
        return this;
    }

    @Override
    public Where<F> findInSet(String property, String value) {
        predicates.add(cb.ge(new FindInSetFunction(cb, root.get(property), value), 1));
        return this;
    }

    @Override
    public Where<F> lte(String property, Number value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.le(root.get(property), value));
        return this;
    }

    @Override
    public Where lt(String property, Number value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.lt(root.get(property), value));
        return this;
    }

    @Override
    public Where<F> start(String property, Date start) {
        if (start == null) {
            return this;
        }
        predicates.add(cb.greaterThanOrEqualTo(root.get(property), start));
        return this;
    }

    @Override
    public Where<F> end(String property, Date end) {
        if (end == null) {
            return this;
        }
        predicates.add(cb.lessThanOrEqualTo(root.get(property), end));
        return this;
    }

    @Override
    public Where<F> isNull(String property, boolean isNull) {
        predicates.add(isNull ? cb.isNull(root.get(property)) : cb.isNotNull(root.get(property)));
        return this;
    }

    @Override
    public <S> PredicateBuilder<F> any(String property, Subquery<S> subQuery) {
        predicates.add(cb.equal(root.get(property), cb.any(subQuery)));
        return this;
    }


    @Override
    public Where<F> like(String property, String value, int position) {
        if (StringUtils.isEmpty(value)) {
            return this;
        }
        StringBuilder sb = new StringBuilder();
        if (position <= 0) {
            sb.append('%');
        }
        sb.append(value);
        if (position >= 0) {
            sb.append('%');
        }
        Expression<String> as = root.get(property).as(String.class);
        predicates.add(cb.like(as, sb.toString()));
        return this;
    }

}
