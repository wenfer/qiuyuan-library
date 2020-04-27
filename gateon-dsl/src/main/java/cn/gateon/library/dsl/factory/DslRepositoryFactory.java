package cn.gateon.library.dsl.factory;

import cn.gateon.library.dsl.repo.DslRepositoryImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.jpa.projection.CollectionAwareProjectionFactory;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.query.JpaQueryLookupStrategy;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @see JpaRepositoryFactory  抄的这个类
 * @since 2.0
 */
public class DslRepositoryFactory extends RepositoryFactorySupport {

    private final EntityManager entityManager;

    private final QueryExtractor extractor;

    private final CrudMethodMetadataPostProcessor crudMethodMetadataPostProcessor;

    /**
     * Creates a new {@link JpaRepositoryFactory}.
     *
     * @param entityManager must not be {@literal null}
     */
    public DslRepositoryFactory(EntityManager entityManager) {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        this.entityManager = entityManager;
        this.extractor = PersistenceProvider.fromEntityManager(entityManager);
        this.crudMethodMetadataPostProcessor = new CrudMethodMetadataPostProcessor();
        addRepositoryProxyPostProcessor(crudMethodMetadataPostProcessor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, ID> JpaEntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return (JpaEntityInformation<T, ID>) JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
    }

    @Override
    protected Object getTargetRepository(RepositoryInformation information) {
        JpaEntityInformation<?, ?> entityInformation = getEntityInformation(information.getDomainType());
        DslRepositoryImpl<?, ?> repository = getTargetRepositoryViaReflection(information, entityInformation, entityManager);
        repository.setRepositoryMethodMetadata(crudMethodMetadataPostProcessor.getCrudMethodMetadata());
        return repository;
    }

    /**
     * 这玩意是方法名查询的核心，如果方法名查询不生效，一定是这里出了问题
     */
    @Override
    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(@Nullable QueryLookupStrategy.Key key,
                                                                   EvaluationContextProvider evaluationContextProvider) {
        return Optional.of(JpaQueryLookupStrategy.create(entityManager, key, extractor, evaluationContextProvider));
    }

    @Override
    protected ProjectionFactory getProjectionFactory(ClassLoader classLoader, BeanFactory beanFactory) {
        CollectionAwareProjectionFactory factory = new CollectionAwareProjectionFactory();
        factory.setBeanClassLoader(classLoader);
        factory.setBeanFactory(beanFactory);
        return factory;
    }


    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return DslRepositoryImpl.class;
    }


}
