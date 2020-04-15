package cn.gateon.library.jpa.core;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface SingleSumQueryer<F> extends Queryer<F,Long>{

    Long sum(String property);

}
