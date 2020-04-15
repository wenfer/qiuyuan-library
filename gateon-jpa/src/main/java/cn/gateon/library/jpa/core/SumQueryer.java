package cn.gateon.library.jpa.core;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2
 */
public interface SumQueryer<F> extends Queryer<F, Long> {

    SumQueryer<F> sum(String property);

}
