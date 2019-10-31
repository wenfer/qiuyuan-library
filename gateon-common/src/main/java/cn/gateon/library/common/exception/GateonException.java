package cn.gateon.library.common.exception;


/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public class GateonException extends RuntimeException {

    public GateonException(String message) {
        super(message);
    }


    public GateonException(String message, Throwable cause) {
        super(message, cause);
    }

}
