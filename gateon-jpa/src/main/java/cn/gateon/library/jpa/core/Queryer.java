package cn.gateon.library.jpa.core;

import cn.gateon.library.common.data.Page;
import cn.gateon.library.common.data.PageRequest;
import cn.gateon.library.jpa.specification.Where;

import java.util.List;

/**
 * <p>
 * 查询器
 *
 * @param <R> 结果类型
 *            </p>
 * @author qiuyuan
 * @since 1.0
 */
public interface Queryer<R> {

    void setWhere(Where where);

    Where where();

    Where join(String property);

    Page<R> page(PageRequest pageRequest);

    long count();

    R findOne();

    List<R> findAll();

    <SF, SR> SubQueryer<SR> subQuery(String property, Class<SF> from, Class<SR> result);
}
