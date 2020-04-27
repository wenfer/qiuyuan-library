package cn.gateon.library.jpa.repo;

import cn.gateon.library.jpa.searcher.MultiSumSearcher;
import cn.gateon.library.jpa.searcher.Searcher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    private final JpaEntityInformation<T, ?> entityInformation;

    private final EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }


    @Override
    public <R> R query(String sql, Class<R> r) {
        return entityManager.createQuery(sql, r).getSingleResult();
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
        Class<T> domainType = getDomainClass();
        return Optional.ofNullable(entityManager.find(domainType, id));
    }

    private Class<T> getDomainClass() {
        return entityInformation.getJavaType();
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


}
