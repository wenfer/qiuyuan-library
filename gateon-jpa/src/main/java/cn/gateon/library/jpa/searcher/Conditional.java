package cn.gateon.library.jpa.searcher;

import cn.gateon.library.jpa.specification.JoinCollectionWhere;
import cn.gateon.library.jpa.specification.PredicateBuilder;
import cn.gateon.library.jpa.specification.Where;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
public interface Conditional {

    void where(PredicateBuilder where);

    boolean clear();

    Where and();

    Where join(String property);

    JoinCollectionWhere joinCollection(String property);

    Where or();

}
