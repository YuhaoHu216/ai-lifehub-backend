package space.huyuhao.user.factory;

import org.springframework.stereotype.Component;
import space.huyuhao.user.enums.OAuthType;
import space.huyuhao.user.service.AbstractOAuthService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hyh
 * @since 2025-11-23
 */
@Component
public class OAuthServiceFactory {

    private final Map<String, AbstractOAuthService> strategyMap;

    public OAuthServiceFactory(List<AbstractOAuthService> strategyList) {
        System.out.println("注入的策略列表: " + strategyList);
        strategyMap = new HashMap<>();
        strategyList.forEach(s -> strategyMap.put(s.getClass().getSimpleName(), s));
    }

    public AbstractOAuthService getService(OAuthType type) {
        return strategyMap.get(type.getServiceName());
    }
}

