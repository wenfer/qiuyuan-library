package site.qiuyuan.library.common.exception;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.3.5
 */
public class ServiceException extends QiuyuanException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
