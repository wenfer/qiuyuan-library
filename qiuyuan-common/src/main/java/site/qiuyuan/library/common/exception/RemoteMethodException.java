package site.qiuyuan.library.common.exception;

import lombok.Getter;

/**
 * <p>
 * 远程方法调用失败
 * </p>
 *
 * @author qiuyuan
 */
@Getter
public class RemoteMethodException extends QiuyuanException {

    private String methodKey;

    private String param;

    private String msg;

    private String result;

    public RemoteMethodException(String methodKey, String msg, String param, String result) {
        super(msg);
        this.methodKey = methodKey;
        this.msg = msg;
        this.param = param;
        this.result = result;
    }

}
