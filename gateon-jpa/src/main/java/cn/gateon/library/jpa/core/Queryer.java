package cn.gateon.library.jpa.core;

import cn.gateon.library.common.data.Page;
import cn.gateon.library.common.data.PageRequest;
import cn.gateon.library.jpa.specification.Having;
import cn.gateon.library.jpa.specification.Or;
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
public interface Queryer<F,R> {

    /**
     * @since 1.2
     * or查询构造器
     */
    Or<F> or();

    Where<F> where();

    /**
     * @return having 构造器
     * @since 1.2
     */
    Having<F> having();

    Where<F> join(String property);

    /**
     * 单属性集合查询
     *
     * @since 1.1.4
     */
    <Z> CollectionQueryer<Z> joinCollection(String property);

    Queryer<F,R> orderBy(String property, boolean asc);

    /**
     * 带字符集转换排序，需要使用convert关键字，
     * 必须使用 {@link cn.gateon.library.jpa.core.jpa.GateonMySQLlDialect} 方言
     */
    Queryer<F,R> orderBy(String property, boolean asc, String convertCharset);

    R findOne();

    List<R> findAll();

    Page<R> page(PageRequest pageable);

    <SF, SR> SubQueryer<SR> subQuery(String property, Class<SF> from, Class<SR> result);
}
