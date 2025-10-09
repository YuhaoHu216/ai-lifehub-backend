package space.huyuhao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import space.huyuhao.dto.ShopDTO;
import space.huyuhao.po.Shop;

import java.util.List;

@Mapper
public interface ShopMapper {
    /**
     * 查询店铺信息
     * @param id 店铺id
     * @return 店铺实体
     */
    @Select("select * from tb_shop where id = #{id}")
    Shop selectShop(int id);
}
