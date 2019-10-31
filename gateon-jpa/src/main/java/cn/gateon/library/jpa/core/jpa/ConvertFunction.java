package cn.gateon.library.jpa.core.jpa;

import lombok.Getter;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.expression.ExpressionImpl;
import org.hibernate.query.criteria.internal.expression.function.BasicFunctionExpression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import java.io.Serializable;

/**
 * <p>
 *     字符集转换
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
@Getter
public class ConvertFunction extends BasicFunctionExpression<String> implements Serializable {

    private static String NAME = "convert";

    public ExpressionImpl<String> value;

    private String charset;

    public ConvertFunction(CriteriaBuilder criteriaBuilder, Expression<String> value, String charset) {
        super((CriteriaBuilderImpl) criteriaBuilder, String.class, NAME);
        this.value = (ExpressionImpl<String>) value;
        this.charset = charset;
    }

    @Override
    public void registerParameters(ParameterRegistry registry) {
        Helper.possibleParameter(getValue(), registry);
    }

    @Override
    public String render(RenderingContext renderingContext) {
        return NAME + "(" +
                getValue().render(renderingContext) +
                " ,  '" + getCharset() + "' )";
    }
}
