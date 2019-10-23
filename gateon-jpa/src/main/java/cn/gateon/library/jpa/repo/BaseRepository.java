package cn.gateon.library.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import site.qiuyuan.base4jpa.core.Queryer;

import java.io.Serializable;

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

}
