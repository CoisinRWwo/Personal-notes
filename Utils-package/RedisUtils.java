package cn.hsa.zephyr.medicine_psi.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Su HangFei
 * @Date: 2024/4/24 14:21
 */
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求表示
     * @param expireTime 过期时间
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(String lockKey, String requestId, long expireTime) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        // 尝试设置键值
        Boolean result = operations.setIfAbsent(lockKey, requestId);
        if (Boolean.TRUE.equals(result)) {
            // 如果设置成功，则为键设置过期时间
            redisTemplate.expire(lockKey, expireTime, TimeUnit.MILLISECONDS);
            return true;
        }
        return false;
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        String script = "if redis.call('get',KEYS[1])== ARGV[1] then " +
                "return redis.call('del'， KEYS[1])" +
                "else " +
                "return " +
                "end";
        Long result = redisTemplate.execute(
                new DefaultRedisScript<>(script, Long.class), Collections.singletonList(lockKey), requestId);
        return result != null && result > 0;
    }
}
