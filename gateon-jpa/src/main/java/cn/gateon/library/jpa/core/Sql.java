package cn.gateon.library.jpa.core;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * <p>
 * query拼接工具类
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class Sql {

    private final CriteriaBuilder cb;

    private final List<Predicate> predicates = new ArrayList<>();

    public Sql(CriteriaBuilder cb) {
        this.cb = cb;
    }

    public void where(Function<CriteriaBuilder, Predicate> function) {
        predicates.add(function.apply(cb));
    }

    public void clean() {
        predicates.clear();
    }

    public AbstractQuery build(AbstractQuery query) {
        query.where(getWhere());
        return query;
    }

    private Predicate[] getWhere() {
        Predicate[] array = new Predicate[this.predicates.size()];
        return this.predicates.toArray(array);
    }

    public Expression<Long> sumAsLong(Path<Integer> property) {
        return cb.coalesce(cb.sumAsLong(property), 0L);
    }

    public CriteriaQuery<Long> sum(Path<Integer> objectPath) {
        CriteriaQuery<Long> sum = cb.createQuery(Long.class);
        sum.where(getWhere());
        return sum.select(cb.nullif(cb.sumAsLong(objectPath), 0L));
    }

    public <T> CriteriaQuery<Long> count(Path<T> root) {
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        query.select(cb.count(root));
        query.where(cb.and(getWhere()));
        return query;
    }
}
