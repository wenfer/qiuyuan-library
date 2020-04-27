package cn.gateon.library.jpa.repo;

import cn.gateon.library.jpa.searcher.MultiSumSearcher;
import cn.gateon.library.jpa.searcher.Searcher;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author qiuyuan
 * @see QuerydslPredicateExecutor<T>
 * @since 1.0
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

    Searcher<T> searcher();

    <R> MultiSumSearcher<R> multiSum(Class<R> resultClass);

    <R> R query(String sql, Class<R> r);

    <S extends T> S save(S t);

    <S extends T> List<S> saveAll(Collection<S> iterable);

    void deleteAll(Collection<? extends T> iterable);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    void delete(T t);

    void deleteById(ID id);


}
