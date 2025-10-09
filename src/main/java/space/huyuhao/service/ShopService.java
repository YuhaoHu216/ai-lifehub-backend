package space.huyuhao.service;

import org.springframework.stereotype.Service;
import space.huyuhao.dto.ShopDTO;
import space.huyuhao.vo.Result;

@Service
public interface ShopService {
    /**
     * 查询店铺信息
     * @param id 店铺id
     * @return 店铺信息
     */
    Result queryShop(int id);
}
