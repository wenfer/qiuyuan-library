package cn.gateon.library.jpa.repo;

import cn.gateon.library.jpa.core.Queryer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author qiuyuan
 * @since 1.0
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * 获取查询器
     *
     * @return 创建一个新的查询器
     */
    Queryer<T> queryer();

    <R> Queryer<R> multiSum(Class<R> clazz);

    <R> R query(String sql,Class<R> r);

    <R> List<R> queryList(String sql, Class<R> r);

}
