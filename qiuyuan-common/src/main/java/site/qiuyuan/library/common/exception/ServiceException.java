package site.qiuyuan.library.common.exception;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 */
public class ServiceException extends QiuyuanException implements ExceptionPromotable{

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String promptMsg() {
        return getMessage();
    }
}
