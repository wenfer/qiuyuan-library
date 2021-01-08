package site.qiuyuan.library.jpa.specification;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2
 */
public interface PredicateBuilder {

    Map<String, CommonBuilder> builders();

    List<Term> terms();

    String getJoinProperty();

    OperatorEnum operator();

}
