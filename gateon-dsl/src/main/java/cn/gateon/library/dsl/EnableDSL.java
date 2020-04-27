package cn.gateon.library.dsl;

import cn.gateon.library.dsl.factory.DslRepositoryFactoryBean;
import cn.gateon.library.dsl.repo.DslRepositoryImpl;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;

import java.lang.annotation.*;

/**
 * @author qiuyuan
 * @since 2.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DslConfiguration.class)
@EnableJpaRepositories(
        value = {"**.repository.**", "**.model"},
        repositoryBaseClass = DslRepositoryImpl.class,
        queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND,
        repositoryFactoryBeanClass = DslRepositoryFactoryBean.class
)
public @interface EnableDSL {
}
