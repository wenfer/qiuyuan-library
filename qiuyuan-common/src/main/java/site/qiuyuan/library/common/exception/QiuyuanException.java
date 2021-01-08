package site.qiuyuan.library.common.exception;


/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class QiuyuanException extends RuntimeException {

    public QiuyuanException(String message) {
        super(message);
    }


    public QiuyuanException(String message, Throwable cause) {
        super(message, cause);
    }

}
