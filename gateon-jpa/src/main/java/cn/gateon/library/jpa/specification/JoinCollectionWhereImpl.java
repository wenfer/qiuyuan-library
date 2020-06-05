package cn.gateon.library.jpa.specification;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0.1
 */
@Setter
@Getter
public class JoinCollectionWhereImpl implements JoinCollectionWhere {

    private String joinProperty;

    protected List<Term> terms = new LinkedList<>();

    public JoinCollectionWhereImpl(String joinProperty) {
        this.joinProperty = joinProperty;
    }

    public JoinCollectionWhereImpl eq(Object value) {
        terms.add(new Term(joinProperty, (cb, expression) -> cb.equal(expression, value)));
        return this;
    }

    @Override
    public JoinCollectionWhere in(Collection<?> values) {
        terms.add(new Term(joinProperty, (cb, expression) -> expression.in(values)));
        return this;
    }

    @Override
    public Map<String, CommonBuilder> builders() {
        return null;
    }

    @Override
    public List<Term> terms() {
        return terms;
    }

    @Override
    public String getJoinProperty() {
        return joinProperty;
    }

    @Override
    public OperatorEnum operator() {
        return OperatorEnum.AND;
    }
}
