package site.qiuyuan.library.repeatsubmit.aop;

import site.qiuyuan.library.common.exception.QiuyuanException;
import site.qiuyuan.library.redis.RedisLockTemplate;
import site.qiuyuan.library.repeatsubmit.annotation.RepeatValid;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Method;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.3.2
 */
@Slf4j
@Aspect
@Component
public class RepeatAopComponent {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private RedisLockTemplate redisLockTemplate;

    public RepeatAopComponent(RedisLockTemplate redisLockTemplate) {
        this.redisLockTemplate = redisLockTemplate;
    }

    private static String LOCK_PREFIX = "_no_repeat_annotation_lock_";

    @Before("@annotation(site.qiuyuan.library.repeatsubmit.annotation.RepeatValid)")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("重复请求校验器拦截到方法:class:{},method:{}", signature.getDeclaringTypeName(), signature.getName());
        RepeatValid repeatValid = method.getAnnotation(RepeatValid.class);
        Object[] args = joinPoint.getArgs();
        if (args.length == 0) {
            return;
        }
        Object arg = args[0];
        if (arg == null) {
            return;
        }
        String md5 = null;
        try {
            md5 = DigestUtils.md5DigestAsHex(MAPPER.writeValueAsBytes(arg));
        } catch (JsonProcessingException e) {
            log.error("json序列化失败:", e);
        }
        if (StrUtil.isNotBlank(md5)) {
            if (!redisLockTemplate.tryLock(repeatValid.prefix() + LOCK_PREFIX + md5, repeatValid.expired())) {
                throw new QiuyuanException("请不要重复操作");
            }
        }
    }

}
