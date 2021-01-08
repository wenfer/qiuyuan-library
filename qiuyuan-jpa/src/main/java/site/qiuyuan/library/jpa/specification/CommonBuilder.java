package site.qiuyuan.library.jpa.specification;

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
public interface CommonBuilder {

    Predicate build(CriteriaBuilder cb, Path<?> expression);

}
