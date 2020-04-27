package cn.gateon.library.jpa.repo;

import cn.gateon.library.jpa.searcher.JoinQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
class JoinQueryImpl<R> extends AbstractSearcherImpl<R> implements JoinQuery {

    JoinQueryImpl(CriteriaBuilder cb, From<?, ?> root, List<Predicate> predicates) {
        super(cb, root, predicates);
    }

    @Override
    public JoinQuery join(String field) {
        Join<?, ?> join = root.join(field);
        return new JoinQueryImpl<>(cb, join, predicates);
    }
}
