package cn.gateon.library.jpa.core.query;

import cn.gateon.library.jpa.core.SubQueryer;
import cn.gateon.library.jpa.specification.Where;

import javax.persistence.criteria.Subquery;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class DefaultSubQuery<F,R> implements SubQueryer<F,R> {

    private Where<F> where;

    private Subquery<R> subquery;

    public DefaultSubQuery(Where<F> where) {
        this.where = where;
    }

    @Override
    public Where<F> where() {
        return where;
    }

    @Override
    public Subquery<R> getQuery() {
        where.build(subquery);
        return subquery;
    }
}
