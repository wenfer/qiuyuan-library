package site.qiuyuan.library.redis.lock;

import site.qiuyuan.library.redis.RedisLockTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.4.2
 */
public class RedisLockTemplateImpl implements RedisLockTemplate {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisLockTemplateImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryLock(String key, long expired) {
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(key, "lock");
        if (lock != null && lock) {
            redisTemplate.expire(key, expired, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    @Override
    public boolean unlock(String key) {
        Boolean delete = redisTemplate.delete(key);
        return delete != null && delete;
    }
}
