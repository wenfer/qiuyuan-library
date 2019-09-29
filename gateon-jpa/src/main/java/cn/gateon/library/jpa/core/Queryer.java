package cn.gateon.library.jpa.core;

import cn.gateon.library.jpa.specification.Where;

import java.util.List;

/**
 * <p>
 * 查询器
 *
 * @param <T> 结果类型
 *            </p>
 * @author qiuyuan
 * @since 1.0
 */
public interface Queryer<F, R> {

    void setWhere(Where<F> where);

    Where<F> where();

    /**
     * 查询单个结果
     */
    R findOne();

    List<R> findAll();

    <SF, SR> SubQueryer<SF, SR> subQuery(String property, Class<SF> from, Class<SR> result);
}
