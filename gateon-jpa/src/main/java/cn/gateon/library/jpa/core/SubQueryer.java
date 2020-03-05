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

    /**
     * 获取条件构造器
     */
    Where<R> where();

    /**
     * 获取拼接出的子查询
     */
    Subquery<R> getQuery();
}
