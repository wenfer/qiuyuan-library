package cn.gateon.library.jpa.core;

import cn.gateon.library.jpa.specification.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    void orderBy(String property, boolean asc);

    Where join(String property);

    Queryer<R> orderBy(String property, boolean asc);

    /**
     * 带字符集转换排序，需要使用convert关键字，
     * 必须使用 {@link cn.gateon.library.jpa.core.jpa.GateonMySQLlDialect} 方言
     */
    Queryer<R> orderBy(String property, boolean asc, String convertCharset);

    R findOne();

    List<R> findAll();

    long count();

    Page<R> find(Pageable pageable);

    <SF, SR> SubQueryer<SR> subQuery(String property, Class<SF> from, Class<SR> result);
}
