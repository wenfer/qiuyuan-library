package cn.gateon.library.redis;

/**
 * <p>
 *     分布式锁
 * </p>
 *
 * @author qiuyuan
 * @since 2.4.2
 */
public interface RedisLockTemplate {

    /**
     *
     * @param key 钥匙
     * @param expired 有效期  (秒)
     * @return
     */
    boolean tryLock(String key, long expired);


    boolean unlock(String key);

}
