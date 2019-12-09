package cn.gateon.library.jpa.specification.impl;

import cn.gateon.library.jpa.core.jpa.FindInSetFunction;
import cn.gateon.library.jpa.specification.Having;
import cn.gateon.library.jpa.specification.Where;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
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
public class WhereImpl<F, R> implements Where, Having {

    private final List<Predicate> predicates;

    private final CriteriaBuilderImpl cb;

    private final From<F, R> root;

    public WhereImpl(CriteriaBuilder cb, From<F, R> root, List<Predicate> predicates) {
        this.predicates = predicates;
        this.cb = (CriteriaBuilderImpl) cb;
        this.root = root;
    }

    @Override
    public Where eq(String property, Object value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.equal(root.get(property), value));
        return this;
    }

    @Override
    public Where neq(String property, Object value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.notEqual(root.get(property), value));
        return this;
    }

    @Override
    public Where in(String property, Object... value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.in(root.get(property)).in(value));
        return this;
    }

    @Override
    public Where nin(String property, Object... value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.not(root.get(property)).in(value));
        return this;
    }

    @Override
    public Where gte(String property, Number value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.ge(root.get(property), value));
        return this;
    }

    @Override
    public Where gt(String property, Number value) {
        if (value == null) {
            return this;
        }
        predicates.add(cb.gt(root.get(property), value));
        return this;
    }

    @Override
    public Where findInSet(String property, String value) {
        predicates.add(cb.isTrue(new FindInSetFunction(cb, root.get(property), value)));
        return this;
    }

    @Override
    public Where lte(String property, Number value) {
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
    public Where start(String property, Date start) {
        if (start == null) {
            return this;
        }
        predicates.add(cb.greaterThanOrEqualTo(root.get(property), start));
        return this;
    }

    @Override
    public Where end(String property, Date end) {
        if (end == null) {
            return this;
        }
        predicates.add(cb.lessThanOrEqualTo(root.get(property), end));
        return this;
    }

    @Override
    public Where isNull(String property, boolean isNull) {
        predicates.add(isNull ? cb.isNull(root.get(property)) : cb.isNotNull(root.get(property)));
        return this;
    }

    @Override
    public Where like(String property, String value, int position) {
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
