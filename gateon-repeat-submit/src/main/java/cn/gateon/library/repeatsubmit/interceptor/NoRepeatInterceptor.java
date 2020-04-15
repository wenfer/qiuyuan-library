package cn.gateon.library.repeatsubmit.interceptor;

import cn.gateon.library.common.rest.Result;
import cn.gateon.library.common.utils.JsonUtil;
import cn.gateon.library.redis.RedisLockTemplate;
import cn.gateon.library.repeatsubmit.RepeatSubmitProperties;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.3
 */
@Slf4j
public class NoRepeatInterceptor implements HandlerInterceptor {

    private RedisLockTemplate redisLockTemplate;

    private static String LOCK_PREFIX = "_no_repeat_lock_";

    private RepeatSubmitProperties properties;

    private Result result = Result.fail(-2, "请求正在处理，请勿重复提交");

    public NoRepeatInterceptor(RedisLockTemplate redisLockTemplate, RepeatSubmitProperties properties) {
        this.redisLockTemplate = redisLockTemplate;
        this.properties = properties;
    }

    /**
     * rt = repeatToken
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String rt = request.getHeader("rt");
        if (StrUtil.isNotBlank(rt)) {
            if (redisLockTemplate.tryLock(properties.getPrefix() + LOCK_PREFIX + rt, properties.getExpired())) {
                return true;
            }
            log.info("rt:{} 可能为重复提交", rt);
            PrintWriter writer = response.getWriter();
            response.setCharacterEncoding(CharsetUtil.UTF_8);
            response.setContentType("text/html; charset=utf-8");
            writer.write(JsonUtil.toJson(result));
            writer.close();
            return false;
        }
        return true;
    }

}
