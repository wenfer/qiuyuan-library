package cn.gateon.library.jpa.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
@Getter
@AllArgsConstructor
public class Term {

    private String property;

    private CommonBuilder builder;

}
