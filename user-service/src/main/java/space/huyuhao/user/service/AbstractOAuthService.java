package space.huyuhao.user.service;

import space.huyuhao.user.po.OAuthUserInfo;

/**
 *
 */
public abstract class AbstractOAuthService {

    // 1. 构建授权 URL
    public abstract String getAuthorizeUrl();

    // 2. 通过 code 换 token
    protected abstract String getAccessToken(String code);

    // 3. 获取用户信息
    protected abstract OAuthUserInfo getUserInfo(String accessToken);

    // 这是模板方法（统一流程）
    public OAuthUserInfo login(String code) {
        String accessToken = getAccessToken(code);
        return getUserInfo(accessToken);
    }
}
