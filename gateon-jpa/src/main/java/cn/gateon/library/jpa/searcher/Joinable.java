package cn.gateon.library.jpa.searcher;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
public interface Joinable {

    JoinQuery join(String field);

}
