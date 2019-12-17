package cn.gateon.library.jpa.core;

import javax.persistence.criteria.Expression;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2
 */
public interface SumQueryer<R> {

    Expression<Long> sum(String property);

}
