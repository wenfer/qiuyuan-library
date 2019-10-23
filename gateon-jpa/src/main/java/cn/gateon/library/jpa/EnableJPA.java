package cn.gateon.library.jpa;

import cn.gateon.library.jpa.repo.BaseRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.*;

/**
 * @author qiuyuan
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableJpaRepositories(
        value = {"**.repository.**", "**.model.**"},
        repositoryBaseClass = BaseRepositoryImpl.class)
public @interface EnableJPA {
}
