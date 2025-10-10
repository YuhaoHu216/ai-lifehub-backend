package space.huyuhao.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import space.huyuhao.constant.RedisConstants;
import space.huyuhao.enums.ErrorCodeEnum;
import space.huyuhao.exception.MyException;
import space.huyuhao.mapper.ShopMapper;
import space.huyuhao.po.Shop;
import space.huyuhao.service.ShopService;
import space.huyuhao.vo.Result;

import java.util.concurrent.TimeUnit;

import static space.huyuhao.constant.RedisConstants.*;

/**
 * @author hyh
 * @since 2025-10
 */
@Service
public class ShopServiceImpl implements ShopService {

    private final StringRedisTemplate stringRedisTemplate;

    public ShopServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Autowired
    private ShopMapper shopMapper;

    /**
     * 查询店铺信息
     * @param id 店铺id
     * @return 店铺信息
     */
    public Result queryShop(Long id) {

//        Shop shop = queryWithPassThrough(id);
        Shop shop = queryWithMutex(id);
        return Result.success(shop);
    }
    // TODO 布隆过滤器解决缓存穿透问题
    // 封装用缓存空串解决缓存穿透的逻辑
    public Shop queryWithPassThrough(Long id){
        // 2. 查询缓存
        String cacheKey = CACHE_SHOP_KEY + id;
        String shopJson = stringRedisTemplate.opsForValue().get(cacheKey);
        // 命中返回,为有效数据(不为null且不为空串)
        if(StrUtil.isNotBlank(shopJson)){
            return JSONUtil.toBean(shopJson, Shop.class);
        }
        // 判断是不是我们之前存的空串
        if(shopJson != null && shopJson.isEmpty()){
            // 返回错误信息
            throw new MyException(ErrorCodeEnum.SHOP_NOT_FOUND);
        }
        // 3. 查询数据库
        Shop shop = shopMapper.selectShopById(id);
        if(shop == null){
            // 空值将空串写入redis
            stringRedisTemplate.opsForValue().set(cacheKey,"",60,TimeUnit.MINUTES);
        }
        // 4.将数据写入缓存
        stringRedisTemplate.opsForValue().set(cacheKey,JSONUtil.toJsonStr(shop),CACHE_SHOP_TTL, TimeUnit.MINUTES);
        return shop;
    }

    // 利用互斥锁解决缓存击穿问题(包含缓存空串解决缓存穿透)
    public Shop queryWithMutex(Long id){
        // 构建key,并查询
        String cacheKey = CACHE_SHOP_KEY + id;
        String shopJson = stringRedisTemplate.opsForValue().get(cacheKey);
        // 判断情况,不为null且不为空串
        if(StrUtil.isNotBlank(shopJson)){
            // 转换为对象返回
            return JSONUtil.toBean(shopJson, Shop.class);
        }
        // 判断是不是之前存储的空串
        if(shopJson != null && shopJson.isEmpty()){
            throw new MyException(ErrorCodeEnum.SHOP_NOT_FOUND);
        }
        // 开始重构缓存
        String lockKey = LOCK_SHOP_KEY + id;
        Shop shop = null;
        try{
            // 如果获取不到锁就休眠线程,再获取一次
            if(!tryLock(lockKey)){
                Thread.sleep(60);
                queryWithMutex(id);
            }
            // 获取到锁就开始重建缓存
            shop = shopMapper.selectShopById(id);
            // 如果没有查询到
            if(shop == null){
                // 缓存空串,然后报错
                stringRedisTemplate.opsForValue().set(cacheKey,"",60,TimeUnit.MINUTES);
                throw new MyException(ErrorCodeEnum.SHOP_NOT_FOUND);
            }
            // 缓存写入redis
            shopJson = JSONUtil.toJsonStr(shop);
            stringRedisTemplate.opsForValue().set(cacheKey,shopJson,CACHE_SHOP_TTL,TimeUnit.MINUTES);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            // 释放互斥锁
            unlock(lockKey);
        }
        return shop;
    }

    // 获取锁
    public boolean tryLock(String key){
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key,"1", RedisConstants.LOCK_SHOP_TTL,TimeUnit.MINUTES);
        return BooleanUtil.isTrue(flag);
    }

    // 释放锁
    public void unlock(String key){
        stringRedisTemplate.delete(key);
    }


    /**
     * 更新店铺信息
     * @param shop 要修改成的信息
     * @return 修改结果
     */
    public Result updateShop(Shop shop) {
        Long id = shop.getId();
        if(id == null){
            return Result.error("店铺不存在");
        }
        // 先修改数据库
        shopMapper.update(shop);
        // 再删除缓存
        String cacheKey = CACHE_SHOP_KEY + id;
        stringRedisTemplate.delete(cacheKey);
        return Result.success();
    }
}
