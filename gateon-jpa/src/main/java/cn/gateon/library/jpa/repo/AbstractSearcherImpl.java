package cn.gateon.library.jpa.repo;

import cn.gateon.library.common.exception.GateonException;
import cn.gateon.library.jpa.searcher.Conditional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
abstract class AbstractSearcherImpl<R> implements Conditional {

    CriteriaBuilder cb;

    protected From<?, ?> root;

    CriteriaQuery<R> query;

    protected List<Predicate> predicates;

    AbstractSearcherImpl(CriteriaBuilder cb, Class<?> from, CriteriaQuery<R> query, List<Predicate> predicates) {
        this.cb = cb;
        this.query = query;
        this.root = query.from(from);
        this.predicates = predicates;
    }

    AbstractSearcherImpl(CriteriaBuilder cb, From<?, ?> root, List<Predicate> predicates) {
        this.cb = cb;
        this.root = root;
        this.predicates = predicates;
    }

    @Override
    public void where(Where where) {
        Map<String, CommonBuilder> builders = where.builders();
        Predicate[] predicateArray = new Predicate[builders.size()];
        int i = 0;
        for (Map.Entry<String, CommonBuilder> entry : builders.entrySet()) {
            Path<Object> objectPath = root.get(entry.getKey());
            if (objectPath == null) {
                throw new GateonException("不存在的字段:" + entry.getKey() + "，请检查是否拼错");
            }
            predicateArray[i] = entry.getValue().build(cb, objectPath);
            i++;
        }
        if (OperatorEnum.AND.equals(where.operator())) {
            this.predicates.add(cb.and(predicateArray));
        } else if (OperatorEnum.OR.equals(where.operator())) {
            this.predicates.add(cb.or(predicateArray));
        }
    }


}
