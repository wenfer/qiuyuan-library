package site.qiuyuan.library.feign;

import site.qiuyuan.library.common.exception.RemoteMethodException;
import site.qiuyuan.library.common.rest.Result;
import site.qiuyuan.library.common.utils.JsonUtil;
import feign.Request;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.3
 */
@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {


    @Override
    public Exception decode(String methodKey, Response response) {
        String s = null, msg = null, param = null;
        try {
            s = Util.toString(response.body().asReader());
        } catch (IOException e) {
            log.warn("读取响应失败:", e);
        }
        if (StringUtils.isNotBlank(s)) {
            Result result = JsonUtil.fromJson(s, Result.class);
            if (result != null) {
                if ("操作成功".equals(result.getMsg())) {
                    msg = "接口异常";
                } else {
                    msg = result.getMsg();
                }
            }
        }
        Request request = response.request();
        log.warn("{}接口异常，响应：{}", methodKey, s);
        if (RequestMethod.POST.name().equals(request.method())) {
            param = new String(request.body());
        } else if (RequestMethod.GET.name().equals(request.method())) {
            param = request.url();
        }
        return new RemoteMethodException(methodKey, msg, param, s);
    }
}
