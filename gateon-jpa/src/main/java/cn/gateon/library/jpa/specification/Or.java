package cn.gateon.library.jpa.specification;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2
 */
public interface Or<F> extends Where<F> {

    /**
     * 构造or查询需要显式调用此方法
     */
    void build();

}
