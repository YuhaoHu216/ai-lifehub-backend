package space.huyuhao.service.impl;

import cn.hutool.cache.Cache;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import space.huyuhao.dto.ShopDTO;
import space.huyuhao.mapper.ShopMapper;
import space.huyuhao.po.Shop;
import space.huyuhao.service.ShopService;
import space.huyuhao.vo.Result;

import static space.huyuhao.constant.RedisConstants.CACHE_SHOP_KEY;

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
    public Result queryShop(int id) {

        // 2. 查询缓存
        String cacheKey = CACHE_SHOP_KEY + id;
        String shopJson = stringRedisTemplate.opsForValue().get(cacheKey);
            // 命中返回
        if(StrUtil.isNotBlank(shopJson)){
            Shop shop = JSONUtil.toBean(shopJson, Shop.class);
            return Result.success(shop);
        }
        // 3. 查询数据库
        Shop shop = shopMapper.selectShop(id);
        if(shop == null){
            return Result.error("店铺不存在");
        }
        // 4.将数据写入缓存
        stringRedisTemplate.opsForValue().set(cacheKey,JSONUtil.toJsonStr(shop));
        return Result.success(shop);
    }
}
