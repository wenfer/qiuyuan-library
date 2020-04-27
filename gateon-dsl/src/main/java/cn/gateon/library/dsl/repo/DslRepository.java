package cn.gateon.library.dsl.repo;

import cn.gateon.library.common.data.Page;
import cn.gateon.library.common.data.PageRequest;
import cn.gateon.library.jpa.repo.BaseRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
public interface DslRepository<T,ID extends Serializable> extends BaseRepository<T,ID> {

    long count(Predicate predicate);

    boolean exists(Predicate predicate);

    Page<T> page(Predicate predicate, PageRequest pageRequest);

    List<T> findAll(OrderSpecifier<?>... orders);

    List<T> findAll(Predicate predicate, OrderSpecifier<?>... orders);

    List<T> findAll(Predicate predicate);

    Optional<T> findOne(Predicate predicate);


}
