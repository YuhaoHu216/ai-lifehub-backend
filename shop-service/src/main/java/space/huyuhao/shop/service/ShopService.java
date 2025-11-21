package space.huyuhao.shop.service;

import org.springframework.stereotype.Service;
import space.huyuhao.shop.vo.Result;
import space.huyuhao.shop.po.Shop;


/**
 * @author hyh
 * @since 2025-10
 */
@Service
public interface ShopService {
    /**
     * 查询店铺信息
     * @param id 店铺id
     * @return 店铺信息
     */
    Result queryShop(Long id);

    /**
     * 修改店铺信息
     * @param shop 要修改成的信息
     * @return
     */
    Result updateShop(Shop shop);
}
