package cn.gateon.library.jpa.specification;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaQuery;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface Where<F> {

    Where<F> is(String property,Object value);


   void build(AbstractQuery query);
}
