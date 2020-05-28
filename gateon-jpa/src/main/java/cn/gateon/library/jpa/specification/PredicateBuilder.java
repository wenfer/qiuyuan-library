package cn.gateon.library.jpa.specification;

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

    String getJoinProperty();

    OperatorEnum operator();

}
