package cn.gateon.library.jpa.specification;

import cn.gateon.library.jpa.enums.OperatorEnum;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2
 */
public interface Or extends Where {

    @Override
    default OperatorEnum operator() {
        return OperatorEnum.OR;
    }
}
