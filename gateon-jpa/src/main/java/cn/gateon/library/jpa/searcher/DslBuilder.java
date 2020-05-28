package cn.gateon.library.jpa.searcher;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
@Setter
@Getter
public class DslBuilder<T> {

    private final EntityPath<T> path;

    private BooleanBuilder booleanBuilder;

    private PathBuilder<T> pathBuilder;

    private DslBuilder(EntityPath<T> entityPath) {
        pathBuilder = new PathBuilder<>(entityPath.getType(), entityPath.getMetadata());
        this.path = entityPath;
        booleanBuilder = new BooleanBuilder();
    }

    public static <T> DslBuilder<T> of(EntityPath<T> entityPath) {
        return new DslBuilder<>(entityPath);
    }

    public BooleanBuilder build() {
        return booleanBuilder;
    }


    public <V> DslBuilder<T> eq(String property, V value) {
        if (value == null) {
            return this;
        }
        booleanBuilder.and(Expressions.booleanOperation(Ops.EQ, pathBuilder.get(property), ConstantImpl.create(value)));
        return this;
    }

    public DslBuilder<T> contains(String property, String value) {
        if (StringUtils.isEmpty(value)) {
            return this;
        }
        booleanBuilder.and(Expressions.booleanOperation(Ops.STRING_CONTAINS, pathBuilder.get(property), ConstantImpl.create(value)));
        return this;
    }

    public <V> DslBuilder<T> ne(String property, V value) {
        if (value == null) {
            return this;
        }
        booleanBuilder.and(Expressions.booleanOperation(Ops.NE, pathBuilder.get(property), ConstantImpl.create(value)));
        return this;
    }

    public <V> DslBuilder<T> isNull(String property) {
        booleanBuilder.and(Expressions.booleanOperation(Ops.IS_NULL, pathBuilder.get(property)));
        return this;
    }

    public <V> DslBuilder<T> notNull(String property) {
        booleanBuilder.and(Expressions.booleanOperation(Ops.IS_NOT_NULL, pathBuilder.get(property)));
        return this;
    }

    public <V> DslBuilder<T> gt(String property, V value) {
        if (value == null) {
            return this;
        }
        booleanBuilder.and(Expressions.booleanOperation(Ops.GT, pathBuilder.get(property), ConstantImpl.create(value)));
        return this;
    }

    public <V> DslBuilder<T> gte(String property, V value) {
        if (value == null) {
            return this;
        }
        booleanBuilder.and(Expressions.booleanOperation(Ops.GOE, pathBuilder.get(property), ConstantImpl.create(value)));
        return this;
    }

    public <V> DslBuilder<T> lt(String property, V value) {
        if (value == null) {
            return this;
        }
        booleanBuilder.and(Expressions.booleanOperation(Ops.LT, pathBuilder.get(property), ConstantImpl.create(value)));
        return this;
    }

    public <V> DslBuilder<T> lte(String property, V value) {
        if (value == null) {
            return this;
        }
        booleanBuilder.and(Expressions.booleanOperation(Ops.LOE, pathBuilder.get(property), ConstantImpl.create(value)));
        return this;
    }
}
