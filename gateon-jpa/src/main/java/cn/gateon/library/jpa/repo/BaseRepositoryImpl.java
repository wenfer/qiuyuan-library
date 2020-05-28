package cn.gateon.library.jpa.repo;

import cn.gateon.library.common.data.Page;
import cn.gateon.library.common.data.PageRequest;
import cn.gateon.library.jpa.searcher.DslBuilder;
import cn.gateon.library.jpa.searcher.MultiSumSearcher;
import cn.gateon.library.jpa.searcher.Searcher;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.*;

/**
 * @author qiuyuan
 * @see org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
 * @see org.springframework.data.jpa.repository.support.QuerydslJpaRepository
 * @since 1.0
 */
@Repository
@Transactional(readOnly = true)
public class BaseRepositoryImpl<T, ID extends Serializable> implements BaseRepository<T, ID> {

    private static final String ID_MUST_NOT_BE_NULL = "id 不可为空";

    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    private final JpaEntityInformation<T, ?> entityInformation;

    private EntityManager entityManager;

    private @Nullable
    CrudMethodMetadata metadata;

    private final EntityPath<T> path;

    private final Querydsl querydsl;

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
    }

    /**
     * Creates a new {@link QuerydslJpaRepository} from the given domain class and {@link EntityManager} and uses the
     * given {@link EntityPathResolver} to translate the domain class into an {@link EntityPath}.
     *
     * @param entityInformation must not be {@literal null}.
     * @param entityManager     must not be {@literal null}.
     * @param resolver          must not be {@literal null}.
     */
    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager,
                              EntityPathResolver resolver) {
        this.entityInformation = entityInformation;
        this.path = resolver.createPath(entityInformation.getJavaType());
        this.querydsl = new Querydsl(entityManager, new PathBuilder<T>(path.getType(), path.getMetadata()));
        this.entityManager = entityManager;
    }

    /**
     * Configures a custom {@link CrudMethodMetadata} to be used to detect {@link LockModeType}s and query hints to be
     * applied to queries.
     **/
    public void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata) {
        this.metadata = crudMethodMetadata;
    }

    @Override
    public <R> R query(String sql, Class<R> r) {
        return entityManager.createQuery(sql, r).getSingleResult();
    }

    @Override
    public Optional<T> findOne(Predicate predicate) {
        try {
            return Optional.ofNullable(createQuery(predicate).select(path).fetchOne());
        } catch (NonUniqueResultException ex) {
            throw new IncorrectResultSizeDataAccessException(ex.getMessage(), 1, ex);
        }
    }

    @Override
    public List<T> findAll(Predicate predicate) {
        return createQuery(predicate).select(path).fetch();
    }

    @Override
    public List<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return executeSorted(createQuery(predicate).select(path), orders);
    }


    @Override
    public Searcher<T> searcher() {
        return new DefaultSearcherImpl<>(entityManager, entityManager.getCriteriaBuilder(), entityInformation.getJavaType());
    }

    @Override
    public <R> MultiSumSearcher<R> multiSum(Class<R> resultClass) {
        return new MultiSumSearcherImpl<>(entityManager, entityManager.getCriteriaBuilder(), entityInformation.getJavaType(), resultClass);
    }

    @Override
    public long count(Predicate predicate) {
        return createQuery(predicate).fetchCount();
    }

    @Override
    public boolean exists(Predicate predicate) {
        return createQuery(predicate).fetchCount() > 0;
    }

    @Override
    public Page<T> page(Predicate predicate, PageRequest pageRequest) {
        Assert.notNull(pageRequest, "Pageable must not be null!");
        final JPQLQuery<?> countQuery = createCountQuery(predicate);
        JPQLQuery<T> jpqlQuery = createQuery(predicate).select(path);
        jpqlQuery.offset(pageRequest.getPage() * pageRequest.getSize());
        jpqlQuery.limit(pageRequest.getSize());
        List<T> fetch = jpqlQuery.fetch();
        return new Page<T>(fetch, pageRequest, countQuery.fetchCount());
    }

    @Override
    public List<T> findAll(OrderSpecifier<?>... orders) {
        Assert.notNull(orders, "Order specifiers must not be null!");
        return executeSorted(createQuery(new Predicate[0]).select(path), orders);
    }

    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        if (entityInformation.isNew(entity)) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Transactional
    @Override
    public <S extends T> List<S> saveAll(Collection<S> entities) {
        List<S> result = new ArrayList<S>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    public Optional<T> findById(ID id) {
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);
        Class<T> domainType = entityInformation.getJavaType();
        if (metadata == null) {
            return Optional.ofNullable(entityManager.find(domainType, id));
        }
        LockModeType type = metadata.getLockModeType();
        Map<String, Object> hints = getQueryHints().withFetchGraphs(entityManager).asMap();
        return Optional.ofNullable(type == null ? entityManager.find(domainType, id, hints) : entityManager.find(domainType, id, type, hints));

    }

    @Override
    public boolean existsById(ID id) {
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);
        Assert.notNull(entityInformation.getIdAttribute(), "未找到id属性");
        return findById(id).isPresent();
    }


    @Override
    @Transactional
    public void deleteById(ID id) {
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);
        delete(findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
                String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), id), 1)));
    }

    @Override
    @Transactional
    public void delete(T entity) {
        Assert.notNull(entity, "The entity must not be null!");
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    @Transactional
    public void deleteAll(Collection<? extends T> entities) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        for (T entity : entities) {
            delete(entity);
        }
    }


    /**
     * Creates a new {@link JPQLQuery} for the given {@link Predicate}.
     *
     * @param predicate
     * @return the Querydsl {@link JPQLQuery}.
     */
    protected JPQLQuery<?> createQuery(Predicate... predicate) {
        AbstractJPAQuery<?, ?> query = doCreateQuery(getQueryHints().withFetchGraphs(entityManager), predicate);
        CrudMethodMetadata metadata = getRepositoryMethodMetadata();
        if (metadata == null) {
            return query;
        }
        LockModeType type = metadata.getLockModeType();
        return type == null ? query : query.setLockMode(type);
    }


    private QueryHints getQueryHints() {
        return metadata == null ? QueryHints.NoHints.INSTANCE :
                DefaultQueryHints.of(entityInformation, metadata);
    }

    protected JPQLQuery<?> createCountQuery(@Nullable Predicate... predicate) {
        return doCreateQuery(getQueryHints(), predicate);
    }

    private AbstractJPAQuery<?, ?> doCreateQuery(QueryHints hints, @Nullable Predicate... predicate) {
        AbstractJPAQuery<?, ?> query = querydsl.createQuery(path);
        if (predicate != null) {
            query = query.where(predicate);
        }

        for (Map.Entry<String, Object> hint : hints) {
            query.setHint(hint.getKey(), hint.getValue());
        }
        return query;
    }


    @Nullable
    protected CrudMethodMetadata getRepositoryMethodMetadata() {
        return metadata;
    }

    private List<T> executeSorted(JPQLQuery<T> query, OrderSpecifier<?>... orders) {
        return executeSorted(query, new QSort(orders));
    }


    private List<T> executeSorted(JPQLQuery<T> query, Sort sort) {
        return querydsl.applySorting(sort, query).fetch();
    }

}
