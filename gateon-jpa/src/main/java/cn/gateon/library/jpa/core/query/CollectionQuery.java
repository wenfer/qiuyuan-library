package cn.gateon.library.jpa.core.query;

import cn.gateon.library.jpa.core.CollectionQueryer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.List;

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

    private final List<Predicate> where;

    public CollectionQuery(Join<?, C> cCollectionJoin, CriteriaBuilder criteriaBuilder, List<Predicate> where) {
        this.root = cCollectionJoin;
        this.where = where;
        this.criteriaBuilder = criteriaBuilder;

    }

    @Override
    public CollectionQueryer<C> eq(C value) {
        where.add(criteriaBuilder.equal(root, value));
        return this;
    }

    @Override
    public CollectionQueryer<C> in(Collection<C> values) {
        where.add(root.in(values));
        return this;
    }
}
