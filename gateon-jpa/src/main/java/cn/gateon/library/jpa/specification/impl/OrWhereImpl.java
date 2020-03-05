package cn.gateon.library.jpa.specification.impl;

import cn.gateon.library.jpa.specification.Or;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2
 */
public class OrWhereImpl<F, R> extends WhereImpl<F, R> implements Or<F> {

    private final CriteriaBuilderImpl cb;

    protected final List<Predicate> predicates;

    public OrWhereImpl(CriteriaBuilder cb, From<F, R> root, List<Predicate> predicates) {
        super(cb, root, new ArrayList<>());
        this.predicates = predicates;
        this.cb = (CriteriaBuilderImpl) cb;
    }


    @Override
    public void build() {
        List<Predicate> predicates = super.predicates;
        Predicate[] pd = new Predicate[predicates.size()];
        predicates.add(cb.or(predicates.toArray(pd)));
    }
}
