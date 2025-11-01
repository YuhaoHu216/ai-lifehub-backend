package space.huyuhao.utils;

import cn.hutool.core.lang.UUID;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;


import java.util.concurrent.TimeUnit;

public class SimpleRedisLock implements Lock{

    // 业务名称
    private String name;
    private static final String KEY_PREFIX = "lock";
    private static final String ID_PREFIX = UUID.randomUUID().toString(true) + "-";

    private StringRedisTemplate stringRedisTemplate;

    public SimpleRedisLock(String name,StringRedisTemplate stringRedisTemplate){
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Override
    public boolean tryLock(long timeoutSec) {
        // 获取线程标识作为value
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        // 获取锁
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + name,threadId,timeoutSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(result);
    }

    @Override
    public void unLock() {
        // 获取线程标识
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        // 获取锁中的线程标识
        String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX + name);
        // 比较后再解锁
        if(threadId.equals(id)){
            stringRedisTemplate.delete(KEY_PREFIX + name);
        }
    }
}
