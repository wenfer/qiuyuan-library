package site.qiuyuan.library.common.exception;

import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/5
 */
public class ObjectNotExistException extends QiuyuanException implements ExceptionPromotable {

    private Serializable id;

    public ObjectNotExistException(Serializable id) {
        super("不存在的对象,id" + id.toString());
    }

    public ObjectNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String promptMsg() {
        return "该数据不存在";
    }
}
