package cn.gateon.library.jpa.repo;

import cn.gateon.library.jpa.core.criterion.Searcher;
import cn.gateon.library.jpa.core.impl.DefaultSearcher;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;

/**
 * @author qiuyuan
 * @since 1.0
 */
public class GateonRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements GateonRepository<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;

    private EntityManager entityManager;

    public GateonRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }


    @Override
    public Searcher<T> searcher() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        Class<T> javaType = entityInformation.getJavaType();
        return new DefaultSearcher<>(entityManager, criteriaBuilder, javaType);
    }


}
