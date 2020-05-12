package cn.gateon.library.dubbo;

import lombok.Getter;
import org.apache.dubbo.rpc.RpcException;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
@Getter
public class DubboException extends RpcException {

    private String serviceId;

    public DubboException(String message, String serviceId) {
        super(message);
        this.serviceId = serviceId;
    }

    public DubboException(String message, Throwable cause) {
        super(3, message, cause);
    }

    public DubboException(String message) {
        super(3, message);
    }

    public DubboException(Throwable cause) {
        super(3, cause);
    }
}
