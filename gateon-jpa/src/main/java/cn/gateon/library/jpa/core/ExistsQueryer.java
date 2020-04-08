package cn.gateon.library.jpa.core;

/**
 * <p>
 *     是否存在
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface ExistsQueryer<F> extends Queryer<F,Boolean> {

    boolean exists();

}
