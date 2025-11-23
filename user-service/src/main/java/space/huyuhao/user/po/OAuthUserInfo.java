package space.huyuhao.user.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hyh
 * @since 2025-11-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthUserInfo {

    // 平台身份标识
    private String openId;      // 唯一ID（GitHub ID、微信 openid 等）
    private String unionId;     // 微信/QQ 的 unionid 可选

    // 基本用户信息
    private String nickname;
    private String avatar;
    private String gender;      // male / female / unknown
    private String email;

    // 登录相关
    private String source;      // QQ / WECHAT / GITHUB
    private String accessToken;
    private Integer expiresIn;

    // 可扩展字段
    private String city;
    private String province;
    private String country;
}
