package space.huyuhao.user.service.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import space.huyuhao.user.po.OAuthUserInfo;
import space.huyuhao.user.service.AbstractOAuthService;

@Slf4j
@Service
public class GithubOAuthService extends AbstractOAuthService {
    @Override
    public String getAuthorizeUrl() {
        return "";
    }

    @Override
    protected String getAccessToken(String code) {
        return "";
    }

    @Override
    protected OAuthUserInfo getUserInfo(String accessToken) {
        log.info("github登录");
        return null;
    }
}
