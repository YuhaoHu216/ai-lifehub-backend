package space.huyuhao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import space.huyuhao.po.Shop;

@Mapper
public interface ShopMapper {
    /**
     * 查询店铺信息
     * @param id 店铺id
     * @return 店铺实体
     */
    @Select("select * from tb_shop where id = #{id}")
    Shop selectShopById(Long id);

    /**
     * 修改店铺信息
     * @param shop 要修改成的信息
     */
    @Update("update tb_shop set name=#{name},type_id=#{typeId} where id = #{id}")
    void update(Shop shop);
}
