package cn.gateon.library.jpa.repo;

import cn.gateon.library.jpa.core.criterion.Searcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author qiuyuan
 * @since 1.0
 */
@NoRepositoryBean
public interface GateonRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    Searcher<T> searcher();
}
