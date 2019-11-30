package cn.gateon.library.jpa.core.query;

import cn.gateon.library.jpa.core.CollectionQueryer;

import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.1.4
 */
public class CollectionQuery<C> implements CollectionQueryer<C> {

    private Path<C> root;

    private CriteriaBuilder criteriaBuilder;

    public CollectionQuery(CollectionJoin<?, C> cCollectionJoin, CriteriaBuilder criteriaBuilder) {
        this.root = cCollectionJoin;
        this.criteriaBuilder = criteriaBuilder;

    }

    @Override
    public CollectionQueryer<C> eq(C value) {
        criteriaBuilder.equal(root,value);
        return this;
    }

    @Override
    public CollectionQueryer<C> in(Object... value) {
        criteriaBuilder.in(root).in(value);
        return this;
    }
}
