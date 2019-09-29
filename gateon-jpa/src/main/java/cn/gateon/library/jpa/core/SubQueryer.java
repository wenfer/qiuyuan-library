package cn.gateon.library.jpa.core;

import cn.gateon.library.jpa.specification.Where;

import javax.persistence.criteria.Subquery;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface SubQueryer<R> {

    Where where();

    Subquery<R> getQuery();
}
