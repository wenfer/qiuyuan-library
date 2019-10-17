package cn.gateon.library.jpa;

import cn.gateon.library.jpa.repo.GateonRepositoryImpl;
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
        repositoryBaseClass = GateonRepositoryImpl.class)
public @interface EnableJPA {
}
