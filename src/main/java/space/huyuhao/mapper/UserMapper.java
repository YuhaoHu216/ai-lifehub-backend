package space.huyuhao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import space.huyuhao.po.User;

@Mapper
public interface UserMapper {
    // 根据手机号查询用户
    @Select("select * from tb_user where phone = #{phone}")
    User getUser(String phone);

    // 保存用户
    @Insert("insert into tb_user(phone,nick_name) values (#{phone},#{nickName})")
    void insertUser(User user);
}
