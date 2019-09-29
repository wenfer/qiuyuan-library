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
public class DefaultSubQuery<R> implements SubQueryer<R> {

    private Where where;

    private Subquery<R> subquery;

    public DefaultSubQuery(Where where,Subquery<R> subquery) {
        this.where = where;
        this.subquery = subquery;
    }

    @Override
    public Where where() {
        return where;
    }

    @Override
    public Subquery<R> getQuery() {
        return subquery;
    }
}
