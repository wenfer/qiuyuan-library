package cn.gateon.library.dsl.repo;

import cn.gateon.library.common.data.Page;
import cn.gateon.library.common.data.PageRequest;
import cn.gateon.library.jpa.repo.BaseRepositoryImpl;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
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
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
public class DslRepositoryImpl<T, ID extends Serializable> extends BaseRepositoryImpl<T, ID> implements DslRepository<T, ID> {

    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    private final EntityPath<T> path;

    private final JpaEntityInformation<T, ID> entityInformation;

    private final EntityManager entityManager;

    private @Nullable CrudMethodMetadata methodMetadata;

    private final Querydsl querydsl;

    /**
     * Creates a new {@link QuerydslJpaRepository} from the given domain class and {@link EntityManager} and uses the
     * given {@link EntityPathResolver} to translate the domain class into an {@link EntityPath}.
     *
     * @param entityInformation must not be {@literal null}.
     * @param entityManager     must not be {@literal null}.
     * @param resolver          must not be {@literal null}.
     */
    public DslRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.path = DEFAULT_ENTITY_PATH_RESOLVER.createPath(entityInformation.getJavaType());
        this.querydsl = new Querydsl(entityManager, new PathBuilder<T>(path.getType(), path.getMetadata()));
        this.entityManager = entityManager;
    }

    private List<T> executeSorted(JPQLQuery<T> query, OrderSpecifier<?>... orders) {
        return executeSorted(query, new QSort(orders));
    }


    @Nullable
    protected CrudMethodMetadata getRepositoryMethodMetadata() {
        return methodMetadata;
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

    private List<T> executeSorted(JPQLQuery<T> query, Sort sort) {
        return querydsl.applySorting(sort, query).fetch();
    }

    protected JPQLQuery<?> createCountQuery(@Nullable Predicate... predicate) {
        return doCreateQuery(getQueryHints(), predicate);
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

    private AbstractJPAQuery<?, ?> doCreateQuery(QueryHints hints, @Nullable Predicate... predicate) {
        AbstractJPAQuery<?, ?> query = querydsl.createQuery(path);
        if (predicate != null) {
            query = query.where(predicate);
        }

        for (Map.Entry<String, Object> hint : getQueryHints()) {
            query.setHint(hint.getKey(), hint.getValue());
        }
        return query;
    }

    private QueryHints getQueryHints() {
        return methodMetadata == null ? QueryHints.NoHints.INSTANCE :
                DefaultQueryHints.of(entityInformation, methodMetadata);
    }


    /**
     * Configures a custom {@link CrudMethodMetadata} to be used to detect {@link LockModeType}s and query hints to be
     * applied to queries.
     *
     * @param crudMethodMetadata
     */
    public void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata) {
        this.methodMetadata = crudMethodMetadata;
    }

}
