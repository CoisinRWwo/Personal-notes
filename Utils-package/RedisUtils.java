import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Su HangFei
 * @Date: 2024/4/24 14:21
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置Redis键值对
     * set key
     *
     * @param key   Redis键
     * @param value Redis值
     */
    public boolean set(final String key, final Object value) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    
    /**
     * 设置Redis键值对，包含Redis的过期时间
     *
     * @param key        Redis键
     * @param value      Redis值
     * @param expireTime 过期时间
     */
    public boolean set(final String key, Object value, Long expireTime) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 根据Redis键，获取Redis值
     * get key
     *
     * @param key Redis键
     * @return
     */
    public Object get(final String key) {
        ValueOperations<String, Object> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }


    /**
     * 根据Redis键，判断是否存在
     *
     * @param key Redis键值
     * @return
     */
    public boolean exist(final String key) {
        Boolean isExist = redisTemplate.hasKey(key);
        if (null == isExist) {
            isExist = false;
        }
        return isExist;
    }


    /**
     * 根据Redis键，删除缓存
     *
     * @param key Redis键值
     * @return
     */
    public boolean remove(final String key) {
        Boolean isDeleted = null;
        if (this.exist(key)) {
            isDeleted = redisTemplate.delete(key);
        }
        if (null == isDeleted) {
            isDeleted = false;
        }
        return isDeleted;
    }


    /**
     * 根据Redis键，设置有效期
     *
     * @param key     Redis键值
     * @param seconds 有效期
     * @return
     */
    public boolean expire(final String key, long seconds) {
        Boolean isExpired = redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        if (null == isExpired) {
            isExpired = false;
        }
        return isExpired;
    }


    /**
     * 根据Redis键，自增1
     * incur key
     *
     * @param key Redis键
     * @return
     */
    public Long incur(final String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.increment(key, 1);
    }


    /**
     * 根据Redis键，添加集合
     * sadd key value1, value2
     *
     * @param key    Redis键
     * @param userId 集合中的元素（用户Id）
     * @return 成功添加到 Redis 集合中的元素数量
     */
    public Long addSet(final String key, final String userId) {
        SetOperations<String, Object> operations = redisTemplate.opsForSet();
        return operations.add(key, userId);
    }


    /**
     * 根据Redis键，移除集合中元素
     * srem key value
     *
     * @param key    Redis键
     * @param userId 移除的元素（用户Id）
     * @return 被成功移除的元素的数量
     */
    public Long removeSet(final String key, final String userId) {
        SetOperations<String, Object> operations = redisTemplate.opsForSet();
        return operations.remove(key, userId);
    }

    /**
     * 根据Redis键，统计集合数量
     * scard key
     *
     * @param key Redis键
     */
    public Long countSet(final String key) {
        SetOperations<String, Object> operations = redisTemplate.opsForSet();
        return operations.size(key);
    }


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
