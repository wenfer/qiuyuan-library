package cn.gateon.library.mvc.exceptionhandle;

import cn.gateon.library.common.exception.GateonException;
import cn.gateon.library.common.exception.RemoteMethodException;
import cn.gateon.library.common.rest.Result;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Method;

/**
 * <p>
 * 全局错误处理
 * </p>
 *
 * @author qiuyuan
 * @since 1.2.5
 */
@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandle {

    @ExceptionHandler(GateonException.class)
    public ResponseEntity<?> handleControllerException(GateonException ex) {
        StackTraceElement stackTraceElement = ex.getStackTrace()[0];
        for (StackTraceElement traceElement : ex.getStackTrace()) {
            if (!StrUtil.equals(traceElement.getClassName(), "cn.gateon.mengbg.feign.FeignConfiguration") && StrUtil.contains("gateon", stackTraceElement.getClassName())) {
                stackTraceElement = traceElement;
            }
        }
        log.warn("业务异常:{},class:{},linenumber:{}", ex.getMessage(),
                stackTraceElement.getClassName(), stackTraceElement.getLineNumber());
        return ResponseEntity.status(500).body(Result.fail(ex.getLocalizedMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleControllerException(Exception ex) {
        log.error("预料之外的异常:", ex);
        return ResponseEntity.status(500).body(Result.fail(ex.getLocalizedMessage()));
    }


    @ExceptionHandler(ClientAbortException.class)
    public ResponseEntity<?> clientAbortException(ClientAbortException ex) {
        log.warn("调用超时导致客户端中断链接:", ex);
        return ResponseEntity.status(500).build();
    }


    @ExceptionHandler(RemoteMethodException.class)
    public ResponseEntity<?> remoteException(RemoteMethodException ex) {
        log.warn("接口调用失败，key:{},参数:{},返回:{}", ex.getMethodKey(), ex.getParam(), ex.getResult());
        return ResponseEntity.ok(Result.fail(-1, ex.getMsg()));
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> requestMethodNotSupport(HttpRequestMethodNotSupportedException e) {
        String method = e.getMethod();
        log.warn("不支持的请求方法:{},仅支持:{}", method, StrUtil.join(",", e.getSupportedMethods()));
        return ResponseEntity.ok(Result.fail(e.getLocalizedMessage()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> invalidException(InvalidFormatException e) {
        Object value = e.getValue();
        Class<?> targetType = e.getTargetType();
        log.warn("参数类型解析失败，值:{},类型:{}", value.toString(), targetType);
        return ResponseEntity.ok(Result.fail(value.toString() + "无法转换为" + targetType + "类型"));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> argumentException(MethodArgumentTypeMismatchException e) {
        //String propertyName = e.getPropertyName();
        MethodParameter parameter = e.getParameter();
        Object value = e.getValue();
        Method method = parameter.getMethod();
        log.info("method:{} 解析参数出错,所在类:{}，参数名:{}，值:{}", method == null ? "" : method.getName(), parameter.getContainingClass().getName()
                , parameter.getParameterName(), value == null ? null : value.toString());
        return ResponseEntity.ok(Result.fail(e.getLocalizedMessage()));
    }


}