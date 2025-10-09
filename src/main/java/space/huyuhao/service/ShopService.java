package space.huyuhao.service;

import org.springframework.stereotype.Service;
import space.huyuhao.dto.ShopDTO;
import space.huyuhao.po.Shop;
import space.huyuhao.vo.Result;

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
    Result queryShop(int id);

    /**
     * 修改店铺信息
     * @param shop 要修改成的信息
     * @return
     */
    Result updateShop(Shop shop);
}
