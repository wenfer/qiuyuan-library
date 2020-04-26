package cn.gateon.library.jpa.repo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.4
 */
@FunctionalInterface
interface CommonBuilder {

    Predicate build(CriteriaBuilder cb, Path<?> expression);

}
