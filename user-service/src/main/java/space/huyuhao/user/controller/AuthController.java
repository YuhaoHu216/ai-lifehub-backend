package space.huyuhao.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.huyuhao.user.enums.OAuthType;
import space.huyuhao.user.factory.OAuthServiceFactory;
import space.huyuhao.user.po.OAuthUserInfo;
import space.huyuhao.user.service.AbstractOAuthService;
import space.huyuhao.user.vo.Result;

/**
 * @author hyh
 * @since 2025-11-23
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private OAuthServiceFactory factory;

    @GetMapping("/{type}/login")
    public Result login(
            @PathVariable String type,
            @RequestParam String code
    ) {
        AbstractOAuthService service
                = factory.getService(OAuthType.valueOf(type.toUpperCase()));

        return Result.success(service.login(code));
    }
}
