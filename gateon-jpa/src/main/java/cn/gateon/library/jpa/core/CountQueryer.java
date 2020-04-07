package cn.gateon.library.jpa.core;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface CountQueryer<T> extends Queryer<T, Long> {

    long count();


}
