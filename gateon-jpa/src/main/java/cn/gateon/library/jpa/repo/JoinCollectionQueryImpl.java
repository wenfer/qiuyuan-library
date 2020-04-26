package cn.gateon.library.jpa.repo;

import cn.gateon.library.jpa.searcher.JoinCollectionQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
class JoinCollectionQueryImpl<T> extends AbstractSearcherImpl<T> implements JoinCollectionQuery<T> {
    JoinCollectionQueryImpl(Join<?, T> join, CriteriaBuilder cb, List<Predicate> predicates) {
        super(cb, join, predicates);
    }

    @Override
    public JoinCollectionQuery<T> is(T value) {
        predicates.add(cb.equal(root, value));
        return this;
    }

    @Override
    public JoinCollectionQuery<T> in(Collection<T> values) {
        predicates.add(root.in(values));
        return this;
    }
}
