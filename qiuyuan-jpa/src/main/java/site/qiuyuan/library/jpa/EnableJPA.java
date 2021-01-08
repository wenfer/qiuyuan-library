package site.qiuyuan.library.jpa;

import site.qiuyuan.library.jpa.factory.BaseRepositoryFactoryBean;
import site.qiuyuan.library.jpa.repo.BaseRepositoryImpl;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;

import java.lang.annotation.*;

/**
 * @author qiuyuan
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DslConfiguration.class)
@EnableJpaRepositories(
        value = {"**.repository.**", "**.model"},
        repositoryBaseClass = BaseRepositoryImpl.class,
        queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND,
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class
)
public @interface EnableJPA {
}
