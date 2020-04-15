package cn.gateon.library.repeatsubmit;

import cn.gateon.library.redis.EnableRedis;
import cn.gateon.library.redis.RedisLockTemplate;
import cn.gateon.library.repeatsubmit.interceptor.NoRepeatInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.3
 */
@EnableRedis
@EnableConfigurationProperties(RepeatSubmitProperties.class)
@Configuration
public class RepeatSubmitConfiguration implements WebMvcConfigurer {

    private RepeatSubmitProperties properties;

    private RedisLockTemplate redisLockTemplate;

    public RepeatSubmitConfiguration(RepeatSubmitProperties properties, RedisLockTemplate redisLockTemplate) {
        this.properties = properties;
        this.redisLockTemplate = redisLockTemplate;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new NoRepeatInterceptor(redisLockTemplate, properties)).addPathPatterns("/**");
    }
}
