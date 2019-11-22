package cn.gateon.library.jpa.core.jpa;

import lombok.Getter;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterContainer;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.expression.ExpressionImpl;
import org.hibernate.query.criteria.internal.expression.function.BasicFunctionExpression;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import java.io.Serializable;

/**
 * <p>
 * 字符集转换
 * </p>
 *
 * @author qiuyuan
 * @since 1.1
 */
@Getter
public class FindInSetFunction extends AbstractSimplePredicate implements Serializable {

    private static String NAME = "find_in_set";

    public ExpressionImpl<String> property;

    private Object value;

    public FindInSetFunction(CriteriaBuilderImpl criteriaBuilder,Expression<String> property,Object value) {
        super(criteriaBuilder);
        this.property = (ExpressionImpl<String>) property;
        this.value = value;
    }


    @Override
    public void registerParameters(ParameterRegistry registry) {
        ParameterContainer.Helper.possibleParameter(getProperty(), registry);
    }

    @Override
    public String render(boolean isNegated, RenderingContext renderingContext) {
        return NAME + "(" +
                value.toString() +
                " ,  '" + getProperty().render(renderingContext) + "' )";
    }
}
