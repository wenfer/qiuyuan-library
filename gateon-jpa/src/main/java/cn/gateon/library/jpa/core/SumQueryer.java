package cn.gateon.library.jpa.core;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2
 */
public interface SumQueryer<F,R> extends Queryer<F, R> {

    SumQueryer<F,R> sum(String property);

}
