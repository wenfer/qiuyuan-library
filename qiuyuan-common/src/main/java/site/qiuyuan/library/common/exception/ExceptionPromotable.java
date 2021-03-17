package site.qiuyuan.library.common.exception;

/**
 * @author wangye
 * 可提示的异常
 * 暂时将异常分为两种
 * 1，系统内异常，供开发调试人员查阅
 * 2，业务异常，消息规范，可直接提示给前端msg
 */
public interface ExceptionPromotable {

    String promptMsg();

}
