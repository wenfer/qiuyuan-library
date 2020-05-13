package cn.gateon.library.dubbo;

import cn.gateon.library.common.rest.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
@Slf4j
@RestControllerAdvice
public class DubboExceptionHandler {


    @ExceptionHandler({RpcException.class})
    public ResponseEntity<?> entity(RpcException ex) {
        log.error("服务调用异常:", ex);
        return ResponseEntity.ok().body(Result.fail(-1, "服务异常，请联系相关人员"));
    }

    @ExceptionHandler({DubboException.class})
    public ResponseEntity<?> dubboException(DubboException ex) {
        log.error("服务[{}]发生异常:", ex.getServiceId(), ex);
        return ResponseEntity.ok().body(Result.fail(-1, ex.getLocalizedMessage()));
    }
}
