package cn.gateon.library.jpa.core.jpa;

import lombok.Getter;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.expression.ExpressionImpl;
import org.hibernate.query.criteria.internal.expression.function.BasicFunctionExpression;

import javax.persistence.criteria.Expression;

/**
 * <p>
 * 字符集转换
 * </p>
 *
 * @author qiuyuan
 * @since 1.1
 */
@Getter
public class FindInSetFunction extends BasicFunctionExpression<Integer> {

    private static String NAME = "find_in_set";

    public ExpressionImpl<String> property;

    private String value;

    public FindInSetFunction(CriteriaBuilderImpl criteriaBuilder, Expression<String> property, String value) {
        super(criteriaBuilder, Integer.class, NAME);
        this.property = (ExpressionImpl<String>) property;
        this.value = value;
    }


    @Override
    public void registerParameters(ParameterRegistry registry) {
        Helper.possibleParameter(getProperty(), registry);
    }

    @Override
    public String render(RenderingContext renderingContext) {
        return NAME + "('" + value + "'," + getProperty().render(renderingContext) + ")";
    }
}
