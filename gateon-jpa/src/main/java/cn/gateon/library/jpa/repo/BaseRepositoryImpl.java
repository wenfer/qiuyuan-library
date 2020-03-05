package cn.gateon.library.jpa.repo;

import cn.gateon.library.jpa.core.Queryer;
import cn.gateon.library.jpa.core.query.DefaultQuery;
import cn.gateon.library.jpa.core.query.MultiSumQuery;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author qiuyuan
 * @since 1.0
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;

    private EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    @Override
    public Queryer<T, T> queryer() {
        return new DefaultQuery<>(entityManager, entityInformation.getJavaType());
    }

    @Override
    public <R> Queryer<T, R> multiSum(Class<R> clazz) {
        return new MultiSumQuery<>(entityManager, entityInformation.getJavaType(), clazz);
    }

    @Override
    public <R> R query(String sql, Class<R> r) {
        return entityManager.createQuery(sql, r).getSingleResult();
    }

}
