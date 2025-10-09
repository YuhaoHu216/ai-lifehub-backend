package space.huyuhao.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import space.huyuhao.dto.ShopDTO;
import space.huyuhao.po.Shop;
import space.huyuhao.service.ShopService;
import space.huyuhao.vo.Result;

@RequestMapping("/shop")
@RestController
@Slf4j
public class ShopController {

    @Autowired
    private ShopService shopService;


    // 根据id查询店铺信息
    @GetMapping("/{id}")
    public Result getShopInfo(@PathVariable Long id) {
        log.info("查询店铺信息，id:{}", id);
        return shopService.queryShop(id);
    }

    // 根据id修改店铺信息
    @PostMapping("/update")
    public Result updateShop(@RequestBody Shop shop) {
        log.info("修改店铺信息:{}", shop);
        return shopService.updateShop(shop);
    }
}
