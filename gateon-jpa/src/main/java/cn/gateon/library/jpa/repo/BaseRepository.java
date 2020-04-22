package cn.gateon.library.jpa.repo;

import cn.gateon.library.common.data.Page;
import cn.gateon.library.common.data.PageRequest;
import cn.gateon.library.jpa.core.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
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

    long count(Predicate predicate);

    boolean exists(Predicate predicate);

    Page<T> page(Predicate predicate, PageRequest pageRequest);

    List<T> findAll(OrderSpecifier<?>... orders);

    List<T> findAll(Predicate predicate, OrderSpecifier<?>... orders);

    List<T> findAll(Predicate predicate);

    Optional<T> findOne(Predicate predicate);

    /**
     * 获取查询器
     *
     * @return 创建一个新的查询器
     */
    Queryer<T, T> queryer();

    <R> Queryer<T, R> queryer(Class<R> resultClass);

    <R> SumQueryer<T, R> multiSum(Class<R> clazz);

    /**
     * 单字段求和
     *
     * @since 1.3.1
     */
    SingleSumQueryer<T> singleSum();

    CountQueryer<T> counter();

    ExistsQueryer<T> exister();

    <R> R query(String sql, Class<R> r);

    <S extends T> S save(S t);

    <S extends T> List<S> saveAll(Collection<S> iterable);

    void deleteAll(Collection<? extends T> iterable);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    void delete(T t);

    void deleteById(ID id);





}
