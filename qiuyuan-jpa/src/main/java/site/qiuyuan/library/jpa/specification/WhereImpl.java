package site.qiuyuan.library.jpa.specification;

import site.qiuyuan.library.jpa.core.jpa.FindInSetFunction;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Subquery;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
class WhereImpl implements Where {

    private String joinProperty;

    private OperatorEnum operator;

    private LinkedList<Term> terms = new LinkedList<>();

    protected Map<String, CommonBuilder> builders = new HashMap<>();

    public WhereImpl(OperatorEnum operatorEnum) {
        this.operator = operatorEnum;
    }

    public WhereImpl(OperatorEnum operatorEnum, String joinProperty) {
        this.operator = operatorEnum;
        this.joinProperty = joinProperty;
    }

    @Override
    public OperatorEnum operator() {
        return operator;
    }


    @Override
    public Where eq(String property, Object value) {
        if (value == null) {
            return this;
        }
        terms.push(new Term(property, ((cb, expression) -> cb.equal(expression, value))));
        return this;
    }

    @Override
    public Where neq(String property, Object value) {
        if (value == null) {
            return this;
        }
        terms.push(new Term(property, ((cb, expression) -> cb.notEqual(expression, value))));
        return this;
    }

    @Override
    public Where in(String property, Collection<?> value) {
        if (value == null) {
            return this;
        }
        terms.push(new Term(property, ((cb, expression) -> expression.in(value))));
        return this;
    }

    @Override
    public Where nin(String property, Collection<?> value) {
        if (value == null) {
            return this;
        }
        terms.push(new Term(property, ((cb, expression) -> cb.not(expression.in(value)))));
        return this;
    }

    @Override
    public Where gte(String property, Number value) {
        if (value == null) {
            return this;
        }
        terms.push(new Term(property, ((cb, expression) -> cb.ge(expression.as(Number.class), value))));
        return this;
    }

    @Override
    public Where gt(String property, Number value) {
        if (value == null) {
            return this;
        }
        terms.push(new Term(property, ((cb, expression) -> cb.gt(expression.as(Number.class), value))));
        return this;
    }

    @Override
    public Where findInSet(String property, String value) {
        if (StringUtils.isEmpty(value)) {
            return this;
        }
        terms.push(new Term(property, ((cb, expression) -> cb.ge(new FindInSetFunction((CriteriaBuilderImpl) cb, expression.as(String.class), value), 1))));
        return this;
    }

    @Override
    public Where lte(String property, Number value) {
        if (value == null) {
            return this;
        }
        terms.push(new Term(property, ((cb, expression) -> cb.le(expression.as(Number.class), value))));
        return this;
    }

    @Override
    public Where lt(String property, Number value) {
        if (value == null) {
            return this;
        }
        terms.push(new Term(property, ((cb, expression) -> cb.lt(expression.as(Number.class), value))));
        return this;
    }

    @Override
    public Where start(String property, Date start) {
        if (start == null) {
            return this;
        }
        terms.push(new Term(property, ((cb, expression) -> cb.greaterThanOrEqualTo(expression.as(Date.class), start))));
        return this;
    }

    @Override
    public Where end(String property, Date end) {
        if (end == null) {
            return this;
        }
        terms.push(new Term(property, ((cb, expression) -> cb.lessThanOrEqualTo(expression.as(Date.class), end))));
        return this;
    }

    @Override
    public Where isNull(String property, boolean isNull) {
        terms.push(new Term(property, ((cb, expression) -> isNull ? expression.isNull() : expression.isNotNull())));
        return this;
    }

    @Override
    public <S> Where any(String property, Subquery<S> subQuery) {
        terms.push(new Term(property, (cb, expression) -> cb.equal(expression, cb.any(subQuery))));
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
        terms.push(new Term(property, (cb, expression) -> cb.like(expression.as(String.class), sb.toString())));
        return this;
    }

    @Override
    public Map<String, CommonBuilder> builders() {
        return builders;
    }

    @Override
    public List<Term> terms() {
        return terms;
    }

    @Override
    public String getJoinProperty() {
        return joinProperty;
    }
}
