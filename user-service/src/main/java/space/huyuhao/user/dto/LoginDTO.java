package space.huyuhao.user.dto;

import lombok.Data;

/**
 * 用户登录的信息
 */
@Data
public class LoginDTO {
    private String phone;
    private String password;
    private String code;
}
