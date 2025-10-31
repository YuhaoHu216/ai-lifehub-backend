package space.huyuhao.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class SimpleRedisLock implements Lock{

    // 业务名称
    private String name;
    private static final String KEY_PREFIX = "lock";

    private StringRedisTemplate stringRedisTemplate;

    public SimpleRedisLock(String name,StringRedisTemplate stringRedisTemplate){
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Override
    public boolean tryLock(long timeoutSec) {
        // 获取线程标识作为value
        Long threadId = Thread.currentThread().getId();
        // 获取锁
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + name,threadId+"",timeoutSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(result);
    }

    @Override
    public void unLock() {
        stringRedisTemplate.delete(KEY_PREFIX + name);
    }
}
