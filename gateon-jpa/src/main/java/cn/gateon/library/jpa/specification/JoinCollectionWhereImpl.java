package cn.gateon.library.jpa.specification;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

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

    protected Map<String, CommonBuilder> builders = new LinkedHashMap<>();

    public JoinCollectionWhereImpl(String joinProperty) {
        this.joinProperty = joinProperty;
    }

    public JoinCollectionWhereImpl eq(Object value) {
        builders.put(joinProperty, (cb, expression) -> cb.equal(expression, value));
        return this;
    }

    @Override
    public JoinCollectionWhere in(Collection<?> values) {
        builders.put(joinProperty, (cb, expression) -> expression.in(values));
        return this;
    }

    @Override
    public Map<String, CommonBuilder> builders() {
        return builders;
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
