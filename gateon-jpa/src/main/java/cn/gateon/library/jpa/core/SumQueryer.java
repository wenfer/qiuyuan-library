package cn.gateon.library.jpa.core;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2
 */
public interface SumQueryer<R> {

    SumQueryer<R> sum(String property);

}
