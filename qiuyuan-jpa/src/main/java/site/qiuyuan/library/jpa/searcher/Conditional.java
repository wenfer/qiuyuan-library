package site.qiuyuan.library.jpa.searcher;

import site.qiuyuan.library.jpa.specification.JoinCollectionWhere;
import site.qiuyuan.library.jpa.specification.PredicateBuilder;
import site.qiuyuan.library.jpa.specification.Where;

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
